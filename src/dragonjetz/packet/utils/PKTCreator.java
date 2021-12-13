package dragonjetz.packet.utils;

import dragonjetz.packet.Packet;

import java.io.DataInput;
import java.io.IOException;

/**
 * An interface for something that creates a packet instance from a data input
 * @param <T> The packet type
 */
public interface PKTCreator<T extends Packet> {
    /**
     * Crates a packet using the given data input, and the given length of the packet to be read
     * @param input The data input
     * @param length The length of the packet tail (not including the head). This usually isn't used by the creators, but it's provided anyway
     * @return A packet instance
     * @throws IOException Thrown if there was an IO problem while reading data
     */
    T create(DataInput input, int length) throws IOException;
}