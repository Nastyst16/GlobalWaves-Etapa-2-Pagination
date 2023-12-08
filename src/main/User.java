package main;

import com.fasterxml.jackson.annotation.JsonIgnore;

import main.commands.searchBar.*;
import main.commands.types.*;

import java.util.ArrayList;

public class User {
    private String username;
    private final int age;
    private final String city;
    private int loadMade;
    private boolean paused;
    private int secondsGone;
    private int repeatStatus;
    private int typeFoundBySearch;
    private int typeSelected;
    private int typeLoaded;
    private boolean shuffle;
    private int remainingTime;
    private String repeatString;
    private boolean isNext;

    private Search currentSearch;
    private ArrayList<Song> everySong;
    private ArrayList<Podcast> everyPodcast;

    private Type currentType;
    private int prevTimestamp;
    private Podcast currentPodcast;
    private Playlist currentPlaylist;
    private ArrayList<Integer> originalIndices;
    private ArrayList<Integer> shuffledIndices;
    private String selectedName;
    private int shuffleSeed;
    private Select currentSelect;
    private ArrayList<Playlist> playListList;
    private ArrayList<Song> likedSongs;
    private ArrayList<Playlist> followedPlaylists;
    private ArrayList<String> likedPlaylists;
    private ArrayList<Podcast> podcastsPlayed;
    private Playlist selectedPlaylist;

//    Stage 2 variables
    private String currentPage;
    private boolean online;
    private String selectedPageOwner;



    public User(final String username, final int age, final String city,
                final ArrayList<Song> everySong, final ArrayList<Podcast> everyPodcast) {
        this.username = username;
        this.age = age;
        this.city = city;

        loadMade = 0;
        paused = false;
        secondsGone = 0;
        repeatStatus = -1;
        typeFoundBySearch = -1;
        typeSelected = -1;
        typeLoaded = -1;
        shuffle = false;
        currentType = null;

        currentSelect = null;
        currentSearch = null;
        playListList = new ArrayList<>();
        shuffledIndices = new ArrayList<>();
        originalIndices = new ArrayList<>();
        likedSongs = new ArrayList<>();
        likedPlaylists = new ArrayList<>();
        podcastsPlayed = new ArrayList<>();
        followedPlaylists = new ArrayList<>();


        this.everyPodcast = everyPodcast;

//        copy the songs
        this.everySong = new ArrayList<>();
        for (Song song : everySong) {
            Song copySong = new Song();
            copySong.setName(song.getName());
            copySong.setDuration(song.getDuration());
            copySong.setAlbum(song.getAlbum());
            copySong.setTags(song.getTags());
            copySong.setLyrics(song.getLyrics());
            copySong.setGenre(song.getGenre());
            copySong.setReleaseYear(song.getReleaseYear());
            copySong.setArtist(song.getArtist());
            copySong.setSecondsGone(song.getSecondsGone());
            copySong.setNumberOfLikes(song.getNumberOfLikes());

            this.everySong.add(copySong);
        }

//        Stage 2:
        currentPage = "Home";
        online = true;
        selectedPageOwner = null;
    }


    /**
     * setting the liked songs
     * @param song the song liked
     * @param songs the songs
     * @return
     */
    public boolean setLikedSongs(final Song song, final ArrayList<Song> songs) {
        if (this.likedSongs.contains(song)) {
            this.likedSongs.remove(song);
            song.setNumberOfLikes(song.getNumberOfLikes() - 1);

            for (Song tmp : songs) {
                if (tmp.getName().equals(song.getName())) {
                    tmp.setNumberOfLikes(tmp.getNumberOfLikes() - 1);
                    break;
                }
            }
            return false;

        } else {
            this.likedSongs.add(song);
            song.setNumberOfLikes(song.getNumberOfLikes() + 1);

            for (Song tmp : songs) {
                if (tmp.getName().equals(song.getName())) {
                    tmp.setNumberOfLikes(tmp.getNumberOfLikes() + 1);
                    break;
                }
            }
            return true;
        }
    }

    /**
     * likes of dislikes a playlist
     * @return true if the playlist was liked, false otherwise
     */
    public boolean setLikedPlaylist() {
        if (this.likedPlaylists.contains(this.selectedName)) {
            likedPlaylists.remove(this.selectedName);
            return false;
        } else {
            likedPlaylists.add(this.selectedName);
            return true;
        }
    }

    /**
     * treating the repeat status
     * @param user current user
     */
    public void treatingRepeatStatus(final User user) {
        Type currentType = user.getCurrentType();

        user.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());

//        if type loaded is a playlist and repeat status is on repeat current song
//        and the time remaining <= 0 we whould update the seconds;
        if (user.getTypeLoaded() == 2 && user.getRepeatStatus() == 2
                && user.getRemainingTime() <= 0) {

            while (currentType.getSecondsGone() >= currentType.getDuration()) {
                currentType.setSecondsGone(currentType.getSecondsGone()
                        - currentType.getDuration());
            }
            user.setRemainingTime(currentType.getDuration());
            this.currentType = currentType;
            user.setCurrentType(currentType);
        }

        if (user.getTypeLoaded() == 0 && user.getRepeatStatus() == 0 && user.getRemainingTime() < 0) {

            user.setCurrentType(null);
            user.setTypeLoaded(-1);
            user.setShuffle(false);
            return;
        }


//        if the type loaded is a song or a podcast
        if (user.getTypeLoaded() == 0 || user.getTypeLoaded() == 1) {

            if (user.getRepeatStatus() == 1 && user.getRemainingTime() < 0) {
                user.setRepeatStatus(0);
                user.setRepeatString("No Repeat");

                currentType.setSecondsGone(currentType.getSecondsGone()
                        - currentType.getDuration());

                user.setRemainingTime((currentType.getDuration())
                        - currentType.getSecondsGone());
            } else if (user.getRepeatStatus() == 2) {
//                if repeat infinite
                while (user.getRemainingTime() < 0) {

                    currentType.setSecondsGone(currentType.getSecondsGone()
                            - currentType.getDuration());
                    user.setRemainingTime((currentType.getDuration())
                            - currentType.getSecondsGone());
                }
            }
//            if the type loaded is a playlist
        } else if (user.getTypeLoaded() == 2) {

            if (user.getRepeatStatus() == 1 && user.getRemainingTime() < 0) {

                int index = user.getCurrentPlaylist().getSongList().size() - 1;
//                if the last song is playing
                if (user.getCurrentPlaylist().getSongList().get(index).
                        getName().equals(user.getSelectedName())) {
                    currentType = user.getCurrentPlaylist().getSongList().get(0);
                    currentType.setSecondsGone(currentType.getDuration() + user.getRemainingTime());
                }
            }
        }


        if (user.getTypeLoaded() == 1) {
//            comutam in episodul urmator pana cand este nevoie
            while (user.getRemainingTime() <= 0) {
                Podcast podcast = user.getCurrentPodcast();

                user.getCurrentPodcast().setLastRemainingEpisode(user.
                        getCurrentPodcast().getLastRemainingEpisode() + 1);
                int indexEpisode = user.getCurrentPodcast().getLastRemainingEpisode();

                Episode newEpisode = user.getCurrentPodcast().getEpisodesList().get(indexEpisode);

                currentType = newEpisode;
                currentType.setSecondsGone(Math.abs(user.getRemainingTime()));

                user.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());
            }
        }

        if (user.getTypeLoaded() == 2) {

//            commuting the next song in playlist
            while (user.getRemainingTime() <= 0) {
                Playlist playlist = user.getCurrentPlaylist();

                int index = user.getCurrentPlaylist().getSongList().size() - 1;

//                if it is the last song in playlist
                if (user.getCurrentPlaylist().getSongList().get(index).
                        getName().equals(user.getCurrentType().getName())) {

                    if (user.getRepeatStatus() == 0) {
                        user.setCurrentType(null);
                        user.setTypeLoaded(-1);
                        user.setShuffle(false);
                        return;
                    }
                }

                if (user.getRepeatStatus() == 1 && user.getCurrentPlaylist().
                        getSongList().get(index).getName().equals(currentType.getName())) {

                    int secsGone = currentType.getSecondsGone() - currentType.getDuration();

                    currentType = user.getCurrentPlaylist().getSongList().get(0);
                    currentType.setSecondsGone(secsGone);
                    user.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());

                    break;
                }

                int indexSong = playlist.getSongList().indexOf((Song) (currentType));

                Song newSong = null;
                if (user.isShuffle()) {

                    int nextShuffledIndex = user.getShuffledIndices().indexOf(indexSong) + 1;

                    if (nextShuffledIndex == user.getShuffledIndices().size()
                            && user.getRepeatStatus() == 1) {

                        int firstIndex = user.getShuffledIndices().get(0);
                        currentType = user.getCurrentPlaylist().getSongList().get(firstIndex);
                        currentType.setSecondsGone(Math.abs(user.getRemainingTime()));
                        user.setRemainingTime(currentType.getDuration()
                                - currentType.getSecondsGone());

                        continue;

                    } else if (nextShuffledIndex == user.getShuffledIndices().size()) {
//                        end of playlist;
                        user.setCurrentType(null);
                        user.setTypeLoaded(-1);
                        user.setShuffle(false);
                        return;
                    }
//
                    nextShuffledIndex = user.getShuffledIndices().get(nextShuffledIndex);

                    newSong = user.getCurrentPlaylist().getSongList().get(nextShuffledIndex);
                    currentType = newSong;

//                if repeat current song we wont change the currentType
                } else if (user.getRepeatStatus() != 2) {
                    if (playlist.getSongList().size() - 1 > indexSong) {

                        currentType.setSecondsGone(0);

                        newSong = playlist.getSongList().get(indexSong + 1);
                        currentType = newSong;
                    } else {
                        break;
                    }
                }

                currentType.setSecondsGone(Math.abs(user.getRemainingTime()));

                user.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());

                if (user.isNext()) {
                    this.currentType = currentType;
                    user.setCurrentType(currentType);
                    return;
                }
            }
        }
        this.currentType = currentType;
        user.setCurrentType(currentType);
    }


//    Stage 2 methods:




    /**
     * get the age
     * @return the age
     */
    public void addPlaylistToList(final Playlist playList) {
        playListList.add(playList);
    }

    /**
     * get the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username
     * @param username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * gets the load made
     * @return the load made
     */
    public int getLoadMade() {
        return loadMade;
    }

    /**
     * set the loadMade
     * @param loadMade the loadMade
     */
    public void setLoadMade(final int loadMade) {
        this.loadMade = loadMade;
    }

    /**
     * get the pause status
     * @return the pause status
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * set the paused
     * @param paused the paused
     */
    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    /**
     * get the secondsGone
     * @return the secondsGone
     */
    public int getSecondsGone() {
        return secondsGone;
    }

    /**
     * set the secondsGone
     * @param secondsGone the secondsGone
     */
    @JsonIgnore
    public void setSecondsGone(final int secondsGone) {
        this.secondsGone = secondsGone;
    }

    /**
     * get the repeatStatus
     * @return the repeatStatus
     */
    public int getRepeatStatus() {
        return repeatStatus;
    }

    /**
     * set the repeatStatus
     * @param repeatStatus the repeatStatus
     */
    @JsonIgnore
    public void setRepeatStatus(final int repeatStatus) {
        this.repeatStatus = repeatStatus;
    }

    /**
     * get the typeFoundBySearch
     * @return the typeFoundBySearch
     */
    public int getTypeFoundBySearch() {
        return typeFoundBySearch;
    }

    /**
     * set the typeFoundBySearch
     * @param typeFoundBySearch the typeFoundBySearch
     */
    @JsonIgnore
    public void setTypeFoundBySearch(final int typeFoundBySearch) {
        this.typeFoundBySearch = typeFoundBySearch;
    }

    /**
     * get the typeSelected
     * @return the typeSelected
     */
    public int getTypeSelected() {
        return typeSelected;
    }

    /**
     * set the typeSelected
     * @param typeSelected the typeSelected
     */
    @JsonIgnore
    public void setTypeSelected(final int typeSelected) {
        this.typeSelected = typeSelected;
    }

    /**
     * get the typeLoaded
     * @return the typeLoaded
     */
    public int getTypeLoaded() {
        return typeLoaded;
    }

    /**
     * set the typeLoaded
     * @param typeLoaded the typeLoaded
     */
    @JsonIgnore
    public void setTypeLoaded(final int typeLoaded) {
        this.typeLoaded = typeLoaded;
    }

    /**
     * get the currentSelect
     * @return the currentSelect
     */
    public Select getCurrentSelect() {
        return currentSelect;
    }

    /**
     * set the currentSelect
     * @param currentSelect the currentSelect
     */
    public void setCurrentSelect(final Select currentSelect) {
        this.currentSelect = currentSelect;
    }

    /**
     * get the likedSongs
     * @return the likedSongs
     */
    public ArrayList<Song> getLikedSongs() {
        return likedSongs;
    }

    /**
     * set the likedSongs
     * @param likedSongs the likedSongs
     */
    public void setLikedSongs(final ArrayList<Song> likedSongs) {
        this.likedSongs = likedSongs;
    }

    /**
     * get the shuffle
     * @return the shuffle
     */
    public boolean isShuffle() {
        return shuffle;
    }

    /**
     * set the shuffle
     * @param shuffle the shuffle
     */
    public void setShuffle(final boolean shuffle) {
        this.shuffle = shuffle;
    }

    /**
     * get the currentSearch
     * @return the currentSearch
     */
    public Search getCurrentSearch() {
        return currentSearch;
    }

    /**
     * set the currentSearch
     * @param currentSearch the currentSearch
     */
    public void setCurrentSearch(final Search currentSearch) {
        this.currentSearch = currentSearch;
    }

    /**
     * get the playListList
     * @return the playListList
     */
    public ArrayList<Playlist> getPlayListList() {
        return playListList;
    }

    /**
     * set the playListList
     * @param playListList the playListList
     */
    public void setPlayListList(final ArrayList<Playlist> playListList) {
        this.playListList = playListList;
    }

    /**
     * get the likedPlaylists
     * @return the likedPlaylists
     */
    public ArrayList<String> getLikedPlaylists() {
        return likedPlaylists;
    }

    /**
     * set the likedPlaylists
     * @param likedPlaylists the likedPlaylists
     */
    public void setLikedPlaylists(final ArrayList<String> likedPlaylists) {
        this.likedPlaylists = likedPlaylists;
    }

    /**
     * get the remainingTime
     * @return the remainingTime
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * set the remainingTime
     * @param remainingTime the remainingTime
     */
    public void setRemainingTime(final int remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * get the currentType
     * @return the currentType
     */
    public Type getCurrentType() {
        return currentType;
    }

    /**
     * set the currentType
     * @param currentType the currentType
     */
    public void setCurrentType(final Type currentType) {
        this.currentType = currentType;
    }

    /**
     * get the repeatString
     * @return the repeatString
     */
    public String getRepeatString() {
        return repeatString;
    }

    /**
     * add a podcast to the podcastsPlayed
     * @param podcast the podcast to be added
     */
    public void addPodcastPlayed(final Podcast podcast) {
        this.podcastsPlayed.add(podcast);
    }

    /**
     * get the podcastsPlayed
     * @return the podcastsPlayed
     */
    public ArrayList<Podcast> getPodcastsPlayed() {
        return podcastsPlayed;
    }

    /**
     * set the podcastsPlayed
     * @param podcastsPlayed the podcastsPlayed
     */
    public void setPodcastsPlayed(final ArrayList<Podcast> podcastsPlayed) {
        this.podcastsPlayed = podcastsPlayed;
    }

    /**
     * set the repeatString
     * @param repeatString the repeatString
     */
    public void setRepeatString(final String repeatString) {
        this.repeatString = repeatString;
    }

    /**
     * get the previous timestamp
     * @return the prevTimestamp
     */
    public int getPrevTimestamp() {
        return prevTimestamp;
    }

    /**
     * set the previous timestamp
     * @param prevTimestamp the previous timestamp
     */
    public void setPrevTimestamp(final int prevTimestamp) {
        this.prevTimestamp = prevTimestamp;
    }

    /**
     * get the currentPodcast
     * @return the currentPodcast
     */
    public Podcast getCurrentPodcast() {
        return currentPodcast;
    }

    /**
     * set the currentPodcast
     * @param currentPodcast the currentPodcast
     */
    public void setCurrentPodcast(final Podcast currentPodcast) {
        this.currentPodcast = currentPodcast;
    }

    /**
     * get the currentPlaylist
     * @return the currentPlaylist
     */
    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    /**
     * set the currentPlaylist
     * @param currentPlaylist the currentPlaylist
     */
    public void setCurrentPlaylist(final Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    /**
     * get the selectedName
     * @return the selectedName
     */
    public String getSelectedName() {
        return selectedName;
    }

    /**
     * set the selectedName
     * @param selectedName the selectedName
     */
    public void setSelectedName(final String selectedName) {
        this.selectedName = selectedName;
    }

    /**
     * get the shuffleSeed
     * @return the shuffleSeed
     */
    public int getShuffleSeed() {
        return shuffleSeed;
    }

    /**
     * set the shuffleSeed
     * @param shuffleSeed the shuffleSeed
     */
    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    /**
     * get the shuffledIndices
     * @return the shuffledIndices
     */
    public ArrayList<Integer> getShuffledIndices() {
        return shuffledIndices;
    }

    /**
     * set the shuffledIndices
     * @param shuffledIndices the shuffledIndices
     */
    public void setShuffledIndices(final ArrayList<Integer> shuffledIndices) {
        this.shuffledIndices = shuffledIndices;
    }

    /**
     * get the selectedPlaylist
     * @return the selectedPlaylist
     */
    public Playlist getSelectedPlaylist() {
        return selectedPlaylist;
    }

    /**
     * set the selectedPlaylist
     * @param selectedPlaylist the selectedPlaylist
     */
    public void setSelectedPlaylist(final Playlist selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }

    /**
     * get the followedPlaylists
     * @return the followedPlaylists
     */
    public ArrayList<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    /**
     * set the followedPlaylists
     * @param followedPlaylists the followedPlaylists
     */
    public void setFollowedPlaylists(final ArrayList<Playlist> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }

    /**
     * get the everySong
     * @return the everySong
     */
    public ArrayList<Song> getEverySong() {
        return everySong;
    }

    /**
     * set the everySong
     * @param everySong the everySong
     */
    public void setEverySong(final ArrayList<Song> everySong) {
        this.everySong = everySong;
    }

    /**
     * get the everyPodcast
     * @return the everyPodcast
     */
    public ArrayList<Podcast> getEveryPodcast() {
        return everyPodcast;
    }

    /**
     * set the everyPodcast
     * @param everyPodcast the everyPodcast
     */
    public void setEveryPodcast(final ArrayList<Podcast> everyPodcast) {
        this.everyPodcast = everyPodcast;
    }

    /**
     * get the next boolean
     * @return the next boolean
     */
    public boolean isNext() {
        return isNext;
    }

    /**
     * set the next boolean
     * @param next the next boolean
     */
    public void setNext(final boolean next) {
        isNext = next;
    }

    /**
     * get the original indices of the songs in the playlist
     * @return the original indices of the songs in the playlist
     */
    public ArrayList<Integer> getOriginalIndices() {
        return originalIndices;
    }


//    Stage 2 getter and setters:

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getSelectedPageOwner() {
        return selectedPageOwner;
    }

    public void setSelectedPageOwner(String selectedPageOwner) {
        this.selectedPageOwner = selectedPageOwner;
    }
}
