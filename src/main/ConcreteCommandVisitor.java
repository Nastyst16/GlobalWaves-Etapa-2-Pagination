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
import main.commands.player.host.AddAnnouncement;
import main.commands.player.host.AddPodcast;
import main.commands.player.host.RemoveAnnouncement;
import main.commands.player.host.RemovePodcast;
import main.commands.player.statistics.GetAllUsers;
import main.commands.player.statistics.GetOnlineUsers;
import main.commands.player.statistics.GetTop5Playlists;
import main.commands.player.statistics.GetTop5Songs;
import main.commands.searchBar.*;
import main.commands.types.*;
import main.commands.player.*;
import main.commands.player.user.*;
import main.users.Artist;
import main.users.Host;

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





    public void setExecutor(ArrayList<Command> commands, SearchBar input, User user, ArrayList<Song> songs,
                            ArrayList<Playlist> everyPlaylist, ArrayList<Podcast> podcasts,
                            ArrayList<User> users, ArrayList<Album> albums, ArrayList<Artist> artists,
                            ArrayList<Host> hosts, Artist artist, Host host) {
        this.commands = commands;
        this.input = input;
        this.user = user;
        this.songs = songs;
        this.everyPlaylist = everyPlaylist;
        this.podcasts = podcasts;
        this.users = users;
        this.albums = albums;
        this.artists = artists;
        this.hosts = hosts;
        this.artist = artist;
        this.host = host;
    }

    @Override
    public void visit(Search search) {
        search.execute(user, songs, everyPlaylist, podcasts, albums, artists, hosts);
    }

    @Override
    public void visit(Select select) {
        select.execute(user, everyPlaylist);
    }

    @Override
    public void visit(Load load) {
        load.execute(user, everyPlaylist, podcasts, albums);
    }

    @Override
    public void visit(PlayPause playPause) {
        playPause.execute(user);
    }

    @Override
    public void visit(Repeat repeat) {
        repeat.execute(commands, user);
    }

    @Override
    public void visit(Status status) {
        status.execute(user);
    }

    @Override
    public void visit(Shuffle shuffle) {
        shuffle.execute(input, user);
    }

    @Override
    public void visit(CreatePlayList createPlayList) {
        createPlayList.execute(input, user, everyPlaylist);
    }

    @Override
    public void visit(AddRemoveInPlaylist addRemoveInPlaylist) {
        addRemoveInPlaylist.execute(input, user);
    }

    @Override
    public void visit(Like like) {
        like.execute(user, songs);
    }

    @Override
    public void visit(ShowPlaylists showPlaylists) {
        showPlaylists.execute(user);
    }

    @Override
    public void visit(ShowPreferredSongs showPreferredSongs) {
        showPreferredSongs.execute(user);
    }

    @Override
    public void visit(Next next) {
        next.execute(user);
    }

    @Override
    public void visit(Prev prev) {
        prev.execute(user);
    }

    @Override
    public void visit(Forward forward) {
        forward.execute(user);
    }

    @Override
    public void visit(Backward backward) {
        backward.execute(user);
    }

    @Override
    public void visit(Follow follow) {
        follow.execute(user, everyPlaylist);
    }

    @Override
    public void visit(SwitchVisibility switchVisibility) {
        switchVisibility.execute(user);
    }

    @Override
    public void visit(GetTop5Playlists getTop5Playlists) {
        getTop5Playlists.execute(everyPlaylist, users);
    }

    @Override
    public void visit(GetTop5Songs getTop5Songs) {
        getTop5Songs.execute(songs);
    }

    @Override
    public void visit(ChangePage changePage) {
        changePage.execute(user);
    }

    @Override
    public void visit(SwitchConnectionStatus switchConnectionStatus) {
        switchConnectionStatus.execute(user);
    }

    @Override
    public void visit(GetOnlineUsers getOnlineUsers) {
        getOnlineUsers.execute(users);
    }

    @Override
    public void visit(AddUser addUser) {
        addUser.execute(songs, podcasts, users, artists, hosts);
    }

    @Override
    public void visit(AddAlbum addAlbum) {
        addAlbum.execute(artist, songs, users, artists, albums);
    }

    @Override
    public void visit(ShowAlbums showAlbums) {
        showAlbums.execute(artist);
    }

    @Override
    public void visit(PrintCurrentPage printCurrentPage) {
        printCurrentPage.execute(user, artists, hosts);
    }

    @Override
    public void visit(AddEvent addEvent) {
        addEvent.execute(user, artist, host);
    }

    @Override
    public void visit(AddMerch addMerch) {
        addMerch.execute(user, artist, host);
    }

    @Override
    public void visit(GetAllUsers getAllUsers) {
        getAllUsers.execute(users, artists, hosts);
    }

    @Override
    public void visit(DeleteUser deleteUser) {
        deleteUser.execute(users, artists, hosts, songs, everyPlaylist, albums, podcasts);
    }

    @Override
    public void visit(AddPodcast addPodcast) {
        addPodcast.execute(host, users, podcasts, hosts);
    }

    @Override
    public void visit(AddAnnouncement addAnnouncement) {
        addAnnouncement.execute(user, artist, host);
    }

    @Override
    public void visit(RemoveAnnouncement removeAnnouncement) {
        removeAnnouncement.execute(host);
    }

    @Override
    public void visit(ShowPodcasts showPodcasts) {
        showPodcasts.execute(host);
    }

    @Override
    public void visit(RemoveAlbum removeAlbum) {
        removeAlbum.execute(user, artist, host, users, songs, albums);
    }

    @Override
    public void visit(RemovePodcast removePodcast) {
        removePodcast.execute(user, artist, host, users, songs, podcasts);
    }

}
