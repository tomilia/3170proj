/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
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
            System.out.print("Connection created.");
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
    
    public void employee_check_ava(String employee_id)throws SQLException{
        //int Expected_Salary=0, Experience=0;
        //String Name,Skills;
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
       /* String psql_employee = "SELECT * FROM Employee WHERE Employee_ID=?";
        PreparedStatement pstmt1=conn.prepareStatement(psql_employee);
        pstmt1.setString(1,employee_id);
        ResultSet rs = pstmt1.executeQuery();
        if (rs.next()){
            Name = rs.getString("Name");
            Expected_Salary = rs.getInt("Expected_Salary");
            Experience = rs.getInt("Experience");
            Skills = rs.getString("Skills");
        }
        */
        //SELECT Position_ID,Position_Title,Salary FROM _Position JOIN Employer NATURAL JOIN Company WHERE Status =? and Salary >=? and Experience <=?
        
        String psql_position ="SELECT Position_ID,Position_Title,Salary,Company,Size,Founded FROM _Position p NATURAL JOIN Employer er NATURAL JOIN Company c JOIN Employee ee WHERE p.Status =? and p.Salary >=ee.Expected_Salary and p.Experience <=ee.Experience AND ee.Skills LIKE CONCAT('%', p.Position_Title, '%') AND Employee_ID=? ";
        PreparedStatement pstmt2=conn.prepareStatement(psql_position);
        pstmt2.setInt(1,1);
        pstmt2.setString(2,employee_id);
        ResultSet rs2 = pstmt2.executeQuery();
        System.out.println("Your available position are:");
    	System.out.println("Position_ID, Position_Title, Salary, Company, Size, Founded");
        while (rs2.next()){
            String Position_ID = rs2.getString("Position_ID");
            String Position_Title = rs2.getString("Position_Title");
            int Salary= rs2.getInt("Salary");
            String Company = rs2.getString("Company");
            int Size= rs2.getInt("Size");
            int Founded= rs2.getInt("Founded");
            System.out.println(Position_ID+","+Position_Title+","+ Salary+","+Company+","+Size+","+Founded);
            
        }
        stmt.close();
        //select
    }
    public int employee_mark_pos(String employee_id)throws SQLException{
        int returnvalue=0;
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
        String psql_position ="SELECT Position_ID,Position_Title,Salary,Company,Size,Founded FROM _Position p NATURAL JOIN Employer er NATURAL JOIN Company c JOIN Employee ee  "
                + "WHERE p.Status =? and p.Salary >=ee.Expected_Salary and p.Experience <=ee.Experience AND ee.Skills LIKE CONCAT('%', p.Position_Title, '%') AND Employee_ID=? AND NOT c.Company = ANY (SELECT Company FROM Employment_History WHERE Employee_ID=?)"
                + "AND NOT p.Position_ID = ANY(SELECT Position_ID FROM Marked WHERE Employee_ID=?)";
        PreparedStatement pstmt=conn.prepareStatement(psql_position);
        pstmt.setInt(1,1);
        pstmt.setString(2,employee_id);
        pstmt.setString(3,employee_id);
        pstmt.setString(4,employee_id);
        ResultSet rs = pstmt.executeQuery();
        System.out.println("You interested poistions are:");
    	System.out.println("Position_ID, Position_Title, Salary, Company, Size, Founded");
        while (rs.next()){
            String Position_ID = rs.getString("Position_ID");
            String Position_Title = rs.getString("Position_Title");
            int Salary= rs.getInt("Salary");
            String Company = rs.getString("Company");
            int Size= rs.getInt("Size");
            int Founded= rs.getInt("Founded");
            System.out.println(Position_ID+","+Position_Title+","+ Salary+","+Company+","+Size+","+Founded);   
            returnvalue=1;
        }
        
      
        stmt.close();
        //insert
        return returnvalue;
    }
    public void insert_mark_pos(String position_id,String employee_id)throws SQLException{
        
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
        String psql_position ="INSERT INTO Marked(Position_ID,Employee_ID) VALUES (?,?) ";
        PreparedStatement pstmt=conn.prepareStatement(psql_position);
        pstmt.setString(1,position_id);
        pstmt.setString(2,employee_id);
        System.out.println("Done");
        pstmt.executeUpdate();
        stmt.close();
    }
    public int check_valid_employee(String employee_id)throws SQLException{
        int returnvalue=0;
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
        String psql_position ="SELECT * FROM Employee WHERE Employee_ID=?";
        PreparedStatement pstmt=conn.prepareStatement(psql_position);
        pstmt.setString(1,employee_id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
            returnvalue=1;
        
        stmt.close();
        return returnvalue;
        //check the employee id is valid
    }
    public void employee_avg_time(){
        
        //select avg
    }
}
