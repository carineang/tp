package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import java.security.MessageDigest;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;

public class UndoCommandTest {

    @Test
    public void execute_UndosLeft_success() {
        Model model = new ModelManager();
        model.addPerson(CARL);
        Model expectedModel = new ModelManager();
        expectedModel.addPerson(CARL);
        expectedModel.undoAddressBook();

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noUndosLeft_failure() {
        Model model = new ModelManager();

        assertCommandFailure(new UndoCommand(), model, Messages.MESSAGE_INVALID_NO_UNDO);
    }
}
