package tw.medfirst.com.project;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;

import tw.medfirst.com.project.baseunit.Logger;

/**
 * Created by KCTsai on 2015/7/22.
 */
public class Application extends MultiDexApplication {
    public static int guidanceTimer = 0;
    public static String domain = "http://172.17.20.150/Medfirst";
    public final static String FILE_ROOT = Environment.getExternalStorageDirectory().getPath() + "/MedFirst";
    public final static String VEDIO_PATH = FILE_ROOT + "/video/";
    public final static String IMAGE_PAHT = FILE_ROOT + "/image/";
    public final static String APK_PATH = FILE_ROOT + "/apk/";
    public static int screen_width;
    public static int screen_height;


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        createFileIfNeed(VEDIO_PATH);
        createFileIfNeed(IMAGE_PAHT);
        createFileIfNeed(APK_PATH);
        screen_height = getResources().getDisplayMetrics().heightPixels;
        screen_width  = getResources().getDisplayMetrics().widthPixels;
        float d = getResources().getDisplayMetrics().density;
//        Logger.e("APP:::", d + ", " + screen_height + ", " + screen_width);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static long ObjToTimestamp(Object time){
        if(time == null || time.toString() == "")
            return -1;
        Calendar c = Calendar.getInstance();
        String str[] = time.toString().split("/");
        if(str.length != 3)
            return -1;
        c.set(Integer.parseInt(str[0]), Integer.parseInt(str[1]) - 1, Integer.parseInt(str[2]));
        Timestamp cTime = new Timestamp(c.getTimeInMillis());

        //check
//        Calendar d = Calendar.getInstance();
//        d.setTime(cTime);
//        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//        Log.e("Time", Long.toString(cTime.getTime()));
//        Log.e("Time", df.format(c.getTime()));
        return cTime.getTime();
    }

    public static void sendMessage(Handler mHandler, int what, int arg1, int arg2, Object obj){
        Message msg = mHandler.obtainMessage();
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }

    public static void createFileIfNeed(final String path){
        File file = new File(path);
        if(!file.exists())
            file.mkdirs();
    }


    public static boolean checkIsFileExist(final String path){
        File file = new File(path);
        if(file.exists())
            return true;
        else
            return false;
    }

    public static String getPath(String name, String type){
        String path = null;
        if(name == null || name.equals("") || type == null)
            return null;
        if(type.equals("v"))
            path = VEDIO_PATH + name;
        else if(type.equals("p"))
            path = IMAGE_PAHT + name;
        else if(type.equals("a"))
            path = APK_PATH + name;
        return path;
    }

    public static boolean deleteFile2(String name, String type){
        String path = getPath(name, type);

        if(path == null)
            return false;
        File file = new File(path);
        return file.delete();
    }

    public static Bitmap getBitmapFromRes(Context context, int resId){
        try{
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;

            InputStream is = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(is, null, opt);
//            Bitmap bitmap = BitmapFactory.decodeFile(sd + "/" + file);
//            return bitmap;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getBitmapFromSDCard(String path)
    {
        try{
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;

//            String sd = Environment.getExternalStorageDirectory().toString() + path;
            File file = new File(path);
            FileInputStream fileInputStream = null;
            if(file.exists()){
                fileInputStream = new FileInputStream(file);
            }
            return BitmapFactory.decodeStream(fileInputStream, null, opt);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
