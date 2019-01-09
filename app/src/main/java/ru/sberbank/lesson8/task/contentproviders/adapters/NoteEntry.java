package ru.sberbank.lesson8.task.contentproviders.adapters;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "notes")
public class NoteEntry {
    @Ignore
    public static final String NOTE_ID = "ru.sberbank.lesson8.task.contentproviders.NOTE_ID";

    @Ignore
    public static final String NOTE_CONTENT = "ru.sberbank.lesson8.task.contentproviders.NOTE_CONTENT";

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @NonNull
    private String content;

    public NoteEntry(Long id, @NonNull String content) {
        this.id = id;
        this.content = content;
    }

    @Ignore
    public NoteEntry(@NonNull String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public String getContent() {
        return content;
    }
}
