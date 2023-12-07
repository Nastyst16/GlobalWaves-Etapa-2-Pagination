package main.users;

import main.commands.types.Album;
import main.commands.types.Announcement;
import main.commands.types.Podcast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Host {

    private final String username;
    private final int age;
    private final String city;

    private final ArrayList<Podcast> hostPodcasts;
    private final ArrayList<Announcement> announcements;


    public Host(String username, int age, String city) {
        this.username = username;
        this.age = age;
        this.city = city;

        this.hostPodcasts = new ArrayList<>();
        this.announcements = new ArrayList<>();
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

    public ArrayList<Podcast> getHostPodcasts() {
        return hostPodcasts;
    }

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }
}
