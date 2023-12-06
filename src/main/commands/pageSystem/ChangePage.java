package main.commands.pageSystem;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.*;

import java.util.ArrayList;

public class ChangePage implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final String nextPage;
    private String message;


    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {

        this.setChangePage(user);
    }

    public ChangePage(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.nextPage = input.getNextPage();
        this.message = input.getUsername() + " is trying to access a non-existent page.";
    }

    public void setChangePage(User user) {

//        if the user is offline
        if (!user.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

        if (this.getNextPage().equals("Home") || this.getNextPage().equals("LikedContent")) {

            user.setCurrentPage(this.getNextPage());
            this.message = this.user + "accessed " + this.getNextPage() + " successfully.";

        } else {
            user.setCurrentPage(null);
        }
    }



    @Override
    public void accept(final CommandVisitor visitor) {
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

    public String getNextPage() {
        return nextPage;
    }
}