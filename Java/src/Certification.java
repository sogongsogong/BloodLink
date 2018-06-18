
public class Certification {
	//ÇåÇæÁõ°´Ã¼
	private String name,email,date,state,certnum;//ÇåÇ÷ÁõÁ¤º¸
	
	public Certification()
	{
		
	}
	public Certification(String certnum,String name,String email,String state) 
	{
		this.certnum=certnum;
		this.name=name;
		this.email=email;
		this.state=state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCertnum() {
		return certnum;
	}
	public void setCertnum(String certnum) {
		this.certnum = certnum;
	}

	

}