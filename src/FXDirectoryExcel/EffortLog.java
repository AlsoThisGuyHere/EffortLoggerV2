package FXDirectoryExcel;

public class EffortLog {
	private int number;
	private String date;
	private String start;
	private String stop;
	private double timeElapsed;
	private String lifeCycleStep;
	private String category;
	private String delInt;  // Deliverable / Interruption / etc.
	
	public EffortLog()
	{
		number = -1;
		date = null;
		start = null;
		stop = null;
		timeElapsed = -1;
		lifeCycleStep = null;
		category = null;
		delInt = null;
	}
	
	/**
	 * Effort log entry for EffortLoggerV2.
	 * @param number		Sequential number assigned to each entry.
	 * @param date			Date entry was registered.
	 * @param start			Time task was started.
	 * @param stop			Time task was stopped.
	 * @param timeElapsed	Total time spent on task in minutes.
	 * @param lifeCycleStep	Project life cycle stage this entry belongs to.
	 * @param category		What kind of task this entry represents.
	 * @param delInt		Name of the task/entry.
	 */
	public EffortLog(int number, String date, String start, String stop, double timeElapsed,
						String lifeCycleStep, String category, String delInt)
	{
		this.number = number;
		this.date = date;
		this.start = start;
		this.stop = stop;
		this.timeElapsed = timeElapsed;
		this.lifeCycleStep = lifeCycleStep;
		this.category = category;
		this.delInt = delInt;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getStop() {
		return stop;
	}
	public void setStop(String stop) {
		this.stop = stop;
	}
	public double getTimeElapsed() {
		return timeElapsed;
	}
	public void setTimeElapsed(double timeElapsed) {
		this.timeElapsed = timeElapsed;
	}
	public String getLifeCycleStep() {
		return lifeCycleStep;
	}
	public void setLifeCycleStep(String lifeCycleStep) {
		this.lifeCycleStep = lifeCycleStep;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDelInt() {
		return delInt;
	}
	public void setDelInt(String delInt) {
		this.delInt = delInt;
	}
	
	/**
	 * Returns a String representation of the effort entry with fields separated by tabs.
	 */
	public String toString()
	{
		return number + "\t" + date + "\t" + start  + "\t" + stop + "\t" + timeElapsed +
				"\t" + lifeCycleStep + "\t" + category + "\t" + delInt;
	}
}
