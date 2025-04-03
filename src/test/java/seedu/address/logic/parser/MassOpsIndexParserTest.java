package seedu.address.logic.parser;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;

public class MassOpsIndexParserTest {
    private MassOpsIndexParser massOpsIndexParser;
    private final Set<Index> tripleIndexes = Set.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
    private final Set<Index> doubleIndexes = Set.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
    private final String singleIndexString = "12345";
    private final Set<Index> singleIndex = Set.of(Index.fromOneBased(Integer.parseInt(singleIndexString)));

    @BeforeEach
    public void setUp() {
        massOpsIndexParser = new MassOpsIndexParser();
    }
    @Test
    public void parseIndexes_validSpacePattern_success() {
        assertDoesNotThrow(() -> tripleIndexes.equals(massOpsIndexParser.parseIndexes("1 2 3")));
        assertDoesNotThrow(() -> tripleIndexes.equals(massOpsIndexParser.parseIndexes(" 1 2  3")));
        assertDoesNotThrow(() -> doubleIndexes.equals(massOpsIndexParser.parseIndexes("1   2 ")));
        assertDoesNotThrow(() -> doubleIndexes.equals(massOpsIndexParser.parseIndexes("   1   2 ")));
        assertDoesNotThrow(() -> singleIndex.equals(massOpsIndexParser.parseIndexes(singleIndexString)));
    }

    @Test
    public void parseIndexes_invalidSpacePattern_exceptionThrown() {
        ParseException pe = assertThrows(ParseException.class, () -> massOpsIndexParser.parseIndexes("1    123oi"));
        assertEquals(MassOpsIndexParser.MESSAGE_INVALID_INDEX, pe.getMessage());
    }

    @Test
    public void parseIndexes_overflowingIndex_exceptionThrown() {
        ParseException pe = assertThrows(
                ParseException.class, () -> massOpsIndexParser.parseIndexes("18327234238349853457834975"));
        assertEquals(MassOpsIndexParser.MESSAGE_INVALID_INDEX, pe.getMessage());

        ParseException pe1 = assertThrows(
                ParseException.class, () -> massOpsIndexParser.parseIndexes("1293889123-124823723857349852378329747"));
        assertEquals(MassOpsIndexParser.MESSAGE_INVALID_INDEX, pe1.getMessage());
    }

    @Test
    public void parseIndexes_validRangePattern_success() {
        assertDoesNotThrow(() -> tripleIndexes.equals(massOpsIndexParser.parseIndexes("1-3")));
        assertDoesNotThrow(() -> tripleIndexes.equals(massOpsIndexParser.parseIndexes(" 1 - 3 ")));
        assertDoesNotThrow(() -> doubleIndexes.equals(massOpsIndexParser.parseIndexes("1-2")));
        assertDoesNotThrow(() -> doubleIndexes.equals(massOpsIndexParser.parseIndexes(" 1 -     2 ")));
        assertDoesNotThrow(() -> singleIndex.equals(massOpsIndexParser.parseIndexes("12345-12345")));
    }

    @Test
    public void parseIndexes_invalidRangePattern_exceptionThrown() {
        ParseException pe = assertThrows(ParseException.class, () -> massOpsIndexParser.parseIndexes("1- 123oi"));
        assertEquals(MassOpsIndexParser.MESSAGE_INVALID_INDEX, pe.getMessage());
    }

    @Test
    public void parseIndexes_rangeExceedsLimit_exceptionThrown() {
        ParseException pe = assertThrows(ParseException.class, () -> massOpsIndexParser.parseIndexes("1-101"));
        assertEquals(MassOpsIndexParser.MESSAGE_RANGE_SIZE_EXCEEDED, pe.getMessage());

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= MassOpsIndexParser.MAX_OPERATIONS_SIZE + 1; i++) {
            builder.append(" ").append(i);
        }
        ParseException pe1 = assertThrows(
                ParseException.class, () -> massOpsIndexParser.parseIndexes(builder.toString()));
        assertEquals(MassOpsIndexParser.MESSAGE_RANGE_SIZE_EXCEEDED, pe1.getMessage());
    }

    @Test
    public void parseIndexes_invalidRangeBoundOrder_exceptionThrown() {
        ParseException pe = assertThrows(ParseException.class, () -> massOpsIndexParser.parseIndexes("100-2"));
        assertEquals(MassOpsIndexParser.MESSAGE_INVALID_RANGE_ORDER, pe.getMessage());
    }

    @Test
    public void parseIndexes_duplicateIndexes_duplicatesIgnored() {
        try {
            Set<Index> expectedIndexes = new HashSet<>();
            expectedIndexes.add(INDEX_FIRST_PERSON);
            expectedIndexes.add(INDEX_SECOND_PERSON);
            expectedIndexes.add(INDEX_THIRD_PERSON);

            Set<Index> resultIndexes = massOpsIndexParser.parseIndexes("1 2 2 3 3");
            assertEquals(expectedIndexes, resultIndexes);
            Set<Index> resultIndexesReversed = massOpsIndexParser.parseIndexes("3 2 1 1");
            assertEquals(expectedIndexes, resultIndexesReversed);
        } catch (ParseException pe) {
            fail();
        }
    }

    @Test
    public void parseIndexes_rangedIgnoresMaxInteger_success() {
        try {
            Set<Index> indexes = massOpsIndexParser.parseIndexes("2147483647-2147483647");
            assertEquals(1, indexes.size());
            assertTrue(indexes.contains(Index.fromOneBased(Integer.MAX_VALUE)));
        } catch (ParseException e) {
            fail();
        }

    }


}
