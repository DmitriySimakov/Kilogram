package com.dmitrysimakov.kilogram.ui.home.photos.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.databinding.FragmentPhotoBinding
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.setTitle
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.format.DateTimeFormatter

class PhotoFragment : Fragment() {
    
    private val args: PhotoFragmentArgs by navArgs()
    
    private val vm: PhotoViewModel by viewModel()
    
    private lateinit var binding: FragmentPhotoBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        vm.photoUri.setNewValue(args.uri)
        
        vm.photo.observe(viewLifecycleOwner) { photo ->
            setTitle(photo.dateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy г.")))
        }
    }
}
