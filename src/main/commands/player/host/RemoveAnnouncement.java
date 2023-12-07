package main.commands.player.host;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.users.Host;

public class RemoveAnnouncement implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    private String message;

    public void execute(Host host) {
        removeAnnouncement(host);
    }


    public void removeAnnouncement(Host host) {

//        verifying if the host exists
        if (host == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

//        verifying if the announcement exists
        for (int i = 0; i < host.getAnnouncements().size(); i++) {
            if (host.getAnnouncements().get(i).getName().equals(this.name)) {
                host.getAnnouncements().remove(i);
                this.message = this.user + " has successfully deleted the announcement.";
                return;
            }
        }

        this.message = this.user + " has no announcement with the given name.";

    }


    public RemoveAnnouncement(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
    }


    public void accept(CommandVisitor commandVisitor) {
        commandVisitor.visit(this);
    }


    public String getCommand() {
        return command;
    }

    public String getUser() {
        return user;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
