package FXDirectoryExcel;

//  @author Frans Emil Malapo
//  Class representing Defect Logs as a Java Object
//  Implements getters and setters to read and modify the object
public class DefectLog {

	//  Each variable corresponds to its Defect Log category
	private int number;
	private String name;
	private String detail;
	private String injected;
	private String removed;
	private String category;
	private String status;
	private int fix;
	
	//  Creates a Defect Log with default values
	//  Strings are set to null, and integers are set to -1
	public DefectLog()
	{
		setNumber(-1);
		setName(null);
		setDetail(null);
		setInjected(null);
		setRemoved(null);
		setCategory(null);
		setStatus(null);
		setFix(-1);
	}
	
	// Defect log entry for EffortLoggerV2.
	// @param number	Sequential number assigned to each defect.
	// @param name		Name of defect.
	// @param detail	Details about the defect.
	// @param injected	Stage when the defect was injected.
	// @param removed	Stage when the defect was removed
	// @param category	What kind of defect the current defect is.
	// @param status	Whether defect is open or closed.
	// @param fix		Fix index.
	public DefectLog(int number, String name, String detail, String injected, String removed,
						String category, String status, int fix)
	{
		this.setNumber(number);
		this.setName(name);
		this.setDetail(detail);
		this.setInjected(injected);
		this.setRemoved(removed);
		this.setCategory(category);
		this.setStatus(status);
		this.setFix(fix);
	}

	//  Returns a String representation of the Defect Log
	//  @return String representation of Defect Log
	public String toString()
	{
		return number + "\t" + name + "\t" + detail  + "\t" + injected + "\t" + removed +
				"\t" + category + "\t" + status + "\t" + fix;
	}
	
	// ========Getters========

	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public String getInjected() {
		return injected;
	}
	
	public String getRemoved() {
		return removed;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getStatus() {
		return status;
	}
	
	public int getFix() {
		return fix;
	}

	// ========Setters========
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setInjected(String injected) {
		this.injected = injected;
	}

	public void setRemoved(String removed) {
		this.removed = removed;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setFix(int fix) {
		this.fix = fix;
	}
}
