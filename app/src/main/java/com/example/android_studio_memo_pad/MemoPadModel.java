package com.example.android_studio_memo_pad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class MemoPadModel extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myMemoDatabase.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_MEMOS = "memos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEMO = "memo";
    protected PropertyChangeSupport propertyChangeSupport;

    public MemoPadModel(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name != null ? name : DATABASE_NAME, factory, version > 0 ? version : DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEMOS_TABLE = "CREATE TABLE memos ( _id INTEGER PRIMARY KEY AUTOINCREMENT, memo TEXT NOT NULL)";
        db.execSQL(CREATE_MEMOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMOS);
        onCreate(db);
    }
    public void addNewMemo(Memo m) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, m.getMemo());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MEMOS, null, values);
        db.close();
        propertyChangeSupport.firePropertyChange(MemoPadPresenter.ELEMENT_ADD, null, null);
    }
    public void deleteMemo(Integer id) {
        // TODO: Add delete Memo feature
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        propertyChangeSupport.firePropertyChange(MemoPadPresenter.ELEMENT_DELETE, null, null);
    }
    public Memo getMemo(int id) {
        String query = "SELECT * FROM " + TABLE_MEMOS + " WHERE " + COLUMN_ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Memo m = null;

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            int newId = cursor.getInt(0);
            String newMemo = cursor.getString(1);
            cursor.close();
            m = new Memo(newId, newMemo);
        }

        db.close();
        return m;
    }

    public String getAllMemos() {
        String query = "SELECT * FROM " + TABLE_MEMOS;
        StringBuilder sb = new StringBuilder();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(0);
                sb.append(getMemo(id)).append("\n");
            }
            while (cursor.moveToNext() );
        }
        db.close();
        return sb.toString();
    }

    public ArrayList<Memo> getAllMemosAsList() {
        String query = "SELECT * FROM " + TABLE_MEMOS;
        ArrayList<Memo> allMemos = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                int newId = cursor.getInt(0);
                String newMemo = cursor.getString(1);
                allMemos.add(new Memo(newId, newMemo));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return allMemos;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

}
