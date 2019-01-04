package com.bigademo.updatedemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private Button btnPatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPatch = findViewById(R.id.btn_patch);
        btnPatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                } else {
                    doBspatch();
//                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                }

//
            }
        });
    }

    private void doBspatch() {
        final File destApk = new File(Environment.getExternalStorageDirectory(), "dest.apk");
        final File patch = new File(Environment.getExternalStorageDirectory(), "PATCH.patch");

        //一定要检查文件都存在
      int result=  PatchUtils.bspatch(ApkExtract.extract(this),
                destApk.getAbsolutePath(),
                patch.getAbsolutePath());
        Log.e("info","patch result   "+result);
        if (destApk.exists()) {
            ApkExtract.install(this, destApk.getAbsolutePath());
        }
    }

}
