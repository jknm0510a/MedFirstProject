package tw.medfirst.com.project;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import java.io.File;
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
    public final static String videoPath = Environment.getExternalStorageDirectory().getPath()+"/MedFirst/video/";
    public final static String imagePath = Environment.getExternalStorageDirectory().getPath()+"/MedFirst/image/";
    public static int screen_width;
    public static int screen_height;


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        createFileIfNeed(videoPath);
        createFileIfNeed(imagePath);
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

    public static boolean deleteFile2(final String path){
        File file = new File(path);
        return file.delete();
    }
//    public void resetGuidanceTimer(){
//
//    }
}
