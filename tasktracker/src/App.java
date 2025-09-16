import java.io.*;
import java.util.ArrayList;


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
                            newTaskDescription = newTaskDescription + " " + args[i];
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
                            for (int i = 2; i < args.length; i++){
                                newUpdatedDescription = newUpdatedDescription + args[i];
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
                   if (args.length > 1){
                        try {
                            int idToMark = Integer.parseInt(args[1]);
                            if (args[2].equals("done")){
                                markTask(idToMark, Task.Status.DONE); 
                                break; 
                            }
                            else if (args[2].equals("in-progress")){
                                markTask(idToMark, Task.Status.IN_PROGRESS);
                                break;
                            }
                            else if (args[2].equals("todo")){
                                markTask(idToMark, Task.Status.TO_DO);
                                break;
                            }
                            else {
                                System.out.println(args[2]); 
                                printHelp();
                                break;
                            }
                        }
                        catch (NumberFormatException e){
                            System.err.println(e);
                            printHelp();
                            break;
                        }
                    }
                    else printHelp();
                    break;

                case "list":
                    if (args.length > 1){
                        if (args[1] == "done"){
                            listTasks(Task.Status.DONE);
                            break;
                        } else if (args[1] == "todo"){
                            listTasks(Task.Status.TO_DO);
                            break;
                        } else if (args[1] == "in-progress") {
                            listTasks(Task.Status.IN_PROGRESS);
                            break;
                        }
                        else printHelp();
                        break;
                    }
                    listTasks();
                    break;

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
        Task task = new Task(id, description, Task.Status.TO_DO);

        tasks.add(task);
    }

    static void updateTask(int id, String newDescription){
        tasks.get(id).description = newDescription;
    }

    static void deleteTask(int id){
        tasks.remove(tasks.get(id));
    }

    static void markTask(int id, Task.Status status){
        try {
            Task taskToMark = tasks.get(id);
            taskToMark.status = status;
            taskToMark.setUpdatedAt();
            System.out.println("Task " + (taskToMark.id + 1) + " " + taskToMark.description + " updated status to " + taskToMark.status.toString() + " at " + taskToMark.updatedAt);
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

    static void printHelp(){
        System.out.println("wrong");
    }
}




