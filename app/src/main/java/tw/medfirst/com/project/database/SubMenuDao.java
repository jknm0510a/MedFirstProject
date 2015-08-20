package tw.medfirst.com.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.Entity.SubMenuEntity;

/**
 * Created by KCTsai on 2015/6/26.
 */
public class SubMenuDao {
    // 表格名稱
//    public static final String TABLE_NAME = "product";
//    public static final String TABLE_NAME_COMPARE = "product_compare";
//    public enum TABLE_NAME_SELECTOR {
//        P1("topmenu"),
//        P2("topmenu_compare");
//        private String tableName;
//        TABLE_NAME_SELECTOR(final String name){
//            this.tableName = name;
//        }
//        public String getTableName(){
//            return tableName;
//        }
//    }
//    public static TABLE_NAME_SELECTOR tableNameSelector = TABLE_NAME_SELECTOR.P1;
    public static final String TABLE_NAME = "sub_menu";
    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";
    // 其它表格欄位名稱
    public static final String TOP_MENU_ID_COLUMN = "TopMenuId";
    public static final String SUB_MENU_TEXT_COLUMN = "SubMenuText";
    public static final String SORT_COLUMN = "sort";
    public static final String ID_COLUMN = "Id";
    public static final String BEGIN_DATE_COLUMN = "BeginDate";
    public static final String END_DATE_COLUMN = "EndDate";



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
                    TOP_MENU_ID_COLUMN + " INTEGER NOT NULL, " +
                    ID_COLUMN + " TEXT NOT NULL, " +
                    SUB_MENU_TEXT_COLUMN + " TEXT NOT NULL, " +
                    BEGIN_DATE_COLUMN + " INTEGER, " +
                    END_DATE_COLUMN + " INTEGER, " +
                    SORT_COLUMN + " INTEGER NOT NULL)";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public SubMenuDao(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public SubMenuEntity insert(SubMenuEntity item) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(TOP_MENU_ID_COLUMN, item.getTopId());
        cv.put(ID_COLUMN, item.getId2());
        cv.put(SUB_MENU_TEXT_COLUMN, item.getText());
        cv.put(BEGIN_DATE_COLUMN, item.getBeginDate());
        cv.put(END_DATE_COLUMN, item.getEndDate());
        cv.put(SORT_COLUMN, item.getSort());


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
    public boolean update(SubMenuEntity item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(TOP_MENU_ID_COLUMN, item.getTopId());
        cv.put(ID_COLUMN, item.getId2());
        cv.put(SUB_MENU_TEXT_COLUMN, item.getText());
        cv.put(BEGIN_DATE_COLUMN, item.getBeginDate());
        cv.put(END_DATE_COLUMN, item.getEndDate());
        cv.put(SORT_COLUMN, item.getSort());
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
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    public boolean deleteByTopId(long topId){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = TOP_MENU_ID_COLUMN + "=" + topId;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    // 刪除所有參數
    public boolean deleteAll(){

        return db.delete(TABLE_NAME, null, null) > 0;
    }

    // 讀取所有記事資料
    public List<SubMenuEntity> getAll() {
        List<SubMenuEntity> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public List<SubMenuEntity> getByTopId(long topId) {
        List<SubMenuEntity> result = new ArrayList<>();
        String where = TOP_MENU_ID_COLUMN + "=" + topId;
        Cursor cursor = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    //取得指定名字(text)的資料物件
    public SubMenuEntity getbyText(String text) {
        // 準備回傳結果用的物件
        SubMenuEntity item = null;
        // 使用編號為查詢條件
        String where = SUB_MENU_TEXT_COLUMN + "=?";
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, new String[] {text}, null, null, null, null);

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


    // 取得指定編號的資料物件
    public SubMenuEntity get(long id) {
        // 準備回傳結果用的物件
        SubMenuEntity item = null;
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
    public SubMenuEntity getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        SubMenuEntity result = new SubMenuEntity();

        result.setId(cursor.getLong(0));
        result.setTopId(cursor.getLong(1));
        result.setId2(cursor.getString(2));
        result.setText(cursor.getString(3));
        result.setBeginDate(cursor.getLong(4));
        result.setEndDate(cursor.getLong(5));
        result.setSort(cursor.getInt(6));
        // 回傳結果
        return result;
    }

//    public List<SubMenuEntity> Compare(String P1, String P2){
//        List<SubMenuEntity> result = new ArrayList<>();
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

    public List<SubMenuEntity> insert(List<SubMenuEntity> item){
        List<SubMenuEntity> result = new ArrayList<SubMenuEntity>();
        int i = 0;
        for(SubMenuEntity data : item){
//            Log.e("insert", Integer.toString(i++));
            result.add(insert(data));
        }
        return result;

    }
    // 建立範例資料
    public void sample() {
        SubMenuEntity item  = new SubMenuEntity(1, "1-1-1", "little cat",-1, -1, 1);
        SubMenuEntity item2 = new SubMenuEntity(1, "1-1-2", "big cat", -1, -1, 2);
        SubMenuEntity item3 = new SubMenuEntity(2, "1-2-1", "little dog",-1, -1, 1);
        SubMenuEntity item4 = new SubMenuEntity(2, "1-2-2", "big dog", -1, -1, 2);
        SubMenuEntity item5 = new SubMenuEntity(3, "1-3-1", "ASUS",-1, -1, 1);
        SubMenuEntity item6 = new SubMenuEntity(3, "1-3-2", "ACER",-1, -1, 2);
        SubMenuEntity item7 = new SubMenuEntity(4, "1-4-1", "TOYOTA", -1, -1, 1);
        SubMenuEntity item8 = new SubMenuEntity(4, "1-4-2", "BANZ",-1, -1, 2);
        List<SubMenuEntity> data = new ArrayList<>();
        data.add(item);
        data.add(item2);
        data.add(item3);
        data.add(item4);
        data.add(item5);
        data.add(item6);
        data.add(item7);
        data.add(item8);
        insert(data);

    }

//    public void sample2() {
//        List<SubMenuEntity> data = new ArrayList<SubMenuEntity>();
//        SubMenuEntity item = new SubMenuEntity(1, "p", "a.png", "201506240001.png", 5);
//        SubMenuEntity item2 = new SubMenuEntity(1, "p", "b.png", "201506240002.png", 4);
//        SubMenuEntity item3 = new SubMenuEntity(1, "p", "c.png", "201506240003.png", 3);
//        SubMenuEntity item4 = new SubMenuEntity(1, "p", "f.png", "201506240006.png", 2);
//        SubMenuEntity item5 = new SubMenuEntity(1, "p", "g.png", "201506240007.png", 1);
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
        List<SubMenuEntity> items;
        items = getAll();
        Log.e("TopMenuDao", TABLE_NAME);
        if(items != null) {
            for (int i = 0; i < items.size(); i++){
                SubMenuEntity e = items.get(i);
                Log.e("TopMenuDao", e.toString());
            }
        }
        Log.e("TopMenuDao", "////////////////////////////////");
        items = null;
    }

}
