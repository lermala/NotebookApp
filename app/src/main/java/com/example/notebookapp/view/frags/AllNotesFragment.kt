package com.example.notebookapp.view.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.notebook.model.Note
import com.example.notebookapp.databinding.FragmentAllNotesBinding
import com.example.notebookapp.view.adapters.NoteAdapter
import com.example.notebookapp.view.contract.contract
import com.github.javafaker.Faker

class AllNotesFragment : Fragment() {

    lateinit var binding: FragmentAllNotesBinding

    private lateinit var notes: ArrayList<Note>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        contract().listenResult(Note::class.java, viewLifecycleOwner){
            if (it != null) { // после работы NoteFragment
                val msg = "Заметка ${it.title} успешно добавлена"
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                notes.add(it)
            } else {
                // nothing
            }
        }

        binding = FragmentAllNotesBinding.inflate(inflater, container, false)

        notes = contract().notesService.notes

        with(binding.rvNotes){
            val adapter = NoteAdapter(notes)
            this.adapter = adapter
            setOnItemClickListener { _, _, position, _ ->
                val currentNote = notes[position]
                contract().showCreatingNote(currentNote)
            }
        }

        binding.fabAdd.setOnClickListener { onAddPressed() }

        // binding.rvNotes.onItemClickListener = AdapterView.OnItemClickListener {
        //         parent, view, position, id -> contract().showCreatingNote(notes[position])
        // }

        return binding.root
    }

    fun onBackPressed(){
        contract().goBack()
    }

    fun onItemListPressed(){
        // navigator().showCreatingNote()
    }

    private fun onAddPressed(){
        contract().showCreatingNote(Note())
    }

}