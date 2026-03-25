package bob;

/**
 * Entry point of the Bob task management application.
 * Bob is a chatbot that helps users manage their tasks (todos, deadlines, events).
 * It supports adding, listing, marking, unmarking, deleting, and finding tasks,
 * with persistent storage between sessions.
 */
public class Bob {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private Parser parser;

    /**
     * Initializes the Bob application with the specified file path for storage.
     * Sets up the UI, parser, and attempts to load existing tasks from storage.
     *
     * @param filePath The path to the file used for saving/loading tasks
     */
    public Bob(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();

        try {
            storage.initialize();
            tasks = new TaskList(storage.load());
            ui.showMessage("Loaded " + tasks.size() + " tasks from save file.");
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }

        // Add shutdown hook to save on Ctrl+C or program termination
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                storage.save(tasks.getAllTasks());
                System.out.println("\n" + Ui.INDENT + "Tasks saved before exit.");
            } catch (Exception e) {
                System.out.println("\n" + Ui.INDENT + "Warning: Could not save tasks.");
            }
        }));
    }

    /**
     * Runs the main program loop, reading and executing user commands
     * until the exit command is received.
     */
    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();

                // Skip empty lines - this prevents the spam
                if (fullCommand.trim().isEmpty()) {
                    continue;
                }

                ui.showLine();

                Command command = parser.parseCommand(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();

            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * The main entry point of the Bob application.
     * Creates a new Bob instance with the default save file path and runs it.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new Bob("data/bob.txt").run();
    }
}
