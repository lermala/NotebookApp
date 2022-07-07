package com.example.notebookapp.model

import com.example.notebook.model.Note
import com.example.notebookapp.model.note_style.NoteStylePantone
import com.github.javafaker.Faker
import kotlin.random.Random

class NotesService {

    private val pantones = PantoneService().pantones.shuffled()

    val notes: ArrayList<Note> = (1..20).map { Note(
        title = Faker.instance().lorem().sentence(),
        content = Faker.instance().lorem().paragraphs(Random.nextInt(1, 11)).joinToString("\n\n"),
        style = NoteStylePantone(pantoneColor = pantones[it % pantones.size])
    )} as ArrayList<Note>

}

