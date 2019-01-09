package ru.sberbank.lesson8.task.contentproviders;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import me.priyesh.chroma.ChromaDialog;

import static me.priyesh.chroma.ColorMode.RGB;
import static ru.sberbank.lesson8.task.contentproviders.utils.Helpers.*;

public class SettingsActivity extends FragmentActivity {
    private SharedPreferences settings;
    private TextView noteTextSize;
    private int progressTextSize;
    private View noteColorView;
    private View textColorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Resources resources = getResources();
        settings = getApplicationContext().getSharedPreferences(getSettingName(resources, R.string.settings_filename), MODE_PRIVATE);

        SeekBar noteTextSizeSeekBar = findViewById(R.id.noteTextSizeSeekBar);
        noteTextSizeSeekBar.setMax( (TEXT_SIZE_MAX - TEXT_SIZE_MIN) / TEXT_SIZE_STEP);
        progressTextSize = settings.getInt(getSettingName(resources, R.string.text_size_setting), 0);
        noteTextSizeSeekBar.setProgress(progressTextSize);
        noteTextSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                noteTextSize.setText(getRealProgress(progress));
                progressTextSize = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        noteTextSize = findViewById(R.id.noteTextSize);
        noteTextSize.setText(getRealProgress(noteTextSizeSeekBar.getProgress()));

        noteColorView = findViewById(R.id.noteColorPicker);
        noteColorView.setBackground(new ColorDrawable(settings.getInt(getSettingName(resources, R.string.note_color_setting), getResources().getColor(R.color.note_color, null))));
        noteColorView.setOnClickListener(this::changeColor);

        textColorView = findViewById(R.id.textColorPicker);
        textColorView.setBackground(new ColorDrawable(settings.getInt(getSettingName(resources, R.string.text_color_setting), getResources().getColor(R.color.text_color, null))));
        textColorView.setOnClickListener(this::changeColor);

        findViewById(R.id.saveSettingsBtn).setOnClickListener((v) -> {
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt(getSettingName(resources, R.string.text_size_setting), progressTextSize);
            editor.putInt(getSettingName(resources, R.string.text_color_setting), getBackgroundColor(textColorView));
            editor.putInt(getSettingName(resources, R.string.note_color_setting), getBackgroundColor(noteColorView));
            editor.apply();
            finish();
        });

        findViewById(R.id.cancelEditSettingsBtn).setOnClickListener((v) -> {
            finish();
        });
    }

    private void changeColor(View v) {
        new ChromaDialog.Builder()
            .initialColor(getBackgroundColor(v))
            .colorMode(RGB)
            .onColorSelected(v::setBackgroundColor)
            .create()
            .show(getSupportFragmentManager(), null);
    }
}
