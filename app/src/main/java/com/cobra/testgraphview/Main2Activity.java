package com.cobra.testgraphview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class Main2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        LineChartView lineChart = (LineChartView) findViewById(R.id.chart);


        List<PointValue>  entries = new ArrayList<>();
        entries.add(new PointValue(0, 4));
        entries.add(new PointValue(1, 8));
        entries.add(new PointValue(2, 6));
        entries.add(new PointValue(3, 2));
        entries.add(new PointValue(4, 8));
        entries.add(new PointValue(5, 4));
        entries.add(new PointValue(4, 4));



        Line line = new Line(MainActivity.getArray()).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();

        data.setLines(lines);
        LineChartView chart = new LineChartView(this);

        lineChart.setLineChartData(data);

        lineChart.setHorizontalScrollBarEnabled(true);
        lineChart.animate();
        lineChart.offsetTopAndBottom(10);










    }

}
