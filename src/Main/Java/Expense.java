package GigsterPractical;
import java.math.BigDecimal;

public class Expense {

    private Integer expense_id;
    private BigDecimal amount;
    private String description;
    private Integer user_id;
    private String date_created;

    public Expense(Integer expense_id, BigDecimal amount, String description, Integer user_id, String date_created) {
        this.expense_id = expense_id;
        this.amount = amount;
        this.description = description;
        this.user_id = user_id;
        this.date_created = date_created;
    }

    public void setExpenseId(String amount) {
        this.expense_id = expense_id;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }
    public void setDateCreated(String date_created) {
        this.description = description;
    }

    public Integer getExpenseId() {
        return this.expense_id;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getUserId() {
        return this.user_id;
    }

    public String getDateCreated() {
        return this.date_created;
    }
}