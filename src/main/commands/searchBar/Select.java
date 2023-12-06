package main.commands.searchBar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class Select implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final int itemNumber;
    private String selectedName;
    private String message;

    /**
     * Execute.
     */
    public void execute(final User user, final ArrayList<Playlist> everyPlaylist) {
        this.setSelect(user, everyPlaylist);
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor for Select command.
     * @param input the input
     */
    public Select(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.itemNumber = input.getItemNumber();
        this.message = "Please conduct a search before making a selection.";
    }

    /**
     * Sets select.
     *
     * @param currentUser   the current user
     * @param everyPlaylist the every playlist
     */
    public void setSelect(final User currentUser, final ArrayList<Playlist> everyPlaylist) {

//                if the last command was search
        if (currentUser.getCurrentSearch() != null) {
            ArrayList<String> resultsPrevSearch = currentUser.getCurrentSearch().getResults();

//                    getting index for setting the message
            int index = this.itemNumber;

//                    make this be a method in select class
            if (index > resultsPrevSearch.size()) {
                this.message = "The selected ID is too high.";
                currentUser.setCurrentSelect(null);
                currentUser.setCurrentType(null);
            } else {
                String name = resultsPrevSearch.get(index - 1);
                this.message = "Successfully selected " + name + ".";
                currentUser.setSelectedName(name);
                this.setSelectedName(name);

                if (currentUser.getTypeFoundBySearch() == 0) {
                    currentUser.setTypeSelected(0);
                } else if (currentUser.getTypeFoundBySearch() == 1) {
                    currentUser.setTypeSelected(1);
                } else if (currentUser.getTypeFoundBySearch() == 2) {
                    currentUser.setTypeSelected(2);

                    for (Playlist playlist : everyPlaylist) {
                        if (playlist.getName().equals(name)) {
                            currentUser.setSelectedPlaylist(playlist);
                            break;
                        }
                    }
                }
                currentUser.setCurrentSelect(this);
            }
        }
        currentUser.setCurrentSearch(null);
        currentUser.setTypeFoundBySearch(-1);
    }

    /**
     * Gets command.
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets user.
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets timestamp.
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets item number.
     * @return the item number
     */
    @JsonIgnore
    public int getItemNumber() {
        return itemNumber;
    }

    /**
     * Gets message.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Gets selected name.
     * @return the selected name
     */
    @JsonIgnore
    public String getSelectedName() {
        return selectedName;
    }

    /**
     * Sets selected name.
     * @param selectedName the selected name
     */
    public void setSelectedName(final String selectedName) {
        this.selectedName = selectedName;
    }

}
