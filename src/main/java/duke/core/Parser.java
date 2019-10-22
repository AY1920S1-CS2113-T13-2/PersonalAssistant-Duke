package duke.core;

public class Parser {

    String userInput;
    String[] parsedInput;

    /**
     * Constructor for the Parser class.
     *
     * @param userInput Takes in user's raw input and stores it to use in its methods, parsing it
     *                  into a format that is appropriate for the command it invokes.
     */
    public Parser(String userInput) throws DukeException {
        this.userInput = userInput.trim();
        try {
            parsedInput = userInput.split("/");
        } catch (Exception e) {
            throw new DukeException("Could not parse user input!");
        }
    }

    /**
     * Parses user input so that it is compatible with `add patient` command.
     *
     *
     * `add patient` output: patient_name, patient_NRIC, patient_room, patient_remark
     *
     * @return A formatted string that will work for the available `add patient` command.
     * @throws DukeException If the method is unable to parse the userInput correctly, it will throw
     *                       a DukeException.
     */
    public String[] parseAddPatient() throws DukeException {
        String[] formattedOutput = new String[4];
        try {
            for (int i = 1; i < parsedInput.length; i++) {
                formattedOutput[i-1] = parsedInput[i].trim();
            }
            return formattedOutput;
        } catch (Exception e) {
            throw new DukeException("Please follow the `add patient /patient name /NRIC /patient room /patient_remark` format.");
        }
    }

    /**
     * Parses user input so that it is compatible with `add task` command.
     *
     * `add task` output: task_description
     *
     * @return A formatted string that will work for the available `add task` command.
     * @throws DukeException If the method is unable to parse the userInput correctly, it will throw
     *                       a DukeException.
     */
    public String parseAddTask() throws DukeException {
        String formattedOutput;
        try {
            formattedOutput = parsedInput[1];
        } catch (Exception e) {
            throw new DukeException("Please follow the `add task /task description`.");
        }
        return formattedOutput;
    }

    /**
     * Takes the user input and formats it so it is compatible with 'Assign' commands.
     *
     * @return A string of formatted output to be used by 'Assign' commands.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String[] parseAssign() throws DukeException {
        String[] formattedInput = new String[5];
        try {
            String[] tempInput = userInput.trim().replaceAll(
                    "(?i)assign ", "").split("\\s+", 4);
            if (tempInput[0].equals("E")) {
                String[] parsedTimes = tempInput[3].split(" to ", 2);

                for (int i = 0; i < 3; i++) {
                    formattedInput[i] = tempInput[i];
                }
                formattedInput[3] = parsedTimes[0];
                formattedInput[4] = parsedTimes[1];
            } else {
                for (int i = 0; i < tempInput.length; i++) {
                    formattedInput[i] = tempInput[i];
                }
            }
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Invalid 'assign' command.");
        }
    }

    /**
     * Takes the user input and formats it so it is compatible with the 'list' case in CommandManager.
     *
     * @return A string of formatted output to be used by 'list' case in CommandManager.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String[] parseList() throws DukeException {
        try {
            String[] formattedInput;
            formattedInput = userInput.replaceAll("(?i)list ", "").trim().split("\\s+");
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Invalid 'list' command.");
        }
    }

    /**
     * Takes the user input and formats it so it is compatible with 'delete patient' command.
     *
     * @return A string of formatted output to be used by 'delete patient' commands.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String parseDeletePatient() throws DukeException {
        try {
            String formattedInput;
            String inputToParse = userInput.replaceAll("(?i)delete patient ", "").trim();
            formattedInput = inputToParse;
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Invalid 'delete' command format.");
        }
    }

    /**
     * Takes the user input and formats it so it is compatible with 'delete task' command.
     *
     * @return A string of formatted output to be used by 'delete task' commands.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String parseDeleteTask() throws DukeException {
        try {
            String formattedInput;
            String inputToParse = userInput.replaceAll("(?i)delete task ", "").trim();
            formattedInput = inputToParse;
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Invalid 'delete task' command.");
        }
    }

    /**
     * Takes user input and formats it so it is compatible with 'delete patient task' command.
     *
     * @return Array of strings to be used by 'delete patient task' command.
     * @throws DukeException when user input cannot be parsed properly.
     */
    public String[] parseDeletePatientTask() throws DukeException {
        try {
            String[] formattedInput;
            String inputToParse = userInput.replaceAll("(?i)delete patient task ", "").trim();
            formattedInput = inputToParse.split("\\s+", 2);

            return formattedInput;

        } catch (Exception e) {
            throw new DukeException("Invalid 'delete patient task' command.");
        }
    }

    /**
     * Takes the user input and formats it so it is compatible with 'update patient' command.
     *
     * @return A string of formatted output to be used by 'update patient' commands.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String parseUpdatePatient() throws DukeException {
        String formattedInput;
        String inputToParse = userInput.replaceAll("(?i)update patient ", "").trim();
        formattedInput = inputToParse;
        return formattedInput;
    }

    /**
     * Takes the user input and formats it so it is compatible with 'find' commands.
     *
     * @return A string of formatted output to be used by 'find' commands.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String parseFind() throws DukeException {
        try {
            String formattedInput;
            String[] inputToParse = userInput.replaceAll("(?i)find patient ", "").trim().split("\\s+");

            if (inputToParse[0].equalsIgnoreCase("task")) {
                formattedInput = userInput.replaceAll("(?i)find patient task ", "").trim();
            } else {
                formattedInput = userInput.replaceAll("(?i)find patient ", "").trim();
            }
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Invalid 'find patient' command.");
        }
    }


    /**
     * Takes the user input and formats it so it is compatible with 'update task' command.
     *
     * @return A string of formatted output to be used by 'update task' commands.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String parseUpdateTask() throws DukeException {
        String formattedInput;
        String inputToParse = userInput.replaceAll("(?i)update task ", "").trim();
        formattedInput = inputToParse;
        return formattedInput;
    }
}