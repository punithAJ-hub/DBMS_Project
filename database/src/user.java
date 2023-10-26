public class user 
{
		protected String password;
	 	protected String userName;
	    protected String firstName;
	    protected String lastName;
	    protected String userRole;
	    
	 
	    //constructors
	    public user() {
	    }
	 
	    public user(String userName) 
	    {
	        this.userName = userName;
	    }
	    
	    

		public user( String userName, String firstName, String lastName,String password, String userRole) {
			this(firstName,lastName,password,userRole);
			this.userName = userName;
			
		}
		public user( String firstName, String lastName,String password, String userRole) {
			super();
			this.password = password;
			this.firstName = firstName;
			this.lastName = lastName;
			this.userRole = userRole;
		}
		
		

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
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

		public String getUserRole() {
			return userRole;
		}

		public void setUserRole(String userRole) {
			this.userRole = userRole;
		}
	    
		

	    
	    
	    
	   
	}