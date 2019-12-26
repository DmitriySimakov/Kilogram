package com.dmitrysimakov.kilogram.ui.home.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.home.photos.PhotosFragmentDirections.Companion.toPhotoFragment
import com.dmitrysimakov.kilogram.util.navigate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_exercises.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosFragment : Fragment() {
    
    private val vm: PhotosViewModel by viewModel()
    
    private val adapter by lazy { PhotosAdapter { navigate(toPhotoFragment(it.uri)) } }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        
        vm.photos.observe(viewLifecycleOwner) { adapter.submitList(it) }
        
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            //TODO
        }
    }
}
