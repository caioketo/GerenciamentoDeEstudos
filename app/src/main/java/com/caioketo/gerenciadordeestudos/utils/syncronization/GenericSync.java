package com.caioketo.gerenciadordeestudos.utils.syncronization;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;

import com.caioketo.gerenciadordeestudos.classes.Aula;
import com.caioketo.gerenciadordeestudos.classes.Base;
import com.caioketo.gerenciadordeestudos.classes.Materia;
import com.caioketo.gerenciadordeestudos.classes.Prova;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.utils.Util;
import com.caioketo.gerenciadordeestudos.utils.authorization.AccountGeneral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caio on 10/11/2014.
 */
public class GenericSync {
    private final AccountManager mAccountManager;
    ParseComServerAccessor parseComService = new ParseComServerAccessor();

    public GenericSync(Context context) {
        mAccountManager = AccountManager.get(context);

    }

    public <T extends Base> List<T> getRemote(String auth, int type) {
        try {
            return parseComService.get(auth, type);
        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }

    public <T extends Base> T fromCursor(Cursor cursor, int type) {
        switch (type) {
            case ParseComServerAccessor.MATERIA_TYPE:
                return (T)Materia.fromCursor(cursor);
            case ParseComServerAccessor.AULA_TYPE:
                return (T)Aula.fromCursor(cursor);
            case ParseComServerAccessor.PROVA_TYPE:
                return (T)Prova.fromCursor(cursor);
            default:
                return null;
        }
    }

    public Uri getUri(int type) {
        switch (type) {
            case ParseComServerAccessor.MATERIA_TYPE:
                return CustomContentProvider.CONTENT_URI_MATERIA;
            case ParseComServerAccessor.AULA_TYPE:
                return CustomContentProvider.CONTENT_URI_AULA;
            case ParseComServerAccessor.PROVA_TYPE:
                return CustomContentProvider.CONTENT_URI_PROVA;
            default:
                return null;
        }
    }

    public <T extends Base> List<T> getLocal(ContentProviderClient provider, int type) {
        ArrayList<T> result = new ArrayList<T>();
        Cursor cursor = null;

        try {
            cursor = provider.query(getUri(type), null, null, null, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                result.add((T)fromCursor(cursor, type));
            }
            cursor.close();
        }

        return result;
    }

    public <T extends Base> void sync(Account account, Bundle extras, String authority,
                     ContentProviderClient provider, SyncResult syncResult, int type){
        Util.provider = provider;
        // Building a print of the extras we got
        StringBuilder sb = new StringBuilder();
        if (extras != null) {
            for (String key : extras.keySet()) {
                sb.append(key + "[" + extras.get(key) + "] ");
            }
        }

        try {
            // Get the auth token for the current account and
            // the userObjectId, needed for creating items on Parse.com account
            String authToken = mAccountManager.blockingGetAuthToken(account,
                    AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
            String userObjectId = mAccountManager.getUserData(account,
                    AccountGeneral.USERDATA_USER_OBJ_ID);



            // Get shows from remote
            List<T> remotes = getRemote(authToken, type);

            // Get shows from local
            List<T> locals = getLocal(provider, type);

            // See what Local shows are missing on Remote
            ArrayList<T> toRemote = new ArrayList<T>();
            for (T local : locals) {
                if (!remotes.contains(local))
                    toRemote.add(local);
            }

            // See what Remote shows are missing on Local
            ArrayList<T> toLocal = new ArrayList<T>();
            for (T remote : remotes) {
                if (!locals.contains(remote))
                    toLocal.add(remote);
            }

            if (toRemote.size() > 0) {
                // Updating remote tv shows
                for (T remote : toRemote) {
                    parseComService.put(authToken, userObjectId, remote, type);
                }
            }

            if (toLocal.size() > 0) {
                // Updating local tv shows
                int i = 0;
                ContentValues toLocalValues[] = new ContentValues[toLocal.size()];
                for (T local : toLocal) {
                    toLocalValues[i++] = local.toContent();
                }
                provider.bulkInsert(getUri(type), toLocalValues);
            }
        } catch (OperationCanceledException e) {
            e.printStackTrace();
        } catch (IOException e) {
            syncResult.stats.numIoExceptions++;
            e.printStackTrace();
        } catch (AuthenticatorException e) {
            syncResult.stats.numAuthExceptions++;
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
