package tw.medfirst.com.project.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KCTsai on 2015/6/26.
 */
public class MyDBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "mydata.db";

    public static final int VERSION = 1;

    private static SQLiteDatabase database;



    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public static SQLiteDatabase getDatabase(Context context) {
//        Log.e("onCreate2", "onCreate2");
        if (database == null || !database.isOpen()) {
            database = new MyDBHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }
//        database.execSQL(ProductDao.CREATE_TABLE);
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        Log.e("onCreate", "onCreate");
        db.execSQL(ProductDao.CREATE_TABLE);
        db.execSQL(ProductDao.CREATE_TABLE_COMPARE);
        db.execSQL(TopMenuDao.CREATE_TABLE);
        db.execSQL(SubMenuDao.CREATE_TABLE);
        db.execSQL(ProductMenuDao.CREATE_TABLE);
        db.execSQL(GudanceDao.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductDao.CREATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ProductDao.CREATE_TABLE_COMPARE);
        db.execSQL("DROP TABLE IF EXISTS " + TopMenuDao.CREATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SubMenuDao.CREATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ProductMenuDao.CREATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GudanceDao.CREATE_TABLE);
        onCreate(db);
    }
}
