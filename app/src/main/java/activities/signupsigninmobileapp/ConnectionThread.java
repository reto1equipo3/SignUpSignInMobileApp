package activities.signupsigninmobileapp;

import businessLogic.Logic;
import message.MessageType;


public class ConnectionThread extends Thread {

    private UserBean user;
    private Logic logic;
    private Integer msg;


    public UserBean getUser() {

        return user;
    }

    public ConnectionThread(UserBean user, int msg, Logic logic) {
        this.user=user;
        this.msg=msg;
        this.logic=logic;
    }

    @Override
    public void run() {
        if (msg == 1) {
            try {
                user = logic.signIn(user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (msg == 2) {
            try {
                logic.signUp(user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
