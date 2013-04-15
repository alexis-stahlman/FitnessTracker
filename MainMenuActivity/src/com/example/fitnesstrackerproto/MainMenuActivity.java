package com.example.fitnesstrackerproto;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

}
