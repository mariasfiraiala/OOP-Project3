package strategy;

import info.Movie;

import java.util.ArrayList;
import java.util.Comparator;

public final class SortByDuration implements SortStrategy {
    private boolean isIncreasing;

    public SortByDuration(final boolean isIncreasing) {
        this.isIncreasing = isIncreasing;
    }

    /**
     * sorts an array of movies based on their duration
     * @param movies the array to be sorted
     */
    public void sort(final ArrayList<Movie> movies) {
        if (!isIncreasing) {
            movies.sort(Comparator.comparingDouble(Movie::getDuration).reversed());
        } else {
            movies.sort(Comparator.comparingDouble(Movie::getDuration));
        }
    }
}
