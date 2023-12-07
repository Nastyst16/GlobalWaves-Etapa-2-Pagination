package main.users;

import main.User;
import main.commands.types.Album;
import main.commands.types.Event;
import main.commands.types.Merch;

import java.util.ArrayList;

public class Artist {

    private final String username;
    private final int age;
    private final String city;

    private final ArrayList<Album> albums;
    private final ArrayList<Event> events;
    private final ArrayList<Merch> merchandise;

    public Artist(String username, int age, String city) {
        this.username = username;
        this.age = age;
        this.city = city;

        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merchandise = new ArrayList<>();
    }


    public ArrayList<Album> getAlbums() {
        return albums;
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

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Merch> getMerchandise() {
        return merchandise;
    }
}
