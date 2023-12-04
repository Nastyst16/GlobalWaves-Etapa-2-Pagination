package main.Commands.SearchBar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.Commands.Types.Playlist;
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
     * Constructor for Select command.
     *
     * @param command    the command
     * @param user       the user
     * @param timestamp  the timestamp
     * @param itemNumber the item number
     */
    public Select(final String command, final String user,
                  final int timestamp, final int itemNumber) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        this.itemNumber = itemNumber;
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

    /**
     * Execute.
     */
    @Override
    public void execute() {

    }
}
