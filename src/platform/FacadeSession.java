package platform;

import java.io.IOException;

public final class FacadeSession {
    private final Session session;

    public FacadeSession(final Session session) {
        this.session = session;
    }

    public void startSession(final String inputFile) throws IOException {
        Session.getInstance().reset();
        Session.getInstance().setupInput(inputFile);
        Session.getInstance().uploadData();
        Session.getInstance().startSession();
    }

    public void stopSession(final String outputFile) throws IOException {
        Session.getInstance().finalRecommendation();
        Session.getInstance().setupOutput(outputFile);
        Session.getInstance().reset();
    }
}
