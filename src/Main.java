
import platform.FacadeSession;
import platform.Session;
import java.io.IOException;

public final class Main {
    private Main() { }
    /**
     * gets info from the files and writes back to files
     * @param args the input and output paths
     * @throws IOException exception thrown at reading
     */
    public static void main(final String[] args) throws IOException {
        FacadeSession facade = new FacadeSession(Session.getInstance());
        facade.startSession(args[0]);
        facade.stopSession(args[1]);
    }
}
