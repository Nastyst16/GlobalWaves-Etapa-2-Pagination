package main.commands.player.statistics;

import main.Collections.Artists;
import main.Collections.Hosts;
import main.Collections.Users;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;

public final class GetAllUsers implements Command {
    private final String command;
    private final int timestamp;
    private final ArrayList<String> result;

    /**
     * Method that executes the command
     */
    public void execute() {

        for (User user : Users.getUsers()) {
            this.result.add(user.getUsername());
        }
        for (Artist artist : Artists.getArtists()) {
            this.result.add(artist.getUsername());
        }
        for (Host host : Hosts.getHosts()) {
            this.result.add(host.getUsername());
        }

    }

    /**
     * Constructor of the class, it sets the command and the timestamp
     * @param input the input command
     */
    public GetAllUsers(final SearchBar input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();

        this.result = new ArrayList<>();
    }

    /**
     * Method that accepts the visitor
     * @param visitor the visitor
     */
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Getter for the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for the result
     * @return the result
     */
    public ArrayList<String> getResult() {
        return result;
    }
}
