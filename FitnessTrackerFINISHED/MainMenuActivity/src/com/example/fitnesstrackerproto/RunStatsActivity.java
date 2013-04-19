package com.example.fitnesstrackerproto;

import com.example.fitnesstrackerproto.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RunStatsActivity extends Activity {

	protected static String timeRan;
	protected static double miles;
	private static double mph = 0;
	private static double avgMile = 0;
	private static int weight = 0;
	private static String avgMileTime = "";
	private static TextView time;
	private static TextView milesRan;
	private static TextView mileTime;
	private static TextView cals;
	private static int m = 0;
	private static double s = 0;
	private static double tot = 0;
	private static int calories = 0;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        
        m = Integer.parseInt(timeRan.substring(0, timeRan.indexOf(':')));
		s = ((double)Integer.parseInt(timeRan.substring(timeRan.indexOf(':')+1))) / 60 ;
		tot = m+s;
        
        weight = 110;
        
        time = (TextView) findViewById(R.id.timeRanEntry);
        milesRan = (TextView) findViewById(R.id.milesRanEntry);
        mileTime = (TextView) findViewById(R.id.avgMPHEntry);
        cals = (TextView) findViewById(R.id.runCalBurnedEntry);
         
        time.setText("  " + formatTimeRan());
        
        milesRan.setText("  " + String.format("%.2f",miles) + " miles");
        
        mileTime.setText("  " + calcMileTime());
        
        cals.setText("  " + calcCals()+ " calories");
        

        Button back = (Button) findViewById(R.id.backButton);
        back.setText("Cancel");
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent mainIntent = new Intent(view.getContext(),
                        MainMenuActivity.class);
                startActivityForResult(mainIntent, 0);
            }
        });
        
        Button add = (Button) findViewById(R.id.addRunButton);
        add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent log = new Intent(view.getContext(),
						WorkoutLogActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("ex", "Run");
				bundle.putDouble("time", tot);
				bundle.putInt("calsBurned", calories);
				bundle.putString("source", "RunStats");
				log.putExtras(bundle);
				startActivityForResult(log, 0); 
			}
		}); 
    }

    private int calcCals() {
		calories = (int) (weight * (tot/60) * mph * 0.01280303); 
		
		return calories;
	}

	private String formatTimeRan() {
    	String times = "";
    	
    	times += timeRan.substring(0, timeRan.indexOf(':')) + " minutes, ";
    	times += timeRan.substring(timeRan.indexOf(':')+1) + " seconds";
    	
		return times;
	}

	private String calcMileTime() {
		avgMile = (tot) / miles;
		
		if(avgMile >= 60) {
			int h = (int) (avgMile/60);
			int m = (int) ((avgMile - (h*60)));
			int s = (int) (avgMile - ((h*60)+m))*60;
			
			avgMileTime += h + " hours, " + m + " min, " + s + " secs";
			mph = miles / (tot);
		}
		else if (avgMile >= 1) {
			int m = (int) avgMile;
			int s = (int) (avgMile - m)*60;
			
			avgMileTime += m + " min, " + s + " secs";
			mph = miles / (tot/60);
		}
		else {
			double s = avgMile*60;
			avgMileTime += s + " secs";
			mph = miles / (tot/(60));
		}
		
		return avgMileTime;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_stats, menu);
        return true;
    }

}