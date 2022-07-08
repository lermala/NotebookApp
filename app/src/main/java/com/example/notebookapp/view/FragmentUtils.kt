package com.example.notebookapp.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notebookapp.App
import com.example.notebookapp.view.screens.AllNotesViewModel
import com.example.notebookapp.view.screens.NoteFragment
import com.example.notebookapp.view.screens.NoteViewModel

class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            AllNotesViewModel::class.java -> {
                AllNotesViewModel(app.notesService)
            }
            NoteViewModel::class.java -> {
                NoteViewModel(app.notesService)
            }
            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)