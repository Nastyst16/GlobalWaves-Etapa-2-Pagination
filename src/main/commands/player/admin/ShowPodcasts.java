package main.commands.player.admin;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.commands.types.Podcast;
import main.users.Host;

import java.util.ArrayList;

public class ShowPodcasts implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final ArrayList<String> result;


    public void execute(Host host) {
        this.setHost(host);
    }

    private void setHost(Host host) {

        for (Podcast podcast : host.getHostPodcasts()) {
            this.result.add(podcast.getName());
        }

    }


    public ShowPodcasts(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();

        this.result = new ArrayList<>();
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

    public ArrayList<String> getResult() {
        return result;
    }
}
