package com.superads.mimaxue1;

import android.os.Environment;
import android.util.Log;

public class NativeEntry {

    private static NativeEntry instance = new NativeEntry();

    private NativeEntry() {
//        File f = new File("/sdcard/my_json_request.txt");
//        if(f.exists()) {
//            Log.d("TAG", "/sdcard/my_json_request.txt exits");
//        }
//        Log.d("TAG", Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    public static NativeEntry get() {return instance;}

    public native void teaEnc();

    public native void teaDec();
}
