package cli_java_task.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
class Task {
    private String title;
    private String description;
    private String dueDate;
    private String priority;
    private boolean completed;

    public Task(String title, String description, String dueDate, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false; // Default status
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n"
                + "Description: " + description + "\n"
                + "Due Date: " + dueDate + "\n"
                + "Priority: " + priority + "\n"
                + "Status: " + (completed ? "Completed" : "Not Completed") + "\n";
    }
}

class TaskManager {
    private List<Task> tasks;
    protected static final Logger logger = LogManager.getLogger();
    private static final String INVALID_TASK_INDEX ="invalid task index.";
    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public String displayTasks() {
        if (tasks.isEmpty()) {
            logger.info("No tasks found.");
            return "Displayed Nothing";
        }
        logger.info("Tasks:");
        for (Task task : tasks) {
            logger.info(task);

        }
        return "Displayed All Tasks";
    }

    public void editTask(int index, Task updatedTask) {
        if (index >= 0 && index < tasks.size()) {
            tasks.set(index, updatedTask);
            logger.info("Task updated successfully.");
        } else {
           logger.info(INVALID_TASK_INDEX );
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            logger.info("Task deleted successfully.");
        } else {
            logger.info(INVALID_TASK_INDEX );
        }
    }

    public void markTaskAsComplete(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.setCompleted(true);
            tasks.set(index, task);
            logger.info("Task marked as complete.");
        } else {
            logger.info(INVALID_TASK_INDEX );
        }
    }
    public List<Task> getTasks() {
        return tasks;
    }
}

public class App {
    public static void main(String[] args) {
        new App().run();
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        Logger logger = LogManager.getLogger();

        boolean exit = false;
        while (!exit) {
            logger.info("\nTask Management Menu:");
            logger.info("1. View tasks");
            logger.info("2. Add task");
            logger.info("3. Edit task");
            logger.info("4. Delete task");
            logger.info("5. Mark task as complete");
            logger.info("6. Exit");
            logger.info("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    String output = taskManager.displayTasks();
                    logger.info(output);
                    break;
                case 2:
                    logger.info("Enter task title: ");
                    String title = scanner.nextLine();
                    logger.info("Enter task description: ");
                    String description = scanner.nextLine();
                    logger.info("Enter due date: ");
                    String dueDate = scanner.nextLine();
                    logger.info("Enter priority (High/Medium/Low): ");
                    String priority = scanner.nextLine();
                    Task newTask = new Task(title, description, dueDate, priority);
                    taskManager.addTask(newTask);
                    logger.info("Task added successfully.");
                    break;
                case 3:
                    logger.info("Enter index of task to edit: ");
                    int editIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    logger.info("Enter new title: ");
                    String newTitle = scanner.nextLine();
                    logger.info("Enter new description: ");
                    String newDescription = scanner.nextLine();
                    logger.info("Enter new due date: ");
                    String newDueDate = scanner.nextLine();
                    logger.info("Enter new priority (High/Medium/Low): ");
                    String newPriority = scanner.nextLine();
                    Task updatedTask = new Task(newTitle, newDescription, newDueDate, newPriority);
                    taskManager.editTask(editIndex, updatedTask);
                    break;
                case 4:
                    logger.info("Enter index of task to delete: ");
                    int deleteIndex = scanner.nextInt();
                    taskManager.deleteTask(deleteIndex);
                    break;
                case 5:
                    logger.info("Enter index of task to mark as complete: ");
                    int completeIndex = scanner.nextInt();
                    taskManager.markTaskAsComplete(completeIndex);
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    logger.info("Invalid choice. Please enter a valid option.");
            }
        }

        logger.info("Exiting Task Management App. Goodbye!");
        scanner.close();

    }
}
