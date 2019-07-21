package com.sy.silent.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sy.silent.camera.utils.SilentCameraHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button startCameraBtn;

    private SilentCameraHelper helper;

    private TextView alarmTextView;

    private CameraManager cameraManager;

    private CameraDevice cameraDevice;

    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            cameraDevice = cameraDevice;
        }

        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            cameraDevice.close();
            MainActivity.this.cameraDevice = null;
        }

        @Override
        public void onError(CameraDevice cameraDevice, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        this.startCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!helper.hasPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    alarmTextView.setText("* Please grant write/read external storage permission.");
                    alarmTextView.setVisibility(View.VISIBLE);
                    return;
                }else if(!helper.hasPermission(getApplicationContext(),Manifest.permission.CAMERA)){
                    alarmTextView.setText("* Please grant camera permission.");
                    alarmTextView.setVisibility(View.VISIBLE);
                    return;
                }else {
                    alarmTextView.setText("");
                    alarmTextView.setVisibility(View.INVISIBLE);
                }
                takePhoto();
            }
        });
    }


    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStoragePublicDirectory("Download") + "/" + System.currentTimeMillis() + ".jpg");
        Uri uri = FileProvider.getUriForFile(MainActivity.this,"com.sy.silent.fileprovider",file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,1);
    }

    private void init(){
        helper = SilentCameraHelper.getInstance();
        this.startCameraBtn = (Button)this.findViewById(R.id.startCameraBtn);
        this.alarmTextView = (TextView)this.findViewById(R.id.alarmTextView);
        this.cameraManager = (CameraManager) getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
