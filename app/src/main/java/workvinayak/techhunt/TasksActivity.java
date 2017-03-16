package workvinayak.techhunt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import workvinayak.techhunt.FirebaseDB.AddRemove;

public class TasksActivity extends BaseActivity {

    long childCount=-1;
    String initialCount=null;
    AddRemove addRemove;
    ProgressDialog progressDialog;
    TextView tv;

    static String[] versionArray = {
            "Please read the instructions carefully before you start.",
            "Collect the paper from the volunteer standing opposite to the first block",
            "Write the names of all your team members on the top right edge of the paper",
            "Computers Computers Computers - Everywhere",
            "Find it and Run it",
            "Now Yell -- Is this what you call Tech Hunt?",
            "Find out the answer of : 703 * 15 - 81 * 71 * 615 / 15",
            "Now that you have reached this level (Kudos to that!!), go to Room 007 and Drink Water. Mind you - you are under surveillance",
            "Solve it : At dawn on Monday, a snail fell into a bucket that was twelve inches deep. During the day, it climbed up 3 inches." +
                    "However during the night, it fell back 1+1 inches. On what day, did the snail finally manage to escape?",
            "Cleverly, there are exactly _ _ _ _ _ e's in the sentence. Don't please count the spaces",
            "Thank You for reading all the instructions carefully. Now go the 8th point and enter the last word in your phone",
            "Submit the sheet of paper quietly to the volunteer. Proceed towards your next task"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Round 4");
        setSupportActionBar(toolbar);

        tv=(TextView)findViewById(R.id.tvShow);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        mDatabase.child("initialValue5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                initialCount=dataSnapshot.getValue(String.class);//Type cast To Integer
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("stage5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childCount=dataSnapshot.getChildrenCount();
                tv.setText(childCount+" Team(s) Cleared to Next Round");
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        initialise();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }

    public void butt(View view)
    {
        EditText et=(EditText)findViewById(R.id.etPass);
        if(et.getText().toString().trim().equalsIgnoreCase("surveillance"))
        {
            //Check the condition
            if(childCount!=-1 && childCount<Integer.parseInt(initialCount))
            {
                SharedPreferences sharedPreferences=this.getSharedPreferences("Activity", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("Intent","5");
                editor.commit();

                progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Loading..");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                addRemove=new AddRemove();
                addRemove.addUser("stage5",androidId);

                progressDialog.dismiss();

                startActivity(new Intent(TasksActivity.this, MorseCode.class));
                finish();
            }

            else
            {
                makeDialog();
            }
        }
        else
        {
            Snackbar.make((CoordinatorLayout)findViewById(R.id.coordLayout),"Retry. You will get it.",Snackbar.LENGTH_SHORT).show();
        }
    }

    void initialise()
    {
        ArrayAdapter adapter = new ArrayAdapter<String>(TasksActivity.this, android.R.layout.simple_list_item_1, versionArray);
        ListView mainListView = (ListView) findViewById(R.id.mobile_list);
        //setAdapter(adapter) is used to initialise the ListView with the adapter.
        mainListView.setAdapter(adapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /*
                1. parent - AdapterView where the click happened.
                2. view - The view within AdapterView that was clicked
                3. position - position of the view clicked in the adapter
                4. id - row id of the item that was clicked ; useful for CursorAdapter. Same as position in ListAdapter
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String cheese = versionArray[position];
                try {
                    //Class.forName(string) gives the class object associated with the given string name
                    Class ourClass = Class.forName("workvinayak.new_start." + cheese);
                    Intent open = new Intent(TasksActivity.this, ourClass);
                    startActivity(open);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

