package reghzy.packet.packets;

import com.sun.deploy.util.StringUtils;
import com.sun.istack.internal.NotNull;
import reghzy.packet.utils.PKTCreator;
import reghzy.packet.utils.PacketUtils;
import reghzy.packet.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet1Chat extends Packet {
    private final String text;

    public Packet1Chat(@NotNull String text) {
        this.text = text;
    }

    static {
        register(Packet1Chat.class, 1, new PKTCreator<Packet1Chat>() {
            @Override
            public Packet1Chat create(DataInput input, int length) throws IOException {
                return new Packet1Chat(PacketUtils.readStringWL(input));
            }
        });
    }

    @Override
    public void write(DataOutput output) throws IOException {
        PacketUtils.writeStringWL(this.text, output);
    }

    @Override
    public int getLength() {
        return PacketUtils.getStringLenWL(this.text);
    }

    public String getText() {
        return text;
    }
}
