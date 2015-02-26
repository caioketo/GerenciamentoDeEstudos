package com.caioketo.gerenciadordeestudos.utils.authorization;

/**
 * Created by Caio on 03/11/2014.
 */
public interface ServerAuthenticate {
    public User userSignUp(final String name, final String email, final String pass, String authType) throws Exception;
    public User userSignIn(final String user, final String pass, String authType) throws Exception;
}