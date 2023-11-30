package application;

public class PlanningPoker {
	
	private String taskDescription;
	
	private String estimates;
	
	private String reason;
	
	@Override
	public String toString() {
		return "Task: " + taskDescription + ", estimates: " + estimates + ", reason: " + reason;
	}

	public PlanningPoker(String taskDescription, String estimates, String reason) {
		this.taskDescription = taskDescription;
		this.estimates = estimates;
		this.reason = reason;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getEstimates() {
		return estimates;
	}

	public void setEstimates(String estimates) {
		this.estimates = estimates;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
