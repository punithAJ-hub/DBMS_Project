
public class Client {
	
	protected String clientId;
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String email;
    protected long phoneNo;
    
    
    public Client(String clientId) {
    	this.clientId=clientId;
    }
    
    
    public Client(String clientId,String firstName, String lastName, String address, String email, long phoneNo) {
    	this(firstName,lastName,address,email,phoneNo);
		this.clientId = clientId;
		
		
    }
    
    
	public Client( String firstName, String lastName, String address, String email, long phoneNo) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.phoneNo = phoneNo;
		
	}
	
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}
    
}
