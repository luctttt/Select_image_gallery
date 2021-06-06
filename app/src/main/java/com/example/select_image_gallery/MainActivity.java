package com.example.select_image_gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class MainActivity extends AppCompatActivity {

    Button btn_select_image;
    ImageView img ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_select_image = findViewById(R.id.btn_select_image);
        img = findViewById(R.id.img);

        btn_select_image.setOnClickListener(v->requestPermission());

    }

    private void requestPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() { // khi nguoi dung cho phep su dung
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }

    private void openImagePicker() {
        TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener() {
            @Override
            public void onImageSelected(Uri uri) {  // uri : su dung de set len imageView
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    img.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(MainActivity.this)        // tao anh
                . setOnImageSelectedListener(listener)
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
    }

}