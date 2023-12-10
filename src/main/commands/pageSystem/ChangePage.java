package main.commands.pageSystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.users.User;

public class ChangePage implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String nextPage;
    private String message;


    public void execute(final User user) {

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
            user.setSelectedPageOwner("");
            this.setMessage(this.user + " accessed " + this.getNextPage() + " successfully.");

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}