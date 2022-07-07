package com.example.notebookapp.view.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notebook.model.Note
import com.example.notebookapp.databinding.FragmentAllNotesBinding
import com.example.notebookapp.model.NotesListener
import com.example.notebookapp.view.adapters.NoteActionListener
import com.example.notebookapp.view.adapters.NoteAdapter
import com.example.notebookapp.view.contract.contract

class AllNotesFragment : Fragment() {

    lateinit var binding: FragmentAllNotesBinding
    lateinit var noteAdapter: NoteAdapter

    private lateinit var notes: MutableList<Note>

    private val usersListener: NotesListener = {
        noteAdapter.notes = it
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAllNotesBinding.inflate(inflater, container, false)

        notes = contract().notesService.notes

        noteAdapter = NoteAdapter(object : NoteActionListener{
            override fun onNoteEdit(note: Note) {
                contract().showCreatingNote()
            }

            override fun onNoteDelete(note: Note) {
                TODO("Not yet implemented")
            }
        })
        noteAdapter.notes = notes

        with (binding.rvNotes) {
            val layoutManager = LinearLayoutManager(requireContext()) // vertical list
            this.layoutManager = layoutManager
            this.adapter = noteAdapter



            // setOnItemClickListener { _, _, position, _ ->
            //     // val currentNote = notes[position]
            //     contract().showEditingNote(position)
            // }
        }

        binding.fabAdd.setOnClickListener { onAddPressed() }

        return binding.root
    }

    // fun val notesListener:

    fun onBackPressed(){
        contract().goBack()
    }

    fun onItemListPressed(){
        // navigator().showCreatingNote()
    }

    private fun onAddPressed(){
        contract().showCreatingNote()
    }

    companion object{
        private const val KEY_CREATING = -1
    }

}