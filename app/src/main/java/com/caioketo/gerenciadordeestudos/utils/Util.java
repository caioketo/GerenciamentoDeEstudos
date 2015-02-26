package com.caioketo.gerenciadordeestudos.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.RemoteException;
import android.util.TypedValue;
import android.view.Display;
import android.view.Window;
import android.widget.ImageView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.caioketo.gerenciadordeestudos.HomeActivity;
import com.caioketo.gerenciadordeestudos.MainActivity;
import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.classes.Materia;
import com.caioketo.gerenciadordeestudos.classes.Trabalho;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.TabelaMateria;
import com.caioketo.gerenciadordeestudos.database.TabelaTrabalho;
import com.tyczj.extendedcalendarview.Event;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Caio on 17/09/2014.
 */
public class Util {
    public static ContentProviderClient provider;

    public static String[] concat(String[]a,String[]b){
        if (a == null) return b;
        if (b == null) return a;
        String[] r = new String[a.length+b.length];
        System.arraycopy(a, 0, r, 0, a.length);
        System.arraycopy(b, 0, r, a.length, b.length);
        return r;
    }

    public static long D2MS(int month, int day, int year, int hour, int minute, int seconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0);
        c.set(year, month, day, hour, minute, seconds);

        return c.getTimeInMillis();
    }

    public static Materia getMateriaById(int id) {
        String[] projection = { TabelaMateria.COLUMN_ID, TabelaMateria.COLUMN_DESCRICAO, TabelaMateria.COLUMN_COR };
        Cursor cursor = null;
        try {
            cursor = provider.query(Uri.parse(CustomContentProvider.CONTENT_URI_MATERIA + "/" + id), projection, null, null,
                    null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (cursor != null) {
            cursor.moveToFirst();
            Materia materia = new Materia();
            materia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_ID)));
            materia.setDescricao(cursor.getString(cursor
                    .getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO)));
            materia.setCor(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_COR)));
            cursor.close();
            return materia;
        }
        return null;
    }

    public static Trabalho getTrabalhoById(int id) {
        String[] projection = { TabelaTrabalho.COLUMN_ID, TabelaTrabalho.COLUMN_ASSUNTO, TabelaTrabalho.COLUMN_CONTEUDO,
            TabelaTrabalho.COLUMN_DATA_ENTREGA, TabelaTrabalho.COLUMN_MATERIA_ID };
        Cursor cursor = MainActivity.instance.getContentResolver().query(Uri.parse(CustomContentProvider.CONTENT_URI_TRABALHO + "/" + id), projection, null, null,
                null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Trabalho trabalho = new Trabalho();
            trabalho.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaTrabalho.COLUMN_ID)));
            trabalho.setConteudo(cursor.getString(cursor
                    .getColumnIndexOrThrow(TabelaTrabalho.COLUMN_CONTEUDO)));
            trabalho.setAssunto(cursor.getString(cursor
                    .getColumnIndexOrThrow(TabelaTrabalho.COLUMN_ASSUNTO)));
            trabalho.setDataEntrega(cursor.getLong(cursor
                    .getColumnIndexOrThrow(TabelaTrabalho.COLUMN_DATA_ENTREGA)));
            trabalho.setMateriaById(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaTrabalho.COLUMN_MATERIA_ID)));
            cursor.close();
            return trabalho;
        }
        return null;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap getBitmapImagem(byte[] bytes) {
        ByteArrayInputStream imageStream = new ByteArrayInputStream(bytes);
        return BitmapFactory.decodeStream(imageStream);
    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                MainActivity.instance.getResources().getDisplayMetrics());
    }

    public static SwipeMenuCreator genMenuCreator() {
        return new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        MainActivity.instance.getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(Util.dp2px(110));
                // set item title
                openItem.setTitle("Visualizar");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        MainActivity.instance.getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(Util.dp2px(110));
                // set a ic_launcher
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
    }

    public static void showImageDialog(Activity activity, Bitmap bitmap) {
        // Get screen size
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();

        if (bitmapHeight > (screenHeight - 250) || bitmapWidth > (screenWidth - 250)) {
            if (bitmapWidth - (screenWidth - 250) >  bitmapHeight - (screenHeight - 250)) {
                float dif = (float)bitmapHeight / (float)bitmapWidth;
                bitmapWidth = (screenWidth - 250);
                bitmapHeight = (int)(dif * bitmapWidth);
            }
            else {
                float dif = (float)bitmapWidth / (float)bitmapHeight;
                bitmapHeight = (screenHeight - 250);
                bitmapWidth = (int)(dif * bitmapHeight);
            }
        }

        //while(bitmapHeight > (screenHeight - 100) || bitmapWidth > (screenWidth - 100)) {
            //bitmapHeight = bitmapHeight / 2;
            //bitmapWidth = bitmapWidth / 2;
        //}
        BitmapDrawable resizedBitmap = new BitmapDrawable(activity.getResources(), Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, false));

// Create dialog
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.thumbnail);

        TouchImageView image = (TouchImageView) dialog.findViewById(R.id.imageview);

// !!! Do here setBackground() instead of setImageDrawable() !!! //
        image.setBackground(resizedBitmap);

// Without this line there is a very small border around the image (1px)
// In my opinion it looks much better without it, so the choice is up to you.
        dialog.getWindow().setBackgroundDrawable(null);

// Show the dialog
        dialog.show();
    }
}
