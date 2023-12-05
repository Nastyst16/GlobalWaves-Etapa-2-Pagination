package main.commands.user;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
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
    public void execute(ArrayList<Command> commands, SearchBar input, User user,
                        ArrayList<Song> songs, ArrayList<Playlist> everyPlaylist,
                        ArrayList<Podcast> podcasts) {
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
