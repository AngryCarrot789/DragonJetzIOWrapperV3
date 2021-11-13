package reghzy.serial;

import com.fazecast.jSerialComm.SerialPort;

public class SerialDevice {
    private final SerialPort port;

    public SerialDevice(String port, int baud) {
        this.port = SerialPort.getCommPort(port);
        this.port.setBaudRate(baud);
        this.port.setParity(SerialPort.NO_PARITY);
        this.port.setNumDataBits(8);
        this.port.setNumStopBits(SerialPort.ONE_STOP_BIT);
    }
}
