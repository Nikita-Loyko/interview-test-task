package com.nloyko.interviewtesttask.util

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbar(message: String) =
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()