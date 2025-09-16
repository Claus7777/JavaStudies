import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class App {
    static ArrayList<Task> tasks;

    
    public static void main(String[] args) throws Exception {
        try {
            tasks = (ArrayList<Task>) loadTasks("tasks");
        } catch (FileNotFoundException e) {
            System.out.println("Task file not found. Creating new one.");
            tasks = new ArrayList<Task>();
        }


        if (args.length > 0){
            switch (args[0]){
                case "add":
                    if (args.length > 1){
                        String newTaskDescription = "";
                        for (int i = 1; i < args.length; i++){
                            newTaskDescription = newTaskDescription + args[i];
                            if ((i+1) < args.length){
                                    newTaskDescription = newTaskDescription + " ";
                                }
                        }
                        for (Task task : tasks) {
                            if (task.description.equals(newTaskDescription)) {
                                System.out.println("This task already exists. Do you want to create a new one anyway? (Y/N)");

                                Scanner scanner = new Scanner(System.in);
                                String response = scanner.nextLine().trim().toUpperCase();
                                boolean validResponse = false;

                                do {
    
                                    if (response.equals("Y") || response.equals("YES")) {
                                        validResponse = true;
                                    } else if (response.equals("N") || response.equals("NO")) {
                                        System.out.println("Task creation cancelled.");
                                        validResponse = true;
                                        scanner.close();
                                        return;
                                    } else {
                                        System.out.println("Please enter Y or N:");
                                        response = scanner.nextLine().trim().toUpperCase();
                                    }
                                } while (!validResponse);
                                scanner.close();
                                break;
                            }
                        }
                        addTask(newTaskDescription);
                        break;
                    }
                    else printHelp();
                    break;

                case "update":
                    if (args.length > 1){
                        try {
                            int idToUpdate = Integer.parseInt(args[1]);
                            String newUpdatedDescription = "";
                            System.out.println(args[2]);
                            for (int i = 2; i < args.length; i++){
                                newUpdatedDescription = newUpdatedDescription + args[i];
                                if ((i+1) < args.length){
                                    newUpdatedDescription = newUpdatedDescription + " ";
                                }
                            }

                            updateTask(idToUpdate, newUpdatedDescription);
                        }
                        catch (NumberFormatException e){
                            System.err.println(e);
                            printHelp();
                            break;
                        }
                    }
                    else printHelp();
                    break;

                case "mark":
                    if (args.length > 2) { 
                        try {
                            int idToMark = Integer.parseInt(args[1]);
                            if (args[2].equals("done")) {
                                markTask(idToMark, Task.Status.DONE); 
                            } else if (args[2].equals("in-progress")) {
                                markTask(idToMark, Task.Status.IN_PROGRESS);
                            } else if (args[2].equals("todo")) {
                                markTask(idToMark, Task.Status.TO_DO);
                            } else {
                                System.out.println("Invalid status: " + args[2]); 
                                printHelp();
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid task ID: " + args[1]);
                            printHelp();
                        }
                    } else {
                        System.out.println("Error: mark command requires both ID and status");
                        printHelp();
                    }
                    break;

                case "list":
                    if (args.length > 1){
                        if (args[1].equals("done")){
                            listTasks(Task.Status.DONE);
                            break;
                        } else if (args[1].equals("todo")){
                            listTasks(Task.Status.TO_DO);
                            break;
                        } else if (args[1].equals("in-progress")) {
                            listTasks(Task.Status.IN_PROGRESS);
                            break;
                        }
                        else printHelp();
                        break;
                    }
                    listTasks();
                    break;
                
                case "help":
                    printHelp(true);

                default:
                    printHelp();
                    break;
            }
        }
        saveTasks();
    }

    static void saveTasks() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream("tasks"))){
                oos.writeObject(tasks);
            }
    }

    static Object loadTasks(String filename) throws IOException, ClassNotFoundException{
        try (ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(filename))){
            return ois.readObject();
        }
    }

    static void addTask(String description){
        int id = tasks.size();
        Task taskToAdd = new Task(id, description, Task.Status.TO_DO);
        System.out.println("Task ID: " + (taskToAdd.id) + " \"" + taskToAdd.description + "\" added to the task list.");

        tasks.add(taskToAdd);
    }

    static void updateTask(int id, String newDescription){
        try {
            Task taskToUpdate = tasks.get(id);
            System.out.println("Task ID: " + (taskToUpdate.id) + " \"" + taskToUpdate.description + "\" updated description to " + newDescription + " at " + taskToUpdate.updatedAt);
            taskToUpdate.description = newDescription;
            taskToUpdate.setUpdatedAt();
        } catch (Exception e) {
            System.out.println("Task not found.");
        }
    }

    static void deleteTask(int id){
        try {
            Task taskToRemove = tasks.get(id);
            System.out.println("Task " + (taskToRemove.id ) + " " + taskToRemove.description + " removed.");
            tasks.remove(taskToRemove.id);
        } catch (Exception e){
            System.out.println("Task doesn't exist");
        }
    }

    static void markTask(int id, Task.Status status){
        try {
            Task taskToMark = tasks.get(id);
            taskToMark.status = status;
            taskToMark.setUpdatedAt();
            System.out.println("Task ID: " + (taskToMark.id) + " " + taskToMark.description + " updated status to " + taskToMark.status.toString() + " at " + taskToMark.updatedAt);
        } catch (Exception e){
            System.err.println(e);
            System.out.println("Task doesn't exist.");
        }      
    }

    static void listTasks(){
        String taskDescription;
        String taskStatus;

        for (int i = 0; i < tasks.size(); i++){
            Task taskToAdd = tasks.get(i);
            taskDescription = taskToAdd.description;
            switch(taskToAdd.status){
                case IN_PROGRESS:
                    taskStatus = "In Progress";
                    break;
                case DONE:
                    taskStatus = "Done";
                    break;
                default:
                    taskStatus = "To do";
                    break;
            }
            System.out.println("ID:" + taskToAdd.id + " " + taskDescription + " Status: " + taskStatus);
        }
        
    }

    static void listTasks(Task.Status status){
        String taskDescription;
        String taskStatus;

        for (int i = 0; i < tasks.size(); i++){
            Task taskToAdd = tasks.get(i);
            if (taskToAdd.status == status){
            taskDescription = taskToAdd.description;

                switch(taskToAdd.status){
                    case IN_PROGRESS:
                        taskStatus = "In Progress";
                        break;
                    case DONE:
                        taskStatus = "Done";
                        break;
                    default:
                        taskStatus = "To do";
                        break;
                }

            System.out.println(taskDescription + ": " + taskStatus);
            }
        }
    }

    public static void printHelp() {
    printHelp(false); 
    }

    public static void printHelp(boolean verbose) {
        if (verbose) {
            printVerboseHelp();
        } else {
            printCompactHelp();
        }
    }

    private static void printCompactHelp() {
        System.out.println("Task Manager CLI - Quick Reference");
        System.out.println("Usage: java TaskManager <command> [arguments]");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  add <description>        Add new task");
        System.out.println("  update <id> <desc>       Update task description");
        System.out.println("  mark <id> <status>       Change task status");
        System.out.println("  list [status]            Show tasks (all or filtered)");
        System.out.println("  help                Show verbose help (use for detailed help)");
        System.out.println();
        System.out.println("Status values: done, in-progress, todo");
        System.out.println("Use 'help' for detailed examples and explanations");
    }

    private static void printVerboseHelp() {
        System.out.println("=== TASK MANAGER CLI - DETAILED HELP ===");
        System.out.println();
        System.out.println("USAGE: java TaskManager <command> [arguments]");
        System.out.println();
        
        System.out.println("ADD: add <description>");
        System.out.println("  Adds a new task");
        System.out.println("  Example: add \"Buy groceries\" or add Complete project");
        System.out.println();
        
        System.out.println("UPDATE: update <id> <new_description>");
        System.out.println("  Updates task description");
        System.out.println("  Example: update 3 \"New description\"");
        System.out.println();
        
        System.out.println("MARK: mark <id> <status>");
        System.out.println("  Changes task status (done, in-progress, todo)");
        System.out.println("  Example: mark 2 done");
        System.out.println();
        
        System.out.println("LIST: list [status]");
        System.out.println("  Shows tasks - all or filtered by status");
        System.out.println("  Examples: list, list done, list in-progress");
        System.out.println();
        
        System.out.println("TIPS:");
        System.out.println("  • Use quotes for descriptions with spaces: add \"Complex task name\"");
        System.out.println("  • Run 'list' first to see available task IDs");
        System.out.println("  • Tasks are automatically saved after each operation");
    }

}




