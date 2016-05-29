package com.cobra.testgraphview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import lecho.lib.hellocharts.model.PointValue;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import lecho.lib.hellocharts.model.PointValue;

public class MainActivity extends AppCompatActivity {


    static ArrayList<PointValue> entries;
    static ArrayList<PointValue> entries1;
    Button Forward, Reverse, Right, Left, Graph, startStop;
    long startTime = 0;
    double finalTime;
    long endTime;
    double gradient = 1;
    double distance = 0;
    String last;
    int speed = 10;
    double lastX = 0;
    double lastY = 0;
    double lastX2 = 0;
    double lastY2 = 0;
    double angle = 90;
    double angleTurned = 0;
    static double maxY = 0;
    static double maxX = 0;
    static double minY = 0;
    static double minX = 0;
    boolean isPressed = false;
    static double secAngle = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Forward = (Button) findViewById(R.id.btForward);
        Reverse = (Button) findViewById(R.id.btReverse);
        Right = (Button) findViewById(R.id.btRight);
        Left = (Button) findViewById(R.id.btLeft);
        startStop = (Button) findViewById(R.id.btStart);
        Graph = (Button) findViewById(R.id.btGraph);
        entries = new ArrayList<>();

        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressed == false){
                    startStop.setTextColor(Color.WHITE);
                    startStop.setBackgroundColor(Color.RED);
                    isPressed = true;
                }
                else if (isPressed == true){
                    startStop.setTextColor(Color.WHITE);
                    startStop.setBackgroundColor(Color.BLACK);
                    isPressed = false;
                }

            }
        });

        Graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int listSize = entries.size();

                for (int i = 0; i<listSize; i++){
                    Log.i("Data: ", entries.get(i).toString());
                }
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

            }
        });


        //This is just for commit purposes
        Forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startTime = System.currentTimeMillis();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    endTime = System.currentTimeMillis();
                    finalTime = (endTime - startTime) / 1000;
                    distance = (finalTime * speed);
                    if (isEmpty()) {
                        entries.add(new PointValue(1, (float) (distance / 10)));
                        lastX = 1;
                        lastY = distance / 10;
                        setMinMax(distance / 10, 1);
                        Log.d("I am here", "inside the if loop");

                    } else {
                        Log.d("in", "first if");
                        double newX1 = 0;
                        double newX2 = 0;
                        double newY1 = 0;
                        double newY2 = 0;

                        newX1 = lastX + Math.sqrt(((distance / 10) * (distance / 10)) / (1 + (gradient * gradient)));
                        newX2 = lastX - Math.sqrt(((distance / 10) * (distance / 10)) / (1 + (gradient * gradient)));

                        newY1 = (gradient * (newX1 - lastX)) + lastY;
                        newY2 = (gradient * (newX2 - lastX)) + lastY;

                        if (angle < 90) {
                            Log.d("in", "90");
                            if (secAngle > 0 && secAngle < 90){
                                if (newY1 > lastY && newX1 > lastX) {
                                    Log.d("in", "else1");
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                } else {
                                    Log.d("in", "else2");
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                            }
                            else {
                                if (newY1 < lastY && newX1 < lastX){
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                }
                                else {
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                            }

                            }

                        else if (angle > 90 && angle <= 180) {
                            Log.d("in", "angle 180");
                            if (secAngle > 90 && secAngle < 180){
                                if (newY1 < lastY && newX1 > lastX){
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                }
                                else {
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                            }
                            else {
                                if (newY1 > lastY && newX1 < lastX){
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                }
                                else {
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                            }
                            }
                        }
                }
                return false;
            }
        });

        Right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startTime = System.currentTimeMillis();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    endTime = System.currentTimeMillis();
                    finalTime = (endTime - startTime);
                    finalTime = 3 % (finalTime / 1000);
                    angleTurned = (360 / 3) * finalTime;

                    if (secAngle + angleTurned < 360){
                        secAngle += angleTurned;
                    }
                    else if ((secAngle + angleTurned) > 360){
                        secAngle = (secAngle + angleTurned) - 360;
                    }
                    if (angle - angleTurned < 0) {
                        angleTurned -= angle;
                        angle = 180;
                    }
                    angle -= angleTurned;
                    double radAngle = Math.toRadians(angle);

                    gradient = Math.tan(radAngle);
                    Log.d("start", "time " + startTime);
                    Log.d("end", "time " + endTime);
                    Log.d("final", "time " + finalTime);
                    Log.d("angle ", "turned " + angleTurned);
                    Log.d("angle", "angle is " + angle);
                    Log.d("gradient", "gradient " + gradient);
                    Log.d("inside", "right loop");
                    Log.d("this is", "int here " + finalTime);
                }
                return false;
            }
        });


        Left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startTime = System.currentTimeMillis();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    endTime = System.currentTimeMillis();
                    finalTime = (endTime - startTime);
                    finalTime = 3 % (finalTime / 1000);
                    angleTurned = (360 / 3) * finalTime;

                    if (secAngle - angleTurned < 0){
                        secAngle = (angleTurned - secAngle);
                    }
                    else if (secAngle - angleTurned > 0){
                        secAngle -= angleTurned;
                    }

                    if (angle + angleTurned > 180){
                        angleTurned += angle - 180;
                        angle = angleTurned;
                    }
                    else {
                        angle += angleTurned;
                    }

                    double radAngle = Math.toRadians(angle);
                    gradient = Math.tan(radAngle);
                    Log.d("start", "time " + startTime);
                    Log.d("end", "time " + endTime);
                    Log.d("final", "time " + finalTime);
                    Log.d("angle ", "turned " + angleTurned);
                    Log.d("angle", "angle is " + angle);
                    Log.d("gradient", "gradient " + gradient);
                    Log.d("inside", "right loop");
                    Log.d("this is", "int here " + finalTime);
                }
                return false;
            }
        });

        Reverse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startTime = System.currentTimeMillis();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    endTime = System.currentTimeMillis();
                    finalTime = (endTime - startTime) / 1000;
                    distance = (finalTime * speed);
                    if (isEmpty()) {
                        entries.add(new PointValue(1, (float) (-1 * (distance / 10))));
                        shiftLast(1, (-1 * (distance / 10)));
                        setMinMax(1, (-1 * (distance/10)));
                    } else {
                        double newX1 = 0;
                        double newX2 = 0;
                        double newY1 = 0;
                        double newY2 = 0;

                        newX1 = lastX + Math.sqrt((distance * distance) / (1 + (gradient * gradient)));
                        newX2 = lastX - Math.sqrt((distance * distance) / (1 + (gradient * gradient)));

                        newY1 = (gradient * (newX1 - lastX)) + lastY;
                        newY2 = (gradient * (newX2 - lastX)) + lastY;

                        if (angle < 90) {
                            Log.d("in", "90");
                            if (secAngle > 0 && secAngle < 90){
                                if (newY1 < lastY && newX1 < lastX) {
                                    Log.d("in", "else1");
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                } else {
                                    Log.d("in", "else2");
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                            }
                            else {
                                if (newY1 > lastY && newX1 > lastX){
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                }
                                else {
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                            }

                        }

                        else if (angle > 90 && angle <= 180) {
                            Log.d("in", "angle 180");
                            if (secAngle > 90 && secAngle < 180){
                                if (newY1 > lastY && newX1 < lastX){
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                }
                                else {
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                            }
                            else {
                                if (newY1 < lastY && newX1 > lastX){
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                }
                                else {
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                                }
                        }

                    }
                }
                return false;
            }
        });

    }

    public static void setMinMax(double x, double y){
        if (y > maxY){
            maxY = y;
        }
        else if (y < minY){
            minY = y;
        }
        if (x > maxX){
            maxX = x;
        }
        else if (x < minX){
            minX = x;
        }
    }

    public static double getMaxY(){
        return maxY;
    }
    public static double getMaxX(){
        return maxX;
    }
    public static double getMinY(){
        return minY;
    }
    public static double getMinX(){
        return minX;
    }

    public void shiftLast(double newX, double newY){
        lastX2 = lastX;
        lastY2 = lastY;

        lastX = newX;
        lastY = newY;
    }

    public boolean decreasingX(){
        if(lastX < lastX2){
            return true;
        }
        else return false;
    }



    public static ArrayList<PointValue> getArray() {
        return entries;
    }
    public static ArrayList<PointValue> getArray1() {
        return entries1;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }

