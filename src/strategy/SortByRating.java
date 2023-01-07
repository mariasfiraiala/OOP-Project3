package strategy;

import info.Movie;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByRating implements SortStrategy{
    private boolean isIncreasing;

    public SortByRating(boolean isIncreasing) {
        this.isIncreasing = isIncreasing;
    }

    public void sort(ArrayList<Movie> movies) {
        if (!isIncreasing) {
            movies.sort(Comparator.comparingDouble(Movie::getRating).reversed());
        } else {
            movies.sort(Comparator.comparingDouble(Movie::getRating));
        }
    }
}
