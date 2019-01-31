/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.logging.Logger;


/**
 * This class is a factory for {@link Logic} interface implementing objects.
 *
 * @author Leticia
 */
//Es una clase de factoria que va ha devolver la interfaz logica con los objectos que necesitamosS
public class LogicFactory {

    protected static final Logger LOGGER = Logger.getLogger("businessLogic");

    /**
     * Logic type for user.
     */
    public static final String USER_CLIENT_TYPE = "USER_CLIENT";

    /**
     * Logic type for a manager producing fake data for test purposes.
     */
    public static final String TEST_TYPE = "TEST_TYPE";

    /**
     * Factory creation method. It returns different {@link Logic} interface
     * implementing objects depending on the type parameter value.
     *
     * @param type Type of implementation for object being returned.
     * @return An object implementing logic according to type.
     */
    public static Logic createLogicImplementation(String type) {
        Logic logic = null; //El objeto que va a retornar.

        LOGGER.info(" Is creating a Logic Implementation");

        switch (type) {
            //Si se solicita el tipo de cliente de usuario se va a usar el LogicImplementation
            case USER_CLIENT_TYPE:
                //Si se solicita el tipo falso  se va a usar el LogicTestData
                logic = new LogicImplementation();
                break;
           /* case TEST_TYPE:
                logic = new LogicTestData();
                break;
            default:
                //If type is not one of the types accepted.
                throw new OperationNotSupportedException("Logic type not supported.");*/

        }

        return logic;

    }
}
