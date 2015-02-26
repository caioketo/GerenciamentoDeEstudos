package com.caioketo.gerenciadordeestudos.utils.syncronization;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Caio on 07/11/2014.
 */
public class GDESyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static GDESyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null)
                sSyncAdapter = new GDESyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
