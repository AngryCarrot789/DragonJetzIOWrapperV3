package reghzy.packet.packets;

import reghzy.packet.Size;
import reghzy.packet.Packet;
import reghzy.packet.utils.PKTCreator;
import reghzy.packet.utils.PacketUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet2AccountInfo extends Packet {
    private final String userName;

    @Size(size = 1)
    private final int age;

    private final boolean married;

    @Size(size = 1)
    private final int childrenCount;

    static {
        register(Packet2AccountInfo.class, 2, new PKTCreator<Packet2AccountInfo>() {
            @Override
            public Packet2AccountInfo create(DataInput input, int length) throws IOException {
                return new Packet2AccountInfo(PacketUtils.readStringWL(input), input.readUnsignedByte(), input.readBoolean(), input.readUnsignedByte());
            }
        });
    }

    public Packet2AccountInfo(String userName, int age, boolean married, int childrenCount) {
        this.userName = userName;
        this.age = age;
        this.married = married;
        this.childrenCount = childrenCount;
    }

    public String getUserName() {
        return this.userName;
    }

    public int getAge() {
        return this.age;
    }

    public boolean isMarried() {
        return this.married;
    }

    public int getChildrenCount() {
        return this.childrenCount;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        PacketUtils.writeStringWL(this.userName, output);
        output.writeByte(this.age);
        output.writeBoolean(this.married);
        output.writeByte(this.childrenCount);
    }

    @Override
    public int getLength() {
        return PacketUtils.getStringLenWL(this.userName) + 3;
    }
}
