package main.commands.pageSystem;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;

import java.util.ArrayList;

public class PrintCurrentPage implements Command {

    private final String user;
    private final String command;
    private final int timestamp;
    private String message;

    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {

        this.setPrintCurrPage(user);
    }

    public void setPrintCurrPage(User user) {

//        if the user is offline
        if (!user.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

//        if the current page is Home
        if (user.getCurrentPage().equals("Home")) {
            this.message = "Liked songs:\n\t" + user.getLikedSongs() + "\n\n"
                    + "Followed playlists:\n\t" + user.getFollowedPlaylists();
//          if the current page is LikedContent
        } else if (user.getCurrentPage().equals("LikedContent")) {
            this.message = "Liked songs:\n\t" + user.getLikedSongs() + "\n\n"
                    + "Followed playlists:\n\t" + user.getFollowedPlaylists();
//          if the current page is Artist..............
        }

    }

    public PrintCurrentPage(final SearchBar input) {
        this.user = input.getUsername();
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
    }


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    public String getUser() {
        return user;
    }
    public String getCommand() {
        return command;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
