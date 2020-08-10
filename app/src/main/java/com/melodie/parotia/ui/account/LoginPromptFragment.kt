package com.melodie.parotia.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.melodie.parotia.databinding.FragmentLoginPromptBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginPromptFragment : Fragment() {
    companion object {
        const val LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL"
    }

    private lateinit var binding: FragmentLoginPromptBinding
    private lateinit var savedStateHandle: SavedStateHandle

    private val accountViewModel: AccountViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginPromptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle.set(LOGIN_SUCCESSFUL, false)

        binding.btnLogin.setOnClickListener {
            accountViewModel.authorize(requireContext())
        }
        accountViewModel.loggedIn.observe(
            viewLifecycleOwner,
            Observer {
                if (it) {
                    savedStateHandle.set(LOGIN_SUCCESSFUL, true)
                    findNavController().popBackStack()
                } else {
                    // show error message
                }
            }
        )
    }
}
