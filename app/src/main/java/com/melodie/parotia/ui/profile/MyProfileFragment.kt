package com.melodie.parotia.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.melodie.parotia.R
import com.melodie.parotia.result.Result
import com.melodie.parotia.ui.account.AccountViewModel
import com.melodie.parotia.ui.account.LoginPromptFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : BaseProfileFragment() {

    private val accountViewModel: AccountViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navController = findNavController()
        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LoginPromptFragment.LOGIN_SUCCESSFUL)
            .observe(
                currentBackStackEntry,
                Observer {
                    if (!it) {
//                    val startDestination = navController.graph.startDestination
//                    val navOptions = NavOptions.Builder()
//                        .setPopUpTo(startDestination, true)
//                        .build()
//                    navController.navigate(startDestination, null, navOptions)
                        navController.popBackStack()
                    }
                }
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountViewModel.loggedIn.observe(
            viewLifecycleOwner,
            {
                // If not logged in, jump to LoginPromptFragment
                if (!it) {
                    findNavController().navigate(R.id.navigation_login_prompt)
                }
            }
        )
        accountViewModel.me.observe(
            viewLifecycleOwner,
            { result ->
                when (result) {
                    Result.Loading -> Log.d("MyProfileFragment", "Loading user data")
                    is Result.Error ->
                        Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show()
                    is Result.Success -> bindUser(result.data)
                }
                // TODO("logout, user data cleared")
                // TODO("maybe need to handle user request failure here")
            }
        )
    }
}
