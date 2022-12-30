package pages;

import java.util.List;

public final class PageHierarchy {
    public PageHierarchy() { }

    /**
     * constructs the page hierarchy for the website
     * @return the root of our hierarchy, the "Logout" page
     */
    public static Page build() {
        Authenticated authenticated = new Authenticated("authenticated",
                List.of());
        Login login = new Login("login",
                List.of("login"));
        Register register = new Register("register",
                List.of("register"));
        Movies movies = new Movies("movies",
                List.of("search", "filter"));
        SeeDetails seeDetails = new SeeDetails("see details",
                List.of("purchase", "watch", "like", "rate"));
        Upgrades upgrades = new Upgrades("upgrades",
                List.of("buy premium account", "buy tokens"));
        Logout logout = new Logout("logout",
                List.of());

        authenticated.setNextPages(List.of(movies, upgrades, logout));
        login.setNextPages(List.of(authenticated, logout));
        register.setNextPages(List.of(authenticated, logout));
        movies.setNextPages(List.of(authenticated, seeDetails, movies, logout));
        seeDetails.setNextPages(List.of(authenticated, movies, upgrades, logout));
        upgrades.setNextPages(List.of(authenticated, movies, logout));
        logout.setNextPages(List.of(register, login));

        return logout;
    }
}
