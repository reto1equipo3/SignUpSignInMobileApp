package Beans;

import java.io.Serializable;

public enum UserPrivilege implements Serializable {
    /**
     * The user is a regular user.
     */
    USER,
    /**
     * The user is a privileged user.
     */
    ADMIN;
}
