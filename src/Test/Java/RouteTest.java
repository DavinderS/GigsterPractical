package GigsterPractical;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
public class RouteTest {
  private RouteController routeController = new RouteController();
  private ResponseEntity<?> test;
  @Test
  public void testLogin() {
    // Login with valid credentials
    test = routeController.login("Dave","abc");
    assertEquals(test.getStatusCode(), HttpStatus.OK);

    // Login with existing username but wrong password
    test = routeController.login("Dave","abcd");
    assertEquals(test.getStatusCode(), HttpStatus.UNAUTHORIZED);

    // Login with non-existing username
    test = routeController.login("Daveeee","abcd");
    assertEquals(test.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }
  
  @Test
  public void testGetExpenses() {
    test = routeController.getExpenses(0,0);
    // Non admin user with at least 1 expense
    assertEquals(test.getStatusCode(), HttpStatus.OK);
    assertEquals(test.getBody(), "[{\"expense_id\":5,\"amount\":333.44,\"description\":\"321321\",\"user_id\":0,\"date_created\":\"21312-2-13 3:21\"}]");

    // non admin user with no expenses
    test = routeController.getExpenses(1,0);
    assertEquals(test.getStatusCode(), HttpStatus.OK);
    assertEquals(test.getBody(), "[]");

    // Admin user with at least 1 expense
    test = routeController.getExpenses(0,1);
    assertEquals(test.getStatusCode(), HttpStatus.OK);
    assertEquals(test.getBody(), "[{\"expense_id\":2,\"amount\":1111.0222,\"description\":\"test\",\"user_id\":20,\"date_created\":\"2333-1-22 14:22\"},{\"expense_id\":3,\"amount\":422,\"description\":\"42\",\"user_id\":20,\"date_created\":\"33333-3-31 15:33\"},{\"expense_id\":4,\"amount\":2,\"description\":\"2\",\"user_id\":20,\"date_created\":\"1902-2-22 14:22\"},{\"expense_id\":5,\"amount\":333.44,\"description\":\"321321\",\"user_id\":0,\"date_created\":\"21312-2-13 3:21\"},{\"expense_id\":6,\"amount\":222,\"description\":\"test\",\"user_id\":20,\"date_created\":\"2017-7-23 14:2\"},{\"expense_id\":7,\"amount\":444,\"description\":\"new\",\"user_id\":20,\"date_created\":\"2017-7-23 14:2\"}]");

    // Admin user with no expenses
    test = routeController.getExpenses(1,1);
    assertEquals(test.getStatusCode(), HttpStatus.OK);
    assertEquals(test.getBody(), "[{\"expense_id\":2,\"amount\":1111.0222,\"description\":\"test\",\"user_id\":20,\"date_created\":\"2333-1-22 14:22\"},{\"expense_id\":3,\"amount\":422,\"description\":\"42\",\"user_id\":20,\"date_created\":\"33333-3-31 15:33\"},{\"expense_id\":4,\"amount\":2,\"description\":\"2\",\"user_id\":20,\"date_created\":\"1902-2-22 14:22\"},{\"expense_id\":5,\"amount\":333.44,\"description\":\"321321\",\"user_id\":0,\"date_created\":\"21312-2-13 3:21\"},{\"expense_id\":6,\"amount\":222,\"description\":\"test\",\"user_id\":20,\"date_created\":\"2017-7-23 14:2\"},{\"expense_id\":7,\"amount\":444,\"description\":\"new\",\"user_id\":20,\"date_created\":\"2017-7-23 14:2\"}]");

  }
  @Test
  public void testCreateUser() {
    // Existing user
    test = routeController.createUser("Dave", "abc", 1);
    assertEquals(test.getStatusCode(), HttpStatus.BAD_REQUEST);
  }
  @Test
  public void testCreateExpense() {
    // Can't test without breaking other tests since I don't have time to create a selector method for the previously created item. Same with testing update
    /*
    BigDecimal big = new BigDecimal(200.02);
    test = routeController.createExpense(4,"2333-1-22 14:22", big, "hi");
    assertEquals(test.getStatusCode(),HttpStatus.ACCEPTED);*/
  }
  @Test
  public void testDeleteExpense() {
    // Can't test without breaking other tests since I don't have time to create a selector method for the previously created item. Same with testing update
  }
}