package ru.sberbank.lesson8.task.contentproviders.dao;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry;

public class NoteViewModel extends AndroidViewModel {

    private NoteService noteService;
    private LiveData<List<NoteEntry>> notes;

    public NoteViewModel(Application application) {
        super(application);
        noteService = new NoteService(application);
        notes = noteService.getAll();
    }

    public LiveData<List<NoteEntry>> getAllNotes() {
        return notes;
    }

    public void create(String text) {
        noteService.save(new NoteEntry(text));
    }

    public void update(long id, String text) {
        noteService.save(new NoteEntry(id, text));
    }
}