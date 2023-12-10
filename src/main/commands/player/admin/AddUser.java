package main.commands.player.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;

public class AddUser implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final String type;
    private final int age;
    private final String city;
    private String message;


    public void execute(final ArrayList<Song> songs, final ArrayList<Podcast> podcasts,
                        final ArrayList<User> users, final ArrayList<Artist> artists,
                        final ArrayList<Host> hosts) {

        this.addUser(songs, podcasts, users, artists, hosts);
    }

    public AddUser(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.type = input.getType();
        this.age = input.getAge();
        this.city = input.getCity();
    }


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    public void addUser(final ArrayList<Song> everySong, final ArrayList<Podcast> everyPodcast,
                        final ArrayList<User> users, final ArrayList<Artist> artists,
                        final ArrayList<Host> hosts) {

        for (User user : users) {
            if (user.getUsername().equals(this.user)) {
                this.message = "The username " + this.user + " is already taken.";
                return;
            }
        }
        for (Artist artist : artists) {
            if (artist.getUsername().equals(this.user)) {
                this.message = "The username " + this.user + " is already taken.";
                return;
            }
        }
        for (Host host : hosts) {
            if (host.getUsername().equals(this.user)) {
                this.message = "The username " + this.user + " is already taken.";
                return;
            }
        }


        if (this.type.equals("user")) {

            users.add(new User(user, age, city, everySong, everyPodcast));
            this.message = "The username " + user + " has been added successfully.";

        } else if (this.type.equals("artist")) {

            artists.add(new Artist(user, age, city));
            this.message = "The username " + user + " has been added successfully.";

        } else if (this.type.equals("host")) {

            hosts.add(new Host(user, age, city));
            this.message = "The username " + user + " has been added successfully.";
        }



    }





    public String getCommand() {
        return command;
    }

    public String getUser() {
        return user;
    }

    public int getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public String getType() {
        return type;
    }

    @JsonIgnore
    public int getAge() {
        return age;
    }

    @JsonIgnore
    public String getCity() {
        return city;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
