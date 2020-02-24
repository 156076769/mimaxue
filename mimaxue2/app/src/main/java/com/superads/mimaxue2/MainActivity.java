package com.superads.mimaxue2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

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

        try {
            long t1 = System.currentTimeMillis();
            Log.d("TAG", t1 + "");
            String in = readFile("/data/data/com.superads.mimaxue2/test/my_json_request.txt");
            //aes/ecb/填充模式
            String r1 = NativeEntry.get().getAESEncrypt(in);
            long t2 = System.currentTimeMillis();
            Log.d("TAG", (t2 - t1) + "");
            String r2 = NativeEntry.get().getAESDecrypt(r1);
            PrintWriter out = new PrintWriter("/data/data/com.superads.mimaxue2/test/my_json_request_dec.txt");
            out.println(r2);
            out.flush();
            long t3 = System.currentTimeMillis();
            Log.d("TAG", (t3 - t2) + "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    private String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }
}

/*
2019-08-06 17:23:32.457 7069-7069/com.superads.mimaxue2 D/TAG: 1565083412456
2019-08-06 17:23:33.415 7069-7069/com.superads.mimaxue2 D/TAG: 959
2019-08-06 17:23:37.428 7069-7081/com.superads.mimaxue2 I/zygote64: Do partial code cache collection, code=61KB, data=40KB
2019-08-06 17:23:37.429 7069-7081/com.superads.mimaxue2 I/zygote64: After code cache collection, code=60KB, data=40KB
2019-08-06 17:23:37.429 7069-7081/com.superads.mimaxue2 I/zygote64: Increasing code cache capacity to 256KB
2019-08-06 17:23:37.471 7069-7069/com.superads.mimaxue2 D/TAG: 4056
 */