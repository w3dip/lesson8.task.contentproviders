package ru.sberbank.lesson8.task.contentproviders;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ru.sberbank.lesson8.task.contentproviders.adapters.NoteAdapter;
import ru.sberbank.lesson8.task.contentproviders.dao.NoteViewModel;

import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteAdapter.noteColor;
import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteAdapter.textColor;
import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteAdapter.textSize;
import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry.NOTE_CONTENT;
import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry.NOTE_ID;
import static ru.sberbank.lesson8.task.contentproviders.utils.Helpers.getRealProgress;
import static ru.sberbank.lesson8.task.contentproviders.utils.Helpers.getSettingName;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_NOTE_ACTIVITY_REQUEST_CODE = 2;

    private SharedPreferences settings;

    private NoteAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.createNoteBtn)
                .setOnClickListener((v) ->
                        startActivityForResult(new Intent(MainActivity.this, CreateNoteActivity.class),
                                NEW_NOTE_ACTIVITY_REQUEST_CODE)
                );
        findViewById(R.id.settingsBtn).setOnClickListener((v) -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        recyclerView = findViewById(R.id.notes);
        recyclerView.setHasFixedSize(true);

        adapter = new NoteAdapter(this);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        settings = getApplicationContext().getSharedPreferences(getSettingName(getResources(), R.string.settings_filename), MODE_PRIVATE);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, notes -> adapter.setNotes(notes));
    }

    @Override
    protected void onStart() {
        textSize = Integer.valueOf(getRealProgress(settings.getInt(getSettingName(getResources(), R.string.text_size_setting), 0)));
        textColor = settings.getInt(getSettingName(getResources(), R.string.text_color_setting), getResources().getColor(R.color.text_color, null));
        noteColor = settings.getInt(getSettingName(getResources(), R.string.note_color_setting), getResources().getColor(R.color.note_color, null));
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        super.onStart();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE) {
                noteViewModel.create(data.getStringExtra(NOTE_CONTENT));
            }
            if (requestCode == EDIT_NOTE_ACTIVITY_REQUEST_CODE) {
                noteViewModel.update(data.getLongExtra(NOTE_ID, 0), data.getStringExtra(NOTE_CONTENT));
            }
        }
    }
}

