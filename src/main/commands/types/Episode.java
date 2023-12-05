package main.commands.types;

import fileio.input.EpisodeInput;

public class Episode implements Type {
    private final String name;
    private final int duration;
    private int secondsGone;


    /**
     * Constructor for Episode
     * @param episodeInput
     */
    public Episode(final EpisodeInput episodeInput) {
        this.name = episodeInput.getName();
        this.duration = episodeInput.getDuration();
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for duration
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Getter for secondsGone
     * @return secondsGone
     */
    public int getSecondsGone() {
        return secondsGone;
    }

    /**
     * Setter for secondsGone
     * @param secondsGone
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
