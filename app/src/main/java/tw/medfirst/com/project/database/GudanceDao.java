package tw.medfirst.com.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.Entity.GudanceEntity;
import tw.medfirst.com.project.Entity.ProductMenuEntity;

/**
 * Created by KCTsai on 2015/6/26.
 */
public class GudanceDao {

    public static final String TABLE_NAME = "gudance";
    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";
    // 其它表格欄位名稱
//    public static final String BEGIN_DATE_COLUMN = "TopMenuId";
//    public static final String SUB_MENU_ID_COLUMN = "SubMenuId";
//    public static final String PRODUCT_ID_COLUMN = "ProductId";
//    public static final String ID_COLUMN = "Id";
//    public static final String SUB_PIC_PATH_COLUMN = "SubPicPath";
//    public static final String TIME_COLUMN = "Time";
    public static final String BEGIN_DATE_COLUMN = "BeginDate";
    public static final String END_DATE_COLUMN = "EndDate";
    public static final String BEGIN_TIME_COLUMN = "BeginTime";
    public static final String END_TIME_COLUMN = "EndTime";
    public static final String MEDIA_URL_COLUMN = "mediaUrl";
    public static final String SORT_COLUMN = "Sort";
    public static final String MEDIA_NAME = "mediaName";



    // 使用上面宣告的變數建立表格的SQL指令
//    public static final String CREATE_TABLE_COMPARE =
//            "CREATE TABLE " + "product_compare" + " (" +
//                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    PRODUCT_ID_COLUMN + " INTEGER NOT NULL, " +
//                    TYPE_COLUMN + " TEXT NOT NULL, " +
//                    FILE_NAME_COLUMN + " TEXT NOT NULL, " +
//                    FILE_PATH_COLUMN + " TEXT NOT NULL, " +
//                    SORT_COLUMN + " TEXT NOT NULL)";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BEGIN_DATE_COLUMN + " STRING, " +
                    END_DATE_COLUMN + " STRING, " +
                    BEGIN_TIME_COLUMN + " STRING, " +
                    END_TIME_COLUMN + " STRING, " +
                    MEDIA_URL_COLUMN + " STRING, " +
                    SORT_COLUMN + " INTEGER NOT NULL, "+
                    MEDIA_NAME + " STRING)";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public GudanceDao(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public GudanceEntity insert(GudanceEntity item) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(BEGIN_DATE_COLUMN, item.getBeginDate());
        cv.put(END_DATE_COLUMN, item.getEndDate());
        cv.put(BEGIN_TIME_COLUMN, item.getBeginTime());
        cv.put(END_TIME_COLUMN, item.getEndTime());
        cv.put(MEDIA_URL_COLUMN, item.getUrl());
        cv.put(SORT_COLUMN, item.getSort());
        cv.put(MEDIA_NAME, item.getFileName());

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        item.setId(id);
        // 回傳結果
        return item;
    }

    // 修改參數指定的物件
    public boolean update(GudanceEntity item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(BEGIN_DATE_COLUMN, item.getBeginDate());
        cv.put(END_DATE_COLUMN, item.getEndDate());
        cv.put(BEGIN_TIME_COLUMN, item.getBeginTime());
        cv.put(END_TIME_COLUMN, item.getEndTime());
        cv.put(MEDIA_URL_COLUMN, item.getUrl());
        cv.put(SORT_COLUMN, item.getSort());
        cv.put(MEDIA_NAME, item.getFileName());
        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + item.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 刪除所有參數
    public boolean deleteAll(){

        return db.delete(TABLE_NAME, null, null) > 0;
    }

    // 讀取所有記事資料
    public List<GudanceEntity> getAll() {
        List<GudanceEntity> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public List<String> getAllPId(){
        List<String> result = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT DISTINCT ProductId FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        return result;
    }

    // 取得指定編號的資料物件
    public GudanceEntity get(long id) {
        // 準備回傳結果用的物件
        GudanceEntity item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return item;
    }

    // 把Cursor目前的資料包裝為物件
    public GudanceEntity getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        GudanceEntity result = new GudanceEntity();

        result.setId(cursor.getLong(0));
        result.setBeginDate(cursor.getString(1));
        result.setEndDate(cursor.getString(2));
        result.setBeginTime(cursor.getString(3));
        result.setEndTime(cursor.getString(4));
        result.setUrl(cursor.getString(5));
        result.setSort(cursor.getInt(6));
        result.setFileName(cursor.getString(7));
        // 回傳結果
        return result;
    }

//    public List<ProductMenuEntity> Compare(String P1, String P2){
//        List<ProductMenuEntity> result = new ArrayList<>();
//        String sql = "SELECT *" +
//                     " FROM " + P1 +
//                     " WHERE " + FILE_NAME_COLUMN + " Not IN " +
//                     "(SELECT " + FILE_NAME_COLUMN +
//                     " FROM " + P2 + ")";
//        Cursor cursor = db.rawQuery(sql, null);
//        if(cursor == null)
//            return null;
//        while(cursor.moveToNext())
//            result.add(getRecord(cursor));
//        cursor.close();
//        return result;
//    }


    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if(cursor == null)
            return 0;
        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }

    public List<GudanceEntity> insert(List<GudanceEntity> item){
        List<GudanceEntity> result = new ArrayList<GudanceEntity>();
        int i = 0;
        for(GudanceEntity data : item){
//            Log.e("insert", Integer.toString(i++));
            result.add(insert(data));
        }
        return result;

    }
    // 建立範例資料
//    public void sample() {
//        ProductMenuEntity item  = new ProductMenuEntity(1, 1, "1", "001.png", 1, " ", -1, -1);
//        ProductMenuEntity item2 = new ProductMenuEntity(1, 2, "2", "002.png", 1, " ", -1, -1);
//        ProductMenuEntity item3 = new ProductMenuEntity(2, 1, "3", "003.png", 1, " ", -1, -1);
////        ProductMenuEntity item4 = new ProductMenuEntity(2, 2, "4", "004.png", 100000, 1);
////        ProductMenuEntity item5 = new ProductMenuEntity(3, 1, "5", "005.png", 100000, 1);
////        ProductMenuEntity item6 = new ProductMenuEntity(3, 2, "6", "006.png", 100000, 1);
////        ProductMenuEntity item7 = new ProductMenuEntity(4, 1, "7", "007.png", 100000, 1);
////        ProductMenuEntity item8 = new ProductMenuEntity(4, 2, "8", "008.png", 100000, 1);
//        List<ProductMenuEntity> data = new ArrayList<>();
//        data.add(item);
//        data.add(item2);
//        data.add(item3);
////        data.add(item4);
////        data.add(item5);
////        data.add(item6);
////        data.add(item7);
////        data.add(item8);
//        insert(data);
//
//    }

//    public void sample2() {
//        List<ProductMenuEntity> data = new ArrayList<ProductMenuEntity>();
//        ProductMenuEntity item = new ProductMenuEntity(1, "p", "a.png", "201506240001.png", 5);
//        ProductMenuEntity item2 = new ProductMenuEntity(1, "p", "b.png", "201506240002.png", 4);
//        ProductMenuEntity item3 = new ProductMenuEntity(1, "p", "c.png", "201506240003.png", 3);
//        ProductMenuEntity item4 = new ProductMenuEntity(1, "p", "f.png", "201506240006.png", 2);
//        ProductMenuEntity item5 = new ProductMenuEntity(1, "p", "g.png", "201506240007.png", 1);
//        data.add(item);
//        data.add(item2);
//        data.add(item3);
//        data.add(item4);
//        data.add(item5);
//        insert(data);
//    }

//    public static void setTableName(TABLE_NAME_SELECTOR E){
//        tableNameSelector = E;
//    }

    public void printAllData(){
        List<GudanceEntity> items;
        items = getAll();
        Log.e("TopMenuDao", TABLE_NAME);
        if(items != null) {
            for (int i = 0; i < items.size(); i++){
                GudanceEntity e = items.get(i);
                Log.e("GudanceDao", e.toString());
            }
        }
        Log.e("GudanceDao", "////////////////////////////////");
        items = null;
    }

}
