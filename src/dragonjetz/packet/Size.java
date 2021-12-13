package dragonjetz.packet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An indicator for the size of a field. This is mainly used just as a visual indicator for how many bytes to send
 * <p>
 *     E.g if data is stored in an integer, but only 2 bytes (a short/ushort value) is sent, then anything above 32767/65535 will be discarded
 * </p>
 */
@Retention(value = RetentionPolicy.SOURCE)
@Target(value = ElementType.FIELD)
public @interface Size {
    /**
     * The size of the field, in bytes
     * <p>
     *     byte = 1,
     *     short = 2,
     *     int = 4,
     *     long = 8
     *     string = ?
     * </p>
     */
    int size();
}
