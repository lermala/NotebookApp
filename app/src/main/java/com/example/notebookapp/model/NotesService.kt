package com.example.notebookapp.model

import com.example.notebook.model.Note
import com.example.notebookapp.model.note_style.NoteStylePantone
import com.example.notebookapp.view.exceptions.NoteNotFoundException
import com.github.javafaker.Faker
import kotlin.random.Random

typealias NotesListener = (notes: List<Note>) -> Unit // слушатель для observer

class NotesService {

    var notes = mutableListOf<Note>()
    private val pantones = PantoneService().pantones
    private val listeners = mutableListOf<NotesListener>()

    init {
        // делаем цвета не совсем рандомными:
        pantones.shuffled()

        notes = (1..20).map { Note(
            id = it,
            title = Faker.instance().lorem().sentence(),
            content = Faker.instance().lorem().paragraphs(Random.nextInt(1, 11)).joinToString("\n\n"),
            style = NoteStylePantone(pantoneColor = pantones[it % pantones.size])
        )} as ArrayList<Note>
    }

    fun getNoteById(noteId: Int): Note {
        val note = notes.firstOrNull { it.id == noteId } ?: throw NoteNotFoundException()
        return note
    }

    fun addNote(note: Note) {
        note.id = notes.last().id + 1 // todo
        notes.add(note)
        notifyChanges()
    }

    fun deleteNote(note: Note) {
        val indexToDelete = notes.indexOfFirst { it.id == note.id }
        if (indexToDelete != -1){
            notes.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun editNote(note: Note) {
        val indexToEdit = notes.indexOfFirst { it.id == note.id }
        if (indexToEdit != -1){
            notes[indexToEdit] = note
            notifyChanges()
        }
    }

    fun addListener(listener: NotesListener) {
        listeners.add(listener)
        listener.invoke(notes)
    }

    fun removeListener(listener: NotesListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(notes) }
    }
}