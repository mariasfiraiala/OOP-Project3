package strategy;

import info.Movie;

import java.util.ArrayList;

public interface SortStrategy {

    /**
     * sorts an array of movies based on multiple factors
     * @param movies the array to be sorted
     */
    void sort(ArrayList<Movie> movies);
}
