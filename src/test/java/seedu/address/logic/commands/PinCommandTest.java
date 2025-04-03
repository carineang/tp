package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code PinCommand}.
 */
public class PinCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_pinsPerson() throws Exception {
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_PERSON);

        CommandResult result = pinCommand.execute(model);

        // Ensure person is now at the top of the list and pinned
        Person pinnedPerson = model.getFilteredPersonList().get(0);
        assertTrue(pinnedPerson.getPin().isPinned());
        String ExpectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS, INDEX_FIRST_PERSON.getOneBased());
        assertEquals(ExpectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PinCommand pinCommand = new PinCommand(outOfBoundIndex);

        assertThrows(CommandException.class, () -> pinCommand.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexAlreadyPinnedPerson_returnsMessage() throws Exception {
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_PERSON);
        pinCommand.execute(model);
        PinCommand newPinCommand = new PinCommand(INDEX_FIRST_PERSON);
        CommandResult newResult = newPinCommand.execute(model);
        String ExpectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_ALREADY_PINNED, INDEX_FIRST_PERSON.getOneBased());
        assertEquals(ExpectedMessage, newResult.getFeedbackToUser());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
