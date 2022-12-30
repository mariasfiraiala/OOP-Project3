package input;

import java.util.ArrayList;

public final class DataInput {
    private ArrayList<UserInput> users = new ArrayList<UserInput>();
    private ArrayList<MovieInput> movies = new ArrayList<MovieInput>();
    private ArrayList<ActionInput> actions = new ArrayList<ActionInput>();

    public DataInput() { }

    public ArrayList<UserInput> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<UserInput> users) {
        this.users = users;
    }

    public ArrayList<MovieInput> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<MovieInput> movies) {
        this.movies = movies;
    }

    public ArrayList<ActionInput> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<ActionInput> actions) {
        this.actions = actions;
    }
}
