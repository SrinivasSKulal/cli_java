package cli_java_task.cli;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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

    public void testAddTask() {
        Task task = new Task("Test Task", "Description", "2025-04-10", "High");
        taskManager.addTask(task);

        List<Task> tasks = taskManager.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
    }

    public void testEditTask() {
        Task task = new Task("Old Task", "Old Description", "2025-04-10", "Low");
        taskManager.addTask(task);

        Task updatedTask = new Task("Updated Task", "Updated Description", "2025-04-15", "High");
        taskManager.editTask(0, updatedTask);

        List<Task> tasks = taskManager.getTasks();
        assertEquals("Updated Task", tasks.get(0).getTitle());
        assertEquals("Updated Description", tasks.get(0).getDescription());
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

        // We do not expect an exception to be thrown, so no assertion here
        // But you can add logging assertions if needed
    }
}
