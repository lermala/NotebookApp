package com.example.notebookapp.view.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notebook.model.Note
import com.example.notebookapp.model.NotesService
import com.example.notebookapp.view.exceptions.NoteNotFoundException

class NoteViewModel(
    private val notesService: NotesService
) : ViewModel() {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

    fun loadNote(noteId: Int) {
        if (_note.value != null) return // checks
        try {
            _note.value = notesService.getNoteById(noteId)
        } catch (e: NoteNotFoundException) {
            e.printStackTrace()
        }
    }

    fun addNote(note: Note) {
        notesService.addNote(note)
    }

    fun editNote(note: Note) {
        notesService.editNote(note)
    }

    fun deleteNote() {
        note.value?.let { notesService.deleteNote(it) }
    }
}