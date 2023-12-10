package main.commands.player.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.*;
import main.users.Artist;
import main.users.Host;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEvent implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    @JsonIgnore
    private final String description;
    @JsonIgnore
    private final String date;
    private String message;


    public void execute(User user, Artist artist, Host host) {
        this.setEvent(user, artist, host);
    }

    public void setEvent(User user, Artist artist, Host host) {

        if (user != null || host != null) {
            this.message = this.user + " is not an artist.";
            return;
        } else if (artist == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

        for (Event event : artist.getEvents()) {
            if (event.getName().equals(this.name)) {
                this.message = this.user + "has another event with the same name.";
                return;
            }
        }

        if (!verifyingFormatDate(this.date)) {
            return;
        }

//        the date format is dd-mm-yyyy verifying if dd is between 1 and 31 and month is between 1 and 12
        String[] dateSplit = this.date.split("-");
        int day = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int year = Integer.parseInt(dateSplit[2]);
        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900 || year > 2023) {
            this.message = "Event for " + this.user + " does not have a valid date.";
            return;
        }
//        for february
        if (month == 2 && day > 28) {
            this.message = "Event for " + this.user + " does not have a valid date.";
            return;
        }




        artist.getEvents().add(new Event(this.user, this.timestamp, this.name, this.description, this.date));
        this.message = this.user + " has added new event successfully.";
    }

    public boolean verifyingFormatDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        dateFormat.setLenient(false);

        try {
            Date parseData = dateFormat.parse(date);
            return true;

        } catch (ParseException e) {
            this.message = "Event for " + this.user + " does not have a valid date.";
            return false;
        }
    }


    public AddEvent(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.description = input.getDescription();
        this.date = input.getDate();
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
