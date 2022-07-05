package com.example.notebookapp.view.contract

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.notebook.model.Note
import com.example.notebookapp.model.NotesService

typealias ResultListener<T> = (T) -> Unit

fun Fragment.contract(): AppContract {
    return requireActivity() as AppContract
}

interface AppContract {

    val notesService: NotesService // getting data

    fun goBack()

    fun showAllNotes()

    fun showEditingNote(noteId: Int)

    fun showCreatingNote()

    fun goToStartFragment()

    // для передачи информации между фрагментами
    fun <T: Parcelable>publishResult(result: T)
    fun <T: Parcelable>listenResult(clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)
}