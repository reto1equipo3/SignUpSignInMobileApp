package activities.signupsigninmobileapp;

import businessLogic.Logic;
import exceptions.BadPasswordException;
import exceptions.DatabaseException;
import exceptions.EmailNotUniqueException;
import exceptions.LoginExistingException;
import exceptions.LoginNotExistingException;
import message.MessageType;
import model.UserBean;

public class ConnectionThread extends Thread {
    private UserBean user;
    private MessageType message;
    private Logic logic;

    @Override
    public void run() {
        switch (message) {
            case SIGN_IN:
                try {
                    user = logic.signIn(user);
                } catch (DatabaseException | LoginNotExistingException | BadPasswordException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case SIGN_UP:
                try{
                    logic.signUp(user);
                } catch(DatabaseException| LoginExistingException| EmailNotUniqueException ex){
                    throw new RuntimeException(ex);
                }
                break;
            case LOG_OUT:
                break;
        }
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public MessageType getMessage(){
        return message;
    }

    public void setMessage(MessageType message) {
        this.message = message;
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }
}
