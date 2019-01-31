package Beans;



import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Data Transfer Object used in UI and client side for representing User entity.
 *
 * @author Leticia
 */
public class UserBean implements Serializable {

    private Integer id;
    private String login;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String postalCode;
    private String town;
    private String address;
    private String creditCard;
    private String cvv;
    private String cardholderName;
    private UserStatus status;
    private UserPrivilege privilege;
    private String password;
    private Timestamp lastAccess;
    private Timestamp lastPasswordChange;

    //Getter and setters
    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }



    public String getTown() {
        return town;
    }

    public String getAddress() {
        return address;
    }



    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastAccess(Timestamp lastAccess) {
        this.lastAccess = lastAccess;
    }

    public void setLastPasswordChange(Timestamp lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    //Constructor
    public UserBean() {
        this.id = id;
        this.login = login;
        this.email = email;
        this.fullName = fullName;
        this.status = status;
        this.privilege = privilege;
        this.password = password;
        this.lastAccess = lastAccess;
        this.lastPasswordChange = lastPasswordChange;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.town = town;
        this.address = address;
        this.creditCard = creditCard;
        this.cvv = cvv;
        this.cardholderName = cardholderName;
    }

    /*public UserBean(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }*/

    public UserBean(Integer id, String login, String email, String fullName, String phoneNumber, String postalCode, String town, String address, String creditCard, String cvv, String cardholderName, UserStatus status, UserPrivilege privilege, String password, Timestamp lastAccess, Timestamp lastPasswordChange) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.town = town;
        this.address = address;
        this.creditCard = creditCard;
        this.cvv = cvv;
        this.cardholderName = cardholderName;
        this.status = status;
        this.privilege = privilege;
        this.password = password;
        this.lastAccess = lastAccess;
        this.lastPasswordChange = lastPasswordChange;
    }

    public UserBean(String s, String toString) {
    }

    //Methods
    /**
     * Method to validate the email is correct.
     *
     * @param String email to validate
     * @return True if the email format is the same tu Pattern
     * @author Leticia
     */
   /* public static boolean checkEmail(String email) {
        //Establecemos el patrón
        Pattern p = Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@"
                + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$");
        //Asociamos el String del email al patron
        //El Matcher analiza si dicha cadena o las subcadenas que la componen pertenecen al lenguaje dado.
        Matcher m = p.matcher(email);
        return m.matches();  //El método matches de String nos permite comprobar si un String cumple una expresión regular pasado como parámetro.
    }
*/

}