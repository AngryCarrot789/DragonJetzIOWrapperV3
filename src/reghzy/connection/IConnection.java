package reghzy.connection;

import java.io.DataInput;
import java.io.DataOutput;

public interface IConnection {
    /**
     * The data input for this connection
     */
    DataInput getInput();

    /**
     * The data output for this connection
     */
    DataOutput getOutput();

    /**
     * Gets the number of bytes available for read
     */
    int getAvailableBytes();

    /**
     * Whether this connection is open or not
     */
    boolean isConnected();

    /**
     * Creates the connection
     */
    void connect();

    /**
     * Breaks the connection
     */
    void disconnect();
}
