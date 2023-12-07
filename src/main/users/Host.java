package main.users;

import main.commands.types.Album;
import main.commands.types.Podcast;

import java.util.ArrayList;

public class Host {

    private final String username;
    private final int age;
    private final String city;

    private ArrayList<Podcast> hostPodcasts;


    public Host(String username, int age, String city) {
        this.username = username;
        this.age = age;
        this.city = city;

        this.hostPodcasts = new ArrayList<>();
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

    public void setHostPodcasts(ArrayList<Podcast> hostPodcasts) {
        this.hostPodcasts = hostPodcasts;
    }
}
