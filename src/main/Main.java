package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.*;
import main.commands.pageSystem.*;
import main.commands.player.admin.AddUser;
import main.commands.player.admin.DeleteUser;
import main.commands.player.admin.ShowAlbums;
import main.commands.player.admin.ShowPodcasts;
import main.commands.player.artist.*;
import main.commands.player.host.AddAnnouncement;
import main.commands.player.host.AddPodcast;
import main.commands.player.host.RemoveAnnouncement;
import main.commands.player.host.RemovePodcast;
import main.commands.player.statistics.*;
import main.commands.searchBar.*;
import main.commands.types.*;
import main.commands.player.*;
import main.commands.player.user.*;
import main.users.Artist;
import main.users.Host;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
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

        ArrayList<Song> songs = new ArrayList<>();
//        storing songs
        for (SongInput songInput : songInputs) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
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
                        new TypeReference<ArrayList<SearchBar>>() { });

        ArrayList<Command> commands = new ArrayList<>();

//        every playlist from every user;
        ArrayList<Playlist> everyPlaylist = new ArrayList<>();
//        every album from every artist
        ArrayList<Album> everyAlbum = new ArrayList<>();
//        every artist
        ArrayList<Artist> everyArtist = new ArrayList<>();
//        every host
        ArrayList<Host> everyHost = new ArrayList<>();


//        creationg the executor
        ConcreteCommandVisitor executor = new ConcreteCommandVisitor();

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

            if (input.getTimestamp() == 290) {
                int x = 5;
            }



//            calculating how many seconds have gone sine the last command for every user
            for (User u : users) {
                if (u.getCurrentType() != null && !u.isPaused() && u.getOnline()) {
                    int newSecsGone = input.getTimestamp() - u.getPrevTimestamp();

                    Type currentType = u.getCurrentType();
                    u.getCurrentType().setSecondsGone(u.
                            getCurrentType().getSecondsGone() + newSecsGone);

                    u.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());
                }

                if (u.getCurrentType() != null) {
                    u.treatingRepeatStatus(u);
                }


                u.setPrevTimestamp(input.getTimestamp());
            }


            int index = commands.size();

            Artist artist = null;
            if (user == null) {
                for (Artist a : everyArtist) {
                    if (a.getUsername().equals(input.getUsername())) {
                        artist = a;
                        break;
                    }
                }
            }
            Host host = null;
            if (user == null && artist == null) {
                for (Host h : everyHost) {
                    if (h.getUsername().equals(input.getUsername())) {
                        host = h;
                        break;
                    }
                }
            }




//            if for debugging
            if (input.getTimestamp() == 805) {
                int x = 5;
            }

            if (input.getTimestamp() == 85) {
                int x = 5;
            }

            if (input.getTimestamp() == 86) {
                int x = 5;
            }



//            creating the commands
            executor.setExecutor(commands, input, user, songs, everyPlaylist,
                    podcasts, users, everyAlbum, everyArtist, everyHost, artist, host);

            switch (command) {
                case "search":              commands.add(new Search(input));                break;
                case "select":              commands.add(new Select(input));                break;
                case "load":                commands.add(new Load(input));                  break;
                case "playPause":           commands.add(new PlayPause(input));             break;
                case "repeat":              commands.add(new Repeat(input));                break;
                case "status":              commands.add(new Status(input));                break;
                case "shuffle":             commands.add(new Shuffle(input));               break;
                case "createPlaylist":      commands.add(new CreatePlayList(input));        break;
                case "addRemoveInPlaylist": commands.add(new AddRemoveInPlaylist(input));   break;
                case "like":                commands.add(new Like(input));                  break;
                case "showPlaylists":       commands.add(new ShowPlaylists(input));         break;
                case "showPreferredSongs":  commands.add(new ShowPreferredSongs(input));    break;
                case "next":                commands.add(new Next(input));                  break;
                case "prev":                commands.add(new Prev(input));                  break;
                case "forward":             commands.add(new Forward(input));               break;
                case "backward":            commands.add(new Backward(input));              break;
                case "follow":              commands.add(new Follow(input));                break;
                case "switchVisibility":    commands.add(new SwitchVisibility(input));      break;
                case "getTop5Playlists":    commands.add(new GetTop5Playlists(input));      break;
                case "getTop5Songs":        commands.add(new GetTop5Songs(input));          break;

//                Stage 2:
                case "switchConnectionStatus": commands.add(new SwitchConnectionStatus(input)); break;
                case "getOnlineUsers":      commands.add(new GetOnlineUsers(input));        break;
                case "changePage":          commands.add(new ChangePage(input));            break;
                case "addUser":             commands.add(new AddUser(input));               break;
                case "addAlbum":            commands.add(new AddAlbum(input));              break;
                case "showAlbums":          commands.add(new ShowAlbums(input));            break;
                case "printCurrentPage":    commands.add(new PrintCurrentPage(input));      break;
                case "addEvent":            commands.add(new AddEvent(input));              break;
                case "addMerch":            commands.add(new AddMerch(input));              break;
                case "getAllUsers":         commands.add(new GetAllUsers(input));           break;
                case "deleteUser":          commands.add(new DeleteUser(input));            break;
                case "addPodcast":          commands.add(new AddPodcast(input));            break;
                case "addAnnouncement":     commands.add(new AddAnnouncement(input));       break;
                case "removeAnnouncement":  commands.add(new RemoveAnnouncement(input));    break;
                case "showPodcasts":        commands.add(new ShowPodcasts(input));          break;
                case "removeAlbum":         commands.add(new RemoveAlbum(input));           break;
                case "removePodcast":       commands.add(new RemovePodcast(input));         break;
                case "removeEvent":         commands.add(new RemoveEvent(input));           break;
                case "getTop5Albums":       commands.add(new GetTop5Albums(input));         break;


                default: break;
            }
//            we have the command created, now we use the visitor design pattern
            commands.get(index).accept(executor);
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

