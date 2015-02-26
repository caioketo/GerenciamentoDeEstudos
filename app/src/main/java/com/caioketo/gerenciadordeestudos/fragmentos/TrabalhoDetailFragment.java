package com.caioketo.gerenciadordeestudos.fragmentos;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.classes.AnexosTrabalho;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.adapters.CustomArrayAdapter;
import com.caioketo.gerenciadordeestudos.database.loaders.ImagensTrabalhoLoader;
import com.caioketo.gerenciadordeestudos.utils.CircularTextImageView;
import com.caioketo.gerenciadordeestudos.utils.Util;
import com.jess.ui.TwoWayAdapterView;
import com.jess.ui.TwoWayGridView;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import nl.changer.polypicker.CameraActivity;
import nl.changer.polypicker.GalleryActivity;
import nl.changer.polypicker.ImagePickerActivity;

/**
 * Created by Caio on 22/10/2014.
 */
public class TrabalhoDetailFragment extends Fragment implements ICFragment,
        LoaderManager.LoaderCallbacks<List<AnexosTrabalho>> {


    private static int INTENT_REQUEST_GET_PICTURES = 0;
    private static int INTENT_REQUEST_GET_CAMERA = 1;
    Context context;
    FloatingActionButton rightLowerButton;
    FloatingActionMenu rightLowerMenu;
    public int trabalhoId = 0;
    CustomArrayAdapter<AnexosTrabalho> mListAdapter;
    TwoWayGridView mImageGrid;
    ArrayList<AnexosTrabalho> mListImagens = new ArrayList<AnexosTrabalho>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trabalho, container, false);
        context = view.getContext();
        ImageView fabIconNew = new ImageView(getActivity());
        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light));
        rightLowerButton = new FloatingActionButton.Builder(getActivity())
                .setContentView(fabIconNew)
                .build();


        mImageGrid = (TwoWayGridView)view.findViewById(R.id.gridview);
        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(getActivity());
        ImageView imgPic = new ImageView(getActivity());
        ImageView imgCamera = new ImageView(getActivity());
        ImageView imgChecklist = new ImageView(getActivity());
        ImageView imgAnexo = new ImageView(getActivity());

        imgPic.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_picture_light));
        imgCamera.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera_light));
        imgChecklist.setImageDrawable(getResources().getDrawable(R.drawable.ic_toc_black_48dp));
        imgAnexo.setImageDrawable(getResources().getDrawable(R.drawable.ic_link_black_48dp));

        SubActionButton btnPic = rLSubBuilder.setContentView(imgPic).build();
        SubActionButton btnCamera = rLSubBuilder.setContentView(imgCamera).build();

        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GalleryActivity.class);
                startActivityForResult(intent, INTENT_REQUEST_GET_PICTURES);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CameraActivity.class);
                startActivityForResult(intent, INTENT_REQUEST_GET_CAMERA);
            }
        });

        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        rightLowerMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(btnPic)
                .addSubActionView(btnCamera)
                .addSubActionView(rLSubBuilder.setContentView(imgChecklist).build())
                .addSubActionView(rLSubBuilder.setContentView(imgAnexo).build())
                .attachTo(rightLowerButton)
                .build();

        mImageGrid.setOnItemClickListener(new TwoWayAdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(TwoWayAdapterView<?> parent, View view, int position, long id) {
                Util.showImageDialog(getActivity(), mListAdapter.getItem(position).getImagemBmp());
            }
        });

        //CircularTextImageView cirtext = (CircularTextImageView)view.findViewById(R.id.circtext);
        //cirtext.setText("TESTANDO O CIRC");
        //cirtext.setBorderColor(Color.BLACK);
        //cirtext.setBorderWidth(20);

        refresh();
        return view;
    }

    public void refresh() {
        if (getLoaderManager().getLoader(0) != null) {
            getLoaderManager().restartLoader(0, null, this);
        }
        else {
            getLoaderManager().initLoader(0, null, this);
        }

        mListAdapter = new CustomArrayAdapter<AnexosTrabalho>(getActivity(), R.layout.trabalho_imagem_row, mListImagens) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                    convertView = inflater.inflate(R.layout.trabalho_imagem_row, parent, false);
                }
                CircularImageView imageView = (CircularImageView) convertView.findViewById(R.id.circimg);
                imageView.setImageBitmap(values.get(position).getImagemBmp());

                return convertView;
            }
        };

        mImageGrid.setAdapter(mListAdapter);
        mImageGrid.setRowHeight(200);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == Activity.RESULT_OK) {
            Parcelable[] parcelableUris = null;
            if (requestCode == INTENT_REQUEST_GET_CAMERA) {
                parcelableUris = intent.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            }
            else if (requestCode == INTENT_REQUEST_GET_PICTURES) {
                parcelableUris = intent.getParcelableArrayExtra(CameraActivity.EXTRA_IMAGE_URIS);
            }

            if(parcelableUris == null) {
                return;
            }

            Uri[] uris = new Uri[parcelableUris.length];
            System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);

            for (int i = 0; i < uris.length; i++) {
                try {
                    AnexosTrabalho anexo = new AnexosTrabalho();
                    anexo.setImagem(Util.getBitmapAsByteArray(MediaStore.Images.Media.getBitmap(
                            getActivity().getContentResolver(), uris[i])));
                    anexo.setTrabalhoById(trabalhoId);
                    ContentValues values = anexo.toContent();
                    getActivity().getContentResolver().insert(CustomContentProvider.CONTENT_URI_ANEXOS_TRABALHO, values);
                }
                catch (Exception e) {
                    Log.e("GDE", e.getMessage());
                }
            }
            refresh();
        }
    }


    @Override
    public boolean fCreateOptionMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.global, menu);
        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.titulo_secao_trabalhos));
        return false;
    }

    @Override
    public boolean fOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean fOnBackPressed() {
        return false;
    }

    @Override
    public Loader<List<AnexosTrabalho>> onCreateLoader(int loaderID, Bundle bundle) {
        switch (loaderID) {
            case 0:
                return new ImagensTrabalhoLoader(getActivity(), trabalhoId);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<List<AnexosTrabalho>> listLoader, List<AnexosTrabalho> list) {
        mListImagens.clear();
        mListAdapter.notifyDataSetChanged();
        mListImagens.addAll(list);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<AnexosTrabalho>> listLoader) {
        mListAdapter.clear();
        mListAdapter.notifyDataSetChanged();
    }
}
