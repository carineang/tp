package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandHistoryTest {
    private CommandHistory emptyCommandHistory;

    @BeforeEach
    public void setUp() {
        emptyCommandHistory = new CommandHistory();
    }

    private CommandHistory getCommandHistoryOfSize(int size) {
        CommandHistory commandHistory = new CommandHistory();
        assertTrue(size > 0 && size <= CommandHistory.MAX_HISTORY_SIZE);
        for (int i = 1; i <= size; i++) {
            commandHistory.addInput("input " + i);
        }
        return commandHistory;
    }

    @Test
    public void maxHistorySize_isPositiveInteger() {
        assertTrue(CommandHistory.MAX_HISTORY_SIZE > 0);
    }

    @Test
    public void addInput_addInputWhenFull_success() {
        for (int i = 0; i < CommandHistory.MAX_HISTORY_SIZE; i++) {
            emptyCommandHistory.addInput("input " + i);
        }
        ObservableList<String> previousCommands = emptyCommandHistory.getPastCommands();
        assertEquals(20, previousCommands.size());
        assertEquals("input 19", previousCommands.get(0));

        emptyCommandHistory.addInput("input 20");
        assertEquals("input 20", previousCommands.get(0));
        assertEquals("input 1", previousCommands.get(19));
    }

    @Test
    public void addInput_duplicateInputsFullHistory_ignored() {
        final String duplicateMessage = "input 20";

        ObservableList<String> expectedInputList = FXCollections.observableArrayList();
        CommandHistory actualFullCommandHistory = new CommandHistory();
        for (int i = 1; i <= CommandHistory.MAX_HISTORY_SIZE; i++) {
            expectedInputList.add(0, "input " + i);
            actualFullCommandHistory.addInput("input " + i);
        }
        // Adding into a full input history
        actualFullCommandHistory.addInput(duplicateMessage);
        assertEquals(expectedInputList, actualFullCommandHistory.getPastCommands());

        actualFullCommandHistory.addInput(new String(duplicateMessage));
        assertEquals(expectedInputList, actualFullCommandHistory.getPastCommands());
    }

    @Test
    public void addInput_duplicateInputsEmptyHistory_ignored() {
        final String duplicateMessage = "add 1 n/123";
        // Adding duplicates into an empty input history
        CommandHistory expectedCommandHistory = new CommandHistory();
        expectedCommandHistory.addInput(duplicateMessage);

        emptyCommandHistory.addInput(duplicateMessage);

        // Add duplicates
        emptyCommandHistory.addInput(duplicateMessage);
        assertEquals(expectedCommandHistory, emptyCommandHistory);
        // check that .equals() equality is actually maintained
        emptyCommandHistory.addInput(new String(duplicateMessage));
        assertEquals(expectedCommandHistory, emptyCommandHistory);
    }

    @Test
    public void addInput_duplicateInputsNonEmptyNonFullHistory_ignored() {
        // Adding duplicates into a non-empty, non-full input history
        final String duplicateMessage = "input 5";
        final int nonEmptyNonFullInputSize = 5;

        CommandHistory nonFullCommandHistory = new CommandHistory();
        CommandHistory expectedNonFullCommandHistory = new CommandHistory();
        for (int i = 1; i <= nonEmptyNonFullInputSize; i++) {
            nonFullCommandHistory.addInput("input " + i);
            expectedNonFullCommandHistory.addInput("input " + i);
        }

        // Add duplicates
        nonFullCommandHistory.addInput(duplicateMessage);
        assertEquals(expectedNonFullCommandHistory, nonFullCommandHistory);

        // check that .equals() equality is actually maintained
        nonFullCommandHistory.addInput(new String(duplicateMessage));
        assertEquals(expectedNonFullCommandHistory, nonFullCommandHistory);
    }

    @Test
    public void equals() {
        CommandHistory commandHistory = new CommandHistory();
        // same values -> returns true
        assertTrue(commandHistory.equals(emptyCommandHistory));

        // same object -> returns true
        assertTrue(commandHistory.equals(commandHistory));

        // null -> returns false
        assertFalse(commandHistory.equals(null));

        // different types -> returns false
        assertFalse(commandHistory.equals(5.0f));

        // different input containers
        commandHistory = getCommandHistoryOfSize(3);
        assertFalse(commandHistory.equals(emptyCommandHistory));

        CommandHistory otherCommandHistory = getCommandHistoryOfSize(3);
        // same non-empty values
        assertTrue(commandHistory.equals(otherCommandHistory));
    }

    @Test
    public void testHashCode() {
        CommandHistory commandHistory = new CommandHistory();
        // same values -> returns same hashcode
        assertEquals(commandHistory.hashCode(), emptyCommandHistory.hashCode());

        // same object -> returns same hashcode
        assertEquals(commandHistory.hashCode(), commandHistory.hashCode());

        // different types -> returns false
        assertNotEquals(commandHistory.hashCode(), 5.0f);

        // different input containers
        commandHistory = getCommandHistoryOfSize(3);
        assertNotEquals(commandHistory.hashCode(), emptyCommandHistory.hashCode());

        CommandHistory otherCommandHistory = getCommandHistoryOfSize(3);
        // same non-empty values
        assertEquals(commandHistory.hashCode(), otherCommandHistory.hashCode());
    }

    @Test
    public void testToString() {
        // empty histories have the same string representation
        assertEquals(emptyCommandHistory.toString(), new CommandHistory().toString());

        // non-empty histories with the same inputs have the same string representations
        assertEquals(getCommandHistoryOfSize(3), getCommandHistoryOfSize(3));

        // non-empty histories with different histories have different string representations
        CommandHistory commandHistory = getCommandHistoryOfSize(3);
        CommandHistory otherHistory = getCommandHistoryOfSize(4);
        assertNotEquals(commandHistory.toString(), otherHistory.toString());

        // same full histories have same representations
        assertEquals(
                getCommandHistoryOfSize(CommandHistory.MAX_HISTORY_SIZE).toString(),
                getCommandHistoryOfSize(CommandHistory.MAX_HISTORY_SIZE).toString());
    }
}
