package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.*;
import main.commands.types.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class Status implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final Map<String, Object> stats;
    private int remainingTime;


    /**
     * Execute the command
     */
    public void execute(final User user) {

        if (user.getCurrentType() != null) {
            this.settingStats(user);
        } else {
            this.settingNoType(user);
        }

        if (this.getRemainingTime() == 0
                && user.getRepeatStatus() == 0) {
            user.setPaused(true);
            user.setCurrentType(null);
        }
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input SearchBar
     */
    public Status(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.stats = new LinkedHashMap<>();
    }

    /**
     * Setting the stats for the current user
     * @param currentUser User
     */
    public void settingStats(final User currentUser) {
        Type currentType = currentUser.getCurrentType();

        if (currentType instanceof Song) {
            currentType = (Song) currentType;

        } else if (currentType instanceof Podcast) {
            currentType = (Podcast) currentType;
            int index = ((Podcast) currentType).getLastRemainingEpisode();

//            currentType is the Episode now;
            currentType = ((Podcast) currentType).getEpisodes().get(index);
        }

        currentUser.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());

        if (currentUser.getRemainingTime() < 0 && currentUser.getRepeatStatus() == 0) {
            currentUser.setRemainingTime(0);
        }

        this.remainingTime = currentUser.getRemainingTime();

        currentUser.setCurrentType(currentType);

        if (currentUser.getRemainingTime() == 0) {
            currentUser.setCurrentType(new Song(""));
            currentUser.setPaused(true);
        }

        stats.put("name", currentUser.getCurrentType().getName());
        stats.put("remainedTime", currentUser.getRemainingTime());
        stats.put("repeat", currentUser.getRepeatString());
        stats.put("shuffle", currentUser.isShuffle());
        stats.put("paused", currentUser.isPaused());
    }

    /**
     * if the user is not playing anything
     * @param currentUser User
     */
    public void settingNoType(final User currentUser) {
        currentUser.setPaused(true);
        currentUser.setTypeLoaded(-1);
        currentUser.setTypeSelected(-1);

        stats.put("name", "");
        stats.put("remainedTime", 0);
        stats.put("repeat", currentUser.getRepeatString());
        stats.put("shuffle", currentUser.isShuffle());
        stats.put("paused", currentUser.isPaused());
    }

    /**
     * Getting the command
     * @return String
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getting the user
     * @return String
     */
    public String getUser() {
        return user;
    }

    /**
     * Getting the timestamp
     * @return int
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Getting the stats
     * @return Map<String, Object>
     */
    public Map<String, Object> getStats() {
        return stats;
    }

    /**
     * Getting the remaining time
     * @return int
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Setting the remaining time
     * @param remainingTime int
     */
    @JsonIgnore
    public void setRemainingTime(final int remainingTime) {
        this.remainingTime = remainingTime;
    }


}
