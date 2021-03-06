package com.example.vanessa.finalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class coachProgress extends AppCompatActivity {

        Button btnIn;
        EditText xInput, yInput;
        GraphView graph;
        LineGraphSeries<DataPoint> series; //= new LineGraphSeries<>(new DataPoint[0]);
        DatabaseHelper dbHelper;
        SQLiteDatabase sqLiteDatabase;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_coach_progress);
            //set id's
            btnIn=(Button) findViewById(R.id.btnInsert);
            xInput=(EditText) findViewById(R.id.tbXVal);
            yInput=(EditText) findViewById(R.id.tbYVal);
            graph=(GraphView) findViewById(R.id.graph);
            //access database
            dbHelper = new DatabaseHelper(this);
            sqLiteDatabase = dbHelper.getWritableDatabase();

            exqButton();
            //graph.addSeries(series);


        }

        private void exqButton() {
            btnIn.setOnClickListener(new View.OnClickListener() {//insert values into database
                @Override
                public void onClick(View v) {
                    int xVal = Integer.parseInt(String.valueOf(xInput.getText()));
                    int yVal = Integer.parseInt(String.valueOf(yInput.getText()));

                    dbHelper.insertData(xVal, yVal);

                    //series.resetData(getData());

                    series=new LineGraphSeries<DataPoint>(getData());
                    graph.addSeries(series);

                    //setting graph limits
                /*graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(1);
                graph.getViewport().setMaxX(12);

                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMinY(1);
                graph.getViewport().setMaxY(10);
                //make graph scroll
                graph.getViewport().setScrollable(true);
                graph.getViewport().setScrollableY(true);*/

                }

                private DataPoint[] getData() {
                    //reading data from database
                    String[] columns = {"xValues", "yValues"};
                    Cursor cursor = sqLiteDatabase.query("tblProgress", columns, null, null, null, null, null);
                    DataPoint[] dp = new DataPoint[cursor.getCount()];

                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToNext();
                        dp[i] = new DataPoint(cursor.getInt(0), cursor.getInt(1));
                    }
                    return dp;
                }
            });
        }
    }