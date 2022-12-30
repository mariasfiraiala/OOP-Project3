package pages;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;

import commands.Commands;
import info.User;
import input.ActionInput;

public final class Upgrades extends Page {
    public Upgrades(final String name, final List<String> possibleActions) {
        super(name, possibleActions);
    }

    /**
     * tradeoff between balance and tokens
     * @param action the info needed for the buying process
     * @param currentUser the user that wants tokens
     * @param output writes to file
     */
    public void buyTokens(final ActionInput action, final User currentUser,
                          final ArrayNode output) {
        int currentBalance = Integer.parseInt(currentUser.getCredentials().getBalance());
        int currentTokens = currentUser.getTokensCount();
        int toBuyTokens = Integer.parseInt(action.getCount());
        if (currentBalance < toBuyTokens) {
            Commands.error(output);
        } else {
            currentUser.getCredentials().setBalance(Integer.toString(currentBalance - toBuyTokens));
            currentUser.setTokensCount(currentTokens + toBuyTokens);
        }
    }

    /**
     * buys premium account
     * @param currentUser the user that wants to switch to premium
     * @param output writes to file
     */
    public void buyPremium(final User currentUser, final ArrayNode output) {
        int currentTokens = currentUser.getTokensCount();
        if (currentTokens < 10) {
            Commands.error(output);
        } else {
            currentUser.setTokensCount(currentTokens - 10);
            currentUser.getCredentials().setAccountType("premium");
        }
    }
}
