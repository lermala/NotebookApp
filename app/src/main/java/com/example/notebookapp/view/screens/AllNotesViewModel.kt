package com.example.notebookapp.view.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notebook.model.Note
import com.example.notebookapp.model.NotesListener
import com.example.notebookapp.model.NotesService

class AllNotesViewModel(
    private val notesService: NotesService
) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    private val listener: NotesListener = { // слушаем данные которые пришли и передаем livedata
        _notes.value = it
    }

    init {
        loadNotes()
    }

    private fun loadNotes() {
        notesService.addListener(listener)
    }

    fun addNote(note: Note) {
        notesService.addNote(note)
    }

    fun deleteNote(note: Note) {
        notesService.deleteNote(note)
    }

    fun editNote(note: Note) {
        notesService.editNote(note)
    }

    override fun onCleared() {
        super.onCleared()
        notesService.removeListener(listener) // отписываемся от сервиса
    }
}