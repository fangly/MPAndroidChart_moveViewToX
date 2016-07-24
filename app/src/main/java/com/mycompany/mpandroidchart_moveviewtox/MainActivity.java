package com.mycompany.mpandroidchart_moveviewtox;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyGraph graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        graph = new MyGraph(this);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.main);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        rl.addView(graph, params);
        addStaticChartDataset();
        addDynamicChartDataset();
    }

    private float lastX = 0f;
    private float lastY = 0f;
    private Thread thread;
    private void addDynamicChartDataset() {
        // Create a dataset, then update it regularly
        if (thread != null)
            thread.interrupt();
        // Create a single Runnable that we will re-use many times
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Add a random value
                float x = lastX + (float) (Math.random() * 10);
                float y = lastY + (float) (Math.random() *  1);
                graph.addDynamicEntry(x, y);
                lastX = x;
                lastY = y;
            }
        };
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    runOnUiThread(runnable);
                    try {
                        Thread.sleep(100); // wait 100 ms between points
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void addStaticChartDataset() {
        float lastX = 0f;
        float lastY = 30f;
        List<Entry> values = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            float x = lastX + (float) (Math.random() * 15);
            float y = lastY + (float) (Math.random() *  2) - 0.5f;
            lastX = x;
            lastY = y;
            values.add(new Entry(x,y));
        }
        graph.createGetSet(values, 0, Color.RED);
    }

}
