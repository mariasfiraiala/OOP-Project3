package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import info.Movie;
import info.Notification;
import info.User;
import input.MovieInput;
import platform.Session;

import java.util.ArrayList;
import java.util.Iterator;

public final class Database {
    private Database() { }
    public static void add(final MovieInput newMovie, final ArrayNode output) {
        for (Movie movie : Session.getInstance().getAllMovies()) {
            if (movie.getName().compareTo(newMovie.getName()) == 0) {
                Commands.error(output);
                return;
            }
        }

        Session.getInstance().getAllMovies().add(new Movie(newMovie));

        for (User user : Session.getInstance().getAllUsers()) {
            boolean isNotBanned = true;
            for (String banned : newMovie.getCountriesBanned()) {
                if (banned.compareTo(user.getCredentials().getCountry()) == 0) {
                    isNotBanned = false;
                    break;
                }
            }

            if (isNotBanned) {
                user.getVisibleMovies().add(new Movie(newMovie));

                for (String genre : newMovie.getGenres()) {
                    if (user.getSubscriptions().contains(genre)) {
                        user.getNotifications().add(new Notification(newMovie.getName(), "ADD"));
                        break;
                    }
                }
            }
        }
    }

    public static void delete(final String movieName, final ArrayNode output) {
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
                while (movie.hasNext()) {
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
            Session.getInstance().getCurrentUser().getNotifications().add(
                    new Notification(movieName, "DELETE"));
        }
    }

    public static void deleteByName(final String movieName, final ArrayList<Movie> movies) {
        movies.removeIf(next -> next.getName().compareTo(movieName) == 0);
    }
}
