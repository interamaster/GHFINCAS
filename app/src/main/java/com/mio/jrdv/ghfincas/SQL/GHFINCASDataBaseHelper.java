package com.mio.jrdv.ghfincas.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mio.jrdv.ghfincas.modelParse.MessageParse;

import java.util.ArrayList;

/**
 * Created by invitado on 10/12/15.
 */

public class GHFINCASDataBaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "NotificationDatabase.db";
    private static final String TABLE_NAME = "notification_table";
    private static final String KEY_ID = "_id";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_DATE = "date";


    String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY,"+KEY_MESSAGE+" TEXT,"+KEY_DATE+" INTEGER)";
    String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;



    public GHFINCASDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE);
        onCreate(db);

    }


    //metodos de ayuda


   // @Override
    public void addNotification (MessageParse notificacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_MESSAGE, notificacion.getMessage());
            values.put(KEY_DATE, notificacion.getTimestamp());

            db.insert(TABLE_NAME, null, values);
            db.close();
        }catch (Exception e){
            Log.e("problem",e+"");
        }
    }

    //@Override
    public ArrayList<MessageParse> getAllNotifications() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MessageParse> NotificationList = null;
        try{
            NotificationList = new ArrayList<MessageParse>();
            //String QUERY = "SELECT * FROM "+TABLE_NAME;
            String QUERY = "SELECT * FROM "+TABLE_NAME +" ORDER BY "+KEY_ID+" DESC";
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast())
            {
                while (cursor.moveToNext())
                {
                    MessageParse notification = new MessageParse();
                    notification.setId(cursor.getInt(0));
                    notification.setMessage(cursor.getString(1));
                    notification.setTimestamp(cursor.getLong(2));

                    NotificationList.add(notification);
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error", e + "");
        }
        return NotificationList;
    }

   // @Override
    public int getNotificationCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            db.close();
            return num;
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return 0;
    }
}
