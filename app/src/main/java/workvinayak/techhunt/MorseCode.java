package workvinayak.techhunt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import workvinayak.techhunt.FirebaseDB.AddRemove;

public class MorseCode extends BaseActivity {

    long childCount=-1;
    String initialCount=null;
    AddRemove addRemove;
    ProgressDialog progressDialog;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Round 5");
        setSupportActionBar(toolbar);

        tv=(TextView)findViewById(R.id.tvShow);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        mDatabase.child("initialValue6").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                initialCount=dataSnapshot.getValue(String.class);//Type cast To Integer
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("stage6").addValueEventListener(new ValueEventListener() {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    public void firstClick(View view)
    {
        EditText et=(EditText)findViewById(R.id.etText);
        if(et.getText().toString().trim().equalsIgnoreCase("books"))
        {
            //Check the condition
            if(childCount!=-1 && childCount<Integer.parseInt(initialCount))
            {
                SharedPreferences sharedPreferences=this.getSharedPreferences("Activity", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("Intent","6");
                editor.commit();

                progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Loading..");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                addRemove=new AddRemove();
                addRemove.addUser("stage6",androidId);

                progressDialog.dismiss();

                startActivity(new Intent(MorseCode.this, LibraryAct.class));
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

    public void played(View view)
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(MorseCode.this, R.raw.morsecode);
        mediaPlayer.start();
    }
}
