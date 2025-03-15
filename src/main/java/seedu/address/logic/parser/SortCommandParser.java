package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_SORT_UNSUCCESSFUL;
import static seedu.address.logic.parser.CliSyntax.*;

public class SortCommandParser implements Parser<SortCommand> {

    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.equals(PREFIX_NAME.getPrefix())) {
            return new SortCommand(PREFIX_NAME.getPrefix());
        } else if (trimmedArgs.equals(PREFIX_PHONE.getPrefix())) {
            return new SortCommand(PREFIX_PHONE.getPrefix());
        } else if (trimmedArgs.equals(PREFIX_EMAIL.getPrefix())) {
            return new SortCommand(PREFIX_EMAIL.getPrefix());
        } else if (trimmedArgs.equals(PREFIX_ADDRESS.getPrefix())) {
            return new SortCommand(PREFIX_ADDRESS.getPrefix());
        } else {
            throw new ParseException(String.format(MESSAGE_SORT_UNSUCCESSFUL, SortCommand.MESSAGE_USAGE));
        }
    }
}
