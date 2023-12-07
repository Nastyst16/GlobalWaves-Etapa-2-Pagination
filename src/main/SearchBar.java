package main;

import main.commands.types.Song;

import java.util.ArrayList;
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
    private int seed;


//    etapa 2
    private String nextPage;
    private String description;
    private String name;
    private String date;
    private int price;
    private int age;
    private String city;
    private int releaseYear;
    private ArrayList<Song> songs;







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


//    etapa 2


    public void setSeed(int seed) {
        this.seed = seed;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}
