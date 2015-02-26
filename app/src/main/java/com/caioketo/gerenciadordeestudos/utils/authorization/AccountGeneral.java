package com.caioketo.gerenciadordeestudos.utils.authorization;

/**
 * Created by Caio on 03/11/2014.
 */
public class AccountGeneral {

    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "com.caioketo.gerenciadordeestudos";

    /**
     * Account name
     */
    public static final String ACCOUNT_NAME = "GDE";

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an GDE account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an GDE account";

    public static final String USERDATA_USER_OBJ_ID = "objectId";

    public static final ServerAuthenticate sServerAuthenticate = new ParseComServerAuthenticate();
}