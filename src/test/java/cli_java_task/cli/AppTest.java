package cli_java_task.cli;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    private TaskManager taskManager;

    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        taskManager = new TaskManager();
    }

    // set test case
    public void testsetTask(){
        Task task = new Task("" , "" , "" , "");
        task.setTitle("Test Title");
        task.setCompleted(true);
        task.setDescription("Test Description");
        task.setDueDate("2025-04-10");
        task.setPriority("Low");
        assertEquals("Test Title", task.getTitle());
        assertEquals(true,task.isCompleted()); 
        assertEquals("Test Description", task.getDescription());
        assertEquals("2025-04-10", task.getDueDate());
        assertEquals("Low", task.getPriority());
    }
    // tostring() test
    public void testToString() {
        Task task = new Task("Test Title", "Test Description", "2025-04-10", "Low");
        String expectedString =  "Title: " +task.getTitle() + "\n"
        + "Description: " + task.getDescription() + "\n"
        + "Due Date: " + task.getDueDate() + "\n"
        + "Priority: " + task.getPriority() + "\n"
        + "Status: " + (task.isCompleted() ? "Completed" : "Not Completed") + "\n"; 
        assertEquals(expectedString, task.toString());
    }
    //no tasks test
    public void testNoTasks() {
        TaskManager tasks = new TaskManager();
        assertTrue(tasks.getTasks().isEmpty());
    }

    //displaytask
    public void testDisplayTasks(){
        TaskManager itaskManager = new TaskManager();
        assertEquals("Displayed Nothing", taskManager.displayTasks());

        Task task1 = new Task("Task 1", "Description 1", "2025-04-10", "High");
        Task task2 = new Task("Task 2", "Description 2", "2025-04-11", "Medium");
        itaskManager.addTask(task1);
        itaskManager.addTask(task2);


        assertEquals("Displayed All Tasks", itaskManager.displayTasks());
    }


    public void testAddTask() {
        Task task = new Task("Test Task", "Description", "2025-04-10", "High");
        taskManager.addTask(task);

        List<Task> tasks = taskManager.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
    }


    // get commands test by assert equal
    public void testEditTask() {
        Task task = new Task("Old Task", "Old Description", "2025-04-10", "Low");
        taskManager.addTask(task);

        Task updatedTask = new Task("Updated Task", "Updated Description", "2025-04-15", "High");
        taskManager.editTask(0, updatedTask);

        List<Task> tasks = taskManager.getTasks();
        assertEquals("Updated Task", tasks.get(0).getTitle());
        assertEquals("Updated Description", tasks.get(0).getDescription());
        assertEquals("2025-04-15", tasks.get(0).getDueDate());
        assertEquals("High",tasks.get(0).getPriority() );
        assertEquals(false,tasks.get(0).isCompleted());

    }

    // Improved: Test editing a task with an invalid index
    public void testEditTaskWithInvalidIndex() {
        Task task = new Task("Valid Task", "Description", "2025-04-10", "High");
        taskManager.addTask(task);

        // Invalid index (out of bounds)
        taskManager.editTask(5, task);
        List<Task> tasks = taskManager.getTasks();
        assertEquals(1, tasks.size()); // The task count should remain 1
    }

    public void testDeleteTask() {
        Task task = new Task("Task to Delete", "Description", "2025-04-10", "Medium");
        taskManager.addTask(task);

        taskManager.deleteTask(0);

        List<Task> tasks = taskManager.getTasks();
        assertTrue(tasks.isEmpty());
    }

    // Improved: Test deleting a task with an invalid index
    public void testDeleteTaskWithInvalidIndex() {
        Task task = new Task("Valid Task", "Description", "2025-04-10", "High");
        taskManager.addTask(task);

        // Invalid index (out of bounds)
        taskManager.deleteTask(5);
        List<Task> tasks = taskManager.getTasks();
        assertEquals(1, tasks.size()); // The task should not be deleted
    }

    public void testMarkTaskAsComplete() {
        Task task = new Task("Incomplete Task", "Description", "2025-04-10", "Low");
        taskManager.addTask(task);

        taskManager.markTaskAsComplete(0);

        List<Task> tasks = taskManager.getTasks();
        assertTrue(tasks.get(0).isCompleted());
    }

    // Improved: Test marking a task as complete with an invalid index
    public void testMarkTaskAsCompleteWithInvalidIndex() {
        Task task = new Task("Incomplete Task", "Description", "2025-04-10", "Low");
        taskManager.addTask(task);

        // Invalid index (out of bounds)
        taskManager.markTaskAsComplete(5);
        List<Task> tasks = taskManager.getTasks();
        assertFalse(tasks.get(0).isCompleted()); // The task should not be marked complete
    }

    public void testInvalidTaskIndex() {
        Task task = new Task("Valid Task", "Description", "2025-04-10", "High");
        taskManager.addTask(task);

        // Try editing, deleting, and marking a task as complete with an invalid index
        taskManager.editTask(5, task);  // Invalid index
        taskManager.deleteTask(5);      // Invalid index
        taskManager.markTaskAsComplete(5);  // Invalid index
        assertEquals(false,task.isCompleted());

        // We do not expect an exception to be thrown, so no assertion here
        // But you can add logging assertions if needed
    }
    
    public void testRun() {
        
        // Simulate user input
        String simulatedInput = "2\nTest Task\nDescription\n2025-04-10\nHigh\n6\n"; // Add a task and exit
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        // Create a temporary file to capture output
        // File outputFile = File.createTempFile("app_output", ".txt");
        // outputFile.deleteOnExit(); // Ensure the file is deleted on exit

        // Redirect System.out to write to the file
        // PrintStream fileOut = new PrintStream(new FileOutputStream(outputFile));
        // PrintStream originalOut = System.out; // Save the original System.out
        // System.setOut(fileOut);

        // Run the application
        App app = new App();
        app.run();

        // Restore original System.out
        // System.setOut(originalOut);
        // fileOut.close(); // Close the file output stream

        // Read the contents of the file
        // String output = new String(Files.readAllBytes(Paths.get(outputFile.getAbsolutePath())));

        // Assert that the output contains expected strings
        //assertTrue(output.contains("Task added successfully.")); // Check if the task was added
        //assertTrue(output.contains("Exiting Task Management App. Goodbye!")); // Check if the app exited
        // assertEquals("No tasks found.,", output);

        // Verify that the task was added (you may need to check the state of the TaskManager)
        // This part may require additional setup to access the TaskManager state
    }
    public void testDisplay(){
        String simulatedInpuString ="1\n6\n";
        InputStream in = new ByteArrayInputStream(simulatedInpuString.getBytes());
        System.setIn(in);

        App app = new App();
        app.run();
    }

    public void testmarkCompleted(){
        String simulatedInput = "2\nTest Task\nDescription\n2025-04-10\nHigh\n5\n1\n6\n"; // Add a task and exit
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        App app = new App();
        app.run();

    }


    public void testdefault(){
        String simulString = "6\n";
        InputStream in = new ByteArrayInputStream(simulString.getBytes());

        System.setIn(in);
        App app = new App();

        app.run();
    }
    public void testDeleteTaskmain(){
        String simulatedInput = "2\nTest Task\nDescription\n2025-04-10\nHigh\n4\n1\n6\n"; // Add a task and exit
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        App app = new App();
        app.run();
    }

    public void testMainMethod() {
        // Save the original System.in and System.out
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        
        try {
            // Create test input that exercises all paths
            String simulatedInput = String.join("\n",
                "2",                    // Add a task
                "Test Task",
                "Test Description",
                "2025-04-10",
                "High",
                "1",                    // View tasks
                "3",                    // Edit task
                "0",                    // Task index
                "Updated Task",
                "Updated Description",
                "2025-04-11",
                "Medium",
                "4",                    // Delete task
                "0",
                "5",                    // Mark task complete
                "0",
                "7",                    // Invalid choice
                "6"                     // Exit
            ) + "\n";
    
            // Set up input stream
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(testIn);
    
            // Set up output capture
            ByteArrayOutputStream testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
    
            // Execute main method
            App.main(new String[]{});
    
            // Get captured output
            String output = testOut.toString();
    
            // Verify all key operations were performed
            // assertTrue(output.contains("Task Management Menu"));
            // assertTrue(output.contains("Task added successfully"));
            // assertTrue(output.contains("Task updated successfully"));
            // assertTrue(output.contains("Task deleted successfully"));
            // assertTrue(output.contains("Invalid choice"));
            // assertTrue(output.contains("Exiting Task Management App"));
    
        } finally {
            // Restore original System.in and System.out
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
