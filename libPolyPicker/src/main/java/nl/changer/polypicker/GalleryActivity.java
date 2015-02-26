package nl.changer.polypicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import nl.changer.polypicker.model.Image;
import nl.changer.polypicker.utils.ImageInternalFetcher;

/**
 * Created by Caio on 20/10/2014.
 */
public class GalleryActivity extends Activity {
    public static final String EXTRA_IMAGE_URIS = "nl.changer.polypicker.extra.selected_image_uris";
    private static final String TAG = GalleryFragment.class.getSimpleName();
    public static final String EXTRA_SELECTION_LIMIT = "nl.changer.polypicker.extra.selection_limit";

    private GridView mGalleryGridView;
    private ImageGalleryAdapter mGalleryAdapter;
    private Set<Image> mSelectedImages;
    private LinearLayout mSelectedImagesContainer;
    private TextView mSelectedImageEmptyMessage;
    private Button mCancelButtonView, mDoneButtonView;
    public ImageInternalFetcher mImageFetcher;

    private int mMaxSelectionsAllowed = Integer.MAX_VALUE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        mSelectedImagesContainer = (LinearLayout) findViewById(R.id.selected_photos_container);
        mSelectedImageEmptyMessage = (TextView)findViewById(R.id.selected_photos_empty);
        mCancelButtonView = (Button) findViewById(R.id.action_btn_cancel);
        mDoneButtonView = (Button) findViewById(R.id.action_btn_done);

        mSelectedImages = new HashSet<Image>();
        mImageFetcher = new ImageInternalFetcher(this, 500);

        mCancelButtonView.setOnClickListener(onFinishGettingImages);
        mDoneButtonView.setOnClickListener(onFinishGettingImages);

        mGalleryAdapter = new ImageGalleryAdapter(this);
        mGalleryGridView = (GridView)findViewById(R.id.gallery_grid);

        Cursor imageCursor = null;
        try {
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION};
            final String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";
            imageCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
            while (imageCursor.moveToNext()) {
                Uri uri = Uri.parse(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                int orientation = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
                mGalleryAdapter.add(new Image(uri, orientation));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(imageCursor != null && !imageCursor.isClosed()) {
                imageCursor.close();
            }
        }

        mGalleryGridView.setAdapter(mGalleryAdapter);
        mGalleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Image image = mGalleryAdapter.getItem(i);
                if (!containsImage(image)) {
                    addImage(image);
                } else {
                    removeImage(image);
                }

                // refresh the view to
                // mGalleryAdapter.getView(i, view, adapterView);
                mGalleryAdapter.notifyDataSetChanged();
            }
        });

        mMaxSelectionsAllowed = getIntent().getIntExtra(EXTRA_SELECTION_LIMIT, Integer.MAX_VALUE);
    }


    class ViewHolder {
        ImageView mThumbnail;
        // This is like storing too much data in memory.
        // find a better way to handle this
        Image mImage;
    }

    public class ImageGalleryAdapter extends ArrayAdapter<Image> {

        public ImageGalleryAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_gallery_thumbnail, null);
                holder = new ViewHolder();
                holder.mThumbnail = (ImageView) convertView.findViewById(R.id.thumbnail_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Image image = getItem(position);
            boolean isSelected = containsImage(image);

            ((FrameLayout) convertView).setForeground(isSelected ? getResources().getDrawable(R.drawable.gallery_photo_selected) : null);

            if (holder.mImage == null || !holder.mImage.equals(image)) {
                mImageFetcher.loadImage(image.mUri, holder.mThumbnail);
                holder.mImage = image;
            }
            return convertView;
        }
    }

    public boolean addImage(Image image) {

        if(mSelectedImages.size() == mMaxSelectionsAllowed) {
            Toast.makeText(this, mMaxSelectionsAllowed + " images selected already", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(mSelectedImages.add(image)) {
                View rootView = LayoutInflater.from(this).inflate(R.layout.list_item_selected_thumbnail, null);
                ImageView thumbnail = (ImageView) rootView.findViewById(R.id.selected_photo);
                rootView.setTag(image.mUri);
                mImageFetcher.loadImage(image.mUri, thumbnail);
                mSelectedImagesContainer.addView(rootView, 0);

                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
                thumbnail.setLayoutParams(new FrameLayout.LayoutParams(px, px));

                if(mSelectedImages.size() >= 1) {
                    mSelectedImagesContainer.setVisibility(View.VISIBLE);
                    mSelectedImageEmptyMessage.setVisibility(View.GONE);
                }
                return true;
            }
        }

        return false;
    }

    public boolean removeImage(Image image) {
        if(mSelectedImages.remove(image)) {
            for(int i = 0; i < mSelectedImagesContainer.getChildCount(); i++) {
                View childView = mSelectedImagesContainer.getChildAt(i);
                if(childView.getTag().equals(image.mUri)){
                    mSelectedImagesContainer.removeViewAt(i);
                    break;
                }
            }

            if(mSelectedImages.size() == 0) {
                mSelectedImagesContainer.setVisibility(View.GONE);
                mSelectedImageEmptyMessage.setVisibility(View.VISIBLE);
            }
            return true;
        }
        return false;
    }

    public boolean containsImage(Image image) {
        return mSelectedImages.contains(image);
    }

    private View.OnClickListener onFinishGettingImages = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.action_btn_done) {

                Uri[] uris = new Uri[mSelectedImages.size()];
                int i = 0;
                for(Image img : mSelectedImages) {
                    uris[i++] = Uri.parse("file://" + img.mUri.toString());
                }

                Intent intent = new Intent();
                intent.putExtra(EXTRA_IMAGE_URIS, uris);
                setResult(Activity.RESULT_OK, intent);
            } else if(view.getId() == R.id.action_btn_cancel) {
                setResult(Activity.RESULT_CANCELED);
            }
            finish();
        }
    };
}
