package workvinayak.techhunt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import workvinayak.techhunt.FirebaseDB.AddRemove;

public class UnlockApp extends AppCompatActivity {

    AddRemove ar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Let the hunt begin");
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences=this.getSharedPreferences("Activity",Context.MODE_PRIVATE);
        String number=sharedPreferences.getString("Intent","0");

        if(number.equals("1"))
            startActivity(new Intent(UnlockApp.this,MainActivity.class));
        else if (number.equals("2"))
            startActivity(new Intent(UnlockApp.this,SecondRound.class));
        else if (number.equals("3"))
            startActivity(new Intent(UnlockApp.this,Gps2.class));
        else if (number.equals("4"))
            startActivity(new Intent(UnlockApp.this,TasksActivity.class));
        else if (number.equals("5"))
            startActivity(new Intent(UnlockApp.this,MorseCode.class));
        else if (number.equals("6"))
            startActivity(new Intent(UnlockApp.this,LibraryAct.class));

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
        if(et.getText().toString().trim().equalsIgnoreCase("13141516"))
        {
            //startActivity(new Intent(MainActivity.this,SecondRound.class));
            ar=new AddRemove();
            String androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
            //Add ProgressBar start
            ar.addUser("stage1",androidId);
            //End

            SharedPreferences sharedPreferences=this.getSharedPreferences("Activity", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("Intent","1");
            editor.commit();

            startActivity(new Intent(UnlockApp.this,Gps2.class));
            finish();
        }
        else
        {
            Snackbar.make((CoordinatorLayout)findViewById(R.id.coordLayout),"Ask the password again.",Snackbar.LENGTH_SHORT).show();
        }
    }
}
