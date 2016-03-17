package sharkitter.view;

public class UserNotFoundAlert extends AlertFrame {

    /**
     * Constructor of UserNotFoundAlert
     */
    public UserNotFoundAlert() {
        super();

        message1.setText("Sorry but your username wasn't recognised.");
        message2.setText("Please check your spelling or create a new account.");
    }
}
