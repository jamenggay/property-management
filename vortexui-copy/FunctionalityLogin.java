/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vortexui;

/**
 *
 * @author Lorenzo
 */
public class FunctionalityLogin {

    /**
     * Validates the login credentials.
     *
     * @param username the username provided by the user
     * @param password the password provided by the user
     * @return true if credentials are valid, false otherwise
     */
    public static boolean validateLogin(String username, String password) {
        // Example: hardcoded credentials for demonstration purposes.
        // Replace these with your actual authentication logic.
        String validUsername = "RavenLim";
        String validPassword = "PusoKoSiya";
        
        return validUsername.equals(username) && validPassword.equals(password);
    }
}
