package FXDirectoryExcel;

//  @author Frans Emil Malapo
//  Class representing Effort Logs as a Java Object
//  Implements getters and setters to read and modify the object
public class EffortLog {

	//  Each variable corresponds to its Effort Log category
	private int number;
	private String date;
	private String start;
	private String stop;
	private double timeElapsed;
	private String lifeCycleStep;
	private String category;
	private String delInt;  // Deliverable / Interruption / etc.

	//  Creates a Effort Log with default values
	//  Strings are set to null, and integers are set to -1
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
	
	//  Effort log entry for EffortLoggerV2.
	//  @param number		Sequential number assigned to each entry.
	//  @param date			Date entry was registered.
	//  @param start		Time task was started.
	//  @param stop			Time task was stopped.
	//  @param timeElapsed	Total time spent on task in minutes.
	//  @param lifeCycleStep	Project life cycle stage this entry belongs to.
	//  @param category		What kind of task this entry represents.
	//  @param delInt		Name of the task/entry.
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
	
	//  Returns a String representation of the Defect Log
	//  @return String representation of Defect Log
	public String toString()
	{
		return number + "\t" + date + "\t" + start  + "\t" + stop + "\t" + timeElapsed +
				"\t" + lifeCycleStep + "\t" + category + "\t" + delInt;
	}
	
	// ========Getters========
	
	public int getNumber() {
		return number;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getStart() {
		return start;
	}
	
	public String getStop() {
		return stop;
	}
	
	public double getTimeElapsed() {
		return timeElapsed;
	}
	
	public String getLifeCycleStep() {
		return lifeCycleStep;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getDelInt() {
		return delInt;
	}
	
	// ========Setters========
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setStart(String start) {
		this.start = start;
	}
	
	public void setStop(String stop) {
		this.stop = stop;
	}
	
	public void setTimeElapsed(double timeElapsed) {
		this.timeElapsed = timeElapsed;
	}
	
	public void setLifeCycleStep(String lifeCycleStep) {
		this.lifeCycleStep = lifeCycleStep;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

	public void setDelInt(String delInt) {
		this.delInt = delInt;
	}
}
