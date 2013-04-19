//#######################################################
//
//                  MemoPassword
//
//                Mr.Preecha Homjai
//
//              chahalung@hotmail.com
//
//
//#######################################################

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
    
    //DATE
    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
    private Date date;
    
	private static WebsitesDataSource datasource;
	private List<WebsiteRecords> tmp_websites;
	private List<RadioButton> all_radiobutton ;
	private WebsiteRecords selected_website;
	private EditText edt_sitename;
	private EditText edt_username;
	private EditText edt_password;
	private Button btn_add;
	
	private String ex = "";
	private double time = 0;
	private int cals = 0;
	private String src = "";
	private Bundle bundle;
	public void initializing(){
        this.datasource = new WebsitesDataSource(this);
		this.datasource.open();
		date = new Date();
		
		if(bundle != null) {
		    ex = bundle.getString("ex");
		    time = bundle.getDouble("time");
		    cals = bundle.getInt("calsBurned");
		    src = bundle.getString("source");
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, workouts);
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.edt_website);
        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);
		
		
		this.all_radiobutton = new ArrayList<RadioButton>();
		this.edt_sitename = (EditText)findViewById(R.id.edt_website);
		this.edt_username = (EditText)findViewById(R.id.edt_username);
		this.edt_password = (EditText)findViewById(R.id.edt_password);
		this.btn_add = (Button)findViewById(R.id.btn_add_update);
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
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
        
      //testing array
      TextView arrayview = (TextView) findViewById(R.id.arrtest);
      List<WebsiteRecords>ArrTest=new ArrayList<WebsiteRecords>();
      
      ArrTest = datasource.getAllWebsite();
      
      ArrayList<String> durs = datasource.getAllDurations();
      ArrayList<String> dates = datasource.getAllDates();
      
      double tot = 0;
      ArrayList<Double> totTimes = new ArrayList<Double>();
      ArrayList<String> totDates = new ArrayList<String>();
      String dateTest = durs.get(0);
      
      for (int i=0; i < durs.size(); i++){
          if (dates.get(i).equals(dateTest)){
              tot += Double.parseDouble(durs.get(i));
          }
          else{
              totTimes.add(tot);
              totDates.add(dateTest);
              tot = Double.parseDouble(durs.get(i));
              dateTest = dates.get(i);
          }
      }
      
//      ArrTest = store();
      String printing="";
      
      for (int i=0; i<totDates.size(); i++){
          printing += totDates.get(i).toString();
          printing += "\n";
      }
      arrayview.setText(printing);
        
        if (src.equals("RunStats")) {
            addRun();
        }
        
        show_list_layout();
    }
	private void addRun()
    {
	    if (selected_website!=null){
            selected_website.setSitename(ex);
            selected_website.setUsername("" + time);
            selected_website.setPassword("" + cals);
            selected_website.setDate(dateFormat.format(date));
            datasource.updateWebsite(selected_website);
            show_mesg(ex + " updated.");
            selected_website = null;
            show_list_layout();
        }else{
            datasource.addWebsite(ex, "" + time, "" + cals, dateFormat.format(date));
            show_mesg(ex + " added.");
            selected_website = null;
            show_add_layout();
        }
    }
    public void btn_addnew_click(View v){
		this.selected_website = null;
		show_add_layout();
	}
	public void btn_back_click(View v){
		this.selected_website = null;
		show_list_layout();
	}
	public void btn_edit_click(View v){
		if(this.selected_website != null){
			show_add_layout();
		}else{
			show_mesg("Please select item to edit.");
		}
	}
	
	public void btn_add_update_click(View v){
		hideKeyboard();
		String str_sitename = this.edt_sitename.getText().toString();
		String str_username = this.edt_username.getText().toString();
		String str_password = this.edt_password.getText().toString();
		if (str_sitename.equals("")){
			edt_sitename.requestFocus();
			show_mesg("Please insert workout.");
		}else if (str_username.equals("")){
			edt_username.requestFocus();
			show_mesg("Please insert duration.");
		}else if (str_password.equals("")){
			edt_password.requestFocus();
			show_mesg("Please insert weight.");
		}else{
		    
		    double dur = Integer.parseInt(edt_username.getText().toString());
		    double pounds = Integer.parseInt(edt_password.getText().toString());
		    int workoutIndex = -1;
		    
		    for (int i=0; i < workouts.length; i++){
		        if(workouts[i].equals(str_sitename)){
		            workoutIndex=i;
		        }
		    }
		    if (workoutIndex != -1){
		        double calb = (double)dur*pounds*calNum[workoutIndex];
		        int calbRounded = (int) calb;
		        str_password = String.valueOf(calbRounded);
		    }
		    else{
		        str_password = "N/A";
		    }
		    
		    
			if (selected_website!=null){
				selected_website.setSitename(str_sitename);
				selected_website.setUsername(str_username);
				selected_website.setPassword(str_password);
				selected_website.setDate(dateFormat.format(date));
				datasource.updateWebsite(selected_website);
				show_mesg(str_sitename + " updated.");
				selected_website = null;
				show_list_layout();
			}else{
				datasource.addWebsite(str_sitename,str_username,str_password,dateFormat.format(date));
				show_mesg(str_sitename + " added.");
				selected_website = null;
				show_add_layout();
			}
		}
	}
	////////////////////// Delete website ///////////////////////////////////
	public void btn_del_click(View v){
		if(this.selected_website != null){
			datasource.deleteWebsite(this.selected_website);
			show_mesg(this.selected_website.getSitename() + " deleted.");
			selected_website = null;
			show_list_layout();
		}else{
			show_mesg("Please select item to delete.");
		}
	}
	///////////////////////// select item click /////////////////////////////////
	public class select_item_click implements OnClickListener{
		private RadioButton rdb_select_item;
		private WebsiteRecords website;
		select_item_click(RadioButton rdb_select_item,WebsiteRecords website){
			this.rdb_select_item = rdb_select_item;
			this.website = website;
		}
		@Override
		public void onClick(View v) {
			for (int i = 0 ; i< all_radiobutton.size();i++){
				RadioButton rdb_select_item = all_radiobutton.get(i);
				rdb_select_item.setChecked(false);
			}
			selected_website = this.website;
			//show_mesg(selected_website.getSitename() + " selected.");
			this.rdb_select_item.setChecked(true);
		}
	
	}
	public void hideKeyboard(){
		InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}
	////////////////////// show add layout ///////////////////////////
	public void show_add_layout(){
		hideKeyboard();
		LinearLayout layout_list = (LinearLayout)findViewById(R.id.layout_list);
		LinearLayout layout_add = (LinearLayout)findViewById(R.id.layout_add_edit);
		layout_list.setVisibility(View.GONE);
		layout_add.setVisibility(View.VISIBLE);
		if (selected_website != null){
			this.edt_sitename.setText(selected_website.getSitename());
			this.edt_username.setText(selected_website.getUsername());
			this.edt_password.setText(selected_website.getPassword());
			this.btn_add.setText("Update");
		}else{
			this.edt_sitename.setText("");
			this.edt_username.setText("");
			this.edt_password.setText("");
			this.edt_sitename.requestFocus();
			this.btn_add.setText("Add");
		}
	}
	public void show_mesg(String msg){
		Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
	}
	/////////////////////show list layout ///////////////////////////
	public void show_list_layout(){
		hideKeyboard();
		LinearLayout layout_list = (LinearLayout)findViewById(R.id.layout_list);
		LinearLayout layout_add = (LinearLayout)findViewById(R.id.layout_add_edit);
		layout_list.setVisibility(View.VISIBLE);
		layout_add.setVisibility(View.GONE);

		this.tmp_websites = datasource.getAllWebsite();
		this.all_radiobutton.clear();
		ListView showlist = (ListView) findViewById(R.id.ListWebsite);
		showlist.removeAllViewsInLayout();
        showlist.setAdapter(new WebsiteItemAdapter(this, android.R.layout.simple_list_item_checked, this.tmp_websites));
	}
	//////////////////////////// custom listview //////////////////////////////////////
	public class WebsiteItemAdapter extends ArrayAdapter<WebsiteRecords> {
		private List<WebsiteRecords> websites;
		public WebsiteItemAdapter(Context context, int textViewResourceId, List<WebsiteRecords> websites) {
			super(context, textViewResourceId, websites);
			this.websites = websites;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			WebsiteRecords website = websites.get(position);
			if (website != null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.listitem, null);
		
				RadioButton rdb_select_item =(RadioButton) view.findViewById(R.id.rdb_select_item);
				LinearLayout layout_item = (LinearLayout) view.findViewById(R.id.layout_item);			
				TextView tv_sitename = (TextView) view.findViewById(R.id.website);
				TextView tv_username = (TextView) view.findViewById(R.id.username);
				TextView tv_password = (TextView) view.findViewById(R.id.password);
				TextView tv_date = (TextView) view.findViewById(R.id.date);
				
				tv_sitename.setText(website.getSitename());
				tv_date.setText("Date  : " + website.getDate());
				tv_username.setText("Duration  : " + website.getUsername() + " minutes" );
				tv_password.setText("Calories Burned : " + website.getPassword() + " cal");
				
				if(selected_website != null){
					if (selected_website.getId() == website.getId()) rdb_select_item.setChecked(true);
				}
				
				all_radiobutton.add(rdb_select_item);
				rdb_select_item.setOnClickListener(new select_item_click(rdb_select_item,website));
				layout_item.setOnClickListener(new select_item_click(rdb_select_item,website));
			}
			return view;
		}
	}
	
//	   public void getAllStringValues() {
//	        ArrayList<String> yourStringValues = new ArrayList<String>();
//	        Cursor result = WebsitesDataSource.database.rawQuery("SELECT * FROM websites", null);
//
//	        
//	        result.moveToFirst();
//	        while (!result.isAfterLast()){
//	            Toast.makeText(getApplicationContext(), result.getString(2), 1000).show();
//	            result.moveToNext();
//	        }
//	        }
	
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}
	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
	
//	public static ArrayList<DateDuration> store() {
////        String dateDur[] = {};\
//	    ArrayList <String> dateDur = new ArrayList<String>();
//        Cursor c = null; // first declare and initialise appropriate objects
//        ArrayList<DateDuration> foos = new ArrayList<DateDuration>();
//        DateDuration member = null;
//        try {
//            
//           c = datasource.getDatabase().rawQuery("SELECT date, username FROM websites", null); // perform query
//           if (c.moveToFirst()) { // move cursor to first row because implicitly
//              do { // cursor is position before first row
//                 member = new DateDuration(); // for each row create new Foo
//                 member.setDuration(c.getString(1));
//                 member.setDate(c.getString(2));
//                 foos.add(member); // add Foo into ArrayList
//              } while (c.moveToNext()); // it moves cursor to next row
//           }
//        }
//        finally { // in finally block release datasource(s), cursor(s)
//           if (c != null) {
//              c.close();
//           }
//           if (datasource.getDatabase() != null && datasource.getDatabase().isOpen()) {
//               datasource.getDatabase().close();
//           }
//        }
//        return foos;
//     }
}