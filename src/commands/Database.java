package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import info.Movie;
import info.User;
import platform.Session;

import java.util.ArrayList;
import java.util.Iterator;

public class Database {
    public static void add(ArrayNode output) {

    }

    public static void delete(String movieName, ArrayNode output) {
        boolean exists = false;
        for (Movie movie : Session.getInstance().getAllMovies()) {
            if (movie.getName().compareTo(movieName) == 0) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            Commands.error(output);
        } else {
            deleteByName(movieName, Session.getInstance().getAllMovies());
            for (User user : Session.getInstance().getAllUsers()) {
                deleteByName(movieName, user.getVisibleMovies());

                Iterator<Movie> movie = user.getPurchasedMovies().iterator();
                while(movie.hasNext()) {
                    Movie next = movie.next();
                    if (next.getName().compareTo(movieName) == 0) {
                        if (user.getCredentials().getAccountType().compareTo("premium") == 0) {
                            user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() + 1);
                        } else {
                            user.setTokensCount(user.getTokensCount() + 1);
                        }
                        movie.remove();
                    }
                }

                deleteByName(movieName, user.getWatchedMovies());
                deleteByName(movieName, user.getLikedMovies());
                deleteByName(movieName, user.getRatedMovies());
            }
        }
    }

    public static void deleteByName(String movieName, ArrayList<Movie> movies) {
        movies.removeIf(next -> next.getName().compareTo(movieName) == 0);
    }
}
