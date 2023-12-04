package main.Commands.Types;

import java.util.List;

public class Podcast implements Type {
    private final String name;
    private final String owner;
    private final List<Episode> episodes;
    private int lastRemainingEpisode;
    private int secondsGone;


    /**
     * default constructor
     */
    public Podcast() {
        this.name = null;
        this.owner = null;
        this.episodes = null;
    }

    /**
     * constructor with parameters
     * @param name name of the podcast
     * @param owner owner of the podcast
     * @param episodes list of episodes
     */
    public Podcast(final String name, final String owner, final List<Episode> episodes) {
        this.name = name;
        this.owner = owner;
        this.episodes = episodes;
    }

    /**
     * gets the name of the podcast
     */
    public String getName() {
        return name;
    }

    /**
     * gets the owner of the podcast
     */
    public String getOwner() {
        return owner;
    }

    /**
     * gets the list of episodes
     */
    public List<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * gets the duration of the podcast
     */
    public int getDuration() {
//        i wrote this because podcast implements type
        return 0;
    }

    /**
     * gets the last remaining episode
     */
    public int getLastRemainingEpisode() {
        return lastRemainingEpisode;
    }

    /**
     * sets the last remaining episode
     */
    public void setLastRemainingEpisode(final int lastRemainingEpisode) {
        this.lastRemainingEpisode = lastRemainingEpisode;
    }

    /**
     * gets the duration of the podcast
     */
    public int getSecondsGone() {
        return this.secondsGone;
    }

    /**
     * sets the duration of the podcast
     */
    public void setSecondsGone(final int secondsGone) {
        this.secondsGone = secondsGone;
    }

    /**
     * execute
     */
    @Override
    public void execute() {

    }
}
