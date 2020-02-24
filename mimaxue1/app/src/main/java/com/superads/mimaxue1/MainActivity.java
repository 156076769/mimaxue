package com.superads.mimaxue1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import com.superads.mimaxue1.ref1.edu.cedarville.cs.crypto.TinyE;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        long t1 = System.currentTimeMillis();
        Log.d("TAG", t1 + "");
        NativeEntry.get().teaEnc();
        long t2 = System.currentTimeMillis();
        Log.d("TAG", (t2 - t1) + "");
        NativeEntry.get().teaDec();
        long t3 = System.currentTimeMillis();
        Log.d("TAG", (t3 - t2) + "");

        long t21 = System.currentTimeMillis();
        Log.d("TAG", t21 + "");
        String _key = "a56babcd00000000ffffffffabcdef01";
        String _iv = "deadbeefcafefeed";
        File file = new File("/data/data/com.superads.mimaxue1/test/my_json_request.txt");
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            byte[] bytes1 = TeaJava.get().encrypt(_key, _iv, TinyE.Mode.CBC, fileContent);
            try (FileOutputStream stream = new FileOutputStream(new File("/data/data/com.superads.mimaxue1/test/my_json_request_enc1.txt"))) {
                stream.write(bytes1);
                stream.close();
            }
            long t22 = System.currentTimeMillis();
            Log.d("TAG", (t22 - t21) + "");
            byte[] bytes2 = TeaJava.get().decrypt(_key, _iv, TinyE.Mode.CBC, bytes1);
            try (FileOutputStream stream = new FileOutputStream(new File("/data/data/com.superads.mimaxue1/test/my_json_request_dec1.txt"))) {
                stream.write(bytes2);
                stream.close();
            }
            long t23 = System.currentTimeMillis();
            Log.d("TAG", (t23 - t22) + "");
        } catch (IOException ex){ex.printStackTrace();}
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


}

/*
2019-08-06 18:24:37.673 21440-21440/com.superads.mimaxue1 D/TAG: 1565087077673
2019-08-06 18:24:37.674 21440-21440/com.superads.mimaxue1 D/SUPER_ADS_NATIVE: before Java_com_superads_mimaxue1_NativeEntry_teaEnc
2019-08-06 18:24:37.696 21440-21440/com.superads.mimaxue1 D/SUPER_ADS_NATIVE: after Java_com_superads_mimaxue1_NativeEntry_teaEnc
2019-08-06 18:24:37.696 21440-21440/com.superads.mimaxue1 D/TAG: 23
2019-08-06 18:24:37.696 21440-21440/com.superads.mimaxue1 D/SUPER_ADS_NATIVE: before Java_com_superads_mimaxue1_NativeEntry_teaDec
2019-08-06 18:24:37.717 21440-21440/com.superads.mimaxue1 D/SUPER_ADS_NATIVE: Java_com_superads_mimaxue1_NativeEntry_teaDec
2019-08-06 18:24:37.717 21440-21440/com.superads.mimaxue1 D/TAG: 21

ECB mode
2019-08-06 18:24:37.717 21440-21440/com.superads.mimaxue1 D/TAG: 1565087077717
2019-08-06 18:24:37.774 21440-21440/com.superads.mimaxue1 I/System.out: performing encryption
2019-08-06 18:24:38.608 21440-21440/com.superads.mimaxue1 D/TAG: 891
2019-08-06 18:24:38.638 21440-21440/com.superads.mimaxue1 I/System.out: performing decryption
2019-08-06 18:24:39.541 21440-21440/com.superads.mimaxue1 D/TAG: 933
2019-08-06 18:24:39.559 21440-21533/com.superads.mimaxue1 D/OpenGLRen

CBC mode
2019-08-06 19:54:54.222 3883-3883/? D/TAG: 1565092494222
2019-08-06 19:54:54.284 3883-3883/? I/System.out: performing encryption
2019-08-06 19:54:55.178 3883-3883/com.superads.mimaxue1 D/TAG: 956
2019-08-06 19:54:55.228 3883-3883/com.superads.mimaxue1 I/System.out: performing decryption
2019-08-06 19:54:56.164 3883-3883/com.superads.mimaxue1 D/TAG: 986
*/
