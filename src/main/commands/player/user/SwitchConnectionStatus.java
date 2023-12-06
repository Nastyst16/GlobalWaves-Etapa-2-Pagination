package main.commands.player.user;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;

import java.util.ArrayList;

public class SwitchConnectionStatus implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    public SwitchConnectionStatus(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.message = this.user + " has changed status successfully.";
    }

    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {
        if (user == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

        if (user.getOnline() == false) {
            user.setOnline(true);
        } else {
            user.setOnline(false);
        }
    }

    @Override
    public void accept(CommandVisitor visitor) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}