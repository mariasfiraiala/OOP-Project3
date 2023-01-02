

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.DataInput;
import platform.Session;

import java.io.File;
import java.io.IOException;

public class Main {
    /**
     * gets info from the files and writes back to files
     * @param args the input and output paths
     * @throws IOException exception thrown at reading
     */
    public static void main(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode output = objectMapper.createArrayNode();
        DataInput database = (DataInput) objectMapper.readValue(new File(args[0]), DataInput.class);

        Session.getInstance().reset();
        Session.getInstance().uploadData(database);
        Session.getInstance().startSession(database.getActions(), output);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
    }
}