package Hackathon.MysqlDB;

public class Subject {

	private String name;
	private String numberclass;
	private String description;
	
	private int categoryid;

	public Subject(String name, String numberclass, String description, int categoryid) {
		super();
		this.name = name;
		this.numberclass = numberclass;
		this.description = description;
		this.categoryid = categoryid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumberclass() {
		return numberclass;
	}

	public void setNumberclass(String numberclass) {
		this.numberclass = numberclass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	
}
