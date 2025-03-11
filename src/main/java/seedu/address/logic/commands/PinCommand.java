package seedu.address.logic.commands;

import seedu.address.model.Model;

public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Pin logic not implemented");
    }
}
