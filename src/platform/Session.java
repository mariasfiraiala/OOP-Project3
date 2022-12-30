package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Commands;
import pages.Page;
import input.ActionInput;
import input.DataInput;
import input.MovieInput;
import input.UserInput;
import info.Movie;
import info.User;
import pages.PageHierarchy;

import java.util.ArrayList;

public final class Session {
    private static Session instance = null;
    private ArrayList<Movie> allMovies = new ArrayList<Movie>();
    private ArrayList<User> allUsers = new ArrayList<User>();
    private User currentUser;
    private Page currentPage = PageHierarchy.build();
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

    /**
     * creates lists for all the users and movies in the system, based on the input
     * @param data the input from which we extract the needed info
     */
    public void uploadData(final DataInput data) {
        for (MovieInput movie : data.getMovies()) {
            allMovies.add(new Movie(movie));
        }
        for (UserInput user : data.getUsers()) {
            allUsers.add(new User(user, allMovies));
        }
    }

    /**
     * applies the commands given from the input
     * @param actions the commands we implement
     * @param output writes to file
     */
    public void startSession(final ArrayList<ActionInput> actions, final ArrayNode output) {
        for (ActionInput action : actions) {
            switch (action.getType()) {
                case "change page" -> Commands.changePage(action, output);
                case "on page" -> Commands.onPage(action, output);
                default -> throw new IllegalStateException("Unexpected value: " + action.getType());
            }
        }
    }

    /**
     * after each test (session), resets the instance to null
     */
    public void reset() {
        instance = null;
    }
}
