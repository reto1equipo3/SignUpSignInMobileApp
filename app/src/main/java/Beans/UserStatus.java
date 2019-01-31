package Beans;

import java.io.Serializable;

public enum UserStatus implements Serializable {
    /**
     * If user is in date base is ENABLED.
     */
    ENABLED,
    /**
     * If user isn't in date base is DISABLED .
     */
    DISABLED;
}
