package ru.sberbank.lesson8.task.contentproviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry.NOTE_CONTENT;
import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry.NOTE_ID;

public class EditNoteActivity extends Activity {
    private TextView editNoteTextView;
    private Long currentNoteId;
    private View saveNoteBtn;
    private View editModeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editNoteTextView = findViewById(R.id.editNoteText);

        Intent intent = getIntent();
        editNoteTextView.setText(intent.getStringExtra(NOTE_CONTENT));
        currentNoteId = intent.getLongExtra(NOTE_ID, 0);

        saveNoteBtn = findViewById(R.id.saveNoteBtn);
        editModeBtn = findViewById(R.id.editModeBtn);

        findViewById(R.id.saveNoteBtn).setOnClickListener((v) -> {
            String text = editNoteTextView.getText().toString();
            if (text.length() > 0) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(NOTE_ID, currentNoteId);
                replyIntent.putExtra(NOTE_CONTENT, text);
                setResult(RESULT_OK, replyIntent);
                finish();
            } else {
                Toast.makeText(v.getContext(), R.string.empty_text, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.editModeBtn).setOnClickListener((v) -> {
            editNoteTextView.setFocusableInTouchMode(true);
            saveNoteBtn.setEnabled(true);
            editModeBtn.setEnabled(false);
        });
        findViewById(R.id.cancelEditNoteBtn).setOnClickListener((v) -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
