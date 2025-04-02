package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Displays the note of a {@code Person} identified using it's displayed index from the address book.
 */
public class ViewNoteCommand extends Command {

    public static final String COMMAND_WORD = "viewnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the note of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_NOTE_PERSON_SUCCESS = "Successfully displaying note: \n %1$s";

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

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(targetIndex.getZeroBased());
        Note note = personToView.getNote();

        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_VIEW_NOTE_PERSON_SUCCESS, note.toString()));
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
