package com.example.notebookapp

import android.app.Application
import com.example.notebookapp.model.NotesService

class App : Application() {

    val notesService = NotesService()

}