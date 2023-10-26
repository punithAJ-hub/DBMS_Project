import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/userDAO")
public class userDAO 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public userDAO(){}
	
	/** 
	 * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
    	//uses default connection to the database
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false&user=root&password=password");
            System.out.println(connect);
        }
    }
    
    public boolean database_login(String userName, String password) throws SQLException{
    	try {
    		connect_func("root","password");
    		String sql = "select * from user where user_name = ?";
    		preparedStatement = connect.prepareStatement(sql);
    		preparedStatement.setString(1, userName);
    		ResultSet rs = preparedStatement.executeQuery();
    		return rs.next();
    	}
    	catch(SQLException e) {
    		System.out.println("failed login");
    		return false;
    	}
    }
	//connect to the database 
    public void connect_func(String username, String password) throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/userdb?"
  			          + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }
    
    public List<user> listAllUsers() throws SQLException {
        List<user> listUser = new ArrayList<user>();        
        String sql = "SELECT * FROM User";      
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String userName = resultSet.getString("user_name");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String password = resultSet.getString("password");
            String userRole = resultSet.getString("user_role");

             
            user users = new user(userName,firstName, lastName, password,userRole);
            listUser.add(users);
        }        
        resultSet.close();
        disconnect();        
        return listUser;
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    public void insert(user users) throws SQLException {
    	connect_func("root","password");         
		String sql = "insert into User(user_name, first_name, last_name, password, user_role) values (?,?,?,?,?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, users.getUserName());
			preparedStatement.setString(2, users.getFirstName());
			preparedStatement.setString(3, users.getLastName());
			preparedStatement.setString(4, users.getPassword());
			preparedStatement.setString(5, users.getUserRole());

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public boolean delete(String email) throws SQLException {
        String sql = "DELETE FROM User WHERE email = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;     
    }
     
    public boolean update(user users) throws SQLException {
        String sql = "update User set user_name = ?, first_name=?, last_name =?,password = ? , user_role=? where user_name = ?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, users.getUserName());
		preparedStatement.setString(2, users.getFirstName());
		preparedStatement.setString(3, users.getLastName());
		preparedStatement.setString(4, users.getPassword());
		preparedStatement.setString(5, users.getUserRole());
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowUpdated;     
    }
    
    public user getUser(String userName) throws SQLException {
    	user user = null;
        String sql = "SELECT * FROM User WHERE user_name = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, userName);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String password = resultSet.getString("password");
            String userRole = resultSet.getString("user_role");
            user = new user(userName, firstName, lastName, password, userRole);
        }
         
        resultSet.close();
        statement.close();
         
        return user;
    }
    
    public boolean checkUserName(String userName) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM User WHERE user_name = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, userName);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
    	return checks;
    }
    
    public boolean checkPassword(String password) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM User WHERE password = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
       	return checks;
    }
    
    
    
    public boolean isValid(String userName, String password) throws SQLException
    {
    	String sql = "SELECT * FROM User";
    	connect_func();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	
    	resultSet.last();
    	
    	int setSize = resultSet.getRow();
    	resultSet.beforeFirst();
    	
    	for(int i = 0; i < setSize; i++)
    	{
    		resultSet.next();
    		if(resultSet.getString("user_name").equals(userName) && resultSet.getString("password").equals(password)) {
    			return true;
    		}		
    	}
    	return false;
    }
    
    
    public void init() throws SQLException, FileNotFoundException, IOException{
    	connect_func();
        statement =  (Statement) connect.createStatement();
        
        String[] INITIAL = {
					        "use testdb; ",
					        "delete from  User; ",
					        "delete from Bill",
					        "delete from Orders",
					        "delete from Quote",
					        "delete from Tree",
					        "delete from Client"
					        
        					};
        
        					
        String[] TUPLES = {("insert into User(user_name, first_name, last_name, password, user_role)"+
        			"values ('susie@gmail.com', 'Susie ', 'Guzman', 'susie1234','clients'),"+
			    		 	"('don@gmail.com', 'Don', 'Cummings','don123','clients'),"+
			    	 	 	"('margarita@gmail.com', 'Margarita', 'Lawson','margarita1234','clients'),"+
			    		 	"('jo@gmail.com', 'Jo', 'Brady','jo1234','clients'),"+
			    		 	"('wallace@gmail.com', 'Wallace', 'Moore','wallace1234', 'clients'),"+
			    		 	"('amelia@gmail.com', 'Amelia', 'Phillips','amelia1234','clients' ),"+
			    			"('sophie@gmail.com', 'Sophie', 'Pierce','sophie1234','clients' ),"+
			    			"('angelo@gmail.com', 'Angelo', 'Francis','angelo1234','clients' ),"+
			    			"('rudy@gmail.com', 'Rudy', 'Smith','rudy1234','clients' ),"+
			    			"('jeannette@gmail.com', 'Jeannette ', 'Stone','jeannette1234','clients' ),"+
			    			"('root', 'default', 'default','pass1234', 'Admin root');")
			    			};
        
        
        String[] CLIENTS = {
        	    "INSERT INTO Client(client_id, first_name, last_name, address, phone_no, email, creditcard_info) VALUES " +
        	    "(1, 'John', 'Doe', '123 Main St, Detroit, USA', '555-555-5555', 'john@gmail.com', '************1234'), " +
        	    "(2, 'Jane', 'Smith', '456 Elm St, Flint, USA', '555-123-4567', 'jane@gmail.com', '************5678'), " +
        	    "(3, 'Bob', 'Johnson', '789 Oak St, Chicago, USA', '555-987-6543', 'bob@gmail.com', '************9876'), " +
        	    "(4, 'Alice', 'Brown', '246 Maple St, Novi, USA', '555-333-2222', 'alice@gmail.com', '************2222'), " +
        	    "(5, 'Charlie', 'Wilson', '789 Pine St, Troy, USA', '555-111-4444', 'charlie@gmail.com', '************4444'), " +
        	    "(6, 'Emily', 'Jones', '654 Cedar St, Detroit, USA', '555-444-7777', 'emily@gmail.com', '************7777'), " +
        	    "(7, 'Daniel', 'Taylor', '890 Birch St, Flint, USA', '555-888-3333', 'daniel@gmail.com', '************3333'), " +
        	    "(8, 'Ella', 'White', '753 Oak St, Chicago, USA', '555-222-6666', 'ella@gmail.com', '************6666'), " +
        	    "(9, 'Frank', 'Lee', '125 Pine St, Novi, USA', '555-999-2222', 'frank@gmail.com', '************2222'), " +
        	    "(10, 'Grace', 'Smith', '367 Maple St, Troy, USA', '555-444-1111', 'grace@gmail.com', '************1111');"
        	};

        	String[] TREES = {
        	    "INSERT INTO Tree(tree_id, size, height, location, isNearHouse, note) VALUES " +
        	    "(1, 'Large', 15.5, 'Backyard', true, 'Tall oak tree'), " +
        	    "(2, 'Medium', 8.2, 'Front yard', false, 'Small apple tree'), " +
        	    "(3, 'Small', 5.0, 'Side yard', true, 'Dwarf pine tree'), " +
        	    "(4, 'Medium', 10.0, 'Front yard', false, 'Maple tree'), " +
        	    "(5, 'Large', 20.0, 'Backyard', true, 'Pine tree'), " +
        	    "(6, 'Small', 6.5, 'Backyard', false, 'Birch tree'), " +
        	    "(7, 'Medium', 9.0, 'Front yard', true, 'Cherry tree'), " +
        	    "(8, 'Large', 18.5, 'Backyard', false, 'Cypress tree'), " +
        	    "(9, 'Medium', 12.0, 'Front yard', true, 'Palm tree'), " +
        	    "(10, 'Small', 7.0, 'Side yard', false, 'Willow tree');"
        	};

        	String[] QUOTES = {
        	    "INSERT INTO Quote(quote_id, client_id, request_date, note, treeId, status) VALUES " +
        	    "(1, 1, '2023-10-24', 'Tree trimming request', 1, 'Pending'), " +
        	    "(2, 2, '2023-10-25', 'Tree removal request', 2, 'Approved'), " +
        	    "(3, 3, '2023-10-26', 'Tree pruning request', 3, 'Pending'), " +
        	    "(4, 4, '2023-10-27', 'Tree removal request', 4, 'Approved'), " +
        	    "(5, 5, '2023-10-28', 'Tree trimming request', 5, 'Pending'), " +
        	    "(6, 6, '2023-10-29', 'Tree pruning request', 6, 'Approved'), " +
        	    "(7, 7, '2023-10-30', 'Tree removal request', 7, 'Pending'), " +
        	    "(8, 8, '2023-10-31', 'Tree trimming request', 8, 'Approved'), " +
        	    "(9, 9, '2023-11-01', 'Tree pruning request', 9, 'Pending'), " +
        	    "(10, 10, '2023-11-02', 'Tree removal request', 10, 'Approved');"
        	};

        	String[] ORDERS = {
        	    "INSERT INTO Orders(order_id, quote_id, start_date, end_date) VALUES " +
        	    "(1, 1, '2023-10-26', '2023-10-28'), " +
        	    "(2, 2, '2023-10-27', '2023-10-30'), " +
        	    "(3, 3, '2023-10-28', '2023-10-30'), " +
        	    "(4, 4, '2023-10-29', '2023-11-01'), " +
        	    "(5, 5, '2023-10-30', '2023-11-02'), " +
        	    "(6, 6, '2023-10-31', '2023-11-03'), " +
        	    "(7, 7, '2023-11-01', '2023-11-03'), " +
        	    "(8, 8, '2023-11-02', '2023-11-04'), " +
        	    "(9, 9, '2023-11-03', '2023-11-05'), " +
        	    "(10, 10, '2023-11-04', '2023-11-06');"
        	};

        	String[] BILLS = {
        	    "INSERT INTO Bill(bill_id, order_id, total_amount, status, note) VALUES " +
        	    "(1, 1, 500.00, 'Paid', 'Tree trimming service'), " +
        	    "(2, 2, 750.00, 'Pending', 'Tree removal service'), " +
        	    "(3, 3, 450.00, 'Paid', 'Tree pruning service'), " +
        	    "(4, 4, 800.00, 'Pending', 'Tree removal service'), " +
        	    "(5, 5, 600.00, 'Paid', 'Tree trimming service'), " +
        	    "(6, 6, 350.00, 'Pending', 'Tree pruning service'), " +
        	    "(7, 7, 900.00, 'Paid', 'Tree removal service'), " +
        	    "(8, 8, 550.00, 'Pending', 'Tree trimming service'), " +
        	    "(9, 9, 400.00, 'Paid', 'Tree pruning service'), " +
        	    "(10, 10, 700.00, 'Pending', 'Tree removal service');"
        	};

        
        
        
        
        
       for(int i=0;i<INITIAL.length;i++) {
    	   statement.execute(INITIAL[i]);
       }
       for(int i=0;i<CLIENTS.length;i++) {
    	   statement.execute(CLIENTS[i]);
       }
       for(int i=0;i<TUPLES.length;i++) {
    	   statement.execute(TUPLES[i]);
       }
      
       for(int i=0;i<TREES.length;i++) {
    	   statement.execute(TREES[i]);
       }
       for(int i=0;i<QUOTES.length;i++) {
    	   statement.execute(QUOTES[i]);
       }
       for(int i=0;i<ORDERS.length;i++) {
    	   statement.execute(ORDERS[i]);
       }
       for(int i=0;i<BILLS.length;i++) {
    	   statement.execute(BILLS[i]);
       }
       
        disconnect();
    }
    
    
   
    
    
    
    
    
	
	

}
