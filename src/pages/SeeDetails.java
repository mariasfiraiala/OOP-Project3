package pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Commands;
import info.Movie;
import info.User;
import input.ActionInput;
import platform.Session;

import java.util.ArrayList;
import java.util.List;

public final class SeeDetails extends Page {
    private Movie currentMovie;
    public SeeDetails(final String name, final List<String> possibleActions) {
        super(name, possibleActions);
    }

    /**
     * sets the current page as "See Details" and prints the selected movie
     * @param action the action that should be performed before changing the page
     * @param output writes in file
     */
    public void changePage(final ActionInput action, final ArrayNode output) {
        currentMovie = null;
        for (Movie movie : ((Movies) this.getNextPage("movies")).getSelectedMovies()) {
            if (movie.getName().compareTo(action.getMovie()) == 0) {
                currentMovie = movie;
            }
        }

        if (currentMovie == null) {
            Commands.error(output);
        } else {
            ArrayList<Movie> newMovies = new ArrayList<>();
            newMovies.add(new Movie(currentMovie));
            Commands.success(Session.getInstance().getCurrentUser(), newMovies,
                    output);
            Session.getInstance().setCurrentPage(this);
        }
    }

    /**
     * purchases the wanted movie, adds it to the list and print the info
     * @param currentUser the user that purchases the movie
     * @param output writes to file
     */
    public void purchase(final User currentUser, final ArrayNode output) {
        if (currentMovie == null) {
            Commands.error(output);
            return;
        }
        if (currentUser.getCredentials().getAccountType().compareTo("premium") == 0) {
            if (currentUser.getNumFreePremiumMovies() > 0) {
                currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
                currentUser.getPurchasedMovies().add(currentMovie);
                ArrayList<Movie> newMovies = new ArrayList<>();
                newMovies.add(new Movie(currentMovie));
                Commands.success(Session.getInstance().getCurrentUser(), newMovies,
                        output);
            } else {
                if (currentUser.getTokensCount() < 2) {
                    Commands.error(output);
                } else {
                    currentUser.setTokensCount(currentUser.getTokensCount() - 2);
                    currentUser.getPurchasedMovies().add(currentMovie);
                    ArrayList<Movie> newMovies = new ArrayList<>();
                    newMovies.add(new Movie(currentMovie));
                    Commands.success(Session.getInstance().getCurrentUser(),
                            newMovies, output);
                }
            }
        } else {
            if (currentUser.getTokensCount() < 2) {
                Commands.error(output);
            } else {
                currentUser.setTokensCount(currentUser.getTokensCount() - 2);
                currentUser.getPurchasedMovies().add(currentMovie);
                ArrayList<Movie> newMovies = new ArrayList<>();
                newMovies.add(new Movie(currentMovie));
                Commands.success(Session.getInstance().getCurrentUser(), newMovies,
                        output);
            }
        }
    }

    /**
     * adds the current movie to the watched list and prints the info
     * @param currentUser the user that watches the movie
     * @param output writes to file
     */
    public void watch(final User currentUser, final ArrayNode output) {
        if (currentMovie == null || !currentUser.getPurchasedMovies().contains(currentMovie)) {
            Commands.error(output);
        } else {
            currentUser.getWatchedMovies().add(currentMovie);
            ArrayList<Movie> newMovies = new ArrayList<>();
            newMovies.add(new Movie(currentMovie));
            Commands.success(Session.getInstance().getCurrentUser(), newMovies,
                    output);
        }
    }

    /**
     * likes the current movie, adds it to the liked list and prints the info
     * @param currentUser the user that likes the movie
     * @param output writes to file
     */
    public void like(final User currentUser, final ArrayNode output) {
        if (currentMovie == null || !currentUser.getWatchedMovies().contains(currentMovie)) {
            Commands.error(output);
        } else {
            currentMovie.setNumLikes(currentMovie.getNumLikes() + 1);
            currentUser.getLikedMovies().add(currentMovie);
            ArrayList<Movie> newMovies = new ArrayList<>();
            newMovies.add(new Movie(currentMovie));
            Commands.success(Session.getInstance().getCurrentUser(), newMovies,
                    output);
        }
    }

    /**
     * rates the current movie, adds it to the rated list and prints the info
     * @param rate the grade given to the movie
     * @param currentUser the user that rates the movie
     * @param output writes to file
     */
    public void rate(final int rate, final User currentUser, final ArrayNode output) {
        if (currentMovie == null || !currentUser.getWatchedMovies().contains(currentMovie)
                || rate > 5 || rate < 0) {
            Commands.error(output);
        } else {
            currentMovie.setSumRatings(currentMovie.getSumRatings() + rate);
            currentMovie.setNumRatings(currentMovie.getNumRatings() + 1);
            currentMovie.setRating((double) currentMovie.getSumRatings()
                    / currentMovie.getNumRatings());
            currentUser.getRatedMovies().add(currentMovie);
            ArrayList<Movie> newMovies = new ArrayList<>();
            newMovies.add(new Movie(currentMovie));
            Commands.success(Session.getInstance().getCurrentUser(), newMovies,
                    output);
        }
    }
}
