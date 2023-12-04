package main.Commands.Player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.Commands.Types.Playlist;

public class CreatePlayList implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String playlistName;
    private final String message;
    private  Playlist playlist;


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    public CreatePlayList(final String command, final String user, final int timestamp,
                          final String playlistName, final String message) {

        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        this.message = message;

        this.playlist = new Playlist(playlistName, user);
    }


    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * @return the playlistName
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /**
     * @param playlistName the playlistName to set
     */
    @JsonIgnore
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return playlist
     */
    public Playlist getPlaylist() {
        return playlist;
    }

    /**
     * @param playlist the playlist to set
     */
    @JsonIgnore
    public void setPlaylist(final Playlist playlist) {
        this.playlist = playlist;
    }

    /**
     * @return the songs
     */
    @Override
    public void execute() {

    }
}
