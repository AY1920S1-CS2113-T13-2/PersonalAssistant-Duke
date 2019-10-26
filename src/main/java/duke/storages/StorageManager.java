package duke.storages;

import duke.exceptions.DukeException;
import duke.models.patients.Patient;
import duke.models.assignedtasks.AssignedTaskWithPeriod;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.AssignedTaskWithDate;
import duke.models.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * It is a centralized class to manage all the storages related to data w/r.
 * It provides API for saving during command execution.
 */
public class StorageManager {

    private static final String COMMAND_COUNTER_FILENAME = "counter.csv";
    private static final String PATIENT_FILENAME = "patients.csv";
    private static final String ASSIGNED_TASK_FILENAME = "patientsTasks.csv";
    private static final String STANDARD_TASK_FILENAME = "standardTasks.csv";

    private static final String[] COMMAND_COUNTER_HEADERS = {"Command Name", "Frequency"};
    private static final String[] ASSIGNED_TASK_HEADERS = {"PID", "TID", "DONE",
        "RECURRENCE", "DEADLINE", "STARTTIME", "ENDTIME", "TASKTYPE", "uuid"};
    private static final String[] PATIENT_HEADERS = {"Id", "Name", "NRIC", "Room", "Remark"};
    private static final String[] STANDARD_TASK_HEADERS = {"Id", "Description"};

    private CsvStorage commandCounterStorage;
    private CsvStorage patientStorage;
    private CsvStorage assignedTaskStorage;
    private CsvStorage standardTaskStorage;

    /**
     * Initialize all storages to perform save/load of all data.
     *
     * @param filePath relative path of where all local data store
     */
    public StorageManager(String filePath) {
        this.commandCounterStorage = new CsvStorage(filePath + "/" + COMMAND_COUNTER_FILENAME);
        this.patientStorage = new CsvStorage(filePath + "/" + PATIENT_FILENAME);
        this.assignedTaskStorage = new CsvStorage(filePath + "/" + ASSIGNED_TASK_FILENAME);
        this.standardTaskStorage = new CsvStorage(filePath + "/" + STANDARD_TASK_FILENAME);
    }

    /**
     * Save patient data in the format of("Id", "Name", "NRIC", "Room", "Remark") to local csv files.
     *
     * @param patients a list containing patient's info
     */
    public void savePatients(ArrayList<Patient> patients) throws DukeException {
        // Initialize capacity of 3000 rows of patient's information
        ArrayList<ArrayList<String>> infoList = new ArrayList<>(3000);
        try {
            for (Patient patient : patients) {
                ArrayList<String> row = new ArrayList<String>();
                row.add(String.valueOf(patient.getId())); // Append value of column ID in a row
                row.add(patient.getName()); // Append value of column Name in a row
                row.add(patient.getNric()); // Append value of column Nric in a row
                row.add(patient.getRoom()); // Append value of column Room in a row
                row.add(patient.getRemark()); // Append value of column Remark in a row
                infoList.add(row);
            }
            patientStorage.write(infoList, PATIENT_HEADERS);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Save task data in the format of ("Id", "Description") to local csv files.
     *
     * @param tasks a list containing task's info
     */
    public void saveTasks(ArrayList<Task> tasks) throws DukeException {
        // Initialize capacity of 30 rows of standard task's info
        ArrayList<ArrayList<String>> infoList = new ArrayList<>(3000);
        try {
            for (Task task : tasks) {
                ArrayList<String> row = new ArrayList<String>();
                row.add(String.valueOf(task.getId())); // Append value of column ID in a row
                row.add(task.getDescription()); // Append value of column description in a row
                infoList.add(row);
            }
            standardTaskStorage.write(infoList, STANDARD_TASK_HEADERS);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Save patientTask data in the format of ("Id", "Description") to local csv files.
     *
     * @param assignedTasks a list of patientTask
     * @throws DukeException show saving error message
     */
    public void saveAssignedTasks(ArrayList<AssignedTask> assignedTasks) throws DukeException {
        // Initialize capacity of 200 rows of patient-specified task's info
        ArrayList<ArrayList<String>> infoList = new ArrayList<>(200);
        try {
            for (AssignedTask assignedTask : assignedTasks) {
                String pid = String.valueOf(assignedTask.getPid());
                String tid = String.valueOf(assignedTask.getPid());
                String uniqueId = String.valueOf(assignedTask.getUuid());
                String isDone = String.valueOf(assignedTask.getIsDone());
                String isRecur = String.valueOf(assignedTask.getIsRecursive());
                String deadline = null;
                String startTime = null;
                String endTime = null;
                String type = assignedTask.getType();

                if (assignedTask instanceof AssignedTaskWithDate) {
                    deadline = ((AssignedTaskWithDate) assignedTask).getTodoDateRaw();
                } else if (assignedTask instanceof AssignedTaskWithPeriod) {
                    startTime = ((AssignedTaskWithPeriod) assignedTask).getStartDateRaw();
                    endTime = ((AssignedTaskWithPeriod) assignedTask).getEndDateRaw();
                }
                ArrayList<String> row = new ArrayList<String>(Arrays.asList(pid, tid, isDone, isRecur,
                    deadline, startTime, endTime, type, uniqueId));
                infoList.add(row);
            }
            assignedTaskStorage.write(infoList, ASSIGNED_TASK_HEADERS);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Save command frequency to local csv files.
     *
     * @param counts A map containing commands as keys and frequent count as values
     */
    public void saveCounters(Map<String, Integer> counts) throws DukeException {
        // Initialize capacity of 20 commands type.
        ArrayList<ArrayList<String>> infoList = new ArrayList<>(20);
        try {
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                ArrayList<String> row = new ArrayList<String>();
                row.add(entry.getKey()); // Append value of column "Command Name" in a row
                row.add(entry.getValue().toString()); // Append value of column 'Frequency' in a row
                infoList.add(row); // Append row to the list of rows
            }
            commandCounterStorage.write(infoList, COMMAND_COUNTER_HEADERS);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Load a list of patients.
     *
     * @return a list of patients containing patient's info
     * @throws DukeException show loading warning message
     */
    public ArrayList<Patient> loadPatients() throws DukeException {
        // Load a list of Map<header, values> from local data file
        ArrayList<Map<String, String>> patientsMap = patientStorage.read();
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        try {
            for (Map<String, String> patientInfo : patientsMap) {
                int id = Integer.parseInt(patientInfo.get(PATIENT_HEADERS[0]));
                String name = patientInfo.get(PATIENT_HEADERS[1]);
                String nric = patientInfo.get(PATIENT_HEADERS[2]);
                String room = patientInfo.get(PATIENT_HEADERS[3]);
                String remark = patientInfo.get(PATIENT_HEADERS[4]);
                patientList.add(new Patient(id, name, nric, room, remark));
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
        return patientList;
    }

    /**
     * Load task info from local csv data files.
     *
     * @return a list of tasks
     * @throws DukeException return load error exception
     */
    public ArrayList<Task> loadTasks() throws DukeException {
        // Load a list of Map<header, values> from local data file
        ArrayList<Map<String, String>> tasksMap = standardTaskStorage.read();
        ArrayList<Task> taskList = new ArrayList<Task>();
        try {
            for (Map<String, String> taskInfo : tasksMap) {
                int id = Integer.parseInt(taskInfo.get(STANDARD_TASK_HEADERS[0]));
                String description = taskInfo.get(STANDARD_TASK_HEADERS[1]);
                taskList.add(new Task(id, description));
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
        return taskList;
    }

    /**
     * Load patient assigned task.
     *
     * @return list of patient task relation
     * @throws DukeException return load error exception
     */
    public ArrayList<AssignedTask> loadAssignedTasks() throws DukeException {
        // Load a list of Map<header, values> from local data file
        ArrayList<Map<String, String>> assignedTaskMap = assignedTaskStorage.read();
        ArrayList<AssignedTask> assignedTaskList = new ArrayList<AssignedTask>();
        try {
            for (Map<String, String> assignedTaskInfo : assignedTaskMap) {
                int pid = Integer.parseInt(assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[0]));
                int tid = Integer.parseInt(assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[1]));
                boolean isDone = Boolean.parseBoolean(assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[2]));
                boolean isRecursive = Boolean.parseBoolean(assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[3]));
                String deadline = assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[4]);
                String startTime = assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[5]);
                String endTime = assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[6]);
                String taskType = assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[7]);
                int uniqueId = Integer.parseInt(assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[8]));
                if (taskType.equals("date")) {
                    assignedTaskList.add(new AssignedTaskWithDate(pid, tid, isDone, isRecursive, deadline, taskType));
                } else if (taskType.equals("period")) {
                    assignedTaskList.add(new AssignedTaskWithPeriod(pid, tid, isDone, isRecursive,
                        startTime, endTime, taskType));
                }
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
        return assignedTaskList;
    }

    /**
     * To load command counter frequency.
     *
     * @return return the command with frequency counts
     * @throws DukeException return load error exception
     */
    public Map<String, Integer> loadCommandFrequency() throws DukeException {
        // Load a list of Map<header, values> from local data file
        ArrayList<Map<String, String>> counterMap = commandCounterStorage.read();
        Map<String, Integer> integratedCounterMap = new HashMap<>();
        try {
            for (Map<String, String> rowInfo : counterMap) {
                String commandName = rowInfo.get(COMMAND_COUNTER_HEADERS[0]);
                int frequency = Integer.parseInt(rowInfo.get(COMMAND_COUNTER_HEADERS[1]));
                integratedCounterMap.put(commandName, frequency);
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
        return integratedCounterMap;
    }
}
