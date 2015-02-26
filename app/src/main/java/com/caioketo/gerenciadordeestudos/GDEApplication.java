package com.caioketo.gerenciadordeestudos;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;

/**
 * Created by Caio on 06/11/2014.
 */
import static com.caioketo.gerenciadordeestudos.utils.authorization.ParseComServerAuthenticate.APP_ID;
import static com.caioketo.gerenciadordeestudos.utils.authorization.ParseComServerAuthenticate.REST_API;

public class GDEApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APP_ID, "jb6wYD7SPEN13y2P8Cr2epSazUUunVLs21E2RxH2");

        PushService.setDefaultPushCallback(this, MainActivity.class);
    }
}
