package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class UndoCommandTest {

    @Test
    public void execute_undosLeft_success() {
        Model model = new ModelManager();
        model.addPerson(CARL);
        Model expectedModel = new ModelManager();
        expectedModel.addPerson(CARL);
        expectedModel.undoAddressBook();

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noundosLeft_failure() {
        Model model = new ModelManager();

        assertCommandFailure(new UndoCommand(), model, Messages.MESSAGE_INVALID_NO_UNDO);
    }
}
