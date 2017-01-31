package venture.student.com.studentspot;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

public class DataB {
    public static final String KEY_ROWID ="_id";
    public static final String KEY_NAME ="p_name";
    public static final String KEY_ROWID1 ="_idd";
    public static final String KEY_NAME1 ="p_named";

    public static final String KEY_PHN ="p_phn";

    private static final String DATABASE_NAME ="CourseGradedba";
    private static final String DATABASE_TABLE ="courseTabled";
	
    private static final String DATABASE_TABLE1 ="notificationstatus";
	
    private static final int DATABASE_VERSION =1;
    private DbHelper myHelper;
    private final Context myContext;
    String value;
    private SQLiteDatabase myDatabase;
    private static class DbHelper extends SQLiteOpenHelper{
      



        @Override
        public void onCreate(SQLiteDatabase db) {
           
// TODO Auto-generated method stub
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            KEY_NAME + " TEXT NOT NULL, " +
                             KEY_PHN + " TEXT NOT NULL);"
            );
            db.execSQL("CREATE TABLE " + DATABASE_TABLE1 + " (" +
                    KEY_ROWID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME1+ " VARCHAR(60) );"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE1);
            onCreate(db);
        }
        public DbHelper(Context context) {

            super(context,DATABASE_NAME,null,DATABASE_VERSION);

        }
    }
    public DataB(Context c){
        myContext = c;
    }
    public DataB open() throws SQLException{
        myHelper = new DbHelper(myContext);
        myDatabase = myHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        myHelper.close();
    }
    public long createEntry1(String name) {
// TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME1, name);

        return myDatabase.insert(DATABASE_TABLE1, null, cv);
    }
    public long createEntry(String name,String phn ) {
// TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);

        cv.put(KEY_PHN, phn);

        return myDatabase.insert(DATABASE_TABLE, null, cv);
    }
    public String getData1() {
// TODO Auto-generated method stub
        String[] columns = new String[]{KEY_ROWID, KEY_NAME, KEY_PHN};
        Cursor c = myDatabase.query(DATABASE_TABLE, columns, null, null, null, null,
                null);
        String result = "";
        int irow = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + c.getString(iName) +"\n";
        }
        return result;
    }
    public String getData3() {
// TODO Auto-generated method stub
        String[] columns = new String[]{KEY_ROWID1, KEY_NAME1};
        Cursor c = myDatabase.query(DATABASE_TABLE1, columns, null,null,null,null,null);

     int result1=0;
        int irow = c.getColumnIndex(KEY_ROWID1);
        int iName = c.getColumnIndex(KEY_NAME1);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            value =c.getString(iName)+"\n";
        }


        return value;

    }
    public  boolean CheckIsDataAlreadyInDBorNot(String fieldValue) {

        String Query = "Select * from "+DATABASE_TABLE1 +" where " + KEY_NAME1 + " = " + fieldValue;
        Cursor cursor =myDatabase.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public String getData2() {
        // TODO Auto-generated method stub
        String[] columns = new String[]{KEY_ROWID, KEY_NAME,KEY_PHN};
        Cursor c = myDatabase.query(DATABASE_TABLE, columns, null, null, null, null,
                null);
        String result1 = "";

        int iphn = c.getColumnIndex(KEY_PHN);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result1 = result1 + c.getString(iphn)+" " +"\n";
        }
        return result1;
    }
    public boolean check(){
            boolean b=false;
        Cursor cursor = myDatabase.rawQuery("SELECT count(*) FROM notificationstatus",null);
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0)
        {

            b=false;

        }if(cursor.getInt(0)==0)
        {
           b=true;
        }



        cursor.close();

        return b;

    }

    public String getName(int l) throws SQLException{
// TODO Auto-generated method stub
        String[] columns = new String[]{ KEY_ROWID, KEY_NAME,KEY_PHN};
        Cursor c = myDatabase.query(DATABASE_TABLE, columns, KEY_ROWID +"="+l,
                null, null, null, null);
        if (c != null){
            c.moveToFirst();
            String name = c.getString(1);
            return name;
        }
        return null;
    }


    public String getPhn(int l) throws SQLException{
        // TODO Auto-generated method stub
        String[] columns = new String[]{ KEY_ROWID, KEY_NAME,KEY_PHN};
        Cursor c = myDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l,
                null, null, null, null);
        if (c != null){
            c.moveToFirst();
            String phn = c.getString(4);
            return phn;
        }
        return null;
    }
    public void updateEntry(long lRow, String mName,String mPhn) throws
            SQLException{
// TODO Auto-generated method stub
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_NAME, mName);

        cvUpdate.put(KEY_PHN, mPhn);

        myDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID+ "=" + lRow, null);
    }
    public void deleteEntry() throws SQLException{
// TODO Auto-generated method stub
        myDatabase.delete(DATABASE_TABLE, null,null);
    }
    public void deleteEntry1() throws SQLException{
// TODO Auto-generated method stub
        myDatabase.delete(DATABASE_TABLE1, null,null);
    }
}