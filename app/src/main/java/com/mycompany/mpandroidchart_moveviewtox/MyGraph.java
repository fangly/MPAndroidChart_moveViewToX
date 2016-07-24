package com.mycompany.mpandroidchart_moveviewtox;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.Hashtable;
import java.util.List;

public class MyGraph extends LineChart {

    static final float X_DISTANCE_MINIMUM_WINDOW  =  100f;
    static final float X_DISTANCE_MAXIMUM_WINDOW  = 1000f;

    public MyGraph(Context ctx) {
        super(ctx);
        LineData data = new LineData();
        this.setData(data);
        this.invalidate();
    }

    public void addDynamicEntry(float x, float y) {
        LineData data = this.getData();
        if (data != null) {
            Integer index = createGetSet(null, 1, Color.BLUE);
            data.addEntry( new Entry(x,y), index );

            // Update graph view range
            this.notifyDataSetChanged();

            //this.setVisibleXRangeMinimum(X_DISTANCE_MINIMUM_WINDOW);
            //this.setVisibleXRangeMaximum(X_DISTANCE_MAXIMUM_WINDOW);

            this.moveViewToX( x - 200 );
            // Left aligned. The blue curve's right end is 200 to the right of left y axis.
            // Ok at the beginning, but wrong as soon as the view reaches end of the red line:
            // then the blue curve's right end sticks to the right of the view.
        }
    }


    public Integer createGetSet(List<Entry> values, int index, int color) {
        // Create a new dataset, with or without initial data points, and return its index.
        // If the dataset already exists, just return its index.
        LineData data = this.getData();
        if (data != null) {
            LineDataSet set = (LineDataSet) data.getDataSetByIndex(index);
            if (set == null) {
                set = new LineDataSet(values, null);
                set.setColor(color);
                set.setCircleColor(color);
                data.addDataSet(set);
                index = data.getIndexOfDataSet(set);
            }
        }
        return index;
    }


}
