package bob;

import java.util.ArrayList;

/**
 * Represents a command to search for tasks containing a specific keyword.
 * The search is case-insensitive and matches partial words.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Creates a find command for the specified keyword.
     *
     * @param keyword The keyword to search for in task descriptions
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching all tasks for the keyword
     * and displaying the matching tasks.
     *
     * @param tasks The task list to search through
     * @param ui The user interface for displaying matching tasks
     * @param storage The storage (unused in find command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matches = tasks.findTasks(keyword);
        ui.showMatchingTasks(matches, keyword);
    }
}
