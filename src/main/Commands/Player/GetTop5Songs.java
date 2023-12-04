package main.Commands.Player;

import main.Command;
import main.Commands.Types.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetTop5Songs implements Command {
    private final String command;
    private final int timestamp;
    private final ArrayList<String> result;
    private static final int TOP_NR = 5;

    /**
     * Constructor
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public GetTop5Songs(final String command, final int timestamp) {
        this.command = command;
        this.timestamp = timestamp;
        result = new ArrayList<>();
    }

    /**
     * Search for the top 5 songs
     *
     * @param everySong all the songs
     */
    public void searchTop5Songs(final ArrayList<Song> everySong) {

        ArrayList<Song> sortedSong = new ArrayList<>(everySong);
        Collections.sort(sortedSong, Comparator.comparingInt(Song::getNumberOfLikes).reversed());

        int i = 0;
        while (i < TOP_NR && i < sortedSong.size()) {
            result.add(sortedSong.get(i).getName());
            i++;
        }
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

    /**
     * Execute the command
     */
    public void execute() {

    }
}
