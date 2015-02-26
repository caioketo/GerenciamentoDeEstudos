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
import android.os.Bundle;

import com.caioketo.gerenciadordeestudos.classes.Aula;
import com.caioketo.gerenciadordeestudos.classes.Prova;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.utils.authorization.AccountGeneral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caio on 07/11/2014.
 */
public class ProvasSyncAdapter {
    private final AccountManager mAccountManager;

    public ProvasSyncAdapter(Context context) {
        mAccountManager = AccountManager.get(context);
    }

    public void sync(Account account, Bundle extras, String authority,
                     ContentProviderClient provider, SyncResult syncResult){
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

            ParseComServerAccessor parseComService = new ParseComServerAccessor();

            // Get shows from remote
            List<Prova> remotes = parseComService.getProvas(authToken);

            // Get shows from local
            ArrayList<Prova> locals = new ArrayList<Prova>();
            Cursor cursor = provider.query(CustomContentProvider.CONTENT_URI_PROVA, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    locals.add(Prova.fromCursor(cursor));
                }
                cursor.close();
            }

            // See what Local shows are missing on Remote
            ArrayList<Prova> toRemote = new ArrayList<Prova>();
            for (Prova local : locals) {
                if (!remotes.contains(local))
                    toRemote.add(local);
            }

            // See what Remote shows are missing on Local
            ArrayList<Prova> toLocal = new ArrayList<Prova>();
            for (Prova remote : remotes) {
                if (!locals.contains(remote))
                    toLocal.add(remote);
            }

            if (toRemote.size() > 0) {
                // Updating remote tv shows
                for (Prova remote : toRemote) {
                    parseComService.putProva(authToken, userObjectId, remote);
                }
            }

            if (toLocal.size() > 0) {
                // Updating local tv shows
                int i = 0;
                ContentValues toLocalValues[] = new ContentValues[toLocal.size()];
                for (Prova local : toLocal) {
                    toLocalValues[i++] = local.toContent();
                }
                provider.bulkInsert(CustomContentProvider.CONTENT_URI_PROVA, toLocalValues);
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
