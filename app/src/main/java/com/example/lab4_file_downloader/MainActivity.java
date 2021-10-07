package com.example.lab4_file_downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button start;
    private volatile boolean stopThread = false;
    private TextView downloadProgress;
    private int currProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start_button);
        downloadProgress = findViewById(R.id.downloadProgress);
    }

    public void mockFileDownloader() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                start.setText("Downloading...");
            }
        });

        for (int progress = 0; progress <= 100; progress += 10) {
            if(stopThread) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        start.setText("Start");
                        downloadProgress.setText("");
                    }
                });
                return;
            }
            Log.d(TAG, "Download Progress: " + progress + "%");
            currProgress = progress;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    downloadProgress.setText("Download Progress: " + currProgress + "%");
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "DOWNLOAD COMPLETE!!!");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                start.setText("Start");
                downloadProgress.setText("");
            }
        });
    }

    public void startDownload(View view){
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view){
        stopThread = true;
    }

    class ExampleRunnable implements Runnable {
        @Override
        public void run() {
            mockFileDownloader();
        }
    }
}

