package authentication;

public class LoginManager {

    public LoginManager() {
    }

    public boolean login(
            String username,
            String password) {

        System.out.println(
                "Username entered: [" + username + "]"
        );

        System.out.println(
                "Password length: " + password.length()
        );

        if (username.equals("Aditi")
                && password.equals("1234")) {

            System.out.println(
                    "LOGIN CHECK: SUCCESS"
            );

            return true;
        }

        System.out.println(
                "LOGIN CHECK: FAILED"
        );

        return false;
    }
}