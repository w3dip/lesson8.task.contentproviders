package ru.sberbank.lesson8.task.contentproviders.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry;
import ru.sberbank.lesson8.task.contentproviders.dao.NoteDao;
import ru.sberbank.lesson8.task.contentproviders.dao.NoteDatabase;

import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry.TABLE_NAME;

public class NoteContentProvider extends ContentProvider {
    public static final String AUTHORITY = "ru.sberbank.lesson8.task.contentproviders.provider";
    private static final int CODE_NOTES_DIR = 1;
    private static final int CODE_NOTES_ITEM = 2;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, TABLE_NAME, CODE_NOTES_DIR);
        MATCHER.addURI(AUTHORITY, TABLE_NAME + "/*", CODE_NOTES_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_NOTES_DIR || code == CODE_NOTES_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            NoteDao noteDao = NoteDatabase.getDatabase(context).noteDao();
            final Cursor cursor;
            if (code == CODE_NOTES_DIR) {
                cursor = noteDao.selectAll();
            } else {
                cursor = noteDao.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_NOTES_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_NAME;
            case CODE_NOTES_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_NOTES_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = NoteDatabase.getDatabase(context).noteDao()
                        .insert(NoteEntry.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_NOTES_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
            @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_NOTES_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_NOTES_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = NoteDatabase.getDatabase(context).noteDao()
                        .deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
            @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_NOTES_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_NOTES_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final NoteEntry noteEntry = NoteEntry.fromContentValues(values);
                noteEntry.setId(ContentUris.parseId(uri));
                final int count = NoteDatabase.getDatabase(context).noteDao()
                        .update(noteEntry);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
