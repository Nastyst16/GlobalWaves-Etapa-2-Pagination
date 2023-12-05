package main.commands.types;

public interface Type {

    /**
     * get name
     * @return
     */
    public int getSecondsGone();

    /**
     * set seconds gone
     * @param secondsGone
     */
    public void setSecondsGone(int secondsGone);

    /**
     * execute
     */
    public void execute();

    /**
     * get duration
     * @return
     */
    int getDuration();

    /**
     * get name
     * @return
     */
    Object getName();
}
