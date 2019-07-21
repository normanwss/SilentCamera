package com.sy.silent.camera.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SilentCameraHelper {

    private static SilentCameraHelper instance;

    private SilentCameraHelper(){}

    public static SilentCameraHelper getInstance(){
        return instance == null ? instance = new SilentCameraHelper() : instance;
    }

    public boolean checkPermission(Activity activity){
        boolean canWriteExternalStorage = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean canUseCamera = ContextCompat.checkSelfPermission(activity,Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
        return canWriteExternalStorage && canUseCamera;
    }

    public boolean hasPermission(Context context,String permissionName){
        return ContextCompat.checkSelfPermission(context,permissionName) == PackageManager.PERMISSION_GRANTED;
    }


}
