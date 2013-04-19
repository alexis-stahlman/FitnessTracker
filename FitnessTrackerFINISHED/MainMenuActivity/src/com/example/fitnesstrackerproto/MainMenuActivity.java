package com.example.fitnesstrackerproto;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
//import android.graph.BarGraph;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuActivity extends Activity {

    @Override 
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
//        //testing array
//        TextView arrayview = (TextView) findViewById(R.id.arraylist);
//        ArrayList <String>ArrTest=new ArrayList<String>();
//        
//        ArrTest = MySQLiteHelper.getAllStringValues();
//        String printing="";
//        for (int i=0; i<ArrTest.size(); i++){
//            printing += ArrTest.get(i);
//            printing += "\n";
//        }
//        arrayview.setText(printing);

        // switch to TrackRun Activity
        Button trackRun = (Button) findViewById(R.id.runButton);
        trackRun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent trackRunIntent = new Intent(view.getContext(),
                        TrackRunActivity.class);
                startActivityForResult(trackRunIntent, 0);
            }
        });

        // switch to LogWorkout Activity
        Button logWorkout = (Button) findViewById(R.id.workoutButton);
        logWorkout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent logWorkoutIntent = new Intent(view.getContext(),
                        WorkoutLogActivity.class);
                startActivityForResult(logWorkoutIntent, 0);
            }
        });
        
        
     // switch to Graph Activity
//        Button graph = (Button) findViewById(R.id.graph);
//        graph.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view)
//            {
//                barGraph();
//            }
//        });
    }

//    public void barGraph(){
//        BarGraph bar = new BarGraph();
//        Intent lineIntent = bar.getIntent(this);
//        startActivity(lineIntent);
//    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

}
