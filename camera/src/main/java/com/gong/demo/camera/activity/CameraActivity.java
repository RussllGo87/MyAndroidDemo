package com.gong.demo.camera.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gong.demo.camera.R;
import com.gong.demo.camera.view.CameraPreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {



    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private final int MY_PERMISSIONS_REQUEST_STORAGE = 2;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 3;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private FrameLayout preview;
    private Button btn_photo_capture;

    private Camera mCamera;
    private CameraPreview mPreview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        preview = (FrameLayout) findViewById(R.id.camera_preview);
        btn_photo_capture = (Button) findViewById(R.id.btn_photo_capture);
        btn_photo_capture.setVisibility(View.GONE);
        btn_photo_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, mPicture);
            }
        });

        // 检查设备是否存在已声明的相机特性
        if(!checkCameraHardware(getApplicationContext())) {
            finish();
        }
    }

    private void checkPermission(String permission, int permissionReq) {

        if(ContextCompat.checkSelfPermission(CameraActivity.this,
                permission) != PackageManager.PERMISSION_GRANTED) {
            // The app has requested this permission previously and the user denied the request.
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(CameraActivity.this,
                    permission)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                // 提示需要权限后,重新请求
//                Toast.makeText(getApplicationContext(), "解释为何需要该权限", Toast.LENGTH_SHORT).show();
                if(permissionReq == MY_PERMISSIONS_REQUEST_CAMERA) {
                    Toast.makeText(getApplicationContext(), "拍照和录像需要相机权限", Toast.LENGTH_SHORT).show();
                }

                if(permissionReq == MY_PERMISSIONS_REQUEST_STORAGE) {
                    Toast.makeText(getApplicationContext(), "保存拍照和录像文件需要存储权限", Toast.LENGTH_SHORT).show();
                }

                if(permissionReq == MY_PERMISSIONS_REQUEST_LOCATION) {
                    Toast.makeText(getApplicationContext(), "照片添加位置信息需要位置权限", Toast.LENGTH_SHORT).show();
                }

                ActivityCompat.requestPermissions(CameraActivity.this,
                        new String[]{permission},
                        permissionReq);

            } else {
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(CameraActivity.this,
                        new String[]{permission},
                        permissionReq);
            }
        } else {
            if(permissionReq == MY_PERMISSIONS_REQUEST_CAMERA) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_STORAGE);
            }

            if(permissionReq == MY_PERMISSIONS_REQUEST_STORAGE) {
                checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, MY_PERMISSIONS_REQUEST_LOCATION);
            }

            if(permissionReq == MY_PERMISSIONS_REQUEST_LOCATION) {
                addCamera();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_STORAGE);

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    btn_photo_capture.setVisibility(View.GONE);
                }
            }
            break;
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, MY_PERMISSIONS_REQUEST_LOCATION);

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    btn_photo_capture.setVisibility(View.GONE);
                }
            }
            break;
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    addCamera();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    btn_photo_capture.setVisibility(View.GONE);
                }
            }
            break;
        }
    }

    private void addCamera() {
        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        preview.addView(mPreview);

        btn_photo_capture.setVisibility(View.VISIBLE);
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }



    @Override
    protected void onStart() {
        super.onStart();

        if(mCamera == null) {
            // 相关权限检查
            checkPermission(Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            try {
                File pictureFile = createImageFile();
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d("PictureCallback", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("PictureCallback", "Error accessing file: " + e.getMessage());
            } finally {
                mPreview.resumePreview();
            }
        }
    };

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storagePath = getExternalFilesDir(null).getAbsolutePath().concat(File.separator).concat("images");
        File storageDir = new File(storagePath);
        if(!storageDir.exists()) {
            storageDir.mkdir();
        }
//        File image = new File(storageDir, imageFileName.concat(".jpg"));
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
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

        return video;
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
