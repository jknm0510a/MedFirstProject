package tw.medfirst.com.project.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tw.medfirst.com.project.Entity.DownloadEntity;
import tw.medfirst.com.project.R;
import tw.medfirst.com.project.activity.MainActivity;
import tw.medfirst.com.project.baseview.BaseActivity;
import tw.medfirst.com.project.database.DBOperation;
import tw.medfirst.com.project.database.ProductMenuDao;
import tw.medfirst.com.project.manager.HttpManager;
import tw.medfirst.com.project.manager.MessageManager;
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
        UpdateManager mUpdateManager = new UpdateManager(this, mHandler, "http://softfile.3g.qq.com:8080/msoft/179/24659/43549/qq_hd_mini_1.4.apk", "DDDDD.apk");
        mUpdateManager.checkUpdateInfo();
//        downloadList = new ArrayList<DownloadEntity>();
//        HttpManager.getInstance().getProductMenuRunnableFromHttp(this, mHandler, "121212", "1.1.1.1");
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
                AddDownloadList(msg.obj);
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
                AddDownloadList(msg.obj);
                HttpManager.getInstance().getGudanceInfoRunnableFromHttp(this, mHandler, "121212", "1.1.1.1");
                break;
            case MessageManager.GUDANCE_INFO_LOADING_ACCESS:
                List<Object> gudanceDatas = (List<Object>) msg.obj;
                DBOperation.getcInstance(this).setGudanceInfo2DB(gudanceDatas, mHandler);
                break;
            case MessageManager.GUDANCE_INFO_ADDING_ACCESS:
                AddDownloadList(msg.obj);
                DownloadMission();
                break;
        }

    }

    private void AddDownloadList(Object obj){
        if(obj == null)
            return;
        try{
            if(downloadList == null)
                downloadList = new ArrayList<DownloadEntity>((List<DownloadEntity>) obj);
            else
                downloadList.addAll((List<DownloadEntity>) obj);

        }catch (ClassCastException e){

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
//            String path = Application.videoPath + downloadList.get(i).getName();
//            if (!Application.checkIsFileExist(path))
                fixedThreadPool.execute(new DownloadRunnable(mHandler, downloadList.get(i), i));
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

    public void onNextClick(View view){
        startActivity(MainActivity.class);
    }


}
