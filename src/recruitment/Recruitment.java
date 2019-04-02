/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recruitment;
import java.sql.SQLException;
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
    public static void main(String[] args) throws SQLException {
        System.out.print("kcods");
      DBConnection db = new DBConnection();
      db.admin_create();

    }

 

   
    
}
