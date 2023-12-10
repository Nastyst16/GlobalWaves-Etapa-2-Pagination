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

//        calculating the number of likes of every album
        ArrayList<Integer> numberOfLikes = new ArrayList<>();
        for (Album album : everyAlbums) {
            int nrOfLikes = 0;
            for (int i = 0; i < album.getSongList().size(); i++) {
                nrOfLikes += album.getSongList().get(i).getNumberOfLikes();
            }
            numberOfLikes.add(nrOfLikes);
        }

        ArrayList<Album> sortedAlbums = new ArrayList<>(everyAlbums);
        for (int i = 0; i < numberOfLikes.size(); i++) {
            for (int j = i + 1; j < numberOfLikes.size(); j++) {
                if (numberOfLikes.get(i) < numberOfLikes.get(j)) {
                    Album aux = sortedAlbums.get(i);
                    sortedAlbums.set(i, sortedAlbums.get(j));
                    sortedAlbums.set(j, aux);

                    int aux2 = numberOfLikes.get(i);
                    numberOfLikes.set(i, numberOfLikes.get(j));
                    numberOfLikes.set(j, aux2);
                } else if (numberOfLikes.get(i).equals(numberOfLikes.get(j))) {
                    if (sortedAlbums.get(i).getName().compareTo(sortedAlbums.get(j).getName()) > 0) {
                        Album aux = sortedAlbums.get(i);
                        sortedAlbums.set(i, sortedAlbums.get(j));
                        sortedAlbums.set(j, aux);

                        int aux2 = numberOfLikes.get(i);
                        numberOfLikes.set(i, numberOfLikes.get(j));
                        numberOfLikes.set(j, aux2);
                    }

                }
            }
        }




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
