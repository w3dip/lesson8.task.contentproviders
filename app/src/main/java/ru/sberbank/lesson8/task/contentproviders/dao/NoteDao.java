package ru.sberbank.lesson8.task.contentproviders.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry;

import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry.COLUMN_ID;
import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry.TABLE_NAME;

@Dao
public interface NoteDao {
    @Query("SELECT * from " + TABLE_NAME)
    LiveData<List<NoteEntry>> getAll();

    @Insert
    long insert(NoteEntry note);

    @Update
    int update(NoteEntry note);

    @Query("SELECT * FROM " + TABLE_NAME)
    Cursor selectAll();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = :id")
    Cursor selectById(long id);

    @Query("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = :id")
    int deleteById(long id);

    @Query("SELECT COUNT(*) FROM " + TABLE_NAME)
    int count();
}
