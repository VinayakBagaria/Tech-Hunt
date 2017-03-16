package workvinayak.techhunt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import workvinayak.techhunt.FirebaseDB.AddRemove;

/**
 * Created by bagariavinayak on 16-11-2016.
 */

public class SecondRound extends BaseActivity
{
    TextView tv;
    long childCount=-1;
    String initialCount=null;
    AddRemove addRemove;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initialDialog();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Round 2");
        setSupportActionBar(toolbar);

        tv=(TextView)findViewById(R.id.tvShow);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        mDatabase.child("initialValue4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                initialCount=dataSnapshot.getValue(String.class);//Type cast To Integer
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("stage4").addValueEventListener(new ValueEventListener() {
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

    public void secondClick(View view)
    {
        EditText et=(EditText)findViewById(R.id.etText);
        if(et.getText().toString().trim().equalsIgnoreCase("we are foobar"))
        {
            //Check the condition
            if(childCount!=-1 && childCount<Integer.parseInt(initialCount))
            {
                SharedPreferences sharedPreferences=this.getSharedPreferences("Activity", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("Intent","3");
                editor.commit();

                progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Loading..");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                addRemove=new AddRemove();
                addRemove.addUser("stage4",androidId);

                progressDialog.dismiss();

                startActivity(new Intent(SecondRound.this, Gps2.class));
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

    public void initialDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Kudos ! Go say hi to them")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Your clue");
        alert.show();
    }
}
