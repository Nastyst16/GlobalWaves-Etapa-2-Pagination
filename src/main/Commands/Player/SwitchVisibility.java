package main.Commands.Player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.User;

public class SwitchVisibility implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final int id;
    private String message;


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * This constructor is used to create a new SwitchVisibility command.
     * @param command the command type
     * @param user the user who called the command
     * @param timestamp the time at which the command was called
     * @param id the id of the playlist
     */
    public SwitchVisibility(final String command, final String user,
                            final int timestamp, final int id) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        this.id = id;
    }

    /**
     * This method is used to set the visibility of a playlist.
     * @param currentUser the current user
     */
    public void setVisibility(final User currentUser) {

        if (this.id > currentUser.getPlayListList().size()) {
            this.message = "The specified playlist ID is too high.";
            return;
        }
        if (currentUser.getPlayListList().get(this.id - 1).getVisibility().equals("public")) {
            currentUser.getPlayListList().get(this.id - 1).setVisibility("private");
            this.message = "Visibility status updated successfully to private.";
        } else {
            currentUser.getPlayListList().get(this.id - 1).setVisibility("public");
            this.message = "Visibility status updated successfully to public.";
        }
    }

    /**
     * This method is used to get the command of the command.
     * @return the command of the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * This method is used to get the user of the command.
     * @return the user of the command
     */
    public String getUser() {
        return user;
    }

    /**
     * This method is used to get the timestamp of the command.
     * @return the timestamp of the command
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * This method is used to get the message of the command.
     * @return the message of the command
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method is used to get the id of the command.
     * @return the id of the command
     */
    @JsonIgnore
    public int getId() {
        return id;
    }

    /**
     * This method is used to execute the command.
     */
    @Override
    public void execute() {

    }
}
