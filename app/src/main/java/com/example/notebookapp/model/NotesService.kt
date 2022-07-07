package com.example.notebookapp.model

import com.example.notebook.model.Note
import com.example.notebookapp.model.note_style.NoteStylePantone
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
            title = Faker.instance().lorem().sentence(),
            content = Faker.instance().lorem().paragraphs(Random.nextInt(1, 11)).joinToString("\n\n"),
            style = NoteStylePantone(pantoneColor = pantones[it % pantones.size])
        )} as ArrayList<Note>
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