package main;

import main.commands.pageSystem.ChangePage;
import main.commands.pageSystem.PrintCurrentPage;
import main.commands.player.admin.AddUser;
import main.commands.player.admin.ShowAlbums;
import main.commands.player.artist.AddAlbum;
import main.commands.player.statistics.GetOnlineUsers;
import main.commands.player.statistics.GetTop5Playlists;
import main.commands.player.statistics.GetTop5Songs;
import main.commands.searchBar.*;
import main.commands.types.*;
import main.commands.player.*;
import main.commands.player.user.*;

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





    public void setExecutor(ArrayList<Command> commands, SearchBar input, User user, ArrayList<Song> songs, ArrayList<Playlist> everyPlaylist,
                            ArrayList<Podcast> podcasts, ArrayList<User> users, ArrayList<Album> albums) {
        this.commands = commands;
        this.input = input;
        this.user = user;
        this.songs = songs;
        this.everyPlaylist = everyPlaylist;
        this.podcasts = podcasts;
        this.users = users;
        this.albums = albums;
    }

    @Override
    public void visit(Search search) {
        search.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(Select select) {
        select.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(Load load) {
        load.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(PlayPause playPause) {
        playPause.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(Repeat repeat) {
        repeat.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(Status status) {
        status.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(Shuffle shuffle) {
        shuffle.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(CreatePlayList createPlayList) {
        createPlayList.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(AddRemoveInPlaylist addRemoveInPlaylist) {
        addRemoveInPlaylist.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(Like like) {
        like.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(ShowPlaylists showPlaylists) {
        showPlaylists.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(ShowPreferredSongs showPreferredSongs) {
        showPreferredSongs.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(Next next) {
        next.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(Prev prev) {
        prev.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(Forward forward) {
        forward.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(Backward backward) {
        backward.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(Follow follow) {
        follow.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(SwitchVisibility switchVisibility) {
        switchVisibility.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(GetTop5Playlists getTop5Playlists) {
        getTop5Playlists.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(GetTop5Songs getTop5Songs) {
        getTop5Songs.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(ChangePage changePage) {
        changePage.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(SwitchConnectionStatus switchConnectionStatus) {
        switchConnectionStatus.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(GetOnlineUsers getOnlineUsers) {
        getOnlineUsers.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(AddUser addUser) {
        addUser.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(AddAlbum addAlbum) {
        addAlbum.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(ShowAlbums showAlbums) {
        showAlbums.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }

    @Override
    public void visit(PrintCurrentPage printCurrentPage) {
        printCurrentPage.execute(commands, input, user, songs, everyPlaylist, podcasts, users, albums);
    }
}
