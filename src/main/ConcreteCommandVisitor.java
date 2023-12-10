package main;

import main.commands.pageSystem.ChangePage;
import main.commands.pageSystem.PrintCurrentPage;
import main.commands.player.admin.AddUser;
import main.commands.player.admin.DeleteUser;
import main.commands.player.admin.ShowAlbums;
import main.commands.player.admin.ShowPodcasts;
import main.commands.player.artist.AddAlbum;
import main.commands.player.artist.AddEvent;
import main.commands.player.artist.AddMerch;
import main.commands.player.artist.RemoveAlbum;
import main.commands.player.artist.RemoveEvent;
import main.commands.player.host.AddAnnouncement;
import main.commands.player.host.AddPodcast;
import main.commands.player.host.RemoveAnnouncement;
import main.commands.player.host.RemovePodcast;
import main.commands.player.statistics.GetTop5Albums;
import main.commands.player.statistics.GetTop5Artists;
import main.commands.player.statistics.GetTop5Playlists;
import main.commands.player.statistics.GetTop5Songs;
import main.commands.searchBar.Search;
import main.commands.searchBar.Select;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.commands.player.AddRemoveInPlaylist;
import main.commands.player.CreatePlayList;
import main.commands.player.Like;
import main.commands.player.Next;
import main.commands.player.Prev;
import main.commands.player.PlayPause;
import main.commands.player.Repeat;
import main.commands.player.Shuffle;
import main.commands.player.Status;
import main.commands.player.Backward;
import main.commands.player.Forward;
import main.commands.player.Follow;
import main.commands.player.SwitchVisibility;
import main.commands.player.Load;
import main.commands.player.statistics.GetOnlineUsers;
import main.commands.player.statistics.GetAllUsers;
import main.commands.player.ShowPlaylists;
import main.commands.player.ShowPreferredSongs;
import main.commands.player.user.SwitchConnectionStatus;
import main.users.Artist;
import main.users.Host;
import main.users.User;

import java.util.ArrayList;

public class ConcreteCommandVisitor implements CommandVisitor {

    private ArrayList<Command> commands;
    private SearchBar input;
    private User user;
    private ArrayList<Song> songs;
    private ArrayList<Playlist> everyPlaylist;
    private ArrayList<Podcast> podcasts;
    private ArrayList<User> users;
    private ArrayList<Album> albums;
    private ArrayList<Artist> artists;
    private ArrayList<Host> hosts;
    private Artist artist;
    private Host host;


    /**
     * constructor
     * @param commands1 - the list of commands
     * @param input1 - the search bar
     * @param user1 - the current user
     * @param songs1 - the list of songs
     * @param everyPlaylist1 - the list of playlists
     * @param podcasts1 - the list of podcasts
     * @param users1 - the list of users
     * @param albums1 - the list of albums
     * @param artists1 - the list of artists
     * @param hosts1 - the list of hosts
     * @param artist1 - the current artist
     * @param host1 - the current host
     */
    public void setExecutor(final ArrayList<Command> commands1, final SearchBar input1,
                            final User user1, final ArrayList<Song> songs1,
                            final ArrayList<Playlist> everyPlaylist1,
                            final ArrayList<Podcast> podcasts1, final ArrayList<User> users1,
                            final ArrayList<Album> albums1, final ArrayList<Artist> artists1,
                            final ArrayList<Host> hosts1, final Artist artist1, final Host host1) {
        this.commands = commands1;
        this.input = input1;
        this.user = user1;
        this.songs = songs1;
        this.everyPlaylist = everyPlaylist1;
        this.podcasts = podcasts1;
        this.users = users1;
        this.albums = albums1;
        this.artists = artists1;
        this.hosts = hosts1;
        this.artist = artist1;
        this.host = host1;
    }

    /**
     * @param search - the command to be executed
     */
    @Override
    public void visit(final Search search) {
        search.execute(user, everyPlaylist, podcasts, albums, artists, hosts);
    }

    /**
     * @param select - the command to be executed
     */
    @Override
    public void visit(final Select select) {
        select.execute(user, everyPlaylist);
    }

    /**
     * @param load - the command to be executed
     */
    @Override
    public void visit(final Load load) {
        load.execute(user, everyPlaylist, podcasts, albums);
    }

    /**
     * @param playPause - the command to be executed
     */
    @Override
    public void visit(final PlayPause playPause) {
        playPause.execute(user);
    }

    /**
     * @param repeat - the command to be executed
     */
    @Override
    public void visit(final Repeat repeat) {
        repeat.execute(commands, user);
    }

    /**
     * @param status - the command to be executed
     */
    @Override
    public void visit(final Status status) {
        status.execute(user);
    }

    /**
     * @param shuffle - the command to be executed
     */
    @Override
    public void visit(final Shuffle shuffle) {
        shuffle.execute(input, user);
    }

    /**
     * @param createPlayList - the command to be executed
     */
    @Override
    public void visit(final CreatePlayList createPlayList) {
        createPlayList.execute(input, user, everyPlaylist);
    }

    /**
     * @param addRemoveInPlaylist - the command to be executed
     */
    @Override
    public void visit(final AddRemoveInPlaylist addRemoveInPlaylist) {
        addRemoveInPlaylist.execute(input, user);
    }

    /**
     * @param like - the command to be executed
     */
    @Override
    public void visit(final Like like) {
        like.execute(user, songs);
    }

    /**
     * @param showPlaylists - the command to be executed
     */
    @Override
    public void visit(final ShowPlaylists showPlaylists) {
        showPlaylists.execute(user);
    }

    /**
     * @param showPreferredSongs - the command to be executed
     */
    @Override
    public void visit(final ShowPreferredSongs showPreferredSongs) {
        showPreferredSongs.execute(user);
    }

    /**
     * @param next - the command to be executed
     */
    @Override
    public void visit(final Next next) {
        next.execute(user);
    }

    /**
     * @param prev - the command to be executed
     */
    @Override
    public void visit(final Prev prev) {
        prev.execute(user);
    }

    /**
     * @param forward - the command to be executed
     */
    @Override
    public void visit(final Forward forward) {
        forward.execute(user);
    }

    /**
     * @param backward - the command to be executed
     */
    @Override
    public void visit(final Backward backward) {
        backward.execute(user);
    }

    /**
     * @param follow - the command to be executed
     */
    @Override
    public void visit(final Follow follow) {
        follow.execute(user, everyPlaylist);
    }

    /**
     * @param switchVisibility - the command to be executed
     */
    @Override
    public void visit(final SwitchVisibility switchVisibility) {
        switchVisibility.execute(user);
    }

    /**
     * @param getTop5Playlists - the command to be executed
     */
    @Override
    public void visit(final GetTop5Playlists getTop5Playlists) {
        getTop5Playlists.execute(everyPlaylist, users);
    }

    /**
     * @param getTop5Songs - the command to be executed
     */
    @Override
    public void visit(final GetTop5Songs getTop5Songs) {
        getTop5Songs.execute(songs);
    }

    /**
     * @param changePage - the command to be executed
     */
    @Override
    public void visit(final ChangePage changePage) {
        changePage.execute(user);
    }

    /**
     * @param switchConnectionStatus - the command to be executed
     */
    @Override
    public void visit(final SwitchConnectionStatus switchConnectionStatus) {
        switchConnectionStatus.execute(user, artist, host);
    }

    /**
     * @param getOnlineUsers - the command to be executed
     */
    @Override
    public void visit(final GetOnlineUsers getOnlineUsers) {
        getOnlineUsers.execute(users);
    }

    /**
     * @param addUser - the command to be executed
     */
    @Override
    public void visit(final AddUser addUser) {
        addUser.execute(songs, podcasts, users, artists, hosts);
    }

    /**
     * @param addAlbum - the command to be executed
     */
    @Override
    public void visit(final AddAlbum addAlbum) {
        addAlbum.execute(user, artist, host, songs, albums, users);
    }

    /**
     * @param showAlbums - the command to be executed
     */
    @Override
    public void visit(final ShowAlbums showAlbums) {
        showAlbums.execute(artist);
    }

    /**
     * @param printCurrentPage - the command to be executed
     */
    @Override
    public void visit(final PrintCurrentPage printCurrentPage) {
        printCurrentPage.execute(user, artists, hosts);
    }

    /**
     * @param addEvent - the command to be executed
     */
    @Override
    public void visit(final AddEvent addEvent) {
        addEvent.execute(user, artist, host);
    }

    /**
     * @param addMerch - the command to be executed
     */
    @Override
    public void visit(final AddMerch addMerch) {
        addMerch.execute(user, artist, host);
    }

    /**
     * @param getAllUsers - the command to be executed
     */
    @Override
    public void visit(final GetAllUsers getAllUsers) {
        getAllUsers.execute(users, artists, hosts);
    }

    /**
     * @param deleteUser - the command to be executed
     */
    @Override
    public void visit(final DeleteUser deleteUser) {
        deleteUser.execute(users, artists, hosts, songs, everyPlaylist, albums, podcasts);
    }

    /**
     * @param addPodcast - the command to be executed
     */
    @Override
    public void visit(final AddPodcast addPodcast) {
        addPodcast.execute(user, artist, host, users, podcasts);
    }

    /**
     * @param addAnnouncement - the command to be executed
     */
    @Override
    public void visit(final AddAnnouncement addAnnouncement) {
        addAnnouncement.execute(user, artist, host);
    }

    /**
     * @param removeAnnouncement - the command to be executed
     */
    @Override
    public void visit(final RemoveAnnouncement removeAnnouncement) {
        removeAnnouncement.execute(host);
    }

    /**
     * @param showPodcasts - the command to be executed
     */
    @Override
    public void visit(final ShowPodcasts showPodcasts) {
        showPodcasts.execute(host);
    }

    /**
     * @param removeAlbum - the command to be executed
     */
    @Override
    public void visit(final RemoveAlbum removeAlbum) {
        removeAlbum.execute(user, artist, host, users, songs, albums);
    }

    /**
     * @param removePodcast - the command to be executed
     */
    @Override
    public void visit(final RemovePodcast removePodcast) {
        removePodcast.execute(user, artist, host, users, podcasts);
    }

    /**
     * @param removeEvent - the command to be executed
     */
    @Override
    public void visit(final RemoveEvent removeEvent) {
        removeEvent.execute(user, artist, host);
    }

    /**
     * @param getTop5Albums - the command to be executed
     */
    @Override
    public void visit(final GetTop5Albums getTop5Albums) {
        getTop5Albums.execute(albums);
    }

    /**
     * @param getTop5Artists - the command to be executed
     */
    @Override
    public void visit(final GetTop5Artists getTop5Artists) {
        getTop5Artists.execute(artists);
    }

}
