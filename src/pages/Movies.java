package pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Commands;
import info.Movie;
import input.ActionInput;
import input.FiltersInput;
import platform.Session;
import strategy.SortByDuration;
import strategy.SortByRating;
import strategy.SortStrategy;

import java.util.ArrayList;
import java.util.List;

public final class Movies extends Page {
    private ArrayList<Movie> selectedMovies;
    public Movies(final String name, final List<String> possibleActions) {
        super(name, possibleActions);
    }

    /**
     * prints all visible movies for the current user and gets on the "Movies" page
     * @param action the action that should be performed before changing the page
     * @param output writes in file
     */
    public boolean changePage(final ActionInput action, final ArrayNode output) {
        selectedMovies = Session.getInstance().getCurrentUser().getVisibleMovies();
        Commands.success(Session.getInstance().getCurrentUser(), selectedMovies,
                output);
        Session.getInstance().setCurrentPage(this);
        return true;
    }

    /**
     * searches for a regex from the selected collection and prints all movies
     * @param startsWith the string with which the movie has to start with
     * @param output writes to file
     */
    public void search(final String startsWith, final ArrayNode output) {
        ArrayList<Movie> tmpCurrentMovies = new ArrayList<>();

        for (Movie movie : Session.getInstance().getCurrentUser().getVisibleMovies()) {
            if (movie.getName().startsWith(startsWith)) {
                tmpCurrentMovies.add(movie);
            }
        }

        selectedMovies = tmpCurrentMovies;
        Commands.success(Session.getInstance().getCurrentUser(), tmpCurrentMovies,
                output);
    }

    /**
     * filters and sorts the selected collection
     * @param filter the characteristics of the movies we are interested in
     * @param output writes to file
     */
    public void filter(final FiltersInput filter, final ArrayNode output) {
        ArrayList<Movie> tmpCurrentMovies = new ArrayList<>(Session.getInstance().getCurrentUser().
                getVisibleMovies());

        if (filter.getContains() != null) {
            if (filter.getContains().getActors() != null) {
                for (String actor : filter.getContains().getActors()) {
                    tmpCurrentMovies.removeIf(next -> !next.getActors().contains(actor));
                }
            }

            if (filter.getContains().getGenre() != null) {
                for (String genre : filter.getContains().getGenre()) {
                    tmpCurrentMovies.removeIf(next -> !next.getGenres().contains(genre));
                }
            }
        }

        if (filter.getSort() != null) {
            ArrayList<SortStrategy> allSorts = new ArrayList<SortStrategy>();
            if (filter.getSort().getRating() != null) {
                SortByRating sortByRating;
                if (filter.getSort().getRating().compareTo("decreasing") == 0) {
                    sortByRating = new SortByRating(false);
                } else {
                    sortByRating = new SortByRating(true);
                }
                allSorts.add(sortByRating);
            }
            if (filter.getSort().getDuration() != null) {
                SortByDuration sortByDuration;
                if (filter.getSort().getDuration().compareTo("decreasing") == 0) {
                    sortByDuration = new SortByDuration(false);
                } else {
                    sortByDuration = new SortByDuration(true);
                }
                allSorts.add(sortByDuration);
            }

            allSorts.forEach((sortStrategy) -> {
                sortStrategy.sort(tmpCurrentMovies);
            });
        }
        selectedMovies = tmpCurrentMovies;
        Commands.success(Session.getInstance().getCurrentUser(), tmpCurrentMovies,
                output);
    }

    public ArrayList<Movie> getSelectedMovies() {
        return selectedMovies;
    }
}
