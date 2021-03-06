package com.dmitrysimakov.kilogram.ui.home.photos.photo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.binding.formatDate
import com.dmitrysimakov.kilogram.databinding.FragmentPhotoBinding
import com.dmitrysimakov.kilogram.util.live_data.EventObserver
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.setTitle
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoFragment : Fragment() {
    
    private val args: PhotoFragmentArgs by navArgs()
    
    private val vm: PhotoViewModel by viewModel()
    
    private lateinit var binding: FragmentPhotoBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = FragmentPhotoBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        vm.photoUri.setNewValue(args.uri)
        vm.photo.observe(viewLifecycleOwner) { photo ->
            setTitle(formatDate(photo.dateTime, "dd MMMM yyyy г."))
        }
        vm.photoDeletedEvent.observe(viewLifecycleOwner, EventObserver { popBackStack() })
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.photo, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.delete_photo -> {
            vm.deletePhoto()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
