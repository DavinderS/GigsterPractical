package GigsterPractical;

public class User {

    private Integer user_id;
    private String user_name;
    private String password;
    private Integer admin;

    public User(Integer user_id, String user_name, String password, Integer admin) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.admin = admin;

    }

    public void setUserId(String user_name) {
        this.user_id = user_id;
    }
    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getUserId() {
        return this.user_id;
    }

    public String getUserName() {
        return this.user_name;
    }

    public String getPassword() {
        return this.password;
    }

    public Integer getAdmin() {
        return this.admin;
    }
}