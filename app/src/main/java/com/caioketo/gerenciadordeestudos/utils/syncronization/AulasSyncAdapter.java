package com.caioketo.gerenciadordeestudos.utils.syncronization;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;

import com.caioketo.gerenciadordeestudos.classes.Aula;
import com.caioketo.gerenciadordeestudos.classes.Materia;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.utils.authorization.AccountGeneral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caio on 07/11/2014.
 */
public class AulasSyncAdapter {
    private final AccountManager mAccountManager;

    public AulasSyncAdapter(Context context) {
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
            List<Aula> remoteAulas = parseComService.getAulas(authToken);

            // Get shows from local
            ArrayList<Aula> localAulas = new ArrayList<Aula>();
            Cursor cursor = provider.query(CustomContentProvider.CONTENT_URI_AULA, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    localAulas.add(Aula.fromCursor(cursor));
                }
                cursor.close();
            }

            // See what Local shows are missing on Remote
            ArrayList<Aula> aulassToRemote = new ArrayList<Aula>();
            for (Aula localAula : localAulas) {
                if (!remoteAulas.contains(localAula))
                    aulassToRemote.add(localAula);
            }

            // See what Remote shows are missing on Local
            ArrayList<Aula> aulasToLocal = new ArrayList<Aula>();
            for (Aula remoteAula : remoteAulas) {
                if (!localAulas.contains(remoteAula))
                    aulasToLocal.add(remoteAula);
            }

            if (aulassToRemote.size() > 0) {
                // Updating remote tv shows
                for (Aula remoteAula : aulassToRemote) {
                    parseComService.putAula(authToken, userObjectId, remoteAula);
                }
            }

            if (aulasToLocal.size() > 0) {
                // Updating local tv shows
                int i = 0;
                ContentValues aulasToLocalValues[] = new ContentValues[aulasToLocal.size()];
                for (Aula localAula : aulasToLocal) {
                    aulasToLocalValues[i++] = localAula.toContent();
                }
                provider.bulkInsert(CustomContentProvider.CONTENT_URI_AULA, aulasToLocalValues);
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
