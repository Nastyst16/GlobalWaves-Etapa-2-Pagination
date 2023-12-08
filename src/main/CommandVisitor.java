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
import main.commands.player.statistics.GetAllUsers;
import main.commands.player.statistics.GetOnlineUsers;
import main.commands.player.statistics.GetTop5Playlists;
import main.commands.player.statistics.GetTop5Songs;
import main.commands.searchBar.*;
import main.commands.player.*;
import main.commands.player.user.*;

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
    void visit(AddUser addUser);

    void visit(AddAlbum addAlbum);
    void visit(ShowAlbums showAlbums);
    void visit(PrintCurrentPage printCurrentPage);
    void visit(AddEvent addEvent);
    void visit(AddMerch addMerch);
    void visit(GetAllUsers getAllUsers);
    void visit(DeleteUser deleteUser);
    void visit(AddPodcast addPodcast);
    void visit(AddAnnouncement addAnnouncement);
    void visit(RemoveAnnouncement removeAnnouncement);
    void visit(ShowPodcasts showPodcasts);
    void visit(RemoveAlbum removeAlbum);
}
