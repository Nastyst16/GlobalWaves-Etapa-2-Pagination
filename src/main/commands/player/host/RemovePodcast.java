package main.commands.player.host;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.Album;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;

public class RemovePodcast implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    private String message;


    public void execute(User user, Artist artist, Host host, ArrayList<User> users,
                        ArrayList<Song> songs, ArrayList<Podcast> podcasts) {
        this.setRemovePodcast(user, artist, host, users, songs, podcasts);
    }


    public void setRemovePodcast(User user, Artist artist, Host host, ArrayList<User> users,
                                 ArrayList<Song> songs, ArrayList<Podcast> podcasts) {

        if (user != null || artist != null) {
            this.message = this.user + " is not an host.";
            return;
        } else if (host == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

//        verifying if the podcast exists
        for (Podcast podcast : host.getHostPodcasts()) {
            if (podcast.getName().equals(this.name)) {
                break;
            }

            this.setMessage(this.name + " doesn't have a podcast with the given name.");
        }


//        verifying if a users currently listens to the podcast
        for (User currentUser : users) {
            if (currentUser.getCurrentType() != null) {
                for (Podcast podcast : host.getHostPodcasts()) {
                    if (podcast.getName().equals(currentUser.getCurrentType().getName()));
                    this.setMessage(this.user + " can't delete this podcast.");
                    return;
                }
            }
        }


//        deleting everything related to the podcast
        for (Podcast p : host.getHostPodcasts()) {

            for(User u : users) {

                u.setEveryPodcast(podcasts);
//                deleting also every user listened podcasts
                for (Podcast podcastToRemove : u.getPodcastsPlayed()) {
                    if (podcastToRemove.getName().equals(p.getName())) {
                        u.getPodcastsPlayed().remove(podcastToRemove);
                        break;
                    }
                }
            }
        }


//        removing the podcast
        for (Podcast podcast : host.getHostPodcasts()) {
            if (podcast.getName().equals(this.name)) {
                host.getHostPodcasts().remove(podcast);
                podcasts.remove(podcast);
                break;
            }
        }


        this.setMessage(this.user + " deleted the podcast successfully.");
    }


    public RemovePodcast(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
