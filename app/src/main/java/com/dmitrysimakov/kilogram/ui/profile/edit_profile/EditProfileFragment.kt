package com.dmitrysimakov.kilogram.ui.profile.edit_profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentEditProfileBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.util.dispatchGetImageContentIntent
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setNewValue
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val RC_TAKE_PHOTO = 1

class EditProfileFragment : Fragment() {
    
    private val vm: EditProfileViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private lateinit var binding: FragmentEditProfileBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditProfileBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.vm = vm
        binding.photo.setOnClickListener { dispatchGetImageContentIntent(RC_TAKE_PHOTO) }
        binding.gender.setOnCheckedChangeListener { _, id ->
            val newGender = when (id) {
                R.id.male -> getString(R.string.male)
                R.id.female -> getString(R.string.female)
                else -> ""
            }
            vm.gender.setNewValue(newGender)
        }
        binding.saveBtn.setOnClickListener {
            vm.saveChanges()
            popBackStack()
        }
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        val user = sharedVM.user.value!!
        vm.setUser(user)
        when (user.gender) {
            getString(R.string.male) -> binding.gender.check(R.id.male)
            getString(R.string.female) -> binding.gender.check(R.id.female)
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val uri = data?.data
        if (requestCode == RC_TAKE_PHOTO && resultCode == Activity.RESULT_OK && uri != null) {
            vm.saveImage(uri)
        }
    }
}
