package bob;

import java.util.regex.Pattern;

public class UnknownCommand extends Command {
    private String input;
    private static final Pattern COMPLIMENT_PATTERN =
            Pattern.compile("handsome|beautiful", Pattern.CASE_INSENSITIVE);
    
    public UnknownCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        // First check if it's a compliment (keep this fun feature)
        if (COMPLIMENT_PATTERN.matcher(input).find()) {
            ui.showComplimentResponse();
            return;
        }

        // Check if it's a help command without the prefix
        if (input.trim().equalsIgnoreCase("help")) {
            ui.showHelp();
            return;
        }

        // For everything else, show error instead of creating task
        ui.showError("Unknown command: '" + input + "'. Type 'help' to see available commands.");
    }
}
