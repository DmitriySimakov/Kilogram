package com.dmitrysimakov.kilogram.ui.profile

import android.app.Activity
import android.content.SharedPreferences
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
import com.dmitrysimakov.kilogram.ui.common.person_page.subscriptions_tab.SUBSCRIBERS_PAGE
import com.dmitrysimakov.kilogram.ui.common.person_page.subscriptions_tab.SUBSCRIPTIONS_PAGE
import com.dmitrysimakov.kilogram.ui.profile.ProfileFragmentDirections.Companion.toChatsFragment
import com.dmitrysimakov.kilogram.ui.profile.ProfileFragmentDirections.Companion.toEditProfileFragment
import com.dmitrysimakov.kilogram.ui.profile.ProfileFragmentDirections.Companion.toSubscriptionsTabFragment
import com.dmitrysimakov.kilogram.util.PreferencesKeys
import com.dmitrysimakov.kilogram.util.navigate
import com.firebase.ui.auth.AuthUI
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : Fragment() {
    
    private lateinit var binding: FragmentProfileBinding
    
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private val preferences: SharedPreferences by inject()
    
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
                R.id.chats -> { navigate(toChatsFragment()) }
                R.id.editProfile -> { navigate(toEditProfileFragment()) }
                R.id.signInSignOut -> signInSignOut()
                R.id.darkTheme -> switchTheme()
                R.id.share -> share()
            }
            true
        }
        
        sharedVM.user.observe(viewLifecycleOwner) { user ->
            val chatsItem = binding.navView.menu.findItem(R.id.chats)
            val editProfileItem = binding.navView.menu.findItem(R.id.editProfile)
            val signInSignOutItem = binding.navView.menu.findItem(R.id.signInSignOut)
    
            chatsItem.isVisible = user != null
            editProfileItem.isVisible = user != null
            signInSignOutItem.title = if (user != null) getString(R.string.sign_out) else getString(R.string.sign_in)
            
            if (user != null) {
                binding.subscribers.setOnClickListener {
                    navigate(toSubscriptionsTabFragment(SUBSCRIBERS_PAGE, user.id))
                }
                binding.subscriptions.setOnClickListener {
                    navigate(toSubscriptionsTabFragment(SUBSCRIPTIONS_PAGE, user.id))
                }
            }
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
            AuthUI.getInstance().signOut(requireContext())
            sharedVM.signOut()
        }
    }
    
    private fun switchTheme() {
        val isDarkTheme = preferences.getBoolean(PreferencesKeys.IS_DARK_THEME, false)
        preferences.edit().putBoolean(PreferencesKeys.IS_DARK_THEME, !isDarkTheme).apply()
        activity?.recreate()
    }
    
    private fun share() = startActivity(ShareCompat.IntentBuilder.from(activity as Activity)
            .setText(getString(R.string.share_text))
            .setType("text/plain")
            .intent
    )
}