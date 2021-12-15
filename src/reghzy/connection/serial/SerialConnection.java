package reghzy.connection.serial;

import com.fazecast.jSerialComm.SerialPort;
import reghzy.connection.IConnection;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;

public class SerialConnection implements IConnection {
    private final SerialPort port;
    private final DataInputStream input;
    private final DataOutputStream output;

    public SerialConnection(String port, int baud) {
        this.port = SerialPort.getCommPort(port);
        this.port.setBaudRate(baud);
        this.port.setParity(SerialPort.NO_PARITY);
        this.port.setNumDataBits(8);
        this.port.setNumStopBits(SerialPort.ONE_STOP_BIT);

        this.input = new DataInputStream(this.port.getInputStream());
        this.output = new DataOutputStream(this.port.getOutputStream());
    }

    public SerialPort getPort() {
        return this.port;
    }

    @Override
    public DataInput getInput() {
        return this.input;
    }

    @Override
    public DataOutput getOutput() {
        return this.output;
    }

    @Override
    public int getAvailableBytes() {
        return this.port.bytesAvailable();
    }

    @Override
    public boolean isConnected() {
        return this.port.isOpen();
    }

    @Override
    public void connect() {
        if (!this.port.openPort()) {
            throw new RuntimeException("Failed to open port");
        }
    }

    @Override
    public void disconnect() {
        if (!this.port.closePort()) {
            throw new RuntimeException("Failed to close port");
        }
    }
}
