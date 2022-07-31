package com.nloyko.interviewtesttask.displaypoints

import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.nloyko.interviewtesttask.R
import com.nloyko.interviewtesttask.databinding.FragmentDisplayPointsBinding
import com.nloyko.interviewtesttask.util.EventObserver
import com.nloyko.interviewtesttask.util.saveToStorage
import com.nloyko.interviewtesttask.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisplayPointsFragment : Fragment() {

    private val viewModel by viewModels<DisplayPointsViewModel>()
    private lateinit var binding: FragmentDisplayPointsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayPointsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.save_to_file, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.save_to_file -> {
                        saveToFileMenuClick()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.onSaveToFile.observe(viewLifecycleOwner, EventObserver {
            checkPermissions(
                onGranted = {
                    val bitmap = binding.chart.drawToBitmap()
                    bitmap.saveToStorage(requireContext()) {
                        showSnackbar(getString(R.string.successfully_saved))
                    }
                },
                onDenied = { showSnackbar(getString(R.string.permissions_denied)) }
            )
        })
    }

    private fun saveToFileMenuClick() {
        viewModel.saveToFile()
    }

    private fun checkPermissions(onGranted: () -> Unit, onDenied: () -> Unit) {
        permissionsBuilder(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).build().send {
            if (it.allGranted()) onGranted() else onDenied()
        }
    }
}