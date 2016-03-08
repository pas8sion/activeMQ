package activeMQ;

import java.io.Serializable;
import java.util.Date;

public class MyMessage implements Serializable {

	private static final long serialVersionUID = -8490634165141705464L;

	private Long id;
	private int number;
	private String name;
	private Date createDate;

	public MyMessage() {
	}

	public MyMessage(Long id, int number, String name, Date createDate) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "MyMessage [id=" + id + ", number=" + number + ", name=" + name + ", createDate=" + createDate + ", this=" + super.toString() + "]";
	}
}
