package main.commands.player.statistics;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.*;

import java.util.ArrayList;

public class GetOnlineUsers implements Command {
    private final String command;
    private final int timestamp;
    private ArrayList<String> result;

    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {

        for (User u : users) {
            if (u.getOnline() == true) {
                result.add(u.getUsername());
            }
        }
    }

    public GetOnlineUsers(SearchBar input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();

        this.result = new ArrayList<>();
    }

    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
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