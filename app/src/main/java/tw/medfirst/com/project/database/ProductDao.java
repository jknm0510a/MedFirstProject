package tw.medfirst.com.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.Entity.ProductEntity;
import tw.medfirst.com.project.Entity.ProductMenuEntity;

/**
 * Created by KCTsai on 2015/6/26.
 */
public class ProductDao {
    // ?????????W??????
    public static final String TAG = "ProductDao";
    //    public static final String TABLE_NAME = "product";
//    public static final String TABLE_NAME_COMPARE = "product_compare";
    public enum TABLE_NAME_SELECTOR {
        P1("product"),
        P2("product_compare");
        private String tableName;
        TABLE_NAME_SELECTOR(final String name){
            tableName = name;
        }
        public String getTableName(){
            return tableName;
        }
    }
    public static TABLE_NAME_SELECTOR tableNameSelector = TABLE_NAME_SELECTOR.P1;
    public static final String KEY_ID = "_id";

    public static final String PRODUCT_NO_COLUMN = "ProductNo";
    public static final String TYPE_COLUMN = "type";
    public static final String FILE_NAME_COLUMN = "fileName";   //????
    public static final String FILE_PATH_COLUMN = "filePath";
    public static final String SORT_COLUMN = "sort";

//    public static final String DATETIME_COLUMN = "datetime";
//    public static final String COLOR_COLUMN = "color";
//    public static final String TITLE_COLUMN = "title";
//    public static final String CONTENT_COLUMN = "content";
//    public static final String FILENAME_COLUMN = "filename";
//    public static final String LATITUDE_COLUMN = "latitude";
//    public static final String LONGITUDE_COLUMN = "longitude";
//    public static final String LASTMODIFY_COLUMN = "lastmodify";


    public static final String CREATE_TABLE_COMPARE =
            "CREATE TABLE " + "product_compare" + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRODUCT_NO_COLUMN + " TEXT NOT NULL, " +
                    TYPE_COLUMN + " TEXT NOT NULL, " +
                    FILE_NAME_COLUMN + " TEXT NOT NULL, " +
                    FILE_PATH_COLUMN + " TEXT NOT NULL, " +
                    SORT_COLUMN + " INTEGER NOT NULL)";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + "product" + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRODUCT_NO_COLUMN + " TEXT NOT NULL, " +
                    TYPE_COLUMN + " TEXT NOT NULL, " +
                    FILE_NAME_COLUMN + " TEXT NOT NULL, " +
                    FILE_PATH_COLUMN + " TEXT NOT NULL, " +
                    SORT_COLUMN + " INTEGER NOT NULL)";


//                    DATETIME_COLUMN + " INTEGER NOT NULL, " +
//                    COLOR_COLUMN + " INTEGER NOT NULL, " +
//                    TITLE_COLUMN + " TEXT NOT NULL, " +
//                    CONTENT_COLUMN + " TEXT NOT NULL, " +
//                    FILENAME_COLUMN + " TEXT, " +
//                    LATITUDE_COLUMN + " REAL, " +
//                    LONGITUDE_COLUMN + " REAL, " +
//                    LASTMODIFY_COLUMN + " INTEGER)";

    private SQLiteDatabase db;

    public ProductDao(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    public void close() {
        db.close();
    }

    public ProductEntity insert(ProductEntity item) {
        ContentValues cv = new ContentValues();

        cv.put(PRODUCT_NO_COLUMN, item.getPNo());
        cv.put(TYPE_COLUMN, item.getType());
        cv.put(FILE_NAME_COLUMN, item.getName());
        cv.put(FILE_PATH_COLUMN, item.getPath());
        cv.put(SORT_COLUMN, item.getSort());
//        cv.put(DATETIME_COLUMN, item.getDatetime());
//        cv.put(COLOR_COLUMN, item.getColor().parseColor());
//        cv.put(TITLE_COLUMN, item.getTitle());
//        cv.put(CONTENT_COLUMN, item.getContent());
//        cv.put(FILENAME_COLUMN, item.getFileName());
//        cv.put(LATITUDE_COLUMN, item.getLatitude());
//        cv.put(LONGITUDE_COLUMN, item.getLongitude());
//        cv.put(LASTMODIFY_COLUMN, item.getLastModify());


        long id = db.insert(tableNameSelector.getTableName(), null, cv);

        item.setId(id);
        return item;
    }

    public boolean update(ProductEntity item) {
        ContentValues cv = new ContentValues();

        cv.put(PRODUCT_NO_COLUMN, item.getPNo());
        cv.put(TYPE_COLUMN, item.getType());
        cv.put(FILE_NAME_COLUMN, item.getName());
        cv.put(FILE_PATH_COLUMN, item.getPath());
        cv.put(SORT_COLUMN, item.getSort());
//        cv.put(DATETIME_COLUMN, item.getDatetime());
//        cv.put(COLOR_COLUMN, item.getColor().parseColor());
//        cv.put(TITLE_COLUMN, item.getTitle());
//        cv.put(CONTENT_COLUMN, item.getContent());
//        cv.put(FILENAME_COLUMN, item.getFileName());
//        cv.put(LATITUDE_COLUMN, item.getLatitude());
//        cv.put(LONGITUDE_COLUMN, item.getLongitude());
//        cv.put(LASTMODIFY_COLUMN, item.getLastModify());

        String where = KEY_ID + "=" + item.getId();

        return db.update(tableNameSelector.getTableName(), cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(tableNameSelector.getTableName(), where , null) > 0;
    }
    public boolean deleteAll(){
        return db.delete(tableNameSelector.getTableName(), null, null) > 0;
    }

    public List<ProductEntity> getAll() {
        List<ProductEntity> result = new ArrayList<>();
        Cursor cursor = db.query(
                tableNameSelector.getTableName(), null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }
    public List<ProductEntity> getByPid(int pid){
        String where = PRODUCT_NO_COLUMN + "=" + pid;
        List<ProductEntity> result = new ArrayList<>();
        Cursor cursor = db.query(
                tableNameSelector.getTableName(), null, where, null, null, null, null, null);
        while(cursor.moveToNext()){
            result.add(getRecord(cursor));
        }
        cursor.close();
        return result;
    }

    public List<LittleEnitity> getAllPath(){
        List<LittleEnitity> result = new ArrayList<LittleEnitity>();
        String sql = "SELECT " + KEY_ID + ", " + FILE_PATH_COLUMN + " " +
                    "FROM " + tableNameSelector.getTableName();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor == null)
            return null;
        while(cursor.moveToNext()){
            LittleEnitity l = new LittleEnitity(cursor.getLong(0), cursor.getString(1));
            result.add(l);
        }
        return result;
    }

    public ProductEntity get(String path) {
        ProductEntity item = null;
        String where = FILE_PATH_COLUMN + "=?";
        Cursor result = db.query(
                tableNameSelector.getTableName(), null, where, new String[] {path}, null, null, null, null);

        if (result.moveToFirst()) {
            item = getRecord(result);
        }

        result.close();
        return item;
    }



    public ProductEntity get(long id) {
        ProductEntity item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                tableNameSelector.getTableName(), null, where, null, null, null, null, null);

        if (result.moveToFirst()) {
            item = getRecord(result);
        }

        result.close();
        return item;
    }

    public ProductEntity getRecord(Cursor cursor) {
        ProductEntity result = new ProductEntity();

        result.setId(cursor.getLong(0));
        result.setPNo(cursor.getString(1));
        result.setType(cursor.getString(2));
        result.setName(cursor.getString(3));
        result.setPath(cursor.getString(4));
        result.setSort(cursor.getInt(5));
//        result.setDatetime(cursor.getLong(1));
//        result.setColor(ItemActivity.getColors(cursor.getInt(2)));
//        result.setTitle(cursor.getString(3));
//        result.setContent(cursor.getString(4));
//        result.setFileName(cursor.getString(5));
//        result.setLatitude(cursor.getDouble(6));
//        result.setLongitude(cursor.getDouble(7));
//        result.setLastModify(cursor.getLong(8));

        return result;
    }

    public List<ProductEntity> Compare(String P1, String P2){
        List<ProductEntity> result = new ArrayList<>();
        String sql = "SELECT *" +
                     " FROM " + P1 +
                     " WHERE " + FILE_PATH_COLUMN + " Not IN " +
                     "(SELECT " + FILE_PATH_COLUMN +
                     " FROM " + P2 + ")";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor == null)
            return null;
        while(cursor.moveToNext())
            result.add(getRecord(cursor));
        cursor.close();
        return result;
//        while (cursor.moveToNext()) {
//            result.add(getRecord(cursor));
//        }
//
//        cursor.close();
//        return result;
    }


    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableNameSelector.getTableName(), null);

        if(cursor == null)
            return 0;
        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }

    public List<ProductEntity> insert(List<ProductEntity> item){
        List<ProductEntity> result = new ArrayList<ProductEntity>();
        int i = 0;
        for(ProductEntity data : item){
//            Log.e("insert", Integer.toString(i++));
            result.add(insert(data));
        }
        return result;

    }

    public void printAllData(){
        List<ProductEntity> items;
        items = getAll();
//        Log.e("TopMenuDao", TABLE_NAME);
        if(items != null) {
            for (int i = 0; i < items.size(); i++){
                ProductEntity e = items.get(i);
                Log.e(TAG, e.toString());
            }
        }
        if(tableNameSelector ==TABLE_NAME_SELECTOR.P1)
            Log.e("TopMenuDao1", "////////////////////////////////");
        else
            Log.e("TopMenuDao2", "////////////////////////////////");
        items = null;
    }

    public static void setTableName(TABLE_NAME_SELECTOR E){
        tableNameSelector = E;
    }

    public class LittleEnitity{
        private long id;
        private String path;
        private boolean check = false;

        public LittleEnitity(long id, String path){
            this.id = id;
            this.path = path;
        }

        public long getId(){
            return id;
        }
        public String getPath(){
            return path;
        }
        public boolean getCheck(){
            return check;
        }

        public void setId(long id){
            this.id = id;
        }
        public void setPath(String path){
            this.path = path;
        }
        public void setCheck(boolean b){
            this.check = b;
        }
    }

}
