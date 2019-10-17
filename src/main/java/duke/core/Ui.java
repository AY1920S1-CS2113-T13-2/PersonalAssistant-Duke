package duke.core;

import duke.patient.Patient;
import duke.relation.PatientTask;
import duke.task.Task;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the necessary ui elements for user interaction.
 */
public class Ui {
    /**
     * weifeng
     * A Scanner to read user input.
     */
    private Scanner scanner;

    /**
     * Constructs a singleton Ui design pattern by using lazy initialization.
     */

    private Ui() {
        scanner = new Scanner(System.in);
    }

    private static Ui ui;

    public static Ui getUi() {
        if (ui == null) {
            ui = new Ui();
        }
        return ui;
    }

    /**
     * Reads user instruction.
     *
     * @return A string that represents the user instruction.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String e) {
        System.out.println("☹" + e);
    }

    public String showErrorGui(String e) {
        String output = "☹" + e ;
        System.out.println(output);
        return output;
    }

    public void taskAdded(Task standardTask) {
        System.out.println("Got it. I've added this task: \n" + standardTask.getDescription());
    }

    public String taskAddedGui(Task standardTask) {
        String output = "Got it. I've added this task: \n" + standardTask.getDescription();
        return output;
    }

    public void showPatientInfo(Patient patient) {
        System.out.println("Name: " + patient.getName() + "  Id: " + patient.getID()
                + "\nNRIC: " + patient.getNRIC() + "  Room: " + patient.getRoom()
                + "\nRemark: " + patient.getRemark());
    }

    public String showPatientInfoGui(Patient patient) {
        String output = "Name: " + patient.getName() + "  Id: " + patient.getID()
                + "\nNRIC: " + patient.getNRIC() + "  Room: " + patient.getRoom()
                + "\nRemark: " + patient.getRemark();
        return output;
    }

    public void showTaskInfo(Task task) {
        System.out.println("Task: " + task.getDescription());
    }

    public String showTaskInfoGui(Task task) {
        String output = "Task: " + task.getDescription();
        return output;
    }


    public void patientsFoundByName(ArrayList<Patient> patients, String name) {
        if (patients.size() > 0) {
            System.out.println("Got it. " + patients.size() + " patients is/are found with name: " + name);
            int i = 1;
            for (Patient patient : patients) {
                System.out.println("Patient #" + i);
                showPatientInfo(patient);
                showLine();
                i++;
            }
        } else {
            System.out.println("No patient was found with name: " + name);
        }
    }

    public String patientsFoundByNameGui(ArrayList<Patient> patients, String name) {
        String output = "";
        if (patients.size() > 0) {
            output += "Got it. " + patients.size() + " patients is/are found with name: " + name;
            int i = 1;
            for (Patient patient : patients) {
                output += "/n Patient #" + i;
                showPatientInfo(patient);
                showLine();
                i++;
            }
        } else {
            output += "No patient was found with name: " + name;
        }
        return output;
    }

    public void tasksFoundByDescription(ArrayList<Task> tasks, String description) {
        if (tasks.size() > 0) {
            System.out.println("Got it. " + tasks.size() + " tasks is/are found with description: " + description);
            int i = 1;
            for (Task task : tasks) {
                System.out.println("Task #" + i);
                showTaskInfo(task);
                showLine();
                i++;
            }
        } else {
            System.out.println("No task was found with description: " + description);
        }
    }

    public String tasksFoundByDescriptionGui(ArrayList<Task> tasks, String description) {
        String output = "";
        if (tasks.size() > 0) {
            output += "Got it. " + tasks.size() + " tasks is/are found with description: /n ";
            int i = 1;
            for (Task task : tasks) {
                output += "Task #" + i;
                showTaskInfo(task);
                showLine();
                i++;
            }
        } else {
            output += "No task was found with description: " + description;
        }
        return output;
    }

    public void patientsFoundById(Patient patient) {
        System.out.println("Got it. The patient is found.");
        showPatientInfo(patient);
    }

    public String patientsFoundByIdGui(Patient patient) {
        String output = "Got it. The patient is found. /n" + showPatientInfoGui(patient);
        return output;
    }


    public void patientAdded(Patient patient) {
        System.out.println("Got it. The following patient has been added:  ");
        showPatientInfo(patient);
    }

    public String patientAddedGui(Patient patient) {
        String output = "Got it. The following patient has been added: /n" + showPatientInfoGui(patient);
        return output;
        //showPatientInfo(patient);
    }

    public void patientTaskAssigned(PatientTask patientTask, String patientName, String taskName) {
        System.out.println("Got it. The following Patient ID: " + patientTask.getPatientId() + " " + patientName + " has been assigned the Task ID: " + patientTask.getTaskID() + " " + taskName);
    }

    public String patientTaskAssignedGui(PatientTask patientTask, String patientName, String taskName) {
        String output = "Got it. The following Patient ID: " + patientTask.getPatientId() + " " + patientName + " has been assigned the Task ID: " + patientTask.getTaskID() + " " + taskName;
        return output;
    }
//
//    public int choosePatientToDelete(int numberOfPatients) {
//        int chosenNumber = -1;
//        while (true) {
//            System.out.println("Enter the number of patient to delete, or enter number 0 to cancel: ");
//            String command = readCommand();
//            try {
//                chosenNumber = Integer.parseInt(command);
//            } catch (Exception e) {
//                System.out.println("Please enter a valid number!");
//                continue;
//            }
//            if (chosenNumber >= 0 && chosenNumber <= numberOfPatients) {
//                if (chosenNumber == 0) {
//                    System.out.println("Delete command is canceled");
//                }
//                return chosenNumber;
//            } else {
//                System.out.println("The patient #" + chosenNumber + " does not exist. Please enter a valid number!");
//            }
//        }
//
//    }
//
//    public int chooseTaskToDelete(int numberOfTasks) {
//        int chosenNumber = -1;
//        while (true) {
//            System.out.println("Enter the number of task to delete, or enter number 0 to cancel: ");
//            String command = readCommand();
//            try {
//                chosenNumber = Integer.parseInt(command);
//            } catch (Exception e) {
//                System.out.println("Please enter a valid number!");
//                continue;
//            }
//            if (chosenNumber >= 0 && chosenNumber <= numberOfTasks) {
//                if (chosenNumber == 0) {
//                    System.out.println("Delete command is canceled");
//                }
//                return chosenNumber;
//            } else {
//                System.out.println("The task #" + chosenNumber + " does not exist. Please enter a valid number!");
//            }
//        }
//
//    }
//
//    public boolean confirmPatientToBeDeleted(Patient patient) {
//        showPatientInfo(patient);
//        while (true) {
//            System.out.println("The patient is to be deleted. Are you sure (Y/N)? ");
//            String command = readCommand();
//            if (command.toLowerCase().equals("y")) {
//                return true;
//            } else if (command.toLowerCase().equals("n")) {
//                System.out.println("Delete command is canceled");
//                return false;
//            } else {
//                System.out.println("Please enter only Y/N to confirm/cancel deletion!");
//            }
//        }
//    }

    public void patientDeleted() {
        System.out.println("Got it. The patient is deleted.");
    }

    public String patientDeletedGui() {
        String output = "Got it. The patient is deleted.";
        return output;
    }

    public void taskDeleted() {
        System.out.println("Got it. The task is deleted.");
    }

    public String taskDeletedGui() {
        String output = "Got it. The task is deleted.";
        return output;
    }

    public void listAllPatients(ArrayList<Patient> patients) {
        for (Patient patient : patients) {
            showPatientInfo(patient);
            showLine();
        }
    }

    public String listAllPatientsGui(ArrayList<Patient> patients) {
        String output = "";
        for (Patient patient : patients) {
            output += showPatientInfoGui(patient) + showLineGui() + "/n";
        }
        return output;
    }

    public void listAllTasks(ArrayList<Task> taskList) {
        int index = 1;
        System.out.println("Here's a list of your tasks: \n");
        for (Task task : taskList) {
            System.out.println(index
                    + ". "
                    + task.getDescription()
                    + " (ID: "
                    + task.getID()
                    + ")"
                    + "\n");
            index++;
        }
    }

    public String listAllTasksGui(ArrayList<Task> taskList) {
        String output = "";
        int index = 1;
        output += "Here's a list of your tasks: \n";
        for (Task task : taskList) {
            output += index
                    + ". "
                    + task.getDescription()
                    + " (ID: "
                    + task.getID()
                    + ")"
                    + "\n";

            index++;
        }
        return output;
    }

//    public boolean confirmTaskToBeDeleted(Task task) {
//        showTaskInfo(task);
//        while (true) {
//            System.out.println("The task is to be deleted. Are you sure (Y/N)? ");
//            String command = readCommand();
//            if (command.toLowerCase().equals("y")) {
//                return true;
//            } else if (command.toLowerCase().equals("n")) {
//                System.out.println("Delete command is canceled");
//                return false;
//            } else {
//                System.out.println("Please enter only Y/N to confirm/cancel deletion!");
//            }
//        }
//    }

    /**
     * Shows a divider line.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public String showLineGui() {
        String output = "____________________________________________________________";
        return output;
    }


    /**
     * Shows bye message to user.
     */
    public void exitInformation() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public String exitInformationGui() {
        String output = "Bye. Hope to see you again soon!";
        return output;
    }

    /**
     * Shows Duke logo and welcome message, and user input instructions.
     */
    public void showWelcome() {
        String logo = " _____        _              _ _        _ \n" +
                "|  __ \\      | |            (_) |      | |\n" +
                "| |  | |_   _| | _____ _ __  _| |_ __ _| |\n" +
                "| |  | | | | | |/ / _ \\ '_ \\| | __/ _` | |\n" +
                "| |__| | |_| |   <  __/ |_) | | || (_| | |\n" +
                "|_____/ \\__,_|_|\\_\\___| .__/|_|\\__\\__,_|_|\n" +
                "                      | |                 \n" +
                "                      |_|                 \n";

        System.out.println(logo);
        System.out.println("Hello! I'm Duke\nWhat can I do for you?\n\n");
        System.out.println("Enter 'help' to show a list of commands ");
    }

    public String showWelcomeGui() {
        String output = " _____        _              _ _        _ \n" +
                "|  __ \\      | |            (_) |      | |\n" +
                "| |  | |_   _| | _____ _ __  _| |_ __ _| |\n" +
                "| |  | | | | | |/ / _ \\ '_ \\| | __/ _` | |\n" +
                "| |__| | |_| |   <  __/ |_) | | || (_| | |\n" +
                "|_____/ \\__,_|_|\\_\\___| .__/|_|\\__\\__,_|_|\n" +
                "                      | |                 \n" +
                "                      |_|                 \n" +
                "Hello! I'm Duke\nWhat can I do for you?\n\n" +
                "Enter 'help' to show a list of commands ";

        return output;
    }


    public void showUpdatedSuccessfully() {
        System.out.println("I have successfully updated the following information: \n");
    }

    public void showLoadingError() {
        System.out.println("Failed to Load from local text file!");
    }

    public String showLoadingErrorGui() {
        String output = "Failed to Load from local text file!";
        return output;

    }

//    public void patientTaskFound(Patient patient, ArrayList<PatientTask> patientTask, ArrayList<Task> tasks) {
//        System.out.println("The tasks of patient " + patient.getID() + " " + patient.getName() + " is found : \n");
//        for (int i = 0; i < patientTask.size(); i++){
//            showLine();
//            System.out.println( tasks.get(i).getID() + ". " + tasks.get(i).getDescription() +"\n");
//            System.out.println( patientTask.get(i).toString());
//            showLine();
//        }
//    }

}
