package com.example.android.scores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Scores.db";
    private static final String TABLE_NAME = "Scores";
    private static final String KEY_ID = "id";
    private static final String KEY_PARENT = "parent";
    private static final String KEY_MATCHNUBER = "matchno";
    private static final String KEY_TEAMAScore = "ScoreA";
    private static final String KEY_TEAMBScore ="ScoreB";
    private static final String[] COLUMNS = { KEY_ID, KEY_PARENT,KEY_MATCHNUBER,KEY_TEAMAScore,KEY_TEAMBScore};
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Create_Table ="CREATE TABLE Scores( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "parent TEXT, "
                + "matchno TEXT, "
                + "ScoreA TEXT, "
                + "ScoreB TEXT )";
        sqLiteDatabase.execSQL(Create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
    public void AddtoDatabase(String parent,String matchno)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PARENT,parent);
        values.put(KEY_MATCHNUBER,matchno);
        values.put(KEY_TEAMAScore,"0");
        values.put(KEY_TEAMBScore,"0");
        db.insert(TABLE_NAME,null, values);
        db.close();
    }
    public void upDateScore(String parent,String matchno,String scoreA,String scoreB)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PARENT,parent);
        values.put(KEY_MATCHNUBER,matchno);
        values.put(KEY_TEAMAScore,scoreA);
        values.put(KEY_TEAMBScore,scoreB);
        db.update(TABLE_NAME,values,KEY_PARENT+ " =?"+" AND "+KEY_MATCHNUBER+" =?",new String[]{parent,matchno});
    }
    public ArrayList<String> getScore(String parent, String matchno)
    {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c= db.query(TABLE_NAME,COLUMNS,KEY_PARENT+ " =?"+" AND "+KEY_MATCHNUBER+" =?",new String[]{parent,matchno},null,null,null);
        if (c.getCount()>0)
        {
            c.moveToFirst();
            String scoreA = c.getString(c.getColumnIndex("ScoreA"));
            String scoreB = c.getString(c.getColumnIndex("ScoreB"));
            list.add(0,scoreA);
            list.add(1,scoreB);
        }


        return list;
    }
}
