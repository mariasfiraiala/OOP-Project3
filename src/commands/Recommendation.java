package commands;

import info.Movie;
import platform.Session;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;

public final class Recommendation {
    private ArrayList<Movie> moviesToChooseFrom = new ArrayList<Movie>();
    private ArrayList<String> preferredGenres = new ArrayList<String>();

    /**
     * gets all unwatched movies for current user and sorts them by likes
     */
    public void prepareMovies() {
        for (Movie movie : Session.getInstance().getCurrentUser().getVisibleMovies()) {
            if (!Session.getInstance().getCurrentUser().getWatchedMovies().contains(movie)) {
                moviesToChooseFrom.add(movie);
            }
        }

        Comparator<Movie> comparator;
        comparator = Comparator.comparing(Movie::getNumLikes, Comparator.reverseOrder());
        moviesToChooseFrom.sort(comparator);
    }

    /**
     * gets all preferred genres by the current user:
     * we store the genre and the number of likes it got in a Tree Map,
     * which, finally, gets sorted by value (the number of likes)
     */
    public void prepareGenres() {
        TreeMap<String, Integer> genres = new TreeMap<String, Integer>();
        for (Movie movie : Session.getInstance().getCurrentUser().getLikedMovies()) {
            for (String genre : movie.getGenres()) {
                if (genres.get(genre) != null) {
                    int tmp = genres.get(genre);
                    ++tmp;
                    genres.put(genre, tmp);
                } else {
                    genres.put(genre, 1);
                }
            }
        }
        Map<String, Integer> sortedGenres = valueSort(genres);
        Set<Map.Entry<String, Integer>> set = sortedGenres.entrySet();

        for (Map.Entry<String, Integer> stringIntegerEntry : set) {
            preferredGenres.add(stringIntegerEntry.getKey());
        }
    }

    /**
     * constructs the recommendation for the preimum user
     * @return the string which contains the movie name or "No recommendation"
     */
    public String getRecommendation() {
        prepareMovies();
        prepareGenres();

        for (String genre : preferredGenres) {
            for (Movie movie : moviesToChooseFrom) {
                if (movie.getGenres().contains(genre)) {
                    return movie.getName();
                }
            }
        }
        return "No recommendation";
    }

    /**
     * sorts a Tree Map by value
     * @param map the map we want to sort
     * @return the sorted map
     * @param <K> Tree Map key
     * @param <V> Tree Map value
     */
    public static <K, V extends Comparable<V>> Map<K, V> valueSort(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(final K k1, final K k2) {
                int comp = map.get(k1).compareTo(map.get(k2));
                if (comp == 0) {
                    return 1;
                } else {
                    return comp;
                }
            }
        };

        Map<K, V> sorted = new TreeMap<K, V>(valueComparator);
        sorted.putAll(map);
        return sorted;
    }
}
