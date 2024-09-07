package client;

public class Courier {
private final String login;
private final String password;
private final String FirstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        FirstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return FirstName;
    }
}
