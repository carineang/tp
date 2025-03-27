package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PinTest {

    @Test
    public void constructor_boolean_setsCorrectState() {
        Pin pinned = new Pin(true);
        Pin notPinned = new Pin(false);

        assertTrue(pinned.isPinned());
        assertFalse(notPinned.isPinned());
        assertEquals("[PINNED]", pinned.toString());
        assertEquals("", notPinned.toString());
    }

    @Test
    public void constructor_validString_setsCorrectState() {
        assertTrue(new Pin("true").isPinned());
        assertTrue(new Pin("[PINNED]").isPinned());
        assertFalse(new Pin("false").isPinned());
        assertFalse(new Pin("").isPinned());
    }

    @Test
    public void constructor_invalidString_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Pin("invalidValue"));
        assertThrows(NullPointerException.class, () -> new Pin((String) null));
    }

    @Test
    public void isPinned_returnsCorrectValue() {
        assertTrue(new Pin(true).isPinned());
        assertFalse(new Pin(false).isPinned());
    }

    @Test
    public void toString_returnsExpectedSymbol() {
        assertEquals("[PINNED]", new Pin(true).toString());
        assertEquals("", new Pin(false).toString());
    }

    @Test
    public void equals_and_hashCode_workCorrectly() {
        Pin pin1 = new Pin(true);
        Pin pin2 = new Pin("[PINNED]");
        Pin pin3 = new Pin(false);

        assertEquals(pin1, pin2);
        assertEquals(pin1.hashCode(), pin2.hashCode());
        assertNotEquals(pin1, pin3);
        assertNotEquals(pin2, pin3);
    }
}
