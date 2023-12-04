package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.*;
import main.Commands.Player.*;
import main.Commands.SearchBar.Search;
import main.Commands.SearchBar.Select;
import main.Commands.Types.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePathInput,
                              final String filePathOutput) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);

        ArrayNode outputs = objectMapper.createArrayNode();

        // TODO add your implementation

//        reading songs
        ArrayList<SongInput> songInputs = library.getSongs();
        int songsCount = 0;

        ArrayList<Song> songs = new ArrayList<>();
//        storing songs
        for (SongInput songInput : songInputs) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));

            ++songsCount;
        }

//        reading Podcasts && Episodes
        ArrayList<PodcastInput> podcastInputs = library.getPodcasts();

        ArrayList<Podcast> podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputs) {
            ArrayList<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }


//        reading users
        ArrayList<UserInput> userInputs = library.getUsers();
        int userCount = 0;

        ArrayList<User> users = new ArrayList<>();

//        storing users
        for (UserInput userInput : userInputs) {
            users.add(new User(userInput.getUsername(), userInput.getAge(),
                    userInput.getCity(), songs, podcasts));
            ++userCount;
        }

//        reading input test files
        ArrayList<SearchBar> searchBarInputs = objectMapper.
                readValue(new File(CheckerConstants.TESTS_PATH + filePathInput),
                        new TypeReference<>() { });

        ArrayList<Command> commands = new ArrayList<>();

//        every playlist from every user;
        ArrayList<Playlist> everyPlaylist = new ArrayList<>();

//        parsing the Json content into corresponding commands
        for (SearchBar input : searchBarInputs) {

            String command = input.getCommand();
            User user = null;
            for (User u : users) {
                if (u.getUsername().equals(input.getUsername())) {
                    user = u;
                    break;
                }
            }

//            calculating how many seconds have gone sine the last command
            if (user != null && user.getCurrentType() != null && !user.isPaused()) {
                int newSecsGone = input.getTimestamp() - user.getPrevTimestamp();

                Type currentType = user.getCurrentType();
                user.getCurrentType().setSecondsGone(user.
                        getCurrentType().getSecondsGone() + newSecsGone);

                user.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());
            }
//            now that we know how much time has gone we can go to the next/s songs if its needed
            if (user != null && user.getCurrentType() != null) {
                user.treatingRepeatStatus(user);
            }

            if (user != null) {
                user.setPrevTimestamp(input.getTimestamp());
            }
            int index = commands.size();

            if (command.equals("search")) {
//                adding the search command, and initializing
                commands.add(new Search(input.getCommand(), input.getUsername(),
                        input.getTimestamp(), input.getType(), input.getFilters()));

                ((Search) (commands.get(index))).setSearch(user, songs, everyPlaylist, podcasts);

            } else if (command.equals("select")) {

                commands.add(new Select(input.getCommand(), input.getUsername(),
                        input.getTimestamp(), input.getItemNumber()));

                ((Select) (commands.get(index))).setSelect(user, everyPlaylist);

            } else if (command.equals("load")) {

                commands.add(new Load(input.getCommand(), input.getUsername(),
                        input.getTimestamp()));

                ((Load) (commands.get(index))).setLoad(user, everyPlaylist, podcasts);

            } else if (command.equals("playPause")) {
                commands.add(new PlayPause(input.getCommand(), input.getUsername(),
                        input.getTimestamp()));

                ((PlayPause) (commands.get(index))).setPlayPause(user);

            } else if (command.equals("repeat")) {
//
                commands.add(new Repeat(input.getCommand(), input.getUsername(),
                        input.getTimestamp()));

                user.setRepeatStatus(((Repeat) (commands.get(index))).setRepeatMessage(user,
                        user.getRepeatStatus(), user.getTypeLoaded()));

            } else if (command.equals("status")) {

                commands.add(new Status(input.getCommand(), input.getUsername(),
                        input.getTimestamp()));

                if (user.getCurrentType() != null) {
                    ((Status) (commands.get(index))).settingStats(user);
                } else {
                    ((Status) (commands.get(index))).settingNoType(user);
                }

                if (((Status) (commands.get(index))).getRemainingTime() == 0
                        && user.getRepeatStatus() == 0) {
                    user.setPaused(true);
                    user.setCurrentType(null);
                }

            } else if (command.equals("shuffle")) {
                commands.add(new Shuffle(input.getCommand(), input.getUsername(),
                        input.getTimestamp(), input.getSeed()));

                user.setShuffleSeed(input.getSeed());

                ((Shuffle) (commands.get(index))).settingShuffle(user);

            } else if (command.equals("createPlaylist")) {

//                verify if a playlist with the same name exists;
                String message = "Playlist created successfully.";
                for (Playlist playlist : everyPlaylist) {
                    if (playlist.getName().equals(input.getPlaylistName())) {
                        message = "A playlist with the same name already exists.";
                    }
                }

                commands.add(new CreatePlayList(input.getCommand(), input.getUsername(),
                        input.getTimestamp(), input.getPlaylistName(), message));

                if (!message.equals("A playlist with the same name already exists.")) {
                    user.addPlaylistToList(((CreatePlayList) (commands.get(index))).getPlaylist());
                    everyPlaylist.add(((CreatePlayList) (commands.get(index))).getPlaylist());
                }

            } else if (command.equals("addRemoveInPlaylist")) {
                commands.add(new AddRemoveInPlaylist(input.getCommand(), input.getUsername(),
                        input.getTimestamp(), input.getPlaylistId()));

//                setting message;
                ((AddRemoveInPlaylist) (commands.get(index))).
                        setMessage(user, input.getPlaylistId());

            } else if (command.equals("like")) {
                commands.add(new Like(input.getCommand(), input.getUsername(),
                        input.getTimestamp()));

                ((Like) (commands.get(index))).likeHelper(user, songs);

            } else if (command.equals("showPlaylists")) {
                commands.add(new ShowPlaylists(input.getCommand(), input.getUsername(),
                        input.getTimestamp()));

//                copying the playlists
                ArrayList<Playlist> copyList = new ArrayList<>();
                ((ShowPlaylists) (commands.get(index))).copyPlaylists(user, copyList);

                ((ShowPlaylists) (commands.get(index))).setResult(copyList);

            } else if (command.equals("showPreferredSongs")) {
                commands.add(new ShowPreferredSongs(input.getCommand(), input.getUsername(),
                        input.getTimestamp()));

                ((ShowPreferredSongs) (commands.get(index))).setResult(user);

            } else if (command.equals("next")) {
                user.setNext(true);

                commands.add((new Next(input.getCommand(), input.getUsername(),
                        input.getTimestamp())));

                ((Next) (commands.get(index))).setNext(user);

                user.setNext(false);
            } else if (command.equals("prev")) {
                commands.add((new Prev(input.getCommand(), input.getUsername(),
                        input.getTimestamp())));

                ((Prev) (commands.get(index))).setPrev(user);

            } else if (command.equals("forward")) {
                commands.add((new Forward(input.getCommand(), input.getUsername(),
                        input.getTimestamp())));

                ((Forward) (commands.get(index))).setForward(user);

            } else if (command.equals("backward")) {
                commands.add((new Backward(input.getCommand(), input.getUsername(),
                        input.getTimestamp())));

                ((Backward) (commands.get(index))).setBackward(user);

            } else if (command.equals("follow")) {
                commands.add((new Follow(input.getCommand(), input.getUsername(),
                        input.getTimestamp())));

                ((Follow) (commands.get(index))).setFollow(user, everyPlaylist);

            } else if (command.equals("switchVisibility")) {
                commands.add((new SwitchVisibility(input.getCommand(), input.getUsername(),
                        input.getTimestamp(), input.getPlaylistId())));

                ((SwitchVisibility) (commands.get(index))).setVisibility(user);

            } else if (command.equals("getTop5Playlists")) {
                commands.add(new GetTop5Playlists(input.getCommand(), input.getTimestamp()));

                ((GetTop5Playlists) (commands.get(index))).searchTop5Playlists(everyPlaylist);

            } else if (command.equals("getTop5Songs")) {
                commands.add(new GetTop5Songs(input.getCommand(), input.getTimestamp()));

                ((GetTop5Songs) (commands.get(index))).searchTop5Songs(songs);
            }
        }
//        parsing the requeriments
        if (!commands.isEmpty()) {
            for (Command comm : commands) {
                outputs.add(objectMapper.valueToTree(comm));
            }
        }
                ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }
}

