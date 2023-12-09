package main.commands.player.statistics;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.commands.types.Album;

import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;

public class GetTop5Albums implements Command {
    private final String command;
    private final int timestamp;
    private final ArrayList<String> result;
    private static final int TOP_NR = 5;



    public void execute(ArrayList<Album> everyAlbums) {
        this.searchTop5Albums(everyAlbums);
    }


    public void searchTop5Albums(ArrayList<Album> everyAlbums) {

        ArrayList<Album> sortedAlbums = new ArrayList<>(everyAlbums);
        Collections.sort(sortedAlbums, Comparator.
                comparingInt(Album::getFollowers).reversed());

        int i = 0;
        while (i < TOP_NR && i < sortedAlbums.size()) {
            result.add(sortedAlbums.get(i).getName());
            i++;
        }

    }



    public GetTop5Albums(SearchBar input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
        result = new ArrayList<>();
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Getter for the command
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the timestamp
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for the result
     *
     * @return the result
     */
    public ArrayList<String> getResult() {
        return result;
    }
}
