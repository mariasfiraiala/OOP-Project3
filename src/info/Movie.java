package info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import input.MovieInput;

public final class Movie extends MovieInput {
    private double rating;
    private int numRatings;
    private int numLikes;
    @JsonIgnore
    private int sumRatings = 0;

    public Movie(final MovieInput movie) {
        super(movie);
        rating = 0;
        numRatings = 0;
        numLikes = 0;
    }

    public Movie(final Movie movie) {
        super(movie);
        this.rating = movie.rating;
        this.numRatings = movie.numRatings;
        this.numLikes = movie.numLikes;
    }
    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public int getSumRatings() {
        return sumRatings;
    }

    public void setSumRatings(final int sumRatings) {
        this.sumRatings = sumRatings;
    }
}
