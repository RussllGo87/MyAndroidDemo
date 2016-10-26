package com.gong.android.demo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.gong.android.demo.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraIntentActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_TAKE_PHOTO = 2;

    public static final int REQUEST_VIDEO_CAPTURE = 3;

    private ImageView iv_demo;
    private VideoView view_video_demo;
    private Button btn_photo_capture;
    private Button btn_video_capture;

    String mCurrentPhotoPath;
    String mCurrentVideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);

        iv_demo = (ImageView) findViewById(R.id.iv_demo);
//        iv_demo.setImageDrawable(new HiveDrawable(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)));
        iv_demo.setImageResource(R.mipmap.ic_launcher);

        btn_photo_capture = (Button) findViewById(R.id.btn_photo_capture);
        btn_photo_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                }
                dispatchTakePictureIntent();
            }
        });

        view_video_demo = (VideoView) findViewById(R.id.view_video_demo);
        btn_video_capture = (Button) findViewById(R.id.btn_video_capture);
        btn_video_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakeVideoIntent();
            }
        });
    }

    private File createVideoFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String videoFileName = "VIDEO_" + timeStamp + "_";
        String storagePath = getExternalFilesDir(null).getAbsolutePath().concat(File.separator).concat("video");
        File storageDir = new File(storagePath);
        if(!storageDir.exists()) {
            storageDir.mkdir();
        }
        File video = new File(storageDir, videoFileName.concat(".mp4"));
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentVideoPath = video.getAbsolutePath();

        return video;
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {

            // Create the File where the video should go
            File videoFile = null;
            try {
                videoFile = createVideoFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("MainActivity", "Error occurred while creating the File");
            }
            //             Continue only if the File was successfully created
            if (videoFile != null) {
                Uri videoURI = Uri.fromFile(videoFile);
//                Uri videoURI = FileProvider.getUriForFile(this,
//                        "com.gong.android.demo.fileProvider",
//                        videoFile);

                Log.d("MainActivity", "dispatchTakeVideoIntent uri == " + videoURI.toString());
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
                takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                takeVideoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storagePath = getExternalFilesDir(null).getAbsolutePath().concat(File.separator).concat("images");
        File storageDir = new File(storagePath);
        if(!storageDir.exists()) {
            storageDir.mkdir();
        }
        File image = new File(storageDir, imageFileName.concat(".jpg"));
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("MainActivity", "Error occurred while creating the File");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.gong.android.demo.fileProvider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(mCurrentPhotoPath);
        Uri contentUri = FileProvider.getUriForFile(this,
                "com.gong.android.demo.fileProvider",
                file);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = iv_demo.getWidth();
        int targetH = iv_demo.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        iv_demo.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            iv_demo.setImageBitmap(imageBitmap);
        }

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            galleryAddPic();
            setPic();
        }

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            Log.d("MainActivity", "onActivityResult uri == " + videoUri.toString());
            view_video_demo.setVideoURI(videoUri);
            view_video_demo.start();
        }
    }
}
