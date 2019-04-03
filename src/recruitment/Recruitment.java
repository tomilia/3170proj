/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recruitment;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author tommylee
 */

public class Recruitment {
     //position of the user
    static Scanner reader = new Scanner(System.in); 
    
    //Step One Question
    void welcome_menu() { // DONE
    	System.out.println("Welcome! Who are you?");
    	System.out.println("1. An administrator\n2. An employee\n3. An employer\n4. Exit\nPlease enter [1-4].");
    
    
    	while (true) {
    		int position_choice = reader.nextInt();
    		
    		
    		switch(position_choice)
    		{
        		case 1:admin_menu(); break;
        		case 2:employee_menu();break;
        		case 3:employer_menu();break;
        		case 4:return;
        		default:System.out.println("[ERROR] Invalid input.\nPlease enter [1-4].");break;
    		}
    	}
    		
    		
    	}
    
    //Step Two Question
    void admin_menu(){
        System.out.println("Administrator, what would you like to do");
        System.out.println("1. Create tables\n2. Delete tables\n3. Load data\n4. Check data\n5. Go back\nPlease enter [1-5]");
        
        while (true) {
        	int administrator_choice = reader.nextInt();
        	switch(administrator_choice)
        	{
            	case 1:create_tables(); break;
            	case 2:delete_tables();break;
            	case 3:load_data();break;
            	case 4:check_data();break;
            	case 5:welcome_menu();break;
            	
            	default:System.out.println("[ERROR] Invalid input.\nPlease enter [1-4].");break;
        	}
        }
    }
    private void employee_menu() {
    	System.out.println("Employee, what would you like to do");
        System.out.println("1. Show Available Positions\n2. Mark Interested Position\n3. Check Average Working Time\n4. Go Back\nPlease enter [1-4].");

        while (true) {
        	int employee_choice = reader.nextInt();
        	switch(employee_choice)
        	{
            	case 1:show_available_positions(); break;
            	case 2:mark_interested_position();break;
            	case 3:check_average_working_time();break;
            	case 4:welcome_menu();break;      	
            	default:System.out.println("[ERROR] Invalid input.\nPlease enter [1-4].");break;
        	}
        }
    }
    private void employer_menu() {
    	System.out.println("Employer, what would you like to do");
        System.out.println("1. Post Position Recruitment\n2. Check employees and arrange an interview\n3. Accept an employee\n4. Go Back\nPlease enter [1-4].");

        while (true) {
        	int employer_choice = reader.nextInt();
        	switch(employer_choice)
        	{
            	case 1:post_position_recruitment(); break;
            	case 2:check_empolyees_and_arrange_an_interview();break;
            	case 3:accept_an_employee();break;
            	case 4:welcome_menu();break;      	
            	default:System.out.println("[ERROR] Invalid input.\nPlease enter [1-4].");break;
        	}
        }
    
    }
    
    //Step Two Action - Administrator
    private void create_tables() {
    	
    	//call : create table
    	
    	System.out.println("Processing...Done! Tables are created!");
    	admin_menu();
    }
    
    private void delete_tables() {
    	
    	//call : delete table
    	
    	System.out.println("Processing...Done! Tables are deleted!");
    	admin_menu();
    }
    
    private void load_data () {
    	System.out.println("Please enter the folder path.");
    	String folder_path = reader.next();
    	
    	//call : load data
    	
    	System.out.println("Processing...Data is loaded!");
    	admin_menu();
    }

    
    private void check_data () {
    	System.out.println("Number of records in each table:");
    	
    	//call : check data
    	
    	admin_menu();
    	
    }
    
    //Step Two Action - Employee
    private void show_available_positions() {
    	System.out.println("Please enter your ID.");
    	int employee_id = reader.nextInt();
    	System.out.println("Your available poisition are:");
    	System.out.println("Position_ID, Position_Title, Salary Company, Size, Founded");
    	
    	// call : show available positions
    	
    	employee_menu();
    	
    }
    
    private void mark_interested_position() {
    	System.out.println("Please enter your ID.");
    	int employee_id = reader.nextInt();
    	System.out.println("You interested poistions are:");
    	System.out.println("Position_ID, Position_Title, Salary Company, Size, Founded");
    	
    	// call : show all positions that the employee may be interested
    	
    	System.out.println("Please enter one interested Position_ID.");
    	int interested_id = reader.nextInt();
    	
    	// call : mark interested position
    	System.out.println("Done.");
    	
    	employee_menu();
    	
    }
    
    private void check_average_working_time() {
    	System.out.println("Please enter your ID.");
    	int employee_id = reader.nextInt();
    	System.out.print("You average working time is: ");
    	
    	// check average working time
    	
    	System.out.println(".");
    	employee_menu();
    }
 
    //Step Two Action - Employer
    private void post_position_recruitment() {
    	System.out.println("Please enter your ID.");
    	int employer_id = reader.nextInt();
    	System.out.println("Please enter the position title.");
    	String position_title = reader.next();
    	System.out.println("Please enter an upper bound of salary.");
    	int upper_salary = reader.nextInt();
    	System.out.println("Plase enter the required experience(press enter to skip).");
    	String required_experience = reader.nextLine(); // ������SKIP�ӤU
    	

    	//�O��n��~~~~~~~~~~~ �I��SKIP
    	
    	// (If at least 1 potential employee.)
    	
    	// call : Display the number of potential employees to the employer. 
    	//System.out.println(" potential employees are found. The position recruitment is posted.");
    	
    	// (If no)
    	
    	//return an error message for the employer
    	//System.out.println("ERROR");
    	//post_position_recruitment();
    	
    	employer_menu();
    	}
	
    
    private void check_empolyees_and_arrange_an_interview() {
    	System.out.println("Please enter your ID.");
    	int employer_id = reader.nextInt();
    	System.out.println("The id of position recruitments posted by you are:");
    	
    	// show the position recruitments posted by you
    	
    	System.out.println("Please pick one postion id.");
    	int picked_position_id = reader.nextInt();
    	
    	System.out.println("The employees who mark interested in this position recruitment are:");
    	System.out.println("Employee_ID,Name,Expected_Salary,Experience,Skills");
    	
    	// show the employees who mark interested in picked_id
    	
    	System.out.println("Please pick one employee by Employee_ID.");
    	int picked_employee_id = reader.nextInt();
    	
    	// �n���n����? �@��ڻ
    	
    	System.out.println("An IMMEDIATE interview has done.");
    	employer_menu();
    	
    }
    
    private void accept_an_employee() {
    	System.out.println("Please enter your ID.");
    	int employer_id = reader.nextInt();
    	System.out.println("Please enter the Employee_ID you want to hire.");
    	int hire_employee_id = reader.nextInt();
    	System.out.println("An Employment History record is created, details are:");
    	System.out.println("Employee_ID, Company, Position_ID, Start, End");
    	
    	// show the hire
    	
    	employer_menu();
    }

    //~~~~~~~~~~~~~~~~ �[�o ~~~~~~~~~~~~~~~~~~~~~~
    public static void main(String[] args) {
    	Recruitment r= new Recruitment();
        try {
            DBConnection db = new DBConnection();
            db.admin_remove();
            db.admin_create();
            db.admin_load_data();
            db.admin_check_table();
        } catch (SQLException ex) {
            Logger.getLogger(Recruitment.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return;
        
    }

 

   
    
}
