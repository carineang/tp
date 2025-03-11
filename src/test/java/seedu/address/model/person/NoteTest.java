package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.Note.BLANK_NOTE_VALUE;
import static seedu.address.model.person.Note.DEFAULT_NOTE_VALUE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void isBlank() {
        // blank notes
        assertTrue(new Note(DEFAULT_NOTE_VALUE).isBlank()); // empty string
        assertTrue(new Note(" ").isBlank()); // spaces only
        assertTrue(new Note("    ").isBlank()); // more spaces only

        // non blank notes
        assertFalse(new Note("Has been bricked").isBlank()); // normal case
        assertFalse(new Note("a").isBlank()); // one character
        assertFalse(new Note(" a").isBlank()); // space before
        assertFalse(new Note("12345").isBlank()); // numbers only
        assertFalse(new Note("😇").isBlank()); // emoji
        assertFalse(new Note("汉字").isBlank()); // Chinese characters
        assertFalse(new Note("ありがとう").isBlank()); // Japanese hiragana
    }

    @Test
    public void equals() {
        Note note = new Note("Valid Note");

        // same values -> return true
        assertTrue(note.equals(new Note("Valid Note")));

        // same object -> return true
        assertTrue(note.equals(note));

        // null -> returns false
        assertFalse(note.equals(null));

        // different types -> return false
        assertFalse(note.equals(123f)); // float
        assertFalse(note.equals(-1)); // negative int
        assertFalse(note.equals(100)); // int
        assertFalse(note.equals(100d)); // double

        // different values -> return false
        assertFalse(note.equals("Other Valid Note")); // float
    }

    @Test
    public void createDefaultNote() {
        Note defaultNote = Note.createDefaultNote();

        // value
        assertEquals(DEFAULT_NOTE_VALUE, defaultNote.value);

        // string
        assertEquals(BLANK_NOTE_VALUE, defaultNote.toString());

        // is blank
        assertTrue(defaultNote.isBlank());
    }

    @Test
    public void toStringMethod() {
        // blank notes
        assertEquals(BLANK_NOTE_VALUE, new Note(DEFAULT_NOTE_VALUE).toString());
        assertEquals(BLANK_NOTE_VALUE, new Note(" ").toString());
        assertEquals(BLANK_NOTE_VALUE, new Note("     ").toString());


        // non blank notes
        assertEquals("overseas", new Note("overseas").toString());
        assertEquals("  a", new Note("  a").toString());
    }
}
