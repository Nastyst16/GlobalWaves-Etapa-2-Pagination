package main;

import main.Commands.Player.*;
import main.Commands.SearchBar.Search;
import main.Commands.SearchBar.Select;

public class ConcreteCommandVisitor implements CommandVisitor {
@Override
    public void visit(Search search) {
        search.execute();
    }

    @Override
    public void visit(Select select) {
        select.execute();
    }

    @Override
    public void visit(Load load) {
        load.execute();
    }

    @Override
    public void visit(PlayPause playPause) {
        playPause.execute();
    }

    @Override
    public void visit(Repeat repeat) {
        repeat.execute();
    }

    @Override
    public void visit(Status status) {
        status.execute();
    }

    @Override
    public void visit(Shuffle shuffle) {
        shuffle.execute();
    }

    @Override
    public void visit(CreatePlayList createPlayList) {
        createPlayList.execute();
    }

    @Override
    public void visit(AddRemoveInPlaylist addRemoveInPlaylist) {
        addRemoveInPlaylist.execute();
    }

    @Override
    public void visit(Like like) {
        like.execute();
    }

    @Override
    public void visit(ShowPlaylists showPlaylists) {
        showPlaylists.execute();
    }

    @Override
    public void visit(ShowPreferredSongs showPreferredSongs) {
        showPreferredSongs.execute();
    }

    @Override
    public void visit(Next next) {
        next.execute();
    }

    @Override
    public void visit(Prev prev) {
        prev.execute();
    }

    @Override
    public void visit(Forward forward) {
        forward.execute();
    }

    @Override
    public void visit(Backward backward) {
        backward.execute();
    }

    @Override
    public void visit(Follow follow) {
        follow.execute();
    }

    @Override
    public void visit(SwitchVisibility switchVisibility) {
        switchVisibility.execute();
    }

    @Override
    public void visit(GetTop5Playlists getTop5Playlists) {
        getTop5Playlists.execute();
    }

    @Override
    public void visit(GetTop5Songs getTop5Songs) {
        getTop5Songs.execute();
    }
}
