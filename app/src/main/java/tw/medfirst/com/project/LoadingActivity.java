package tw.medfirst.com.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tw.medfirst.com.project.Entity.DownloadEntity;
import tw.medfirst.com.project.Entity.GudanceEntity;
import tw.medfirst.com.project.Entity.ProductMenuEntity;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.baseview.BaseActivity;
import tw.medfirst.com.project.database.DBOperation;
import tw.medfirst.com.project.database.ProductMenuDao;
import tw.medfirst.com.project.manager.HttpManager;
import tw.medfirst.com.project.manager.MessageManager;
import tw.medfirst.com.project.manager.TagManager;
import tw.medfirst.com.project.manager.UpdateManager;
import tw.medfirst.com.project.runnable.DownloadRunnable;

/**
 * Created by KCTsai on 2015/7/30.
 *
 * 目前讀取流程：自我更新 -> 商品介紹menu -> 商品介紹內容 -> 商品優惠menu -> .....
 */
public class LoadingActivity extends BaseActivity{
    private final static String TAG = "LoadingActivity";
    private final static int MAX_THREAD = 3;
    private List<DownloadEntity> downloadList;

    private ExecutorService fixedThreadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutID = R.layout.activity_loading;
        super.onCreate(savedInstanceState);
//        UpdateManager mUpdateManager = new UpdateManager(this);
//        mUpdateManager.checkUpdateInfo();
        downloadList = new ArrayList<DownloadEntity>();
        HttpManager.getInstance().getProductMenuRunnableFromHttp(this, mHandler, "121212", "1.1.1.1");
//        HttpManager.getInstance().getGudanceInfoRunnableFromHttp(this, mHandler, "121212", "1.1.1.1");
//        testThread();




    }

    @Override
    protected void processMessage(Message msg) {
        switch (msg.what){
            case MessageManager.PRODUCT_MENU_LOADING_ACCESS:
                List<Object> menuData = (List<Object>) msg.obj;
//                Logger.e(TAG, data.toString());
                DBOperation.getcInstance(this).setProductPageMenuDB(menuData, mHandler);
                DBOperation.getcInstance(this).printAllProductMenu();
                break;
            case MessageManager.PRODUCT_MENU_ADDING_ACCESS:
                ProductMenuDao productMenuDao = new ProductMenuDao(this);
                List<String> productDatas = productMenuDao.getAllPId();
                String str = productDatas.toString();
                str = str.replace("[", "");
                str = str.replace("]", "");
                str = str.replace(" ", "");
//                Logger.e(TAG, str);
                HttpManager.getInstance().getProductInfoRunnableFromHttp(this, mHandler, "121212", "1.1.1.1", str);


                break;
            case MessageManager.PRODUCT_INFO_LOADING_ACCESS:
                List<Object> infoData = (List<Object>) msg.obj;
                DBOperation.getcInstance(this).setProductPageInfo2DB(infoData, mHandler);
                break;
            case MessageManager.PRODUCT_INFO_ADDING_ACCESS:
                if(downloadList == null)
                    downloadList = new ArrayList<DownloadEntity>((List<DownloadEntity>) msg.obj);
                else
                    downloadList.addAll((List<DownloadEntity>) msg.obj);
                HttpManager.getInstance().getGudanceInfoRunnableFromHttp(this, mHandler, "121212", "1.1.1.1");
//                for(DownloadEntity d : downloadList) {
//                    Logger.e(TAG, d.getType() + ", " + d.getPath());
//                }
                break;
            case MessageManager.GUDANCE_INFO_LOADING_ACCESS:
                List<Object> gudanceDatas = (List<Object>) msg.obj;
                DBOperation.getcInstance(this).setGudanceInfo2DB(gudanceDatas, mHandler);
                if (gudanceDatas == null)
                    return;
                List<GudanceEntity> gudanceList = new ArrayList<>();
                for(Object obj : gudanceDatas){

                    Map<String, Object> gudanceData = (Map<String, Object>) obj;
                    double sort = (double) gudanceData.get("sort");
                    GudanceEntity g = new GudanceEntity(
                            (String)gudanceData.get("beginDate"), (String)gudanceData.get("endDate"),
                            (String)gudanceData.get("beginTime"), (String)gudanceData.get("endTime"),
                            (String)gudanceData.get("mediaUrl"), (int)sort);
                    gudanceList.add(g);

                    downloadList.add(new DownloadEntity("v", g.getUrl(), g.getFileName()));
                }
//                Logger.e(TAG, gudanceList.size() + ", " + downloadList.size());
                DownloadMission();
                break;
        }

    }

    private void DownloadMission() {

//        downloadList.add(new DownloadEntity("p", "/Content/uploads/64e0fbf8-6ded-4f74-9c49-ac2d18d00fc0/635714285841680854.jpg", "635714285841680854.jpg"));
//        downloadList.add(new DownloadEntity("v", "/Content/uploads/021a4d6f-308a-46c2-ab24-eb0aeceac1b1/635724721531434309.mp4", "635724721531434309.mp4"));
//        downloadList.add(new DownloadEntity("v", "/Content/uploads/021a4d6f-308a-46c2-ab24-eb0aeceac1b1/635724721531434309.mp4", "635724721531434309.mp4"));
        if(downloadList == null || downloadList.size() == 0)
            return;
        if(fixedThreadPool == null)
            fixedThreadPool = Executors.newFixedThreadPool(MAX_THREAD);
        for(int i = 0; i < downloadList.size(); i++) {
            //check is exist????
            String path = Application.videoPath + downloadList.get(i).getName();
//            if (!Application.checkIsFileExist(path))
            fixedThreadPool.execute(new DownloadRunnable(downloadList.get(i), i));
//            else
//                Logger.e(TAG, "index " + i + " " + downloadList.get(i).getName() + " is exist");
        }

    }


    @Override
    protected void processLoading() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        runSwitch = false;
    }



}
