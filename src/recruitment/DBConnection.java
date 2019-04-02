/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recruitment;
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
    String employee = "CREATE TABLE IF NOT EXISTS Employee(Employee_ID VARCHAR(6),Name VARCHAR(30),Expected_Salary Integer,Experience Integer, PRIMARY KEY(Employee_ID));";
            stmt.executeUpdate(employee);
    String employer = "CREATE TABLE IF NOT EXISTS Employer(Employer_ID VARCHAR(6),Name VARCHAR(30),Company VARCHAR(30), PRIMARY KEY(Employer_ID),FOREIGN KEY (Company) REFERENCES Company(Company));";
            stmt.executeUpdate(employer);
    String position = "CREATE TABLE IF NOT EXISTS _Position(Position_ID VARCHAR(6),Position_Title VARCHAR(30),Salary Integer,Experience Integer,Employer_ID VARCHAR(6),Status BOOLEAN,PRIMARY KEY(Position_ID),FOREIGN KEY (Employer_ID) REFERENCES Employer(Employer_ID));";
            stmt.executeUpdate(position);
    String employment_hist = "CREATE TABLE IF NOT EXISTS Employment_History(Position_ID VARCHAR(6),Employee_ID VARCHAR(6),Company VARCHAR(30),Start DATE,End DATE,PRIMARY KEY(Position_ID),FOREIGN KEY (Company) REFERENCES Company(Company));";
            stmt.executeUpdate(employment_hist);
    String marked = "CREATE TABLE IF NOT EXISTS Marked(Position_ID VARCHAR(6),Employee_ID VARCHAR(6),Status BOOLEAN DEFAULT TRUE,PRIMARY KEY(Position_ID,Employee_ID),FOREIGN KEY (Position_ID) REFERENCES _Position(Position_ID), FOREIGN KEY (Employee_ID) REFERENCES Employee(Employee_ID));";
            stmt.executeUpdate(marked);
    stmt.close();
    }
}
