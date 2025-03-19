package seedu.address.logic.commands;


import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

/**
 * Displays the note of a {@code Person} identified using it's displayed index from the address book.
 */
public class ViewNoteCommand extends Command {

    public static final String COMMAND_WORD = "viewnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the note of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_NOTE_PERSON_SUCCESS = "Successfully displayed note";

    private final Index targetIndex;

    /**
     * @param targetIndex of the person in the filtered person list to change notes
     */
    public ViewNoteCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_VIEW_NOTE_PERSON_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewNoteCommand)) {
            return false;
        }

        ViewNoteCommand otherViewNoteCommand = (ViewNoteCommand) other;
        return targetIndex.equals(otherViewNoteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
