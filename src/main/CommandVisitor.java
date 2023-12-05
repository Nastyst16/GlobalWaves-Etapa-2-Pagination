package main;

import main.commands.pageSystem.ChangePage;
import main.commands.searchBar.*;
import main.commands.player.*;
import main.commands.user.GetOnlineUsers;
import main.commands.user.SwitchConnectionStatus;

public interface CommandVisitor {
    void visit(Search search);
    void visit(Select select);
    void visit(Load load);
    void visit(PlayPause playPause);
    void visit(Repeat repeat);
    void visit(Status status);
    void visit(Shuffle shuffle);
    void visit(CreatePlayList createPlayList);
    void visit(AddRemoveInPlaylist addRemoveInPlaylist);
    void visit(Like like);
    void visit(ShowPlaylists showPlaylists);
    void visit(ShowPreferredSongs showPreferredSongs);
    void visit(Next next);
    void visit(Prev prev);
    void visit(Forward forward);
    void visit(Backward backward);
    void visit(Follow follow);
    void visit(SwitchVisibility switchVisibility);
    void visit(GetTop5Playlists getTop5Playlists);
    void visit(GetTop5Songs getTop5Songs);


//    Stage 2:
    void visit(ChangePage changePage);
    void visit(SwitchConnectionStatus switchConnectionStatus);
    void visit(GetOnlineUsers getOnlineUsers);

}
