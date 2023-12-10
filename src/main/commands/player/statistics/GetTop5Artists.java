package main.commands.player.statistics;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.commands.searchBar.Search;
import main.commands.types.Album;
import main.commands.types.Song;
import main.users.Artist;

import java.util.ArrayList;

public class GetTop5Artists implements Command {
    private final String command;
    private final int timestamp;
    private final ArrayList<String> result;
    private static final int TOP_NR = 5;



    public void execute(ArrayList<Artist> everyArtist) {
        this.searchTop5Artists(everyArtist);
    }


    private void searchTop5Artists(ArrayList<Artist> everyArtist) {

//        the artist with the most liked songs
        ArrayList<Integer> numberOfLikes = new ArrayList<>();

        for (Artist artist : everyArtist) {
            int nrOfLikes = 0;
            for (Album album : artist.getAlbums()) {
                for (Song song : album.getSongList()) {
                    nrOfLikes += song.getNumberOfLikes();
                }
            }
            numberOfLikes.add(nrOfLikes);
        }

        ArrayList<Artist> sortedArtists = new ArrayList<>(everyArtist);
        for (int i = 0; i < numberOfLikes.size(); i++) {
            for (int j = i + 1; j < numberOfLikes.size(); j++) {
                if (numberOfLikes.get(i) < numberOfLikes.get(j)) {
                    Artist aux = sortedArtists.get(i);
                    sortedArtists.set(i, sortedArtists.get(j));
                    sortedArtists.set(j, aux);

                    int aux2 = numberOfLikes.get(i);
                    numberOfLikes.set(i, numberOfLikes.get(j));
                    numberOfLikes.set(j, aux2);
                }

                if (numberOfLikes.get(i).equals(numberOfLikes.get(j))) {
                    if (sortedArtists.get(i).getUsername().compareTo(sortedArtists.get(j).getUsername()) > 0) {
                        Artist aux = sortedArtists.get(i);
                        sortedArtists.set(i, sortedArtists.get(j));
                        sortedArtists.set(j, aux);

                        int aux2 = numberOfLikes.get(i);
                        numberOfLikes.set(i, numberOfLikes.get(j));
                        numberOfLikes.set(j, aux2);
                    }
                }
            }
        }

        int i = 0;
        while (i < TOP_NR && i < sortedArtists.size()) {
            result.add(sortedArtists.get(i).getUsername());
            i++;

            if (i == TOP_NR) {
                break;
            }
        }
    }

    public GetTop5Artists(SearchBar input) {
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
