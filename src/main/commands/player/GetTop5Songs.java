package main.commands.player;

import main.Command;
import main.CommandVisitor;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetTop5Songs implements Command {
    private final String command;
    private final int timestamp;
    private final ArrayList<String> result;
    private static final int TOP_NR = 5;


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public GetTop5Songs(final SearchBar input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
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
    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input,
                        final User user, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts) {

        this.searchTop5Songs(songs);
    }
}
