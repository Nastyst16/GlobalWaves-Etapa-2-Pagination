package main.commands.player.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.searchBar.Search;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;

import java.util.ArrayList;

public class AddUser implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final String type;
    private final int age;
    private final String city;
    private String message;


    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {

        this.addUser(songs, podcasts, users);
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
                        final ArrayList<User> users) {

        for (User user : users) {
            if (user.getUsername().equals(this.user)) {
                this.message = "The username " + this.user + " is already taken.";
                return;
            }
        }

        users.add(new User(user, age, city, everySong, everyPodcast));
        users.get(users.size() - 1).setType(type); // set the user's type to the input type
        this.message = "The username " + user + " has been added successfully.";
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
