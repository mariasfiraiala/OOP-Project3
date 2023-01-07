package platform;

import java.io.IOException;

public final class FacadeSession {
    private final Session session;

    public FacadeSession(final Session session) {
        this.session = session;
    }

    /**
     * facade implementation for starting a session
     * @param inputFile the file from which we get input
     * @throws IOException exception thrown at reading
     */
    public void startSession(final String inputFile) throws IOException {
        Session.getInstance().reset();
        Session.getInstance().setupInput(inputFile);
        Session.getInstance().uploadData();
        Session.getInstance().startSession();
    }

    /**
     * facade implementation for stopping a session
     * @param outputFile the file in which we write the output
     * @throws IOException exception thrown writing
     */
    public void stopSession(final String outputFile) throws IOException {
        Session.getInstance().finalRecommendation();
        Session.getInstance().setupOutput(outputFile);
        Session.getInstance().reset();
    }
}
