package musicplayer.simplemobiletools.com;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final int MIN_DURATION_SECS = 20;
    private ArrayList<Song> songs;

    @Bind(R.id.songs) ListView songsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        songs = new ArrayList<>();

        getSortedSongs();

        final SongAdapter adapter = new SongAdapter(this, songs);
        songsList.setAdapter(adapter);
    }

    private void getSortedSongs() {
        getSongs();
        Collections.sort(songs, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
    }

    private void getSongs() {
        final ContentResolver musicResolver = getContentResolver();
        final Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            final int idIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            final int titleIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            final int artistIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            final int durationIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            do {
                if (musicCursor.getInt(durationIndex) > MIN_DURATION_SECS) {
                    final long id = musicCursor.getLong(idIndex);
                    final String title = musicCursor.getString(titleIndex);
                    final String artist = musicCursor.getString(artistIndex);
                    songs.add(new Song(id, title, artist));
                }
            } while (musicCursor.moveToNext());
            musicCursor.close();
        }
    }

    @OnClick(R.id.previousBtn)
    public void previousClicked() {

    }

    @OnClick(R.id.playPauseBtn)
    public void playPauseClicked() {

    }

    @OnClick(R.id.nextBtn)
    public void nextClicked() {

    }

    @OnClick(R.id.stopBtn)
    public void stopClicked() {

    }
}