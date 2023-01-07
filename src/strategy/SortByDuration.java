package strategy;

import info.Movie;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByDuration implements SortStrategy{
    private boolean isIncreasing;

    public SortByDuration(boolean isIncreasing) {
        this.isIncreasing = isIncreasing;
    }

    public void sort(ArrayList<Movie> movies) {
        if (!isIncreasing) {
            movies.sort(Comparator.comparingDouble(Movie::getDuration).reversed());
        } else {
            movies.sort(Comparator.comparingDouble(Movie::getDuration));
        }
    }
}
