package com.example.fitnesstrackerproto;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;

public class BarGraph {

    public Intent getIntent(Context context)
    {

        ArrayList<Double> y = WorkoutLogActivity.getTimes();
        ArrayList<String> day = WorkoutLogActivity.getDates();
        CategorySeries series = new CategorySeries("Demo Bar Graph 1");
        for (int i = 0; i < y.size(); i++) {
            series.add("Bar " + (i + 1), y.get(i));
        }


        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series.toXYSeries());

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.setChartTitle("Total Time Exercised");
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setXTitle("Workouts per Day");
        mRenderer.setYTitle("Time Exercised (minutes)");
        mRenderer.setYLabelsAngle(-90);
        mRenderer.setBarSpacing(1.5);
        mRenderer.setAxesColor(Color.GREEN);
        mRenderer.setLabelsColor(Color.MAGENTA);
        mRenderer.setZoomEnabled(true, true);
        mRenderer.setLabelsTextSize(20);
        mRenderer.setAxisTitleTextSize(20);
        mRenderer.setXLabels(0);
        for (int i = 1; i <= y.size(); i++) {
            mRenderer.addXTextLabel(i, day.get(i - 1));
        }

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesSpacing((float) 0.5);
        mRenderer.setShowLegend(false);

        mRenderer.addSeriesRenderer(renderer);

        Intent intent = ChartFactory.getBarChartIntent(context, dataset,
                mRenderer, Type.DEFAULT);
        return intent;
    }

}
