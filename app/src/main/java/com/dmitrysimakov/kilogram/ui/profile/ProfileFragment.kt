package com.dmitrysimakov.kilogram.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import com.dmitrysimakov.kilogram.BuildConfig
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentProfileBinding
import com.dmitrysimakov.kilogram.ui.RC_SIGN_IN
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : Fragment() {
    
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private lateinit var binding: FragmentProfileBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        binding.vm = sharedVM
        binding.lifecycleOwner = this
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        binding.authBtn.setOnClickListener {
            if (sharedVM.user.value == null) signIn() else signOut()
        }
        
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.share -> share()
            }
            true
        }
        
        activity?.fab?.hide()
    }
    
    private fun signIn() {
        val providers = listOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build()
        )
        activity?.startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setAvailableProviders(providers)
                .setLogo(R.mipmap.ic_launcher_round)
                .build(), RC_SIGN_IN)
    }
    
    private fun signOut() {
        AuthUI.getInstance().signOut(context!!)
    }
    
    private fun share() {
        val shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setText("Kilogram - крутой фитнес трекер. Переходи по ссылке и тренируйся вместе со мной.")
                .setType("text/plain")
                .intent
        startActivity(shareIntent)
    }
}