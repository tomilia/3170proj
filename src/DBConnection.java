/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.Random; //暫用
/**
 *
 * @author tommylee
 */
public class DBConnection {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static String dbURL = "jdbc:mysql://137.189.90.245:2633/db16";
    private static String dbUsername = "Group16";
    private static String dbPassword = "CSCI3170";
    public DBConnection() throws SQLException{
        
     try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            //Get a connection
            conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
            System.out.println("Connection created.");
            conn.close();
        }   
        catch (Exception except)
        {
         except.printStackTrace();
      
        }
       
    }
    public void admin_create() throws SQLException{
     
    String company = "CREATE TABLE IF NOT EXISTS Company(Company VARCHAR(30),Size INT(11),Founded INT(4), PRIMARY KEY(Company));";
    conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
            stmt = conn.createStatement();
            stmt.executeUpdate(company);
    String employee = "CREATE TABLE IF NOT EXISTS Employee(Employee_ID VARCHAR(6),Name VARCHAR(30),Expected_Salary Integer,Experience Integer,Skills VARCHAR(50), PRIMARY KEY(Employee_ID));";
            stmt.executeUpdate(employee);
    String employer = "CREATE TABLE IF NOT EXISTS Employer(Employer_ID VARCHAR(6),Name VARCHAR(30),Company VARCHAR(30), PRIMARY KEY(Employer_ID),FOREIGN KEY (Company) REFERENCES Company(Company) ON DELETE CASCADE ON UPDATE CASCADE);";
            stmt.executeUpdate(employer);
    String position = "CREATE TABLE IF NOT EXISTS _Position(Position_ID VARCHAR(6),Position_Title VARCHAR(30),Salary Integer,Experience Integer,Employer_ID VARCHAR(6),Status BOOL DEFAULT TRUE,PRIMARY KEY(Position_ID),FOREIGN KEY (Employer_ID) REFERENCES Employer(Employer_ID) ON DELETE CASCADE ON UPDATE CASCADE) ;";
            stmt.executeUpdate(position);
    String employment_hist = "CREATE TABLE IF NOT EXISTS Employment_History(Employee_ID VARCHAR(6),Company VARCHAR(30),Position_ID VARCHAR(6),Start DATE,End DATE,PRIMARY KEY(Position_ID),FOREIGN KEY (Company) REFERENCES Company(Company) ON DELETE CASCADE ON UPDATE CASCADE);";
            stmt.executeUpdate(employment_hist);
    String marked = "CREATE TABLE IF NOT EXISTS Marked(Position_ID VARCHAR(6),Employee_ID VARCHAR(6),Status BOOL DEFAULT TRUE,PRIMARY KEY(Position_ID,Employee_ID),FOREIGN KEY (Position_ID) REFERENCES _Position(Position_ID) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (Employee_ID) REFERENCES Employee(Employee_ID) ON DELETE CASCADE ON UPDATE CASCADE);";
            stmt.executeUpdate(marked);
    stmt.close();
    }
    public void admin_remove() throws SQLException{
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        
            stmt = conn.createStatement();
    String employment_hist = "DROP Table IF EXISTS Employment_History";
            stmt.executeUpdate(employment_hist);
    String marked = "DROP Table IF EXISTS Marked";
            stmt.executeUpdate(marked);
    String position = "DROP Table IF EXISTS _Position;";
            stmt.executeUpdate(position);
    String employee = "DROP Table IF EXISTS Employee";
            stmt.executeUpdate(employee);

    String employer = "DROP Table IF EXISTS Employer;";
            stmt.executeUpdate(employer);
    String company = "DROP Table IF EXISTS Company";
            stmt.executeUpdate(company);



    
    stmt.close();
    }
    public void admin_load_data() throws SQLException{
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
     String company="LOAD DATA LOCAL INFILE 'test_data/company.csv' INTO TABLE Company\n" +
"FIELDS TERMINATED BY ',' ENCLOSED BY '\"'\n" +
"LINES TERMINATED BY '\\n'\n" +
";";
     
     stmt.executeUpdate(company);
     String employee="LOAD DATA LOCAL INFILE 'test_data/employee.csv' INTO TABLE Employee\n" +
"FIELDS TERMINATED BY ',' ENCLOSED BY '\"'\n" +
"LINES TERMINATED BY '\\n'\n" +
";";
     
     stmt.executeUpdate(employee);
      String employer="LOAD DATA LOCAL INFILE 'test_data/employer.csv' INTO TABLE Employer\n" +
"FIELDS TERMINATED BY ',' ENCLOSED BY '\"'\n" +
"LINES TERMINATED BY '\\n'\n" +
";";
     
     stmt.executeUpdate(employer);
     String position="LOAD DATA LOCAL INFILE 'test_data/position.csv' INTO TABLE _Position\n" +
"FIELDS TERMINATED BY ',' ENCLOSED BY '\"'\n" +
"LINES TERMINATED BY '\\n'\n" +
"(Position_ID,Position_Title,Salary,Experience,Employer_ID,@Status)\n" +
"SET Status = (@Status = 'TRUE');";
     
     stmt.executeUpdate(position);
      String emp_hist="LOAD DATA LOCAL INFILE 'test_data/history.csv' INTO TABLE Employment_History\n" +
"FIELDS TERMINATED BY ',' ENCLOSED BY '\"'\n" +
"LINES TERMINATED BY '\\n'\n" +
";";
     
     stmt.executeUpdate(emp_hist);
    stmt.close();
    }
    public void admin_check_table() throws SQLException{
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
                    PreparedStatement company = conn.prepareStatement("SELECT COUNT(*) FROM Company");
                    PreparedStatement employee = conn.prepareStatement("SELECT COUNT(*) FROM Employee");
                    PreparedStatement employer = conn.prepareStatement("SELECT COUNT(*) FROM Employer");
                    PreparedStatement position = conn.prepareStatement("SELECT COUNT(*) FROM _Position");
                    PreparedStatement emp_hist = conn.prepareStatement("SELECT COUNT(*) FROM Employment_History");
                    PreparedStatement marked = conn.prepareStatement("SELECT COUNT(*) FROM Marked");
           ResultSet com= company.executeQuery();
           ResultSet empe= employee.executeQuery();
           ResultSet empr= employer.executeQuery();
           ResultSet pos= position.executeQuery();
           ResultSet hist= emp_hist.executeQuery();
           ResultSet mark= marked.executeQuery();
           
           if(com.next()){
           System.out.println("Company:"+com.getInt(1));
           }
           if(empe.next()){
           System.out.println("Employee:"+empe.getInt(1));
           }
           if(empr.next()){
           System.out.println("Employer:"+empr.getInt(1));
           }
           if(pos.next()){
           System.out.println("Position:"+pos.getInt(1));
           }
           if(hist.next()){
           System.out.println("Employment_History:"+hist.getInt(1));
           }
           if(mark.next()){
           System.out.println("Marked:"+mark.getInt(1));
           }
    }
    public void employee_check_ava(){
        //select
    }
    public void employee_mark_pos(){
        //insert
    }
    public void employee_avg_time(){
        //select avg
    }
    
    
    public void employer_post_rec(String employer_id,String position_title,int upper_salary,int required_experience) throws SQLException{
        
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
        //required_experience default -> 0                           If employee haven't worked , it will not count , but we should count
        String countEmployee = "SELECT COUNT(DISTINCT E.Employee_ID) FROM Employee E , Employment_History H WHERE E.Employee_ID = H.Employee_ID AND H.End IS NOT NULL AND E.Expected_Salary <= ? AND E.Experience >= ? AND E.Skills LIKE CONCAT('%' , ? , '%')"; 
        PreparedStatement pstmt = conn.prepareStatement(countEmployee);
        
        Random rand = new Random();
        pstmt.setInt(1, upper_salary);
        pstmt.setInt(2, required_experience);
        pstmt.setString(3,  position_title);
        ResultSet number = pstmt.executeQuery();
        
        if(!(number.next()) || number.getInt(1) == 0) {
        	System.out.println("ERROR MESSAGE");
        }
        else {
        	 
        	//無ERROR 但未insert到!!!!!!!!!!!!!!!!!!!!
        	String insertPost = "INSERT INTO _Position (Employer_ID, Position_Title, Salary, Experience, Status, Position_ID) VALUES (?,?,?,?,1,?)";
             PreparedStatement pstmt2 = conn.prepareStatement(insertPost);
             pstmt2.setString(1, employer_id);
             pstmt2.setString(2, position_title);
             pstmt2.setInt(3,upper_salary);
             pstmt2.setInt(4, required_experience);
             pstmt2.setInt(5, rand.nextInt(999999)); //暫用去 random 個position_id
             
        	System.out.println(number.getInt(1)+" potential employees are found. The position recruiment is posted.");
    	}
    	
    		
    	}
    	
    
    public void employer_checkPosition(String employer_id) throws SQLException{ //DONE!!
    	conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
        String checkPosition ="SELECT P.Position_ID FROM _Position P WHERE P.Employer_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(checkPosition);
        pstmt.setString(1, employer_id);
        ResultSet checkedPosition = pstmt.executeQuery();
        System.out.println("");
        while(checkedPosition.next()) {
        	System.out.println(checkedPosition.getString(1));
        }
    }
    
    public void employer_show_employee_interested(String picked_position_id) throws SQLException { //DONE!!!!!!!!!!
    	conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
        String showEmployee_interested = "SELECT E.* FROM Marked M, Employee E WHERE M.Position_ID = ? AND M.Employee_ID = E.Employee_ID";
        PreparedStatement pstmt = conn.prepareStatement(showEmployee_interested);
        pstmt.setString(1,picked_position_id);
        ResultSet interested_Employee = pstmt.executeQuery();
        while(interested_Employee.next()) {
        	System.out.print(interested_Employee.getString(1)+", ");
        	System.out.print(interested_Employee.getString(2)+", ");
        	System.out.print(interested_Employee.getInt(3)+", ");
        	System.out.print(interested_Employee.getInt(4)+", ");
        	System.out.println(interested_Employee.getString(5));
        }
        
    }
    
    public void employer_createRecord(String employer_id,String hire_employee_id) throws SQLException {
    	conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
        String theCompany ="";
        String searchCompany = "SELECT Company FROM Employer E WHERE E.Employer_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(searchCompany);
        pstmt.setString(1, employer_id);
        ResultSet resultCompany = pstmt.executeQuery();
        if (resultCompany.next()) {
        	theCompany = resultCompany.getString(1);
        }

        
        String createRecord = "INSERT INTO Employment_History VALUES (?,?,?,?,NULL)";
        PreparedStatement pstmt2 = conn.prepareStatement(createRecord);
        pstmt2.setString(1,hire_employee_id);
        pstmt2.setString(2, theCompany);
        pstmt2.setDate(4, new java.sql.Date(System.currentTimeMillis()));
        
    }
   
}
