package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Redoes the effect of the last undo
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Successfully redone command";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.addressBookHasRedo()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NO_REDO);
        }

        model.redo();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
