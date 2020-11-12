package cs3560_twitter;

public abstract class ProfileInfo {
	
	private int id;
	private String name;
	private int total;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getName() {
		return name;
	}

	public void setName(String text) {
		this.name = text;
	}

}
