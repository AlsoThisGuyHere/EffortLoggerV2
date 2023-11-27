package effortloggerv2;

public class Task {

	private String task;
	
	private String estimates;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getEstimates() {
		return estimates;
	}

	public void setEstimates(String estimates) {
		this.estimates = estimates;
	}
	
	public Task(String task, String estimates) {
		this.setTask(task);
		this.setEstimates(estimates);
	}
	
	public String toString() {
		return "Task title: " + this.getTask() + " Estimates: " + this.getEstimates();
	}
}
