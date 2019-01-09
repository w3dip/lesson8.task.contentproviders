package ru.sberbank.lesson8.task.contentproviders.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ru.sberbank.lesson8.task.contentproviders.EditNoteActivity;
import ru.sberbank.lesson8.task.contentproviders.R;

import static android.graphics.PorterDuff.Mode.SRC_ATOP;
import static ru.sberbank.lesson8.task.contentproviders.MainActivity.EDIT_NOTE_ACTIVITY_REQUEST_CODE;
import static ru.sberbank.lesson8.task.contentproviders.adapters.NoteEntry.*;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private static final int MAX_NOTE_LENGTH = 140;

    private final LayoutInflater inflater;
    private List<NoteEntry> notes = Collections.emptyList();
    public static int textSize;
    public static int noteColor;
    public static int textColor;

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public Long id;
        public String allText;
        public TextView note;
        public NoteViewHolder(View v) {
            super(v);
            note = v.findViewById(R.id.notes_item_content);
            note.setTextSize(textSize);
            note.getBackground().setColorFilter(noteColor, SRC_ATOP);
            note.setTextColor(textColor);
            note.setOnClickListener((item) -> {
                Context context = item.getRootView().getContext();
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra(NOTE_ID, id);
                intent.putExtra(NOTE_CONTENT, allText);
                ((Activity)context).startActivityForResult(intent, EDIT_NOTE_ACTIVITY_REQUEST_CODE);
            });
        }
    }

    public NoteAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        return new NoteViewHolder(inflater.inflate(R.layout.note, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        NoteEntry entry = notes.get(position);
        holder.id = entry.getId();
        String allText = entry.getContent();
        holder.allText = allText;
        holder.note.setText(allText.length() > MAX_NOTE_LENGTH ? allText.substring(0, MAX_NOTE_LENGTH) + "..." : allText);
    }

    public void setNotes(List<NoteEntry> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
