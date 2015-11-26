package schnauzer.digital.autoamigo;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Hashtable;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.DatabaseUtils;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AutoAmigoDB.db";
    public static final String USERS_TABLE_NAME = "users";
    public static final String RIDE_TABLE_NAME = "ride";
    public static final String POST_TABLE_NAME = "post";

    public static final String USER_COLUMN_ID = "id_user";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_LASTNAME = "lastname";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_TYPE = "type";
    public static final String USER_COLUMN_HANDICAP = "handicap";
    public static final String USER_COLUMN_PHONE = "phone";



    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table users " + "(id integer primary key not null, name text,lastname text,phone text,email text, handicap text,type text)"+
                "create table post " + "(id integer primary key not null,id_user integer,date text,hour text,content text)"+
                "create table ride " + "(id integer primary key not null,id_user integer,name text,date text,hs text,hs_ampm text,hl text,hl_ampm text)"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    //Users
    public boolean insertUser  (String name, String lastname ,String phone, String email, String handicap,String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("lastname", lastname);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("handicap", handicap);
        contentValues.put("type",type);
        db.insert("users", null, contentValues);
        return true;
    }
    public Cursor searchData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users where id="+id+"", null );
        return res;
    }
    public int numberOfUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME);
        return numRows;
    }
    public boolean updateUser (Integer id, String name,String lastname ,String phone, String email, String handicap,String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("lastname", lastname);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("handicap", handicap);
        contentValues.put("type",type);
        db.update("users", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }
    public Integer deleteUser (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    //Post
    public boolean insertPost  (Integer id_user,String date, String hour, String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_user", id_user);
        contentValues.put("date", date);
        contentValues.put("hour", hour);
        contentValues.put("content", content);
        db.insert("post", null, contentValues);
        return true;
    }
    public Cursor searchPost(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from post where id="+id+"", null );
        return res;
    }
    public int numberOfPost(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, POST_TABLE_NAME);
        return numRows;
    }
    public boolean updatePost (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        db.update("post", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deletePost (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("post",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    //Ride
     public boolean insertRide  (Integer id_user,String nameRide,String date, String HS,String HSampm,String HL,String HLampm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_user", id_user);
        contentValues.put("name", nameRide);
        contentValues.put("date", date);
        contentValues.put("hs", HS);
         contentValues.put("hs_ampm", HSampm);
         contentValues.put("hl", HL);
         contentValues.put("hl_ampm", HLampm);
        db.insert("ride", null, contentValues);
        return true;
    }
    public Cursor searchRide(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from ride where id="+id+"", null );
        return res;
    }
    public int numberOfRide(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, RIDE_TABLE_NAME);
        return numRows;
    }
    public boolean updateRide (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        db.update("ride", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteRide (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("ride",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


}