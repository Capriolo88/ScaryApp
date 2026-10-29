package it.capriolo.scaryapp;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.playButton);
        Button stopButton = findViewById(R.id.stopButton);

        playButton.setOnClickListener(v -> playRingtone());
        stopButton.setOnClickListener(v -> stopRingtone());
    }

    private void playRingtone() {
        stopRingtone();

        int ringtoneResId = getResources().getIdentifier("ringtone", "raw", getPackageName());
        if (ringtoneResId == 0) {
            ringtoneResId = getResources().getIdentifier("RINGTONE", "raw", getPackageName());
        }

        if (ringtoneResId == 0) {
            Toast.makeText(this, "File audio non trovato", Toast.LENGTH_SHORT).show();
            return;
        }

        mediaPlayer = MediaPlayer.create(this, ringtoneResId);
        if (mediaPlayer != null) {
            mediaPlayer.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
            );

            mediaPlayer.setVolume(1.0f, 1.0f);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    private void stopRingtone() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRingtone();
    }
}
