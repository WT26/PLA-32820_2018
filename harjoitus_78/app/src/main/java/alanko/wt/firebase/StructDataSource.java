package alanko.wt.firebase;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class StructDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_SCORE,
            MySQLiteHelper.COLUMN_GENRE};

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
        return newStruct;
    }

    public void deleteStruct(Struct struct) {
        long id = struct.getId();
        System.out.println("Struct deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_GAME_RANK, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
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
}