package workvinayak.techhunt.FirebaseDB;

/**
 * Created by bagariavinayak on 17-11-2016.
 */

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gulzar on 17-11-2016.
 */
public class AddRemove {
    public DatabaseReference mDatabase;
    public AddRemove() { mDatabase= FirebaseDatabase.getInstance().getReference();}

    public void addUser(String stage,String uID)
    {
        mDatabase.child(stage).child(uID).setValue(true);
    }

}
