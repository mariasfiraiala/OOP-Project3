package info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import commands.Commands;
import input.UserInput;

import java.util.ArrayList;
import java.util.HashMap;

public final class User extends UserInput {
    private int tokensCount;
    private int numFreePremiumMovies = 15;
    @JsonIgnore
    private ArrayList<Movie> visibleMovies = new ArrayList<Movie>();
    @JsonIgnore
    private ArrayList<String> subscriptions = new ArrayList<String>();
    @JsonIgnore
    private HashMap<String, Integer> oldRatings = new HashMap<>();
    private ArrayList<Movie> purchasedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> watchedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> likedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> ratedMovies = new ArrayList<Movie>();
    private ArrayList<Notification> notifications = new ArrayList<Notification>();

    public User() { }

    public User(final UserInput user, final ArrayList<Movie> movies) {
        super(user);
        for (Movie movie : movies) {
            if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                visibleMovies.add(movie);
            }
        }
    }

    public User(final User user) {
        super(user);
        this.tokensCount = user.tokensCount;
        this.numFreePremiumMovies = user.numFreePremiumMovies;
        this.visibleMovies = Commands.deepCopy(user.visibleMovies);
        this.purchasedMovies = Commands.deepCopy(user.purchasedMovies);
        this.watchedMovies = Commands.deepCopy(user.watchedMovies);
        this.likedMovies = Commands.deepCopy(user.likedMovies);
        this.ratedMovies = Commands.deepCopy(user.ratedMovies);
        this.notifications = Commands.deepCopyNotifications(user.notifications);
    }

    public ArrayList<Movie> getVisibleMovies() {
        return visibleMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(final ArrayList<String> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public HashMap<String, Integer> getOldRatings() {
        return oldRatings;
    }

    public void setOldRatings(final HashMap<String, Integer> oldRatings) {
        this.oldRatings = oldRatings;
    }
}
