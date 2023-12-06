package main.users;

import main.User;
import main.commands.types.Album;

import java.util.ArrayList;

public class Artist {

    private final String username;
    private final int age;
    private final String city;

    private ArrayList<Album> artistAlbums;

    public Artist(String username, int age, String city) {
        this.username = username;
        this.age = age;
        this.city = city;

        this.artistAlbums = new ArrayList<>();
    }


    public ArrayList<Album> getArtistAlbums() {
        return artistAlbums;
    }

    public void setArtistAlbums(ArrayList<Album> artistAlbums) {
        this.artistAlbums = artistAlbums;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }
}
