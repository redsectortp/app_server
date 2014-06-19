package com.whatnow.serverside.shared.login;

import java.io.Serializable;

/**
 *
 * @author Thiago
 */
public class BasicAuthenticationPair implements Serializable {
    
    private String username;
    private String password;
    
    public BasicAuthenticationPair() { }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
