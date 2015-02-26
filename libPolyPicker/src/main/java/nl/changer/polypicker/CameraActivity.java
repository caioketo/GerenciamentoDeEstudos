package nl.changer.polypicker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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
public class CameraActivity extends Activity implements Camera.ShutterCallback, Camera.PictureCallback {

    public static final String EXTRA_IMAGE_URIS = "nl.changer.polypicker.extra.selected_image_uris";
    public static final String EXTRA_SELECTION_LIMIT = "nl.changer.polypicker.extra.selection_limit";

    private static final String TAG = CameraFragment.class.getSimpleName();

    Camera mCamera;
    ImageButton mTakePictureBtn;
    private Set<Image> mSelectedImages;
    private LinearLayout mSelectedImagesContainer;
    private TextView mSelectedImageEmptyMessage;
    private Button mCancelButtonView, mDoneButtonView;
    public ImageInternalFetcher mImageFetcher;
    private int mMaxSelectionsAllowed = Integer.MAX_VALUE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

        mSelectedImagesContainer = (LinearLayout) findViewById(R.id.selected_photos_container);
        mSelectedImageEmptyMessage = (TextView)findViewById(R.id.selected_photos_empty);
        mCancelButtonView = (Button) findViewById(R.id.action_btn_cancel);
        mDoneButtonView = (Button) findViewById(R.id.action_btn_done);

        mSelectedImages = new HashSet<Image>();
        mImageFetcher = new ImageInternalFetcher(this, 500);

        mCancelButtonView.setOnClickListener(onFinishGettingImages);
        mDoneButtonView.setOnClickListener(onFinishGettingImages);

        mCamera = Camera.open();
        CameraPreview preview = new CameraPreview(this, mCamera);

        ViewGroup previewHolder = (ViewGroup)findViewById(R.id.preview_holder);
        previewHolder.addView(preview);
        // take picture even when the preview is clicked.
        previewHolder.setOnClickListener(mOnTakePictureClicked);

        mTakePictureBtn = (ImageButton)findViewById(R.id.take_picture);
        mTakePictureBtn.setOnClickListener(mOnTakePictureClicked);

        mMaxSelectionsAllowed = getIntent().getIntExtra(EXTRA_SELECTION_LIMIT, Integer.MAX_VALUE);
    }


    View.OnClickListener mOnTakePictureClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mTakePictureBtn.isEnabled()){
                mTakePictureBtn.setEnabled(false);
                if(mCamera != null) {
                    mCamera.takePicture(CameraActivity.this, null, CameraActivity.this);
                }
            }
        }
    };

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        mTakePictureBtn.setEnabled(true);
        Bitmap picture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        // rotates the image to portrait
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        picture = Bitmap.createBitmap(picture, 0, 0, picture.getWidth(), picture.getHeight(), matrix, true);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), picture, "" , "");
        Uri contentUri = Uri.parse(path);
        Image image = getImageFromContentUri(contentUri);
        addImage(image);

        mCamera.startPreview();
    }


    public Image getImageFromContentUri(Uri contentUri) {

        String[] cols = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.ImageColumns.ORIENTATION
        };

        // can post image
        Cursor cursor = getContentResolver().query(contentUri, cols, null, null, null);
        cursor.moveToFirst();
        Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
        int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
        return new Image(uri, orientation);
    }

    @Override
    public void onShutter() {

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
