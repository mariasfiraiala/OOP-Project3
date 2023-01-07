package pages;

import java.util.List;

public final class PageHierarchy {
    private PageHierarchy() { }

    /**
     * constructs the page hierarchy for the website
     * @return the root of our hierarchy, the "Logout" page
     */
    public static Page build() {
        Authenticated authenticated = (Authenticated) PageFactory.createPage(PageFactory.
                PageType.Authenticated);
        Login login = (Login) PageFactory.createPage(PageFactory.
                PageType.Login);
        Register register = (Register) PageFactory.createPage(PageFactory.PageType.
                Register);
        Movies movies = (Movies) PageFactory.createPage(PageFactory.PageType.
                Movies);
        SeeDetails seeDetails = (SeeDetails) PageFactory.createPage(PageFactory.PageType.
                SeeDetails);
        Upgrades upgrades = (Upgrades) PageFactory.createPage(PageFactory.PageType.
                Upgrades);
        Logout logout = (Logout) PageFactory.createPage(PageFactory.PageType.
                Logout);

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
