package tw.jm.Crawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskList {

	private BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<Task>();
	private Integer taskListLimited;

	@Override
	public String toString() {
		return "TaskList [taskQueue=" + taskQueue + ", taskListLimited=" + taskListLimited + "]";
	}

	public TaskList(Integer taskListLimited) {
		this.taskListLimited = taskListLimited;
	}

	public synchronized void setTask(Task task) throws InterruptedException {
		while (taskQueue.size() >= taskListLimited) {
			wait();
		}
		taskQueue.put(task);
		notifyAll();
	}

	public synchronized Task getTask() throws InterruptedException {
		while (taskQueue.size() <= 0) {
			wait();			
		}

		Task task = taskQueue.take();
		notifyAll();
		return task;
	}

	public Integer getTaskQueueSize() {
		return taskQueue.size();
	}
}
