package com.example.notebookapp.model

import com.example.notebook.model.Note
import com.example.notebookapp.model.note_style.NoteStylePantone
import com.github.javafaker.Faker
import kotlin.random.Random

class NotesService {

    private val pantones = PantoneService().pantones

    val notes: ArrayList<Note> = (1..40).map { Note(
        title = Faker.instance().lorem().sentence(),
        content = Faker.instance().lorem().paragraph(10),
        style = NoteStylePantone(pantoneColor = pantones[Random.nextInt(pantones.size)])
    )} as ArrayList<Note>

}