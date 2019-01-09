package ru.sberbank.lesson8.task.contentproviders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry.NOTE_CONTENT;

public class CreateNoteActivity extends AppCompatActivity {
    private TextView newNoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        newNoteTextView = findViewById(R.id.newNoteText);
        findViewById(R.id.newNoteBtn).setOnClickListener((v) -> {
            String text = newNoteTextView.getText().toString();
            if (text.length() > 0) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(NOTE_CONTENT, text);
                setResult(RESULT_OK, replyIntent);
                finish();
            } else {
                Toast.makeText(v.getContext(), R.string.empty_text, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.cancelNewNoteBtn).setOnClickListener((v) -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
