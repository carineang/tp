package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the last command
 * The undos only affect commands that change the state of the address book
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Successfully undone last command";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.addressBookHasUndo()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NO_UNDO);
        }

        model.undo();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
