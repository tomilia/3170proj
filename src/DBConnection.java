/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.Random; 
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public int admin_load_data(String path) {
        try {
              System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
            conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword);
            stmt = conn.createStatement();
            PreparedStatement company=conn.prepareStatement("LOAD DATA LOCAL INFILE ? INTO TABLE Company\n" +
                    "FIELDS TERMINATED BY ',' ENCLOSED BY '\"'\n" +
                    "LINES TERMINATED BY '\\n'\n" +
                    ";");
            company.setString(1,path+"/company.csv");
            company.executeQuery();
            
            PreparedStatement employee=conn.prepareStatement("LOAD DATA LOCAL INFILE ? INTO TABLE Employee\n" +
                    "FIELDS TERMINATED BY ',' ENCLOSED BY '\"'\n" +
                    "LINES TERMINATED BY '\\n'\n" +
                    ";");
            employee.setString(1,path+"/employee.csv");
            employee.executeQuery();
            PreparedStatement employer=conn.prepareStatement("LOAD DATA LOCAL INFILE ? INTO TABLE Employer\n" +
                    "FIELDS TERMINATED BY ',' ENCLOSED BY '\"'\n" +
                    "LINES TERMINATED BY '\\n'\n" +
                    ";");
            
            employer.setString(1,path+"/employer.csv");
            employer.executeQuery();
            PreparedStatement position=conn.prepareStatement("LOAD DATA LOCAL INFILE ? INTO TABLE _Position\n" +
                    "FIELDS TERMINATED BY ',' ENCLOSED BY '\"'\n" +
                    "LINES TERMINATED BY '\\n'\n" +
                    "(Position_ID,Position_Title,Salary,Experience,Employer_ID,@Status)\n" +
                    "SET Status = (@Status = 'TRUE');");
            
            position.setString(1,path+"/position.csv");
            position.executeQuery();
            PreparedStatement emp_hist=conn.prepareStatement("LOAD DATA LOCAL INFILE ? INTO TABLE Employment_History\n" +
                    "FIELDS TERMINATED BY ',' ENCLOSED BY '\"'\n" +
                    "LINES TERMINATED BY '\\n'\n" +
                    ";");
            emp_hist.setString(1,path+"/history.csv");
            emp_hist.executeQuery();
            stmt.close();
            return 1;
        } catch (SQLException ex) {
            
            return -1;
        }
    }
    public void admin_check_table(){
        try {
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
            System.out.println("Number of records in each table:");
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
        } catch (SQLException ex) {
            System.out.println("Table not exist");
        }
    }
    
    public void employee_check_ava(String employee_id)throws SQLException{

        //int Expected_Salary=0, Experience=0;
        //String Name,Skills;
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
       /* String psql_employee = "SELECT * FROM Employee WHERE Employee_ID=?";
>>>>>>> a53ab958e732d6ba7e23d83d0c3fcdf26a74be73
        PreparedStatement pstmt1=conn.prepareStatement(psql_employee);
        pstmt1.setString(1,employee_id);
        ResultSet rs = pstmt1.executeQuery();
        if (rs.next()){
            Name = rs.getString("Name");
            Expected_Salary = rs.getInt("Expected_Salary");
            Experience = rs.getInt("Experience");
            Skills = rs.getString("Skills");
<<<<<<< HEAD
            System.out.println("You are employee:"+Name+Expected_Salary+Experience+Skills+"\n");
            
        }
        
        //SELECT Position_ID,Position_Title,Salary FROM _Position NATURAL JOIN Employer NATURAL JOIN Company WHERE Status =? and Salary >=? and Experience <=?
        String psql_position ="SELECT Position_ID,Position_Title,Salary,Company,Size,Founded FROM _Position NATURAL JOIN Employer NATURAL JOIN Company WHERE Status =? and Salary >=? and Experience <=?";
        PreparedStatement pstmt2=conn.prepareStatement(psql_position);
        pstmt2.setInt(1,1);
        pstmt2.setInt(2,Expected_Salary);
        pstmt2.setInt(3,Experience);
        ResultSet rs2 = pstmt2.executeQuery();
=======
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

    public void employer_post_rec(String employer_id,String position_title,int upper_salary,int required_experience) throws SQLException{
        
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
        //required_experience default -> 0                           If employee haven't worked , it will not count , but we should count
        String countEmployee = "SELECT COUNT(*) FROM Employee E WHERE E.Employee_ID NOT IN (SELECT Employee_ID FROM Employment_History WHERE End IS NULL) AND E.Expected_Salary <= ? AND E.Experience >= ? AND E.Skills LIKE CONCAT ('%' , ? , '%')";
        
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
     
        	 String insertPost = "INSERT INTO _Position (Employer_ID, Position_Title, Salary, Experience, Status, Position_ID) VALUES (?,?,?,?,1,?)";
             PreparedStatement pstmt2 = conn.prepareStatement(insertPost);
             pstmt2.setString(1, employer_id);
             pstmt2.setString(2, position_title);
             pstmt2.setInt(3,upper_salary);
             pstmt2.setInt(4, required_experience);
             pstmt2.setString(5, String.valueOf(rand.nextInt(999999))); 
             pstmt2.executeUpdate();
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
     public int check_valid_position(String employee_id,String position_id)throws SQLException{
        int returnvalue=0;
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
        String psql_position ="SELECT * FROM _Position p NATURAL JOIN Employer er NATURAL JOIN Company c JOIN Employee ee  "
                + "WHERE p.Status =? and p.Salary >=ee.Expected_Salary and p.Experience <=ee.Experience AND ee.Skills LIKE CONCAT('%', p.Position_Title, '%') AND Employee_ID=? AND NOT c.Company = ANY (SELECT Company FROM Employment_History WHERE Employee_ID=?)"
                + "AND NOT p.Position_ID = ANY(SELECT Position_ID FROM Marked WHERE Employee_ID=?) AND p.Position_ID=?";
        PreparedStatement pstmt=conn.prepareStatement(psql_position);
        pstmt.setInt(1,1);
        pstmt.setString(2,employee_id);
        pstmt.setString(3,employee_id);
        pstmt.setString(4,employee_id);
        pstmt.setString(5,position_id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
            returnvalue=1;
        
        stmt.close();
        return returnvalue;
        //check the employee id is valid
    }


    public void employee_avg_time(String employee_id){
        try { 
            conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword);
            PreparedStatement totaldays = conn.prepareStatement("select DATEDIFF(End, Start) as totaldays from Employment_History where Employee_ID=? and End IS NOT NULL order by Convert(End,datetime) desc limit 3;");
            totaldays.setString(1, employee_id);
            ResultSet rs = totaldays.executeQuery();
            int days=0;
            int counter=0;
           
            while(rs.next())
            {
                counter++;
                String vac=rs.getString("totaldays");
                days+=Integer.parseInt(vac);
            }
            if(counter<3)
            {
                System.out.println("Not enough record(s) found");
                return;
            }
            System.out.println("Your average working time is: "+days/counter+" days.");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //wayne
    public void accept_employee(String employer_id,String employee_id)throws SQLException{
        String company ="";
        String position_id ="";
        Date date=new java.sql.Date(System.currentTimeMillis());
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        stmt = conn.createStatement();
        String psql ="SELECT * FROM Employer WHERE Employer_ID=?";
        PreparedStatement pstmt=conn.prepareStatement(psql);
        pstmt.setString(1,employer_id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
        	company = rs.getString("Company");
        }
        String psql2 ="SELECT m.Position_ID FROM Marked m NATURAL JOIN _Position p WHERE m.Status=? AND p.Employer_ID=? AND m.Employee_ID=?";
        PreparedStatement pstmt2=conn.prepareStatement(psql2);
        pstmt2.setInt(1,1);
        pstmt2.setString(2,employer_id);
        pstmt2.setString(3,employee_id);
        ResultSet rs2 = pstmt2.executeQuery();
        
        if (rs2.next()) {
        	position_id = rs2.getString(1);
        }
        
        
        String createRecord = "INSERT INTO Employment_History VALUES (?,?,?,?,NULL)";
        PreparedStatement pstmt3 = conn.prepareStatement(createRecord);
        pstmt3.setString(1,employee_id);
        pstmt3.setString(2, company);
        pstmt3.setString(3, position_id);
        pstmt3.setDate(4, date);
        pstmt3.executeUpdate();
         
    	System.out.println("An Employment History record is created, details are:");
    	System.out.println("Employee_ID, Company, Position_ID, Start, End");
        System.out.println(employee_id+","+company+","+position_id+","+date+",NULL");
        String updateRecord = "UPDATE _Position SET Status = 0 WHERE Position_ID =? ";
        PreparedStatement pstmt4 = conn.prepareStatement(updateRecord);
        pstmt4.setString(1,position_id);
        pstmt4.executeUpdate();
        
        stmt.close();
    }
    public int check_valid_accept(String employer_id,String employee_id)throws SQLException{
        conn = DriverManager.getConnection(dbURL,dbUsername,dbPassword); 
        //stmt = conn.createStatement();
        String psql2 ="SELECT m.Position_ID FROM Marked m NATURAL JOIN _Position p WHERE m.Status=? AND p.Employer_ID=? AND m.Employee_ID=?";
        PreparedStatement pstmt2=conn.prepareStatement(psql2);
        pstmt2.setInt(1,1);
        pstmt2.setString(2,employer_id);
        pstmt2.setString(3,employee_id);
        ResultSet rs2 = pstmt2.executeQuery();
        
        if (rs2.next()) {
            pstmt2.close();
            return 1;
        }
        pstmt2.close();
        return 0;
    }
}
