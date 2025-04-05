package cli_java_task.cli;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    private TaskManager taskManager;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(AppTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        taskManager = new TaskManager();
    }

    /**
     * Test adding a task
     */
    public void testAddTask()
    {
        Task task = new Task("Test Task", "Description", "2025-04-10", "High");
        taskManager.addTask(task);

        List<Task> tasks = taskManager.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
    }

    /**
     * Test editing a task
     */
    public void testEditTask()
    {
        Task task = new Task("Old Task", "Old Description", "2025-04-10", "Low");
        taskManager.addTask(task);

        Task updatedTask = new Task("Updated Task", "Updated Description", "2025-04-15", "High");
        taskManager.editTask(0, updatedTask);

        List<Task> tasks = taskManager.getTasks();
        assertEquals("Updated Task", tasks.get(0).getTitle());
        assertEquals("Updated Description", tasks.get(0).getDescription());
    }

    /**
     * Test deleting a task
     */
    public void testDeleteTask()
    {
        Task task = new Task("Task to Delete", "Description", "2025-04-10", "Medium");
        taskManager.addTask(task);

        taskManager.deleteTask(0);

        List<Task> tasks = taskManager.getTasks();
        assertTrue(tasks.isEmpty());
    }

    /**
     * Test marking a task as complete
     */
    public void testMarkTaskAsComplete()
    {
        Task task = new Task("Incomplete Task", "Description", "2025-04-10", "Low");
        taskManager.addTask(task);

        taskManager.markTaskAsComplete(0);

        List<Task> tasks = taskManager.getTasks();
        assertTrue(tasks.get(0).isCompleted());
    }

    /**
     * Test invalid task index handling
     */
    public void testInvalidTaskIndex()
    {
        Task task = new Task("Valid Task", "Description", "2025-04-10", "High");
        taskManager.addTask(task);

        try {
            taskManager.editTask(5, task);
            taskManager.deleteTask(5);
            taskManager.markTaskAsComplete(5);
        } catch (Exception e) {
            fail("Exception should not be thrown for invalid task index.");
        }
    }
}