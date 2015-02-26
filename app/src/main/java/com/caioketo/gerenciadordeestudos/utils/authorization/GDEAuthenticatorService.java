package com.caioketo.gerenciadordeestudos.utils.authorization;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Caio on 03/11/2014.
 */
public class GDEAuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        GDEAuthenticator authenticator = new GDEAuthenticator(this);
        return authenticator.getIBinder();
    }
}