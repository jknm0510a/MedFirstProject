package tw.medfirst.com.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.Entity.TopMenuEntity;

/**
 * Created by KCTsai on 2015/6/26.
 */
public class TopMenuDao {
    // ���W��
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
    public static final String TABLE_NAME = "top_menu";
    // �s��������W�١A�T�w����
    public static final String KEY_ID = "_id";
    // �䥦������W��
    public static final String TOP_MENU_TEXT_COLUMN = "TopMenuText";
    public static final String ID_COLUMN = "Id";
    public static final String BEGIN_DATE_COLUMN = "BeginDate";
    public static final String END_DATE_COLUMN = "EndDate";
    public static final String SORT_COLUMN = "sort";



    // �ϥΤW���ŧi���ܼƫإߪ�檺SQL��O
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
                    ID_COLUMN + " TEXT NOT NULL, " +
                    TOP_MENU_TEXT_COLUMN + " TEXT NOT NULL, " +
                    BEGIN_DATE_COLUMN + " INTEGER, " +
                    END_DATE_COLUMN + " INTEGER, " +
                    SORT_COLUMN + " INTEGER NOT NULL)";

    // ��Ʈw����
    private SQLiteDatabase db;

    // �غc�l�A�@�몺���γ����ݭn�ק�
    public TopMenuDao(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    // ������Ʈw�A�@�몺���γ����ݭn�ק�
    public void close() {
        db.close();
    }

    // �s�W�Ѽƫ�w������
    public TopMenuEntity insert(TopMenuEntity item) {
        // �إ߷ǳƷs�W��ƪ�ContentValues����
        ContentValues cv = new ContentValues();

        // �[�JContentValues����]�˪��s�W���
        // �Ĥ@�ӰѼƬO���W�١A �ĤG�ӰѼƬO��쪺���
        cv.put(ID_COLUMN, item.getId2());
        cv.put(TOP_MENU_TEXT_COLUMN, item.getText());
        cv.put(BEGIN_DATE_COLUMN, item.getBeginDate());
        cv.put(END_DATE_COLUMN, item.getEndDate());
        cv.put(SORT_COLUMN, item.getSort());


        // �s�W�@����ƨè�o�s��
        // �Ĥ@�ӰѼƬO���W��
        // �ĤG�ӰѼƬO�S����w���Ȫ��w�]��
        // �ĤT�ӰѼƬO�]�˷s�W��ƪ�ContentValues����
        long id = db.insert(TABLE_NAME, null, cv);

        // �]�w�s��
        item.setId(id);
        // �^�ǵ��G
        return item;
    }

    // �ק�Ѽƫ�w������
    public boolean update(TopMenuEntity item) {
        // �إ߷ǳƭק��ƪ�ContentValues����
        ContentValues cv = new ContentValues();

        // �[�JContentValues����]�˪��ק���
        // �Ĥ@�ӰѼƬO���W�١A �ĤG�ӰѼƬO��쪺���
        cv.put(ID_COLUMN, item.getId2());
        cv.put(TOP_MENU_TEXT_COLUMN, item.getText());
        cv.put(BEGIN_DATE_COLUMN, item.getBeginDate());
        cv.put(END_DATE_COLUMN, item.getEndDate());
        cv.put(SORT_COLUMN, item.getSort());
        // �]�w�ק��ƪ���󬰽s��
        // �榡���u���W�١׸�ơv
        String where = KEY_ID + "=" + item.getId();

        // ����ק��ƨæ^�ǭק諸��Ƽƶq�O�_���\
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // �R���Ѽƫ�w�s�������
    public boolean delete(long id){
        // �]�w��󬰽s���A�榡���u���W��=��ơv
        String where = KEY_ID + "=" + id;
        // �R����w�s����ƨæ^�ǧR���O�_���\
        return db.delete(TABLE_NAME, where , null) > 0;
    }
    // �R���Ҧ��Ѽ�
    public boolean deleteAll(){

        return db.delete(TABLE_NAME, null, null) > 0;
    }

    // Ū��Ҧ��O�Ƹ��
    public List<TopMenuEntity> getAll() {
        List<TopMenuEntity> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public TopMenuEntity get(String text) {
        // �ǳƦ^�ǵ��G�Ϊ�����
        TopMenuEntity item = null;
        // �ϥνs�����d�߱��
        String where = TOP_MENU_TEXT_COLUMN + "=?";
        // ����d��
        Cursor result = db.query(
                TABLE_NAME, null, where, new String[] {text}, null, null, null, null);

        // �p�G���d�ߵ��G
        if (result.moveToFirst()) {
            // Ū��]�ˤ@����ƪ�����
            item = getRecord(result);
        }

        // ����Cursor����
        result.close();
        // �^�ǵ��G
        return item;
    }


    // ��o��w�s������ƪ���
    public TopMenuEntity get(long id) {
        // �ǳƦ^�ǵ��G�Ϊ�����
        TopMenuEntity item = null;
        // �ϥνs�����d�߱��
        String where = KEY_ID + "=" + id;
        // ����d��
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // �p�G���d�ߵ��G
        if (result.moveToFirst()) {
            // Ū��]�ˤ@����ƪ�����
            item = getRecord(result);
        }

        // ����Cursor����
        result.close();
        // �^�ǵ��G
        return item;
    }

    // ��Cursor�ثe����ƥ]�ˬ�����
    public TopMenuEntity getRecord(Cursor cursor) {
        // �ǳƦ^�ǵ��G�Ϊ�����
        TopMenuEntity result = new TopMenuEntity();

        result.setId(cursor.getLong(0));
        result.setId2(cursor.getString(1));
        result.setText(cursor.getString(2));
        result.setBeginDate(cursor.getLong(3));
        result.setEndDate(cursor.getLong(4));
        result.setSort(cursor.getInt(5));

        // �^�ǵ��G
        return result;
    }

    // ��o��Ƽƶq
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

    public List<TopMenuEntity> insert(List<TopMenuEntity> item){
        List<TopMenuEntity> result = new ArrayList<TopMenuEntity>();
        int i = 0;
        for(TopMenuEntity data : item){
//            Log.e("insert", Integer.toString(i++));
            result.add(insert(data));
        }
        return result;

    }
    // �إ߽d�Ҹ��
    public void sample() {
        TopMenuEntity item = new TopMenuEntity("1-1", "Cat", -1, -1, 1);
        TopMenuEntity item2 = new TopMenuEntity("1-2", "Dog", -1, -1, 2);
        TopMenuEntity item3 = new TopMenuEntity("1-3", "Computer", -1, -1, 3);
        TopMenuEntity item4 = new TopMenuEntity("1-4", "Car",-1, -1, 4);
        List<TopMenuEntity> data = new ArrayList<>();
        data.add(item);
        data.add(item2);
        data.add(item3);
        data.add(item4);
        insert(data);

    }

    public void printAllData(){
        List<TopMenuEntity> items;
        items = getAll();
        Log.e("TopMenuDao", TABLE_NAME);
        if(items != null) {
            for (int i = 0; i < items.size(); i++){
                TopMenuEntity e = items.get(i);
                Log.e("TopMenuDao", e.toString());
            }
        }
        Log.e("TopMenuDao", "////////////////////////////////");
        items = null;
    }

}
