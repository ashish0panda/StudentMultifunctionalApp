package com.example.spectrumtask2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;


public class TimeTableViewer extends AppCompatActivity implements DownloadFile.Listener{

    private RemotePDFViewPager remotePDFViewPager;

    private PDFPagerAdapter pdfPagerAdapter;

    private ProgressBar progressBar;
    String url;

    private LinearLayout pdfLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_viewer);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //initialize the pdfLayout
        pdfLayout = findViewById(R.id.pdf_layout);

        //initialize the url variable
        url = "https://firebasestorage.googleapis.com/v0/b/spectrumtask2.appspot.com/o/timetable.pdf?alt=media&token=dfa6aa50-49ba-4213-8576-b4e6f5f04c08" ;

        //Create a RemotePDFViewPager object
        remotePDFViewPager = new RemotePDFViewPager(this, url, this);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        pdfPagerAdapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(pdfPagerAdapter);
        updateLayout();
        progressBar.setVisibility(View.GONE);
    }
    private void updateLayout() {

        pdfLayout.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }
    @Override
    public void onFailure(Exception e) {
        Toast.makeText(TimeTableViewer.this,"Some error Occurred",Toast.LENGTH_SHORT).show();
        Log.e("Error",Log.getStackTraceString(e));
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }
    public void onDestroy() {
        super.onDestroy();

        if (pdfPagerAdapter != null) {
            pdfPagerAdapter.close();
        }
    }
}