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

import com.caioketo.gerenciadordeestudos.classes.Materia;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.utils.authorization.AccountGeneral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caio on 06/11/2014.
 */
public class MateriasSyncAdapter {
    private final AccountManager mAccountManager;

    public MateriasSyncAdapter(Context context) {
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
            List<Materia> remoteMaterias = parseComService.getMaterias(authToken);

            // Get shows from local
            ArrayList<Materia> localMaterias = new ArrayList<Materia>();
            Cursor curMaterias = provider.query(CustomContentProvider.CONTENT_URI_MATERIA, null, null, null, null);
            if (curMaterias != null) {
                while (curMaterias.moveToNext()) {
                    localMaterias.add(Materia.fromCursor(curMaterias));
                }
                curMaterias.close();
            }

            // See what Local shows are missing on Remote
            ArrayList<Materia> materiasToRemote = new ArrayList<Materia>();
            for (Materia localMateria : localMaterias) {
                if (!remoteMaterias.contains(localMateria))
                    materiasToRemote.add(localMateria);
            }

            // See what Remote shows are missing on Local
            ArrayList<Materia> materiasToLocal = new ArrayList<Materia>();
            for (Materia remoteMateria : remoteMaterias) {
                if (!localMaterias.contains(remoteMateria))
                    materiasToLocal.add(remoteMateria);
            }

            if (materiasToRemote.size() > 0) {
                // Updating remote tv shows
                for (Materia remoteMateria : materiasToRemote) {
                    parseComService.putMateria(authToken, userObjectId, remoteMateria);
                }
            }

            if (materiasToLocal.size() > 0) {
                // Updating local tv shows
                int i = 0;
                ContentValues materiasToLocalValues[] = new ContentValues[materiasToLocal.size()];
                for (Materia localMateria : materiasToLocal) {
                    materiasToLocalValues[i++] = localMateria.toContent();
                }
                provider.bulkInsert(CustomContentProvider.CONTENT_URI_MATERIA, materiasToLocalValues);
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
