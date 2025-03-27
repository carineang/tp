package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;

public class RedoCommandTest {

    @Test
    public void execute_RedosLeft_success() {
        Model model = new ModelManager();
        model.addPerson(CARL);
        model.undoAddressBook();
        Model expectedModel = new ModelManager();
        expectedModel.addPerson(CARL);
        expectedModel.undoAddressBook();
        expectedModel.redoAddressBook();

        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noRedosLeft_failure() {
        Model model = new ModelManager();

        assertCommandFailure(new RedoCommand(), model, Messages.MESSAGE_INVALID_NO_REDO);
    }
}
