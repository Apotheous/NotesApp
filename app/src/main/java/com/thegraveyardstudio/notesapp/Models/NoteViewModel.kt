package com.thegraveyardstudio.notesapp.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.thegraveyardstudio.notesapp.Database.NoteDatabase
import com.thegraveyardstudio.notesapp.Database.NotesRepository

class NoteViewModel(application: Application) :AndroidViewModel(application) {

    private val repository : NotesRepository
    val allnotes : LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NotesRepository(dao)
        allnotes = repository.allNotes
    }

}