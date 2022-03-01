package fr.kuai.reveilapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Songs {
    // Attributs //
    String song_path, song_name, song_Album, song_Artist;

    // Méthodes //
    public String getaPath() {
        return song_path;
    }

    public void setaPath(String aPath) {
        this.song_path = aPath;
    }

    public String getaName() {
        return song_name;
    }

    public void setaName(String aName) {
        this.song_name = aName;
    }

    public String getaAlbum() {
        return song_Album;
    }

    public void setaAlbum(String aAlbum) {
        this.song_Album = aAlbum;
    }

    public String getaArtist() {
        return song_Artist;
    }

    public void setaArtist(String aArtist) {
        this.song_Artist = aArtist;
    }

    /********* Liste les musiques de l'utilisateur *********/
    public List<Songs> getAllAudioFromDevice(final Context context) {

        final List<Songs> tempAudioList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST,};
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {
                Songs song = new Songs();

                String path = c.getString(0);
                String name = c.getString(1);
                String album = c.getString(2);
                String artist = c.getString(3);

                song.setaName(name);
                song.setaAlbum(album);
                song.setaArtist(artist);
                song.setaPath(path);

                tempAudioList.add(song);
            }
            c.close();
        }

        return tempAudioList;
    }

    @NonNull
    @Override
    public String toString() {
        return " \uD83C\uDFB5" + getaName()+ " \uD83D\uDC64" + getaArtist() + " \uD83D\uDCBF" + getaAlbum();
    }
}
