package FXDirectoryExcel;

public class DefectLog {
	private int number;
	private String name;
	private String detail;
	private String injected;
	private String removed;
	private String category;
	private String status;
	private int fix;
	
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getInjected() {
		return injected;
	}

	public void setInjected(String injected) {
		this.injected = injected;
	}

	public String getRemoved() {
		return removed;
	}

	public void setRemoved(String removed) {
		this.removed = removed;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getFix() {
		return fix;
	}

	public void setFix(int fix) {
		this.fix = fix;
	}
	
	public String toString()
	{
		return number + "\t" + name + "\t" + detail  + "\t" + injected + "\t" + removed +
				"\t" + category + "\t" + status + "\t" + fix;
	}
}
