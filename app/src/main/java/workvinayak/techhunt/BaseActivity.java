package workvinayak.techhunt;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by bagariavinayak on 17-11-2016.
 */

public class BaseActivity extends AppCompatActivity{
    public DatabaseReference mDatabase;
    String androidId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

    }

    public void makeDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("You have been eliminated. Thanx for participating!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Too slow");
        alert.show();
    }
}
