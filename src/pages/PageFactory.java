package pages;

import java.util.List;

public final class PageFactory {
    public enum PageType {
        Authenticated, Login, Logout, Movies, Register, SeeDetails, Upgrades
    }

    private PageFactory() { }

    /**
     * factory function, constructs pages which extend the Page abstract class
     * @param pageType enum for all pages we can construct
     * @return the newly created page
     */
    public static Page createPage(final PageType pageType) {
        switch (pageType) {
            case Authenticated -> {
                return new Authenticated("authenticated",
                        List.of());
            }
            case Login -> {
                return new Login("login",
                        List.of("login"));
            }
            case Register -> {
                return new Register("register",
                        List.of("register"));
            }
            case Movies -> {
                return new Movies("movies",
                        List.of("search", "filter"));
            }
            case SeeDetails -> {
                return new SeeDetails("see details",
                        List.of("purchase", "watch", "like", "rate", "subscribe"));
            }
            case Upgrades -> {
                return new Upgrades("upgrades",
                        List.of("buy premium account", "buy tokens"));
            }
            case Logout -> {
                return new Logout("logout",
                        List.of());
            }
            default -> throw new IllegalArgumentException("The page type " + pageType
                    + " is not supported");
        }
    }
}
