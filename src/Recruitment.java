/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    DBConnection db;
    //Step One Question
    void welcome_menu() {
    	System.out.println("Welcome! Who are you?");
    	System.out.println("1. An administrator\n2. An employee\n3. An employer\n4. Exit\nPlease enter [1-4].");
    
    
    	while (true) {
    		try {
    		String position_choice = reader.next();
    		switch(Integer.parseInt(position_choice)) {
        		case 1:admin_menu(); break;
        		case 2:employee_menu();break;
        		case 3:employer_menu();break;
        		case 4:System.exit(0);
        		default:System.out.println("[ERROR] Invalid input.\nPlease enter [1-4].");break;
    		}
    		} catch (NumberFormatException e) {
    			System.out.println("[ERROR] Invalid input.\nPlease enter [1-4].");
    		}
    		
    	}
    		
    	
    }
    //Step Two Question
    void admin_menu(){
        System.out.println("Administrator, what would you like to do");
        System.out.println("1. Create tables\n2. Delete tables\n3. Load data\n4. Check data\n5. Go back\nPlease enter [1-5].");
        
        while (true) {
        	try {
        	String administrator_choice = reader.next();
        	switch(Integer.parseInt(administrator_choice))
        	{
            	case 1:create_tables(); break;
            	case 2:delete_tables();break;
            	case 3:load_data();break;
            	case 4:check_data();break;
            	case 5:welcome_menu();break;
            	default:System.out.println("[ERROR] Invalid input.\nPlease enter [1-5].");break;
        	}
        	} catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter [1-5].");
        	}
        }
    }
        
    private void employee_menu() {
    	System.out.println("Employee, what would you like to do");
        System.out.println("1. Show Available Positions\n2. Mark Interested Position\n3. Check Average Working Time\n4. Go Back\nPlease enter [1-4].");

        while (true) {
        	try {
        	String employee_choice = reader.next();
        	switch(Integer.parseInt(employee_choice))
        	{
            	case 1:show_available_positions(); break;
            	case 2:mark_interested_position();break;
            	case 3:check_average_working_time();break;
            	case 4:welcome_menu();break;      	
            	default:System.out.println("[ERROR] Invalid input.\nPlease enter [1-4].");break;
        	}
        	}
        	catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter [1-4].");
        	}
        }
    }
    
    private void employer_menu() {
    	System.out.println("Employer, what would you like to do");
        System.out.println("1. Post Position Recruitment\n2. Check employees and arrange an interview\n3. Accept an employee\n4. Go Back\nPlease enter [1-4].");

        while (true) {
        	try {
        	String employer_choice = reader.next();
        	switch(Integer.parseInt(employer_choice))
        	{
            	case 1:post_position_recruitment(); break;
            	case 2:check_empolyees_and_arrange_an_interview();break;
            	case 3:accept_an_employee();break;
            	case 4:welcome_menu();break;      	
            	default:System.out.println("[ERROR] Invalid input.\nPlease enter [1-4].");break;
        	}
        	} catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter [1-4].");
        	}
        }
    
    }
    
    //Step Two Action - Administrator
    private void create_tables() {
    	
        try {
            //call : create table
            db= new DBConnection();
            
            db.admin_create();
               System.out.println("Processing...Done! Tables are created!");
    	admin_menu();
        } catch (SQLException ex) {
            Logger.getLogger(Recruitment.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        
    }
    
    private void delete_tables() {
    	
    	//call : delete table
    
        try {
            db = new DBConnection();
            db.admin_remove();
            System.out.println("Processing...Done! Tables are deleted!");
            admin_menu();
        } catch (SQLException ex) {
            Logger.getLogger(Recruitment.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
    	
    }
    
    private void load_data () {
        System.out.println("Please enter the folder path.");
    	String folder_path = reader.next();
        try {
            db = new DBConnection();
            db.admin_load_data();
            System.out.println("Processing...Data is loaded!");
            admin_menu();
        } catch (SQLException ex) {
            Logger.getLogger(Recruitment.class.getName()).log(Level.SEVERE, null, ex);
        }

    	
        
    	//call : load data from the user input folder
    	

    }

    
    private void check_data () {
        
    	System.out.println("Number of records in each table:");
    	try {
            db = new DBConnection();
            db.admin_check_table();
            admin_menu();
        } catch (SQLException ex) {
            Logger.getLogger(Recruitment.class.getName()).log(Level.SEVERE, null, ex);
        }

    	//call : check data (display the number of records of each tables
    	
    	
    }
    
    //Step Two Action - Employee
    private void show_available_positions() {
    	System.out.println("Please enter your ID.");
        String employee_id;
    	while (true) {
    		try {
    			employee_id = reader.next();
    		         
    			break;
    		}
    		catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter your ID.");
        	}
    	}
        
        try {   
            db = new DBConnection();
            db.employee_check_ava(employee_id);
            employee_menu();
        } catch (SQLException ex) {
            Logger.getLogger(Recruitment.class.getName()).log(Level.SEVERE, null, ex);
        }
    	// call : show available positions
    	
    }
    
    private void mark_interested_position() {
    	System.out.println("Please enter your ID.");
    	while (true) {
    		try {
    			String temp = reader.next(); 
    			int employee_id = Integer.parseInt(temp);
    			break;
    		}
    		catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter your ID.");
        	}
    	}
    	
    	System.out.println("You interested poistions are:");
    	System.out.println("Position_ID, Position_Title, Salary Company, Size, Founded");
    	
    	// call : show all positions that the employee may be interested
    	
    	System.out.println("Please enter one interested Position_ID.");
    	while (true) {
    		try {
    			String temp = reader.next();
    			int interested_id = Integer.parseInt(temp);
    			break;
    		}catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter one interested Position_ID.");
        	}
    	}
    	// call : mark interested position
    	System.out.println("Done.");
    	
    	employee_menu();
    	
    }
    
    private void check_average_working_time() {
    	System.out.println("Please enter your ID.");
    	while (true) {
    		try {
    			String temp = reader.next();
    			int employee_id = Integer.parseInt(temp);
    			break;
    		} catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter your ID.");
        	}
    	}
    	
    	
    	// (if => 3 records)
    	// System.out.print("You average working time is: .");
    	
    		// check average working time
    	
    	// (if less than 3 records)
    	// System.out.println("Less than 3 records.");

    	
    	employee_menu();
    }
 
    //Step Two Action - Employer
    private void post_position_recruitment() {
    	System.out.println("Please enter your ID.");
    	while(true) {
    		try {
    			String temp = reader.next();			
    			int employer_id = Integer.parseInt(temp);
    			break;
    		}catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter your ID.");
        	}
    	}
    	
    	System.out.println("Please enter the position title.");
    	String position_title = reader.next();

    	
    	System.out.println("Please an upper bound of salary.");
    	while(true) {
    		try {
    			String temp = reader.next();	
    			int upper_salary = Integer.parseInt(temp);
    			break;
    		}catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease an upper bound of salary.");
        	}
    	}
    	
    	System.out.println("Please enter the required experience(press enter to skip.)");
    	
    	String stuff = reader.nextLine(); 
    	//�]��nextLine�|�pEnter��,���W���T��enter �O�쳣�|�p�I,�ҥH�۰ʸ���
    	
    	while(true) {
    		try {
    			String temp = reader.nextLine();
    			if(temp.length() == 0) // skip this funciton
    				break;
    			int required_experience = Integer.parseInt(temp);
    			break;
    		} catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter the required experience(press enter to skip.)");
        	}
    	}

    	//�O��n��~~~~~~~~~~~ �I��SKIP
    	
    	// (If at least 1 potential employee.)
    	
    	// call : Display the number of potential employees to the employer. 
    	//System.out.println(" potential employees are found. The position recruitment is posted.");
    	
    	// (If no)
    	
    	//display an error message for the employer
    	//System.out.println("ERROR");
    	
    	employer_menu();
    	}
	
    
    private void check_empolyees_and_arrange_an_interview() {
    	System.out.println("Please enter your ID.");
    	while(true) {
    		try {
    			String temp = reader.next();
    			int employer_id = Integer.parseInt(temp);
    			break;
    		} catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter your ID.");
        	}
    	}
    	
    	System.out.println("The id of position recruitments posted by you are:");
    	
    	// show the position recruitments posted by you
    	
    	System.out.println("Please pick one position id.");
    	while(true) {
    		try {
    			String temp = reader.next();
    			int picked_position_id = Integer.parseInt(temp);
    			break;
    		}catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease pick one position id.");
        	}
    	}
    	
    	System.out.println("The employees who mark interested in this position recruitment are:");
    	System.out.println("Employee_ID,Name,Expected_Salary,Experience,Skills");
    	
    	// show the employees who mark interested in picked_position_id
    	
    	System.out.println("Please pick one employee by Employee_ID.");

    	while(true) {
    		try {
    			String temp = reader.next();
    			int picked_employee_id = Integer.parseInt(temp);
    			break;
    		} catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease pick one employee by Employee_ID.");
        	}
    	}
    	System.out.println("An IMMEDIATE interview has done.");
    	employer_menu();
    	
    }
    
    private void accept_an_employee() {
    	System.out.println("Please enter your ID.");
    	while(true) {
    		try {
    			String temp = reader.next();
    			int employer_id = Integer.parseInt(temp);
    			break;
    		}catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter your ID.");
        	}
    	}
    	
    	System.out.println("Please enter the Employee_ID you want to hire.");
    	while(true) {
    		try {
    			String temp = reader.next();
    			int hire_employee_id = Integer.parseInt(temp);
    			break;
    		}catch (NumberFormatException e) {
        		System.out.println("[ERROR] Invalid input.\nPlease enter the Employee_ID you want to hire.");
        	}
    	}
    	
    	// check whether the employee is suitable or not (in����)
    	
    	// (if OK)
    	System.out.println("An Employment History record is created, details are:");
    	System.out.println("Employee_ID, Company, Position_ID, Start, End");
    	// show the hire
    	
    	// (if no OK)
    	// System.out.println("ERROR");
    	
    	employer_menu();
    }

    //~~~~~~~~~~~~~~~~ �[�o ~~~~~~~~~~~~~~~~~~~~~~
    public static void main(String[] args) {
    	Recruitment r= new Recruitment();

    	
    	r.welcome_menu();
       
        return;
   
    }

}
