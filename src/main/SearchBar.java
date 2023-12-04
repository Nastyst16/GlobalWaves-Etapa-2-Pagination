package main;

import java.util.Map;

public class SearchBar {
    private String command;
    private String username;
    private int timestamp;
    private String type;
    private Map<String, Object> filters;
    private int itemNumber;
    private String playlistName;
    private int playlistId;
    private  int seed;

    /**
     * default constructor
     */
    public SearchBar() {

    }

    /** get command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /** set command
     * @param command the command to set
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /** get username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /** set username
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /** get timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /** set timestamp
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /** get type
     * @return the type
     */
    public String getType() {
        return type;
    }

    /** set type
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /** get filters
     * @return the filters
     */
    public Map<String, Object> getFilters() {
        return filters;
    }

    /** set filters
     * @param filters the filters to set
     */
    public void setFilters(final Map<String, Object> filters) {
        this.filters = filters;
    }

    /** get item number
     * @return the itemNumber
     */
    public int getItemNumber() {
        return itemNumber;
    }

    /** set item number
     * @param itemNumber the itemNumber to set
     */
    public void setItemNumber(final int itemNumber) {
        this.itemNumber = itemNumber;
    }

    /** get playlist name
     * @return the playlistName
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /** set playlist name
     * @param playlistName the playlistName to set
     */
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /** get playlist id
     * @return the playlistId
     */
    public int getPlaylistId() {
        return playlistId;
    }

    /** set playlist id
     * @param playlistId the playlistId to set
     */
    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }

    /**
     * @return the seed
     */
    public int getSeed() {
        return seed;
    }
}
