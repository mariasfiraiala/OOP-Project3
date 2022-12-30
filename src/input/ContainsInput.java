package input;

import java.util.ArrayList;

public final class ContainsInput {
    private ArrayList<String> actors = new ArrayList<String>();
    private ArrayList<String> genre = new ArrayList<String>();

    public ContainsInput() { }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }
}
