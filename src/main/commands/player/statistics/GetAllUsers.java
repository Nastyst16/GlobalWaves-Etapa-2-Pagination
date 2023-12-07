package main.commands.player.statistics;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;

public class GetAllUsers implements Command {
    private final String command;
    private final int timestamp;
    private final ArrayList<String> result;


    public void execute(ArrayList<User> users, ArrayList<Artist> artists, ArrayList<Host> hosts) {

        for (User user : users) {
            this.result.add(user.getUsername());
        }
        for (Artist artist : artists) {
            this.result.add(artist.getUsername());
        }
        for (Host host : hosts) {
            this.result.add(host.getUsername());
        }

    }

    public GetAllUsers(SearchBar input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();

        this.result = new ArrayList<>();
    }

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
}
