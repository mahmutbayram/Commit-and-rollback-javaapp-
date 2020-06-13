
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CommitAndRollback {
    private String username = "root";
    private String password = "";
    
    private String db_name = "demo";
    
    private String host =  "localhost";
    
    private int port = 3306;
    
    private Connection con = null;
    
    private Statement statement = null;
    
    /*Database Transaction
    
    Most of the time, we do many database operations in our programs one after another.
    For example, we have 3 linked table updates (delete, update).
    Let's say we run these processes and queries consecutively.
    
    statement.executeupdat A (query1);
    statement.executeupdat A (sorgu2); // There was an error here and our program is over.
    statement.executeupdat A (sorgu3);
    
    
    In such a case, there is an error in our 2nd query.
    However, since there was no error in query 1, this query updated our database.
    However, if these queries are related, query 1 should not work.
    Here we use Transactions to prevent such situations.
    
    (ATM Example)
    
    To use transaction logic, these queries are only
    we want it to work collectively.
    
    From the moment we write these queries, Java automatically queries without queries
    It runs. So, by doing something like con.setAutoCommit (false) first
    We're blocking.
    
    commit (): Run all queries. We use it to run all of them when there is no problem.
    rollback (): Cancel all queries. We use it to not run any of them when there is a problem.
    
    
    So this time, we have written our programs a little safer.
    
    Note: Even if we write setAutoCommit (false), we have a query that doesn't update the Database,
    it is operated as there will be no security problems.
    */
    
    public CommitAndRollback() {
        
            
        //"jbdc:mysql://localhost:3306/demo" 
                
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db_name+ "?useUnicode=true&characterEncoding=utf8";
        
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver is not found....");
        }
        
        
        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Sql connection success...");
            
            
        } catch (SQLException ex) {
            System.out.println("Sql connection was not successfull...");
            ex.printStackTrace();
        }
        
        
    }
    public void commitandrollback() {
        Scanner scanner = new Scanner(System.in);
        
        try {
            con.setAutoCommit(false);
            
            String query1 = "Delete From employee where id = 3";
            String query2 = "Update employee set department = 'example department' where id = 1";
            
            System.out.println("Before updating..");
            getEmployee();
            
            Statement statement = con.createStatement();
            
            statement.executeUpdate(query1);
            
            statement.executeUpdate(query2);
            
            System.out.println("Save your transactions? (yes/no)");
            String answer = scanner.nextLine();
            
            if (answer.equals("yes")){ //We state that we want to run the commands ourselves.
                con.commit();
                getEmployee();
                System.out.println("Database updated...");
                
            }
            else {
                con.rollback(); // We use it to give up running processes and stop processes.
                System.out.println("Database update canceled...");
                getEmployee();
                
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CommitAndRollback.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }
    
    public void getEmployee() {
        
        String query = "Select * From employee";
      
        try {
            statement = con.createStatement();
            
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String department = rs.getString("department");
                
                System.out.println("Id : " + id + "Name: " + name + "Surname : " + surname + " Department : " + department);
                
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CommitAndRollback.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    public static void main(String[] args) {
        CommitAndRollback comroll = new CommitAndRollback();
        //dbConnection.getEmployee();
        
        comroll.commitandrollback();
        
        
        
    }
}
