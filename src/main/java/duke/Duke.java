package duke;

import duke.MementoPattern.Memento;
import duke.MementoPattern.MementoManager;
import duke.MementoPattern.MementoParser;
import duke.command.Command;
import duke.core.*;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.statistic.Counter;
import duke.storage.StorageManager;
import duke.task.TaskManager;


/**
 * Represents Duke, a Personal Assistant to help
 * users tracking their progress.
 */
public class Duke {
    /**
     * A Storage object that handles reading tasks from a local
     * file and saving them to the same file.
     */
    private StorageManager storageManager;

    /**
     * A TaskList object that deals with add, delete, mark as done,
     * find functions of a list of tasks.
     */
    private PatientTaskList patientTaskList;
    private TaskManager taskManager;
    private PatientManager patientManager;
    private Counter counter;

    /**
     * A Ui object that deals with interactions with the user.
     */
    private MementoManager mementoManager;
    private MementoParser mementoParser;
    /**
     * A Ui object that deals with interactions with the user.
     */
    private Ui ui = Ui.getUi();

    /**
     * Constructs a Duke object with a relative file path.
     * Initialize the user interface and reads tasks from the specific text file.
     *
     * @param filePath A string that represents the path of the local file
     *                 used for storing tasks.
     */
    public Duke(String filePath) {
        storageManager = new StorageManager(filePath);
        mementoManager = new MementoManager();
        try {
            patientTaskList = new PatientTaskList(storageManager.loadAssignedTasks());
            taskManager = new TaskManager(storageManager.loadTasks());
            patientManager = new PatientManager(storageManager.loadPatients());
            counter = new Counter(storageManager.loadCommandFrequency());

        } catch (DukeException e) {
            ui.showLoadingError();
            System.out.println(e.getMessage());
            taskManager = new TaskManager();
        }
    }

    /**
     * .
     *
     * @return .
     */
    public void getDukeStateFromMemento(Memento memento) {
        taskManager = memento.getTaskState();
        patientTaskList = memento.getPatientTaskState();
        patientManager = memento.getPatientState();
    }

    /**
     * .
     * .
     * @return .
     */
    public Memento saveDukeStateToMemento() {
        return new Memento(new TaskManager(taskManager.getTaskList())
                , new PatientTaskList(patientTaskList.fullPatientTaskList())
                , new PatientManager(patientManager.getPatientList()));
    }

    /**
     * Runs the Duke program.
     * Reads user input until a "bye" message is received.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = CommandManager.manageCommand(fullCommand);
                if (mementoParser.getSaveFlag(c).equals("save")) {
                    Memento newMem = saveDukeStateToMemento();
                    mementoManager.add(newMem);
                } else if (mementoParser.getSaveFlag(c).equals("pop")) {
                    getDukeStateFromMemento(mementoManager.pop());
                }
                c.execute(patientTaskList, taskManager, patientManager,
                    ui, storageManager);
                counter.runCommandCounter(c, storageManager, counter);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        System.exit(0);
    }

    /**
     * Starts the Duke thread and Reminder thread concurrently
     * by passing a filepath to duke and a global ui object&
     * task list to Reminder.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new Duke("./data").run();
    }
}
