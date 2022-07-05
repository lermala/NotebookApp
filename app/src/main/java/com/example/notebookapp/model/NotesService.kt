package com.example.notebookapp.model

import com.example.notebook.model.Note
import com.github.javafaker.Faker

class NotesService {

    val notes: ArrayList<Note> = (1..2).map { Note(
        title = Faker.instance().lorem().sentence(),
        content = Faker.instance().lorem().paragraph(10)
    )} as ArrayList<Note>

}