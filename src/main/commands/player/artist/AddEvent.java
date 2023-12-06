package main.commands.player.artist;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;

public class AddEvent implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final String name;
    private final String description;
    private final String date;


    public void execute(ArrayList<Command> commands, SearchBar input, User user,
                        ArrayList<Song> songs, ArrayList<Playlist> everyPlaylist,
                        ArrayList<Podcast> podcasts, ArrayList<User> users,
                        ArrayList<Album> albums, ArrayList<Artist> artists, ArrayList<Host> hosts) {

//        this.setEvent(User);

    }

    public void setEvent(User user) {

    }


    public AddEvent(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.description = input.getDescription();
        this.date = input.getDate();
    }

    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
