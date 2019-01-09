package ru.sberbank.lesson8.task.contentproviders.adapters;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class NoteEntry {
    @Ignore
    public static final String TABLE_NAME = "notes";

    @Ignore
    public static final String COLUMN_ID = "id";

    @Ignore
    public static final String COLUMN_CONTENT = "content";

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

    @Ignore
    public NoteEntry() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public static NoteEntry fromContentValues(ContentValues values) {
        final NoteEntry noteEntry = new NoteEntry();
        if (values.containsKey(COLUMN_ID)) {
            noteEntry.id = values.getAsLong(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_CONTENT)) {
            noteEntry.content = values.getAsString(COLUMN_CONTENT);
        }
        return noteEntry;
    }
}
