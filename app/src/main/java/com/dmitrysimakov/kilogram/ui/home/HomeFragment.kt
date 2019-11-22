package com.dmitrysimakov.kilogram.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.data.local.entity.Photo
import com.dmitrysimakov.kilogram.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.util.*

private const val RC_TAKE_PHOTO = 1

class HomeFragment : Fragment() {
    
    private val vm: HomeViewModel by viewModel()
    
    private lateinit var binding: FragmentHomeBinding
    
    private val photosAdapter by lazy { PhotosAdapter() }
    
    private var lastPhotoDate: Long? = null
    private var lastPhotoUri: String? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.vm = vm
        binding.photosRV.adapter = photosAdapter
        binding.lifecycleOwner = this
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.fab?.hide()
        
        binding.startTrainingButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.toCreateTrainingFragment())
        }
    
        binding.addPhotoButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
        
        vm.recentPhotos.observe(viewLifecycleOwner, Observer { photosAdapter.submitList(it) })
    }
    
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also { file ->
                val photoURI: Uri = FileProvider.getUriForFile(
                        context!!,
                        "com.dmitrysimakov.kilogram",
                        file
                )
                lastPhotoUri = photoURI.toString()
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, RC_TAKE_PHOTO)
            }
        }
    }
    
    @Throws(IOException::class)
    private fun createImageFile(): File {
        lastPhotoDate = Date().time
        val filename = "Kilogram_${lastPhotoDate}"
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(filename, ".jpg", storageDir)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_TAKE_PHOTO && resultCode == RESULT_OK) {
            vm.addPhoto(Photo(lastPhotoDate!!, lastPhotoUri!!))
        }
    }
}
