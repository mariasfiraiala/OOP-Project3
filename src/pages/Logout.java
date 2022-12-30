package pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionInput;
import platform.Session;

import java.util.List;

public final class Logout extends Page {
    public Logout(final String name, final List<String> possibleActions) {
        super(name, possibleActions);
    }

    /**
     * sets the current page as "Logout" and nulls the current user
     * @param action the action that should be performed before changing the page
     * @param output writes in file
     */
    public void changePage(final ActionInput action, final ArrayNode output) {
        Session.getInstance().setCurrentPage(this);
        Session.getInstance().setCurrentUser(null);
    }
}
