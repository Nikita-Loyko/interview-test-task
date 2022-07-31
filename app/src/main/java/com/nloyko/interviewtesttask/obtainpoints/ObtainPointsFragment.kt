package com.nloyko.interviewtesttask.obtainpoints

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nloyko.interviewtesttask.R
import com.nloyko.interviewtesttask.databinding.FragmentObtainPointsBinding
import com.nloyko.interviewtesttask.util.EventObserver
import com.nloyko.interviewtesttask.util.hideKeyboard
import com.nloyko.interviewtesttask.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ObtainPointsFragment : Fragment() {

    private val viewModel by viewModels<ObtainPointsViewModel>()
    private lateinit var binding: FragmentObtainPointsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentObtainPointsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.progressBar.hide()
        binding.pointsCountTextField.setOnEditorActionListener(::onEditorAction)

        return binding.root
    }

    private fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            viewModel.obtainPoints()
        }
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.inputValidation.observe(viewLifecycleOwner) {
            binding.pointsCountLayout.error =
                if (it) null else getString(R.string.number_validation_error)

            binding.goButton.isEnabled = it
        }

        viewModel.dataLoading.observe(viewLifecycleOwner) {
            if (it) {
                context?.hideKeyboard(requireView())
                binding.pointsCountTextField.clearFocus()
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }

        viewModel.pointsLoadResult.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                findNavController().navigate(R.id.action_requestPointsFragment_to_displayPointsFragment)
            } else {
                showSnackbar(getString(R.string.server_error))
            }
        })
    }
}