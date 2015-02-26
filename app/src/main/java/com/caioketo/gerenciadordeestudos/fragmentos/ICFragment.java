package com.caioketo.gerenciadordeestudos.fragmentos;

import android.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Caio on 19/09/2014.
 */
public interface ICFragment {
    public boolean fCreateOptionMenu(Menu menu);
    public boolean fOptionsItemSelected(MenuItem item);
    public boolean fOnBackPressed();
}
