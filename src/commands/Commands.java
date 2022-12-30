package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.Movie;
import info.User;
import input.ActionInput;
import pages.Login;
import pages.Movies;
import pages.Page;
import pages.Register;
import pages.SeeDetails;
import pages.Upgrades;
import platform.Session;

import java.util.ArrayList;

public final class Commands {
    private Commands() { }

    /**
     * the "change page" action implementation
     * @param action info needed to change the page
     * @param output writes to file
     */
    public static void changePage(final ActionInput action, final ArrayNode output) {
        Page nextPage = Session.getInstance().getCurrentPage().getNextPage(action.getPage());
        if (nextPage == null) {
            error(output);
        } else {
            nextPage.changePage(action, output);
        }
    }

    /**
     * the "on page" action implementation
     * @param action info needed to change the page
     * @param output writes to file
     */
    public static void onPage(final ActionInput action, final ArrayNode output) {
        if (!Session.getInstance().getCurrentPage().getPossibleActions().contains(action.
                getFeature())) {
            error(output);
        } else {
            switch (action.getFeature()) {
                case "register" -> ((Register) Session.getInstance().getCurrentPage()).
                        register(action.getCredentials(), output);
                case "login" -> ((Login) Session.getInstance().getCurrentPage()).
                        login(action.getCredentials(), output);
                case "search" -> ((Movies) Session.getInstance().getCurrentPage()).
                        search(action.getStartsWith(), output);
                case "filter" -> ((Movies) Session.getInstance().getCurrentPage()).
                        filter(action.getFilters(), output);
                case "buy tokens" -> ((Upgrades) Session.getInstance().getCurrentPage()).
                        buyTokens(action, Session.getInstance().getCurrentUser(), output);
                case "buy premium account" -> ((Upgrades) Session.getInstance().getCurrentPage()).
                        buyPremium(Session.getInstance().getCurrentUser(), output);
                case "purchase" -> ((SeeDetails) Session.getInstance().getCurrentPage()).
                        purchase(Session.getInstance().getCurrentUser(), output);
                case "watch" -> ((SeeDetails) Session.getInstance().getCurrentPage()).
                        watch(Session.getInstance().getCurrentUser(), output);
                case "like" -> ((SeeDetails) Session.getInstance().getCurrentPage()).
                        like(Session.getInstance().getCurrentUser(), output);
                case "rate" -> ((SeeDetails) Session.getInstance().getCurrentPage()).
                        rate(action.getRate(), Session.getInstance().getCurrentUser(), output);
                default -> throw new IllegalStateException("Unexpected value: "
                        + action.getFeature());
            }
        }
    }

    /**
     * prints the generic error
     * @param output writes to file
     */
    public static void error(final ArrayNode output) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("error", "Error");
        node.putPOJO("currentMoviesList", new ArrayList<>());
        node.putPOJO("currentUser", null);
        output.addPOJO(node);
    }

    /**
     * prints the "on page" success (the list of current movies is empty)
     * @param currentUser the user that made the operation
     * @param output writes to file
     */
    public static void onPageSuccess(final User currentUser, final ArrayNode output) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.putPOJO("error", null);
        node.putPOJO("currentMoviesList", new ArrayList<>());
        node.putPOJO("currentUser", new User(currentUser));
        output.addPOJO(node);
    }

    /**
     * prints success messages but with custom movie list
     * @param currentUser the user that made the operation
     * @param currentMovies the list that needs to be printed
     * @param output writes to file
     */
    public static void success(final User currentUser,
                               final ArrayList<Movie> currentMovies,
                               final ArrayNode output) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.putPOJO("error", null);
        node.putPOJO("currentMoviesList", deepCopy(currentMovies));
        node.putPOJO("currentUser", new User(currentUser));
        output.addPOJO(node);
    }

    /**
     * deep copies an array of movies
     * @param movies the movies we want copied
     * @return the newly created list
     */
    public static ArrayList<Movie> deepCopy(final ArrayList<Movie> movies) {
        ArrayList<Movie> newMovies = new ArrayList<>();

        for (Movie movie : movies) {
            newMovies.add(new Movie(movie));
        }

        return newMovies;
    }
}
