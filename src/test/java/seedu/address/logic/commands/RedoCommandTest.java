package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class RedoCommandTest {

    @Test
    public void execute_redosLeft_success() {
        Model model = new ModelManager();
        model.addPerson(CARL);
        model.commit();
        model.undo();
        Model expectedModel = new ModelManager();
        expectedModel.addPerson(CARL);
        expectedModel.commit();
        expectedModel.undo();
        expectedModel.redo();

        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noredosLeft_failure() {
        Model model = new ModelManager();

        assertCommandFailure(new RedoCommand(), model, Messages.MESSAGE_INVALID_NO_REDO);
    }
}
