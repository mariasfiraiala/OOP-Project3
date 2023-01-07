package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.DataInput;

public class FacadeSession {
    private final Session session;

    public FacadeSession(Session session) {
        this.session = session;
    }

    public void startSession(DataInput database, ArrayNode output) {
        Session.getInstance().reset();
        Session.getInstance().uploadData(database);
        Session.getInstance().startSession(database.getActions(), output);
    }

    public void stopSession(ArrayNode output) {
        Session.getInstance().finalRecommendation(output);
        Session.getInstance().reset();
    }
}
