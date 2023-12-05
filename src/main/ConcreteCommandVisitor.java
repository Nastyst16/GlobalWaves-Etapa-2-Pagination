package main;

import main.commands.pageSystem.ChangePage;
import main.commands.searchBar.*;
import main.commands.types.*;
import main.commands.player.*;
import main.commands.user.GetOnlineUsers;
import main.commands.user.SwitchConnectionStatus;

import java.util.ArrayList;

public class ConcreteCommandVisitor implements CommandVisitor {

    private ArrayList<Command> commands;
    private SearchBar input;
    private User user;
    private ArrayList<Song> songs;
    private ArrayList<Playlist> everyPlaylist;
    private ArrayList<Podcast> podcasts;





    public void setExecutor(ArrayList<Command> commands, SearchBar input, User user, ArrayList<Song> songs, ArrayList<Playlist> everyPlaylist,
                            ArrayList<Podcast> podcasts) {
        this.commands = commands;
        this.input = input;
        this.user = user;
        this.songs = songs;
        this.everyPlaylist = everyPlaylist;
        this.podcasts = podcasts;
    }

    @Override
    public void visit(Search search) {
        search.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(Select select) {
        select.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(Load load) {
        load.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(PlayPause playPause) {
        playPause.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(Repeat repeat) {
        repeat.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(Status status) {
        status.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(Shuffle shuffle) {
        shuffle.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(CreatePlayList createPlayList) {
        createPlayList.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(AddRemoveInPlaylist addRemoveInPlaylist) {
        addRemoveInPlaylist.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(Like like) {
        like.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(ShowPlaylists showPlaylists) {
        showPlaylists.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(ShowPreferredSongs showPreferredSongs) {
        showPreferredSongs.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(Next next) {
        next.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(Prev prev) {
        prev.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(Forward forward) {
        forward.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(Backward backward) {
        backward.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(Follow follow) {
        follow.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(SwitchVisibility switchVisibility) {
        switchVisibility.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(GetTop5Playlists getTop5Playlists) {
        getTop5Playlists.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(GetTop5Songs getTop5Songs) {
        getTop5Songs.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(ChangePage changePage) {
        changePage.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(SwitchConnectionStatus switchConnectionStatus) {
        switchConnectionStatus.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }

    @Override
    public void visit(GetOnlineUsers getOnlineUsers) {
        getOnlineUsers.execute(commands, input, user, songs, everyPlaylist, podcasts);
    }
}
