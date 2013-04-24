package com.example.fitnesstrackerproto;

import android.app.Activity;
import android.os.Bundle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.fitnesstrackerproto.R;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

public class WorkoutLogActivity extends Activity {

    String[] workouts = { "Aerobics, general", "Aerobics, high impact",
            "Aerobics, low impact", "Aerobics, step", "Backpacking, hiking",
            "Badminton", "Ballet", "Basketball, competitive",
            "Basketball, casual", "Bowling", "Boxing, in ring",
            "Boxing, punching bag", "Boxing, sparring",
            "Calisthenics, light, pushups, situps, crunches",
            "Calisthenics, fast, pushups, situps, crunches", "Canoeing",
            "Climbing", "Crew, sculling, rowing", "Cricket", "Croquet",
            "Cross country skiing", "Curling", "Cycling", "Diving",
            "Downhill skiing", "Fencing", "Fishing", "Football, playing catch",
            "Football, competitive", "Football, casual", "Frisbee, casual",
            "Frisbee, ultimate", "Golf, general", "Golf, miniature golf",
            "Gymnastics", "Hiking, cross country", "Hockey, field",
            "Hockey, ice", "Horseback riding", "Horseshoe pitching",
            "Ice skating", "Jumping rope", "Kayaking",
            "Martial arts, judo, karate, jujitsu", "Martial arts, kick boxing",
            "Martial arts, tae kwan do", "Krav maga", "Juggling", "Kickball",
            "Lacrosse", "Polo", "Racquetball", "Rock climbing", "Rugby",
            "Roller skating", "Roller blading", "Soccer, competitive",
            "Soccer, casual", "Softball or baseball", "Squash",
            "Stair machine", "Stationary cycling", "Stretching", "Swimming",
            "Table tennis, ping pong", "Tai chi", "Tennis, doubles",
            "Tennis, singles", "Track and field, shot, discus",
            "Track and field, high jump, pole vault",
            "Track and field, hurdles", "Trampoline",
            "Volleyball, competitive", "Volleyball, casual", "Water polo",
            "Water skiing", "Water calisthenics",
            "Weight lifting, body building, vigorous", "Weight lifting, light",
            "Yoga" };

    double[] calNum = { 0.0492, 0.053, 0.0379, 0.0644, 0.0535, 0.0342, 0.0342,
            0.0606, 0.0454, 0.0227, 0.0908, 0.0454, 0.0681, 0.0266, 0.0606,
            0.053, 0.053, 0.0908, 0.0379, 0.019, 0.074, 0.0303, 0.068, 0.0227,
            0.052, 0.0454, 0.0227, 0.01898, 0.0681, 0.0606, 0.0227, 0.0606,
            0.0342, 0.0227, 0.0303, 0.0454, 0.0606, 0.0606, 0.0492, 0.0227,
            0.053, 0.0757, 0.0379, 0.0757, 0.0757, 0.0757, 0.0757, 0.0303,
            0.053, 0.0606, 0.0606, 0.053, 0.065, 0.0757, 0.053, 0.0908, 0.0757,
            0.053, 0.0379, 0.0908, 0.0681, 0.053, 0.019, 0.0644, 0.0303,
            0.0303, 0.0454, 0.0606, 0.0303, 0.0454, 0.0757, 0.0266, 0.0606,
            0.0227, 0.0757, 0.0454, 0.0303, 0.0429, 0.0227, 0.0303 };

    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
    private Date date;

    private static WorkoutsDataSource datasource;
    private List<WorkoutRecords> tmp_workouts;
    private List<RadioButton> all_radiobutton;
    private WorkoutRecords selected_workout;
    private EditText edt_exercise;
    private EditText edt_duration;
    private EditText edt_calories;
    private Button btn_add;

    private String ex = "";
    private double time = 0;
    private int cals = 0;
    private String src = "";
    private Bundle bundle;

    static ArrayList<Double> totTimes;
    static ArrayList<String> totDates;

    public void initializing()
    {
        this.datasource = new WorkoutsDataSource(this);
        this.datasource.open();
        date = new Date();
        totDates = new ArrayList<String>();
        totTimes = new ArrayList<Double>();

        if (bundle != null) {
            ex = bundle.getString("ex");
            time = bundle.getDouble("time");
            cals = bundle.getInt("calsBurned");
            src = bundle.getString("source");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, workouts);
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.edt_exercise);
        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);

        this.all_radiobutton = new ArrayList<RadioButton>();
        this.edt_exercise = (EditText) findViewById(R.id.edt_exercise);
        this.edt_duration = (EditText) findViewById(R.id.edt_duration);
        this.edt_calories = (EditText) findViewById(R.id.edt_calories);
        this.btn_add = (Button) findViewById(R.id.btn_add_update);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bundle = getIntent().getExtras();

        Button mainmenu = (Button) findViewById(R.id.mainmenu);
        mainmenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent mainmenuIntent = new Intent(view.getContext(),
                        MainMenuActivity.class);
                startActivityForResult(mainmenuIntent, 0);
            }
        });

        initializing();

        ArrayList<String> durs = datasource.getAllDurations();
        ArrayList<String> dates = datasource.getAllDates();
        if (durs.size() != 0) {

            double tot = 0;
            String dateTest = dates.get(0);

            for (int i = 0; i < durs.size(); i++) {
                if (dates.get(i).equals(dateTest)) {
                    tot += Double.parseDouble(durs.get(i));
                } else {
                    totTimes.add(tot);
                    totDates.add(dateTest);
                    tot = Double.parseDouble(durs.get(i));
                    dateTest = dates.get(i);
                }
                if (i == durs.size() - 1) {
                    totTimes.add(tot);
                    totDates.add(dateTest);
                }

            }

        }

        if (src.equals("RunStats")) {
            addRun();
        }

        // switch to Graph Activity
        Button graph = (Button) findViewById(R.id.graph);
        graph.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                barGraph();
            }
        });
        show_list_layout();
    }

    public void barGraph()
    {
        BarGraph bar = new BarGraph();
        Intent lineIntent = bar.getIntent(this);
        startActivity(lineIntent);
    }

    private void addRun()
    {
        if (selected_workout != null) {
            selected_workout.setExercise(ex);
            selected_workout.setDuration("" + time);
            selected_workout.setCalories("" + cals);
            selected_workout.setDate(dateFormat.format(date));
            datasource.updateWorkout(selected_workout);
            show_mesg(ex + " updated." + "\n" + "Go back to view changes");
            selected_workout = null;
            show_list_layout();
        } else {
            datasource.addWorkout(ex, "" + time, "" + cals,
                    dateFormat.format(date));
            show_mesg(ex + " added." + "\n" + "Go back to view changes");
            selected_workout = null;
            show_add_layout();
        }
    }

    public void btn_addnew_click(View v)
    {
        this.selected_workout = null;
        show_add_layout();
    }

    public void btn_back_click(View v)
    {
        this.selected_workout = null;
        show_list_layout();
    }

    public void btn_edit_click(View v)
    {
        if (this.selected_workout != null) {
            show_add_layout();
        } else {
            show_mesg("Please select item to edit.");
        }
    }

    public void btn_add_update_click(View v)
    {
        hideKeyboard();
        String str_exercise = this.edt_exercise.getText().toString();
        String str_duration = this.edt_duration.getText().toString();
        String str_calories = this.edt_calories.getText().toString();
        if (str_exercise.equals("")) {
            edt_exercise.requestFocus();
            show_mesg("Please insert workout.");
        } else if (str_duration.equals("")) {
            edt_duration.requestFocus();
            show_mesg("Please insert duration.");
        } else if (str_calories.equals("")) {
            edt_calories.requestFocus();
            show_mesg("Please insert weight.");
        } else {

            double dur = Integer.parseInt(edt_duration.getText().toString());
            double pounds = Integer.parseInt(edt_calories.getText().toString());
            int workoutIndex = -1;

            for (int i = 0; i < workouts.length; i++) {
                if (workouts[i].equals(str_exercise)) {
                    workoutIndex = i;
                }
            }
            if (workoutIndex != -1) {
                double calb = (double) dur * pounds * calNum[workoutIndex];
                int calbRounded = (int) calb;
                str_calories = String.valueOf(calbRounded);
            } else {
                str_calories = "N/A";
            }

            if (selected_workout != null) {
                selected_workout.setExercise(str_exercise);
                selected_workout.setDuration(str_duration);
                selected_workout.setCalories(str_calories);
                selected_workout.setDate(dateFormat.format(date));
                datasource.updateWorkout(selected_workout);
                show_mesg(str_exercise + " updated." + "\n" + "Go back to view changes");
                selected_workout = null;
                show_list_layout();
            } else {
                datasource.addWorkout(str_exercise, str_duration, str_calories,
                        dateFormat.format(date));
                show_mesg(str_exercise + " added." + "\n" + "Go back to view changes");
                selected_workout = null;
                show_add_layout();
            }
        }
    }

    public void btn_del_click(View v)
    {
        if (this.selected_workout != null) {
            datasource.deleteWorkout(this.selected_workout);
            show_mesg(this.selected_workout.getExercise() + " deleted.");
            selected_workout = null;
            show_list_layout();
        } else {
            show_mesg("Select item to delete.");
        }
    }

    public class select_item_click implements OnClickListener {
        private RadioButton rdb_select_item;
        private WorkoutRecords workout;

        select_item_click(RadioButton rdb_select_item, WorkoutRecords workout) {
            this.rdb_select_item = rdb_select_item;
            this.workout = workout;
        }

        @Override
        public void onClick(View v)
        {
            for (int i = 0; i < all_radiobutton.size(); i++) {
                RadioButton rdb_select_item = all_radiobutton.get(i);
                rdb_select_item.setChecked(false);
            }
            selected_workout = this.workout;
            this.rdb_select_item.setChecked(true);
        }

    }

    public void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void show_add_layout()
    {
        hideKeyboard();
        LinearLayout layout_list = (LinearLayout) findViewById(R.id.layout_list);
        LinearLayout layout_add = (LinearLayout) findViewById(R.id.layout_add_edit);
        layout_list.setVisibility(View.GONE);
        layout_add.setVisibility(View.VISIBLE);
        if (selected_workout != null) {
            this.edt_exercise.setText(selected_workout.getExercise());
            this.edt_duration.setText(selected_workout.getDuration());
            this.edt_calories.setText(selected_workout.getCalories());
            this.btn_add.setText("Update");
        } else {
            this.edt_exercise.setText("");
            this.edt_duration.setText("");
            this.edt_calories.setText("");
            this.edt_exercise.requestFocus();
            this.btn_add.setText("Add");
        }
    }

    public void show_mesg(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void show_list_layout()
    {
        hideKeyboard();
        LinearLayout layout_list = (LinearLayout) findViewById(R.id.layout_list);
        LinearLayout layout_add = (LinearLayout) findViewById(R.id.layout_add_edit);
        layout_list.setVisibility(View.VISIBLE);
        layout_add.setVisibility(View.GONE);

        this.tmp_workouts = datasource.getAllWorkout();
        this.all_radiobutton.clear();
        ListView showlist = (ListView) findViewById(R.id.ListWorkout);
        showlist.removeAllViewsInLayout();
        showlist.setAdapter(new WorkoutItemAdapter(this,
                android.R.layout.simple_list_item_checked, this.tmp_workouts));
    }

    public class WorkoutItemAdapter extends ArrayAdapter<WorkoutRecords> {
        private List<WorkoutRecords> workouts;

        public WorkoutItemAdapter(Context context, int textViewResourceId,
                List<WorkoutRecords> workouts) {
            super(context, textViewResourceId, workouts);
            this.workouts = workouts;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = convertView;
            WorkoutRecords workout = workouts.get(position);
            if (workout != null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.listitem, null);

                RadioButton rdb_select_item = (RadioButton) view
                        .findViewById(R.id.rdb_select_item);
                LinearLayout layout_item = (LinearLayout) view
                        .findViewById(R.id.layout_item);
                TextView tv_exercise = (TextView) view
                        .findViewById(R.id.workout);
                TextView tv_duration = (TextView) view
                        .findViewById(R.id.duration);
                TextView tv_calories = (TextView) view
                        .findViewById(R.id.calories);
                TextView tv_date = (TextView) view.findViewById(R.id.date);

                tv_exercise.setText(workout.getExercise());
                tv_date.setText("Date  : " + workout.getDate());
                tv_duration.setText("Duration  : " + workout.getDuration()
                        + " minutes");
                tv_calories.setText("Calories Burned : "
                        + workout.getCalories() + " cal");

                if (selected_workout != null) {
                    if (selected_workout.getId() == workout.getId())
                        rdb_select_item.setChecked(true);
                }

                all_radiobutton.add(rdb_select_item);
                rdb_select_item.setOnClickListener(new select_item_click(
                        rdb_select_item, workout));
                layout_item.setOnClickListener(new select_item_click(
                        rdb_select_item, workout));
            }
            return view;
        }
    }

    @Override
    protected void onResume()
    {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        datasource.close();
        super.onPause();
    }

    public static ArrayList<Double> getTimes()
    {
        return totTimes;
    }

    public static ArrayList<String> getDates()
    {
        return totDates;
    }

}