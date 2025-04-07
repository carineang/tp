package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void nullTagName_isValidTagName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void isValidTagName_returnsTrue() {
        assertTrue(Tag.isValidTagName("Tag"));
    }

    @Test
    public void tagWithSpace_isValidTagName_returnsTrue() {
        assertTrue(Tag.isValidTagName("Tag with space"));
    }

    @Test
    public void constructor_validTagNameWithExtraSpaces_normalizesTagName() {
        // leading and trailing spaces
        Tag tagWithExtraSpaces = new Tag("   urgent task   ");
        assertTrue(tagWithExtraSpaces.toString().equals("[urgent task]"));

        // multiple spaces between words
        tagWithExtraSpaces = new Tag("urgent    task");
        assertTrue(tagWithExtraSpaces.toString().equals("[urgent task]"));

        // multiple spaces between words and leading/trailing spaces
        tagWithExtraSpaces = new Tag("   urgent    task   ");
        assertTrue(tagWithExtraSpaces.toString().equals("[urgent task]"));

        // multiple consecutive spaces
        tagWithExtraSpaces = new Tag("urgent  task  123");
        assertTrue(tagWithExtraSpaces.toString().equals("[urgent task 123]"));
    }
}
