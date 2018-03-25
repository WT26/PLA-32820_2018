package alanko.wt.firebase;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class StructDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_SCORE,
            MySQLiteHelper.COLUMN_GENRE};

    private String personId;


    public StructDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Struct createStruct(String name, String score, String genre) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_SCORE, score);
        values.put(MySQLiteHelper.COLUMN_GENRE, genre);
        long insertId = database.insert(MySQLiteHelper.TABLE_GAME_RANK, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_GAME_RANK,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Struct newStruct = cursorToStruct(cursor);
        cursor.close();

        //get firebase user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference UID = database.getReference("users");

        UID.push().getKey();
        String uniqueGame = UID.child(user.getUid()).push().getKey();

        Struct struct = new Struct();
        struct.setId(insertId);
        struct.setName(name);
        struct.setScore(score);
        struct.setGenre(genre);

        DatabaseReference gamerankDB = UID.child(user.getUid());
        gamerankDB.child(uniqueGame).setValue(struct);

        return newStruct;
    }

    public Struct createStructFBDB(String name, String score, String genre){

        //get firebase user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference UID = database.getReference("users");

        UID.push().getKey();
        UID.child(user.getUid()).setValue("gamerank");

        Struct struct = new Struct();
        struct.setName(name);
        struct.setScore(score);
        struct.setGenre(genre);

        DatabaseReference gamerankDB = UID.child(user.getUid());

        return struct;
    }

    public void deleteStruct(Struct struct) {
        long id = struct.getId();
        System.out.println("Struct deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_GAME_RANK, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);



    }
    public void deleteStruct(long index) {
        //get firebase user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference UID = database.getReference("users");

        DatabaseReference gamerankDB = UID.child(user.getUid());

        gamerankDB.orderByChild("id").equalTo(index).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    child.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(" ", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }

    public List<Struct> getAllStructs() {
        List<Struct> structs = new ArrayList<Struct>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_GAME_RANK,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Struct struct = cursorToStruct(cursor);
            structs.add(struct);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return structs;
    }

    private Struct cursorToStruct(Cursor cursor) {
        Struct struct = new Struct();
        struct.setId(cursor.getLong(0));
        struct.setName(cursor.getString(1));
        struct.setScore(cursor.getString(2));
        struct.setGenre(cursor.getString(3));
        return struct;
    }

    public List<Struct> getAllStructFBDB(){
        List<Struct> structs = new ArrayList<Struct>();


        return structs;
    }
}