package main.commands.types;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Album extends Playlist {
    private final String user;
    private final String name;
    private final int releaseYear;
    private final String description;
    private final ArrayList<Song> albumSongs;
    private final ArrayList<String> songs;
    @JsonIgnore
    private String visibility;
    @JsonIgnore
    private int followers;


    public Album(final String user, final String name, final int releaseYear,
                 final String description, final ArrayList<Song> albumSongs) {
        super(user, name, albumSongs);
        this.user = user;
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.albumSongs = albumSongs;

        this.songs = new ArrayList<>();

        for (Song song : albumSongs) {
            this.songs.add(song.getName());
        }


    }



    @JsonIgnore
    public String getUser() {
        return user;
    }



    public String getName() {
        return name;
    }

    @JsonIgnore
    public int getReleaseYear() {
        return releaseYear;
    }

    @JsonIgnore
    public String getDescription() {
        return description;
    }

    @JsonIgnore
    public ArrayList<Song> getAlbumSongs() {
        return albumSongs;
    }

    public ArrayList<String> getSongs() {
        return songs;
    }


    @Override
    public String getVisibility() {
        return visibility;
    }

    @Override
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    @Override
    public int getFollowers() {
        return followers;
    }

    @Override
    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
