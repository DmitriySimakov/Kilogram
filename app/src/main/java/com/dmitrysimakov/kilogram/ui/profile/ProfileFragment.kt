package com.dmitrysimakov.kilogram.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dmitrysimakov.kilogram.BuildConfig
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentProfileBinding
import com.dmitrysimakov.kilogram.ui.RC_SIGN_IN
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.profile.ProfileFragmentDirections.Companion.toEditProfileFragment
import com.dmitrysimakov.kilogram.ui.profile.ProfileFragmentDirections.Companion.toSubscriptionsTabFragment
import com.dmitrysimakov.kilogram.ui.profile.subscriptions.FOLLOWED_PAGE
import com.dmitrysimakov.kilogram.ui.profile.subscriptions.FOLLOWERS_PAGE
import com.dmitrysimakov.kilogram.util.navigate
import com.firebase.ui.auth.AuthUI
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
        
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.signInSignOut -> signInSignOut()
                R.id.editProfile -> { navigate(toEditProfileFragment()) }
                R.id.share -> share()
            }
            true
        }
        
        binding.followers.setOnClickListener { navigate(toSubscriptionsTabFragment(FOLLOWERS_PAGE)) }
        binding.followed.setOnClickListener { navigate(toSubscriptionsTabFragment(FOLLOWED_PAGE)) }
        
        sharedVM.user.observe(viewLifecycleOwner) { user ->
            val signInSignOutItem = binding.navView.menu.findItem(R.id.signInSignOut)
            val editProfileItem = binding.navView.menu.findItem(R.id.editProfile)
            
            signInSignOutItem.title = if (user != null) getString(R.string.sign_out) else getString(R.string.sign_in)
            editProfileItem.isVisible = user != null
        }
    }
    
    private fun signInSignOut() {
        if (sharedVM.user.value == null) {
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
        } else {
            AuthUI.getInstance().signOut(context!!)
            sharedVM.signOut()
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