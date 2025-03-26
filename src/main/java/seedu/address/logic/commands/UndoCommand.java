package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.nio.charset.CoderMalfunctionError;

import static java.util.Objects.requireNonNull;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Successfully undone last command";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.addressBookHasUndo()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NO_UNDO);
        }

        model.undoAddressBook();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
