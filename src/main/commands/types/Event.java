package main.commands.types;

public class Event {
    private final String user;
    private final int timestamp;
    private final String name;
    private final String description;
    private final String date;


    public Event(String user, int timestamp, String name, String description, String date) {
        this.user = user;
        this.timestamp = timestamp;
        this.name = name;
        this.description = description;
        this.date = date;
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
}
