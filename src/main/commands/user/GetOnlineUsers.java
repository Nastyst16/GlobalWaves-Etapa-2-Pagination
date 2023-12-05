package main.commands.user;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.pageSystem.*;
import main.commands.searchBar.*;
import main.commands.types.*;
import main.commands.player.*;

import java.util.ArrayList;

public class GetOnlineUsers implements Command {
    private final String command;
    private final int timestamp;
    private final ArrayList<User> users;
    private ArrayList<String> result;


    public GetOnlineUsers(SearchBar input, ArrayList<User> users) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
        this.users = users;

        this.result = new ArrayList<>();
    }

    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void execute(ArrayList<Command> commands, SearchBar input, User user,
                        ArrayList<Song> songs, ArrayList<Playlist> everyPlaylist,
                        ArrayList<Podcast> podcasts) {

        for (User u : users) {
            if (u.getOnline() == true) {
                result.add(u.getUsername());
            }
        }
    }

    public String getCommand() {
        return command;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public ArrayList<String> getResult() {
        return result;
    }

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }
}
