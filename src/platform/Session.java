package platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Commands;
import commands.Recommendation;
import info.Notification;
import pages.Page;
import input.ActionInput;
import input.DataInput;
import input.MovieInput;
import input.UserInput;
import info.Movie;
import info.User;
import pages.PageHierarchy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public final class Session {
    private static Session instance = null;
    private ArrayList<Movie> allMovies = new ArrayList<Movie>();
    private ArrayList<User> allUsers = new ArrayList<User>();
    private User currentUser;
    private Page currentPage = PageHierarchy.build();
    private Stack<Page> pageHistory = new Stack<Page>();
    private ObjectMapper objectMapper;
    private ArrayNode output;
    private DataInput database;
    private ObjectWriter objectWriter;

    private Session() { }

    /**
     * Singleton pattern
     * @return the newly created object or the already existing one
     */
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public ArrayList<Movie> getAllMovies() {
        return allMovies;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

    public Stack<Page> getPageHistory() {
        return pageHistory;
    }

    public ArrayNode getOutput() {
        return output;
    }

    public void setOutput(final ArrayNode output) {
        this.output = output;
    }

    public DataInput getDatabase() {
        return database;
    }

    public void setDatabase(final DataInput database) {
        this.database = database;
    }

    /**
     * sets up the file from which we read the input
     * @param inputFile file used for reading info from
     * @throws IOException exception thrown at reading
     */
    public void setupInput(final String inputFile) throws IOException {
        objectMapper = new ObjectMapper();
        output = objectMapper.createArrayNode();
        database = objectMapper.readValue(new File(inputFile), DataInput.class);
    }

    /**
     * creates lists for all the users and movies in the system, based on the input
     */
    public void uploadData() {
        for (MovieInput movie : database.getMovies()) {
            allMovies.add(new Movie(movie));
        }
        for (UserInput user : database.getUsers()) {
            allUsers.add(new User(user, allMovies));
        }
    }

    /**
     * applies the commands given from the input
     */
    public void startSession() {
        for (ActionInput action : database.getActions()) {
            switch (action.getType()) {
                case "change page" -> Commands.changePage(action, output);
                case "on page" -> Commands.onPage(action, output);
                case "back" -> Commands.back(action, output);
                case "database" -> Commands.database(action, output);
                default -> throw new IllegalStateException("Unexpected value: " + action.getType());
            }
        }
    }

    /**
     * prints a recommendation notification in case the last user is premium
     */
    public void finalRecommendation() {
        if (currentUser != null && currentUser.getCredentials().getAccountType().
                compareTo("premium") == 0) {
            Recommendation recommendation = new Recommendation();
            currentUser.getNotifications().add(new Notification(recommendation.getRecommendation(),
                    "Recommendation"));
            Commands.success(currentUser, null, output);
        }
    }

    /**
     * after each test (session), resets the instance to null
     */
    public void reset() {
        instance = null;
    }

    /**
     * sets up the writing of the platform content in the output file
     * @param outputFile file used for writing info to
     * @throws IOException exception thrown at writing
     */
    public void setupOutput(final String outputFile) throws IOException {
        objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(outputFile), output);
    }
}
