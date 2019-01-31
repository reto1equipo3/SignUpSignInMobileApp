package businessLogic;

import activities.signupsigninmobileapp.SocketClient;
import exceptions.BadPasswordException;
import exceptions.EmailNotUniqueException;
import exceptions.LoginExistingException;
import exceptions.LoginNotExistingException;
import exceptions.DatabaseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Message;
import message.MessageType;
import model.UserBean;
;

/**
 * This class implements {@link Logic} business logic interface.
 *
 * @author Imanol
 */
public class LogicImplementation implements Logic {

	/**
	 * {@link Logger} object used to log messages for the class.
	 */
	private static final Logger LOGGER = Logger.getLogger("businesslogic");

	/**
	 * Sends a message to the server to sign in the user.
	 *
	 * @param user The user to sign in.
	 * @return User with all data.
	 * @throws LoginNotExistingException The user doesn't exist.
	 * @throws BadPasswordException Input password is wrong.
	 * @throws DatabaseException Database related exception
	 */
	@Override
	public UserBean signIn(UserBean user) throws LoginNotExistingException, BadPasswordException, DatabaseException {
		LOGGER.info("LogicImplementation::signIn: Beginning signing in user.");
		// Create a message of type SIGN_IN and add the user
		Message message = new Message(MessageType.SIGN_IN, user);
		// Create a socket instance
		SocketClient socket = new SocketClient();
		// Initialize the socket with a message
		message = socket.init(message);

		// Handle the received message
		switch (message.getMessage()) {
			case SIGN_IN:
				LOGGER.info("LogicImplementation::signIn: Signed in correctly.");
				if (message.getData() instanceof UserBean) {
					user = (UserBean) message.getData();
				} else {
					throw new DatabaseException("Error. Something bad happened with the database.");
				}
				break;
			case E_BADPASSWORD:
				LOGGER.log(Level.SEVERE,
					"LogicImplementation::signIn: Exception validating password.");
				throw new BadPasswordException("Error validating password: " + message.getMessage());
			case E_LOGINNOTFOUND:
				LOGGER.log(Level.SEVERE,
					"LogicImplementation::signIn: Exception finding login.");
				throw new LoginNotExistingException("Error finding login: " + message.getMessage());
			case E_ERROR:
				LOGGER.log(Level.SEVERE,
					"LogicImplementation::signIn: Exception something went wrong.");
				throw new DatabaseException(message.getMessage().toString());
		}

		return user;
	}

	/**
	 * Sends a message to the server to sign up a user.
	 *
	 * @param user The user to sign up.
	 * @throws LoginExistingException Input login already exists.
	 * @throws EmailNotUniqueException Input email already exists.
	 * @throws DatabaseException Database related exception
	 */
	@Override
	public void signUp(UserBean user) throws LoginExistingException, EmailNotUniqueException, DatabaseException {
		LOGGER.info("LogicImplementation::signUp: Beginning signing in user.");
		// Create message of type SIGN_UP and add the user
		Message message = new Message(MessageType.SIGN_UP, user);
		// Create a socket instance
		SocketClient socket = new SocketClient();
		// Initialize the socket with a message
		message = socket.init(message);

		// Handle the received message
		switch (message.getMessage()) {
			case E_EMAILNOTUNIQUE:
				LOGGER.log(Level.SEVERE,
					"LogiImplementation::signUp: Exception adding email.");
				throw new EmailNotUniqueException("Error adding email:\n" + message.getMessage());
			case E_LOGINEXISTS:
				LOGGER.log(Level.SEVERE,
					"LogiImplementation::signUp: Exception adding login.");
				throw new EmailNotUniqueException("Error adding login:\n" + message.getMessage());
			case E_ERROR:
				LOGGER.log(Level.SEVERE,
					"LogiImplementation::signUp: Exception something went wrong.");
				throw new EmailNotUniqueException("Error:\n" + message.getMessage());
		}
	}

	/**
	 * Sends a message to the server to log out the user.
	 *
	 * @param user The user to log out.
	 * @throws DatabaseException Database related exception
	 */
	@Override
	public void logOut(UserBean user) throws DatabaseException {
		LOGGER.info("LogicImplementation::signUp: Beginning logging out user.");
		// Create message of type LOG_OUT and add the user
		Message message = new Message(MessageType.LOG_OUT, user);
		// Create a socket instance
		SocketClient socket = new SocketClient();
		// Initialize the socket with a message
		message = socket.init(message);

		// Handle the received message
		switch (message.getMessage()) {
			case E_ERROR:
				LOGGER.log(Level.SEVERE,
					"LogicImplementation::logOut: Exception something went wrong.");
				throw new DatabaseException("Error:\n" + message.getMessage());
		}
	}

}
