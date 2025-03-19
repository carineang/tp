package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

public class InputHistoryTest {

    private InputHistory emptyInputHistory;
    @BeforeEach
    public void setUp() {
        emptyInputHistory = new InputHistory();
    }

    @Test
    public void addInput_addInputWhenFull_success() {
        for (int i = 0; i < 20; i++) {
            emptyInputHistory.addInput("input " + i);
        }
        ObservableList<String> previousCommands = emptyInputHistory.getPastCommands();
        assertEquals(20, previousCommands.size());
        assertEquals("input 19", previousCommands.get(0));

        emptyInputHistory.addInput("input 20");
        assertEquals("input 20", previousCommands.get(0));
        assertEquals("input 1", previousCommands.get(19));
    }
}
