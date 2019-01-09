package ru.sberbank.lesson8.task.contentproviders.dao;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry;

public class NoteService {

    private NoteDao noteDao;
    private LiveData<List<NoteEntry>> notes;

    public NoteService(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        noteDao = db.noteDao();
        notes = noteDao.getAll();
    }

    public LiveData<List<NoteEntry>> getAll() {
        return notes;
    }

    public void save(NoteEntry note) {
        new saveAsyncTask(noteDao).execute(note);
    }

    private static class saveAsyncTask extends AsyncTask<NoteEntry, Void, Void> {

        private NoteDao noteDao;

        saveAsyncTask(NoteDao dao) {
            noteDao = dao;
        }

        @Override
        protected Void doInBackground(final NoteEntry... params) {
            NoteEntry note = params[0];
            if (note != null) {
                if (note.getId() == null) {
                    noteDao.insert(note);
                } else {
                    noteDao.update(note);
                }
            }
            return null;
        }
    }
}
