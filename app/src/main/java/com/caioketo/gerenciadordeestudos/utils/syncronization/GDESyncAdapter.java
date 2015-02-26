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
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.utils.authorization.AccountGeneral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caio on 07/11/2014.
 */
public class GDESyncAdapter extends AbstractThreadedSyncAdapter {
    private final AulasSyncAdapter mAulasSyncAdapter;
    private final GenericSync mGenericSync;
    private final ProvasSyncAdapter mProvasSyncAdapter;

    public GDESyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mAulasSyncAdapter = new AulasSyncAdapter(context);
        mGenericSync = new GenericSync(context);
        mProvasSyncAdapter = new ProvasSyncAdapter(context);
    }


    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult){
        mGenericSync.sync(account, extras, authority, provider, syncResult, ParseComServerAccessor.MATERIA_TYPE);
        mGenericSync.sync(account, extras, authority, provider, syncResult, ParseComServerAccessor.AULA_TYPE);
        mGenericSync.sync(account, extras, authority, provider, syncResult, ParseComServerAccessor.PROVA_TYPE);
    }
}
