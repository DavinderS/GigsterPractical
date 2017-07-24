package GigsterPractical;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.Connection;
import com.google.gson.Gson;
import java.math.BigDecimal;

@RestController
public class RouteController {
    private SQLiteConnection connection = new SQLiteConnection();
    private static final Gson gson = new Gson();

    @CrossOrigin()
    @RequestMapping(value = "/createUser", method=RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestParam(value="username") String username, @RequestParam(value="password") String password, @RequestParam(value="admin") Integer admin) {
        Integer statusCode = connection.executeUpdate("insert into users values(null,'"+username+"','"+password+"', "+admin+")");
        if (statusCode == 0) {
            // We send back the username because we want the client to know who logged in. Also don't need to send the user id or the password
            return new ResponseEntity<User>(new User(null, username, null, admin), HttpStatus.ACCEPTED);
        } else if (statusCode == 19) {
            return new ResponseEntity<String>(gson.toJson("Username already exists"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<String>(gson.toJson("Unhandled Error"), HttpStatus.NOT_IMPLEMENTED);
        }
        
    }


    @CrossOrigin()
    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public ResponseEntity<?> login(@RequestParam(value="username") String username, @RequestParam(value="password") String password) {
        String message = "";
        HttpStatus status;
        ResultSet rs = connection.executeQuery("select * from users where user_name='"+username+"' AND password='"+password+"'");
        try {
            if (rs.next())
            {
                // Client needs to know who logged in so send the user back
                User user = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
                status = HttpStatus.OK;
                return new ResponseEntity<User>(user, status);
            } else
            {
                message = "Incorrect password";
                status = HttpStatus.UNAUTHORIZED;
                return new ResponseEntity<String>(gson.toJson(message), status);
            }
        } catch (Exception e) {
            System.err.println(e);
            message = "Username does not exist";
            status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<String>(gson.toJson(message), status);
        }
    }


    @CrossOrigin()
    @RequestMapping(value = "/getExpenses", method=RequestMethod.GET)
    // Obviously not a secure way to do it but running out of time, need to authenticate the user in production product
    public ResponseEntity<?> getExpenses(@RequestParam(value="userid") Integer userid, @RequestParam(value="admin") Integer admin) {
        ArrayList<Expense> expenses = new ArrayList<Expense>();
        String query = "select * from expenses";
        if (admin == 0) {
            query = "select * from expenses where user_id='"+userid+"'";
        }
        ResultSet rs = connection.executeQuery(query);
        try {
            while(rs.next())  
            {
                Expense expense = new Expense(rs.getInt(1), rs.getBigDecimal(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                expenses.add(expense);
            }
        } catch (Exception e) {
            System.err.println(e);
            return new ResponseEntity<String>(gson.toJson("Error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(gson.toJson(expenses), HttpStatus.OK);
    }


    @CrossOrigin()
    @RequestMapping(value = "/deleteExpense", method=RequestMethod.POST)
    public ResponseEntity<?> deleteExpense(@RequestParam(value="expenseid") Integer expenseid) {
        Integer statusCode = connection.executeUpdate("delete from expenses where expense_id='"+expenseid+"'");
        if (statusCode == 0) {
            return new ResponseEntity<String>(gson.toJson("Success"), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<String>(gson.toJson("Error"), HttpStatus.BAD_REQUEST);
        }
    }
    
    @CrossOrigin()
    @RequestMapping(value = "/updateExpense", method=RequestMethod.POST)
    public ResponseEntity<?> updateExpense(@RequestParam(value="expenseid") Integer expenseid, @RequestParam(value="datecreated") String datecreated, @RequestParam(value="amount") BigDecimal amount, @RequestParam(value="description") String description) {
        Integer statusCode = connection.executeUpdate("update expenses set date_created='"+datecreated+"', amount='"+amount+"', description='"+description+"' where expense_id='"+expenseid+"'");
        if (statusCode == 0) {
            return new ResponseEntity<String>(gson.toJson("Success"), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<String>(gson.toJson("Error"), HttpStatus.BAD_REQUEST);
        }
    }


    @CrossOrigin()
    @RequestMapping(value = "/createExpense", method=RequestMethod.POST)
    public ResponseEntity<?> createExpense(@RequestParam(value="userid") Integer userid, @RequestParam(value="datecreated") String datecreated, @RequestParam(value="amount") BigDecimal amount, @RequestParam(value="description") String description) {
        Integer statusCode = connection.executeUpdate("insert into expenses values(null,'"+amount+"','"+description+"','"+userid+"','"+datecreated+"')");
        if (statusCode == 0) {
            return new ResponseEntity<String>(gson.toJson("Success"), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<String>(gson.toJson("Error"), HttpStatus.BAD_REQUEST);
        }
        
    }


    
} 