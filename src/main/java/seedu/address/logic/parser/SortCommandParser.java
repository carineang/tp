package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_SORT_UNSUCCESSFUL;
import static seedu.address.logic.parser.CliSyntax.*;

public class SortCommandParser implements Parser<SortCommand> {

    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_SORT_UNSUCCESSFUL, SortCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.equals(PREFIX_NAME.getPrefix())) {
            System.out.println("Sorted successfully...");
            return new SortCommand(PREFIX_NAME.getPrefix());
        }

        return null;
    }
}