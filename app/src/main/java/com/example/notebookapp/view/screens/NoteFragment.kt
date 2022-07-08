package com.example.notebookapp.view.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.notebook.model.Note
import com.example.notebookapp.R
import com.example.notebookapp.databinding.FragmentNoteBinding
import com.example.notebookapp.view.contract.CustomAction
import com.example.notebookapp.view.contract.HasCustomAction
import com.example.notebookapp.view.contract.HasCustomTitle
import com.example.notebookapp.view.contract.contract
import com.example.notebookapp.view.factory

class NoteFragment : Fragment(), HasCustomTitle, HasCustomAction {

    private lateinit var binding: FragmentNoteBinding
    private val viewModel: NoteViewModel by viewModels { factory() }
    // lateinit var note: Note // version 1
    // val note: Note // version 2
    //     get() = arguments?.getParcelable<Note>(KEY_NOTE) as Note

    private var note = Note()
    private var idNote: Int = KEY_CREATING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // version 1
        // note = savedInstanceState?.getParcelable<Note>(KEY_NOTE) ?:
        //         arguments?.getParcelable<Note>(KEY_NOTE) ?:
        //         throw IllegalArgumentException("You need to specify note to launch this fragment")

        idNote = savedInstanceState?.getInt(KEY_NOTE_ID) ?:
            arguments?.getInt(KEY_NOTE_ID) ?:
            KEY_CREATING // -1 means creating new Note

        if (idNote != KEY_CREATING) {
            // note = contract().notesService.notes[idNote] // todo
            viewModel.loadNote(requireArguments().getInt(KEY_NOTE_ID))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)

        viewModel.note.observe(viewLifecycleOwner, Observer {
            note = it
            binding.noteTitle.setText(it.title)
            binding.noteContent.setText(it.content)
        })

        return binding.root
    }

   //  override fun onSaveInstanceState(outState: Bundle) {
   //      super.onSaveInstanceState(outState)
   //      outState.putInt(KEY_NOTE_ID, idNote)
   //  }

    // кастомизация верхнего меню
    override fun getCustomAction(): CustomAction {
        return CustomAction(
            iconRes = R.drawable.ic_done,
            textRes = R.string.save,
            onCustomAction = { onSavePressed() }
        )
    }

    override fun getTitleRes(): Int {
        return if (note.title.isBlank()){
            R.string.newNote
        } else R.string.note
    }

    private fun onSavePressed(){
        if (checkInput().isEmpty()){
            // todo
        } else {

        }
        note.title = binding.noteTitle.text.toString()
        note.content = binding.noteContent.text.toString()


        if (note.id == KEY_CREATING) {
            viewModel.addNote(note)
        } else {
            viewModel.editNote(note)
        }

        contract().goBack()
    }

    private fun checkInput(): String {
        var msgError = ""

        if (binding.noteTitle.text.isBlank()){
            msgError = "Input title"
        }

        return msgError
    }

    companion object{
        private const val KEY_NOTE_ID = "KEY_NOTE_ID"
        private const val KEY_CREATING = -1

        public fun newInstance(noteId: Int): NoteFragment{
            val fragment = NoteFragment()
            fragment.arguments = bundleOf(KEY_NOTE_ID to noteId)
            return fragment
        }
    }

}