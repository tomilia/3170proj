/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recruitment;
import java.util.Scanner;
/**
 *
 * @author tommylee
 */

public class Recruitment {
     //position of the user
    static Scanner reader = new Scanner(System.in); 
    int position;
    void admin_menu(){
        System.out.println("Administrator, what would you like to do");
        System.out.println("1. Create tables\n2. Delete tables\n3. Load data\n4. Check data\n5. Go back\nPlease enter [1-5]");

        int n = reader.nextInt();
    }
    private void employee_menu() {
       
    }
    private void employer_menu() {
    
    }
    public static void main(String[] args) {
        System.out.println("Welcome! Who are you?");
        System.out.println("1. An administrator\n2. An employee\n3. An employer\n4. Exit\nPlease enter [1-4]");
        Recruitment r= new Recruitment();
        int n = reader.nextInt();
        
        
        switch(n)
        {
            case 1:r.admin_menu();break;
            case 2:r.employee_menu();break;
            case 3:r.employer_menu();break;
            default:System.out.println("invalid");break;
        }

    }

 

   
    
}
