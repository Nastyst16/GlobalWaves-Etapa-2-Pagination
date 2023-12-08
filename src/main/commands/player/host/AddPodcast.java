package main.commands.player.host;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.searchBar.Search;
import main.commands.types.Episode;
import main.commands.types.Podcast;
import main.users.Host;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddPodcast implements Command {

    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    @JsonIgnore
    private final ArrayList<Episode> episodes;
    private String message;


    public void execute(Host host, ArrayList<User> users, ArrayList<Podcast> everyPodcast,
                            ArrayList<Host> hosts) {
        addPodcast(host, users, everyPodcast, hosts);
    }

    public AddPodcast(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.episodes = input.getEpisodes();
    }

    public void addPodcast(Host host, ArrayList<User> users, ArrayList<Podcast> everyPodcast,
                            ArrayList<Host> hosts) {


//        verifying if the user is a host
        for (Host h : hosts) {
            if (h.getUsername().equals(this.user)) {
                break;
            }
            this.setMessage(this.user + " is not a host.");
            return;
        }

//        verifying if the host exists
        if (host == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

//        verifying if the podcast already exists
        for (Podcast podcast : everyPodcast) {
            if (podcast.getName().equals(this.name)) {
                this.message = this.user + " has another podcast with the same name.";
                return;
            }
        }



        everyPodcast.add(new Podcast(this.name, this.user, this.episodes));
        host.getHostPodcasts().add(everyPodcast.get(everyPodcast.size() - 1));
        for (User u : users) {
//            u.setEveryPodcast(everyPodcast);

//            deepcopy
            ArrayList<Podcast> userPodcasts = new ArrayList<>();
            for (Podcast podcast : everyPodcast) {

//                deepcopy episodes
                ArrayList<Episode> episodesCopy = new ArrayList<>();
                for (Episode episode : podcast.getEpisodesList()) {
                    episodesCopy.add(new Episode(episode.getName(), episode.getDuration(), episode.getDescription()));
                }
                userPodcasts.add(new Podcast(podcast.getName(), this.user, episodesCopy));
            }
            u.setEveryPodcast(userPodcasts);
        }


        this.setMessage(this.user + " has added new podcast successfully.");
    }


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
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

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
