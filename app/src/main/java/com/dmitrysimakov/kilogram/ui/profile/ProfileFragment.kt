package com.dmitrysimakov.kilogram.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
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
        
        binding.authBtn.setOnClickListener { onAuthButtonClicked() }
        
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.share -> { share() }
            }
            true
        }
        
        activity?.fab?.hide()
    }
    
    private fun onAuthButtonClicked() {
        if (sharedVM.user.value == null) {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(listOf(
                            AuthUI.IdpConfig.EmailBuilder().build(),
                            AuthUI.IdpConfig.GoogleBuilder().build(),
                            AuthUI.IdpConfig.FacebookBuilder().build()))
                    .setLogo(R.mipmap.ic_launcher_round)
                    .build(), RC_SIGN_IN)
        } else {
            context?.let { AuthUI.getInstance().signOut(it) }
        }
    }
    
    private fun share() {
        val shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setText("Kilogram - крутой фитнес трекер. Переходи по ссылке и тренируйся вместе со мной.")
                .setType("text/plain")
                .intent
        startActivity(shareIntent)
    }
}