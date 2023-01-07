package strategy;

import info.Movie;

import java.util.ArrayList;
import java.util.Comparator;

public final class SortByRating implements SortStrategy {
    private boolean isIncreasing;

    public SortByRating(final boolean isIncreasing) {
        this.isIncreasing = isIncreasing;
    }

    /**
     * sorts an array of movies based on their rating
     * @param movies the array to be sorted
     */
    public void sort(final ArrayList<Movie> movies) {
        if (!isIncreasing) {
            movies.sort(Comparator.comparingDouble(Movie::getRating).reversed());
        } else {
            movies.sort(Comparator.comparingDouble(Movie::getRating));
        }
    }
}
