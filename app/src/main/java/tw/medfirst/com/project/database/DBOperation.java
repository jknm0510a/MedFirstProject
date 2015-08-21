package tw.medfirst.com.project.database;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.medfirst.com.project.Entity.DownloadEntity;
import tw.medfirst.com.project.Entity.GudanceEntity;
import tw.medfirst.com.project.Entity.ProductEntity;
import tw.medfirst.com.project.database.ProductDao.LittleEnitity;
import tw.medfirst.com.project.Entity.ProductMenuEntity;
import tw.medfirst.com.project.Entity.SubMenuEntity;
import tw.medfirst.com.project.Entity.TopMenuEntity;
import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.manager.MessageManager;

/**
 * Created by KCTsai on 2015/6/30.
 */
public class DBOperation {
    private final static String TAG = "DBOperation";
    private static DBOperation cInstance;
    private final Context mContext;
    private static Context context;
    private static ProductDao productDao;
    private static TopMenuDao topMenuDao;
    private static SubMenuDao subMenuDao;
    private static ProductMenuDao productMenuDao;
    private static GudanceDao gudanceDao;

    private DBOperation(Context context){
        mContext = context;
        cInstance = this;
    }

    public static DBOperation getcInstance(Context context){
        if (cInstance == null) {
            cInstance = new DBOperation(context);
            productDao = new ProductDao(context);
            topMenuDao = new TopMenuDao(context);
            subMenuDao = new SubMenuDao(context);
            gudanceDao = new GudanceDao(context);

            productMenuDao = new ProductMenuDao(context);
        }
        //
        return cInstance;
    }

    public void printAllProductMenu(){
        topMenuDao.printAllData();
        subMenuDao.printAllData();
        productMenuDao.printAllData();
    }

    public void setProductPageMenuDB(List<Object> tops, Handler handler){
        processingTop(tops, handler);
    }

    private void processingTop(List<Object> tops, Handler handler) {
        List<TopMenuEntity> datas = topMenuDao.getAll();
        List<DownloadEntity> downloadList = new ArrayList<DownloadEntity>();   //下載清單
        if(tops == null)
            return;

        for(Object top : tops){
            String topText = ((LinkedTreeMap<String, Object>) top).get("menu").toString();
            double sort = (double) ((LinkedTreeMap<String, Object>) top).get("sort");
            List<Object> subs = (List<Object>) ((LinkedTreeMap<String, Object>) top).get("context");
            String id2 = (String) ((LinkedTreeMap<String, Object>) top).get("id");
            long beginDate = Application.ObjToTimestamp(((LinkedTreeMap<String, Object>) top).get("beginDate"));
            long endDate = Application.ObjToTimestamp(((LinkedTreeMap<String, Object>) top).get("endDate"));
            TopMenuEntity newData = new TopMenuEntity(id2, topText, beginDate, endDate, (int)sort);
            boolean isUpdate = false;
            long id = 0;

            //update if database not null & find equal text from API
            for (TopMenuEntity data : datas) {
                if (data.getId2().equals(id2)) {
                    newData.setId(data.getId());
                    id = newData.getId();
                    topMenuDao.update(newData);
                    data.setCheck(true);
                    isUpdate = true;
                }
            }
            //insert
            if(!isUpdate) {
                id = (topMenuDao.insert(newData)).getId();
                newData.setId(id);
                newData.setCheck(true);
                datas.add(newData);
            }
            processingSub(subs, id, downloadList);
        }
        //delete data if not checked
        for (TopMenuEntity data : datas)
            if(!data.getCheck()) {
                topMenuDao.delete(data.getId());
                subMenuDao.deleteByTopId(data.getId());
                productMenuDao.deleteByTopId(data.getId());
            }
        Application.sendMessage(handler, MessageManager.PRODUCT_MENU_ADDING_ACCESS, 0, 0, downloadList);
    }

    private void processingSub(List<Object> subs, final long id, List<DownloadEntity> downloadList) {
        List<SubMenuEntity> datas = subMenuDao.getByTopId(id);
        if(subs == null)
            return;

//        subs.remove(1);
        for(Object sub : subs){
            String subText = ((LinkedTreeMap<String, Object>) sub).get("subMenu").toString();
            double sort = (double) ((LinkedTreeMap<String, Object>) sub).get("sort");
            String id2 = (String) ((LinkedTreeMap<String, Object>) sub).get("id");
            long begineDate = Application.ObjToTimestamp(((LinkedTreeMap<String, Object>) sub).get("beginDate"));
            long endDate = Application.ObjToTimestamp(((LinkedTreeMap<String, Object>) sub).get("endDate"));
            List<Object> productMenus = (List<Object>) ((LinkedTreeMap<String, Object>) sub).get("productContext");
            SubMenuEntity newData = new SubMenuEntity(id, id2, subText,begineDate, endDate, (int)sort);
            boolean isUpdate = false;
            long subId = 0;

            //update if database not null & find equal text from API
            for (SubMenuEntity data : datas) {
                if (data.getId2().equals(id2)) {
                    newData.setId(data.getId());
                    subId = newData.getId();
                    subMenuDao.update(newData);
                    data.setCheck(true);
                    isUpdate = true;
                }
            }
            //insert
            if(!isUpdate) {
                subId = (subMenuDao.insert(newData)).getId();
                newData.setId(id);
                newData.setCheck(true);
                datas.add(newData);
            }
            processingProduct(productMenus, id, subId, downloadList);
        }
        for (SubMenuEntity data : datas) {
            if (!data.getCheck()) {
                subMenuDao.delete(data.getId());
                productMenuDao.deleteBySubId(data.getId());
            }
        }


    }

    private void processingProduct(List<Object> products, final long topId, final long subId, List<DownloadEntity> downloadList) {
        List<ProductMenuEntity> datas = productMenuDao.getByTopSubId(topId, subId);
        if(products == null)
            return;

        for(Object product : products){
            String pId = ((LinkedTreeMap<String, Object>) product).get("productNo").toString();
            double sort = (double) ((LinkedTreeMap<String, Object>) product).get("sort");
            String subPicPath = ((LinkedTreeMap<String, Object>) product).get("thumbnail") == null?
                    "":((LinkedTreeMap<String, Object>) product).get("thumbnail").toString();
//            int time = (int) ((LinkedTreeMap<String, Object>) product).get("time");
            String id2 = (String) ((LinkedTreeMap<String, Object>) product).get("id");
            long beginDate = Application.ObjToTimestamp(((LinkedTreeMap<String, Object>) product).get("beginDate"));
            long endDate = Application.ObjToTimestamp(((LinkedTreeMap<String, Object>) product).get("endDate"));
            ProductMenuEntity newData = new ProductMenuEntity(topId, subId, pId, subPicPath, (int)sort, id2, beginDate, endDate);
            boolean isUpdate = false;
            for (ProductMenuEntity data : datas) {
                if (data.getId2().equals(id2)) {
                    newData.setId(data.getId());
//                    id2 = newData.getId();
                    productMenuDao.update(newData);
                    data.setCheck(true);
                    isUpdate = true;
                }
            }
            if(!isUpdate) {
                productMenuDao.insert(newData);
                newData.setId(topId);
                newData.setCheck(true);
                datas.add(newData);
            }
            downloadList.add(new DownloadEntity("p", subPicPath, newData.getPicName()));
        }

        for (ProductMenuEntity data : datas) {
            if (!data.getCheck()) {
                Application.deleteFile2(data.getPicName(), "p");
                productMenuDao.delete(data.getId());
            }
        }
//        productMenuDao.printAllData();
    }

    public void setProductPageInfo2DB(List<Object> datas, Handler handler){
        processingProductInfo(datas, handler);
    }

    /**
     * 先把json解開，依序放入List<ProductEntity>內
     * @param datas
     * @param handler
     */
    private void processingProductInfo(List<Object> datas, Handler handler) {
        if(datas == null)
            return;

        List<ProductEntity> entities = decomposeProductJson(datas);

        if(entities == null)
            return;
        ProductDao.tableNameSelector = ProductDao.TABLE_NAME_SELECTOR.P1;
        List<ProductDao.LittleEnitity> data = productDao.getAllPath();  //DB整理出來的資料
        List<DownloadEntity> downloadList = new ArrayList<DownloadEntity>();   //下載清單
        for(ProductEntity p2 : entities){
            boolean isUpdate = false;
            for(ProductDao.LittleEnitity l : data){
                if(l.getPath().equals(p2.getPath())){
                    isUpdate = true;
                    p2.setId(l.getId());
                    productDao.update(p2);
                    l.setCheck(true);
                }
            }

            if(!isUpdate){
                long id = productDao.insert(p2).getId();
                ProductDao.LittleEnitity b = productDao.new LittleEnitity(id, p2.getPath());
                b.setCheck(true);
                data.add(b);
            }

            DownloadEntity d = new DownloadEntity(p2.getType(), p2.getPath(), p2.getName());
            downloadList.add(d);
        }

        for(ProductDao.LittleEnitity d : data){
            if(!d.getCheck()) {
                ProductEntity p = productDao.get(d.getId());
                if(p == null)
                    return;
                Application.deleteFile2(p.getName(), p.getType());
                productDao.delete(d.getId());

            }

        }

        //send message back to activity

        Application.sendMessage(handler, MessageManager.PRODUCT_INFO_ADDING_ACCESS, 0, 0, downloadList);
//        productDao.printAllData();

    }

    private List<ProductEntity> decomposeProductJson(List<Object> datas){
        List<ProductEntity> entities = new ArrayList<ProductEntity>();
        for(Object obj : datas){

            Map<String, Object> data = (Map<String, Object>) obj;
            String productNo = (String) data.get("productNo");
            List<Object> pages = (List<Object>) data.get("fileContext");
            if(pages != null){
                for(Object obj2 : pages){
                    ProductEntity entity = new ProductEntity();
                    Map<String, Object> page = (Map<String, Object>) obj2;
                    entity.setPNo(productNo);
                    entity.setType((String) page.get("type"));
//                    entity.setName("");  //保留欄位
                    entity.setPath((String) page.get("url"));
                    double d = (double) page.get("sort");
                    entity.setSort((int) d);
                    entities.add(entity);
                }
            }
        }
        return entities;
    }

    public void setGudanceInfo2DB(List<Object> gudanceDatas, Handler handler){
        List<GudanceEntity> gudanceList =  decomposeGudanceJson(gudanceDatas);
        List<DownloadEntity> downloadList = new ArrayList<DownloadEntity>();   //下載清單
        if(gudanceList == null)
            return;
//        gudanceList.remove(0);

        List<GudanceEntity> dbDatas = gudanceDao.getAll();
        for(GudanceEntity data : gudanceList){
            String name = data.getFileName();
            boolean isUpdate = false;

            for(GudanceEntity dbData : dbDatas){
                if(dbData.getFileName().equals(name)){
                    data.setId(dbData.getId());
                    gudanceDao.update(data);
                    dbData.setCheck(true);
                    isUpdate = true;
                }
            }
            if(!isUpdate){
                data = gudanceDao.insert(data);
                data.setCheck(true);
                dbDatas.add(data);
            }
            DownloadEntity d = new DownloadEntity("v", data.getUrl(), data.getFileName());
            downloadList.add(d);
        }
        for(GudanceEntity dbData : dbDatas){
            if(!dbData.getCheck()) {
                Application.deleteFile2(dbData.getFileName(), "v");
                gudanceDao.delete(dbData.getId());
            }
        }
        Application.sendMessage(handler, MessageManager.GUDANCE_INFO_ADDING_ACCESS, 0, 0, downloadList);


    }

    public List<GudanceEntity> decomposeGudanceJson(List<Object> gudanceDatas){
        if (gudanceDatas == null)
            return null;
        List<GudanceEntity> gudanceList = new ArrayList<>();
        for(Object obj : gudanceDatas){

            Map<String, Object> gudanceData = (Map<String, Object>) obj;
            double sort = (double) gudanceData.get("sort");
            GudanceEntity g = new GudanceEntity(
                    (String)gudanceData.get("beginDate"), (String)gudanceData.get("endDate"),
                    (String)gudanceData.get("beginTime"), (String)gudanceData.get("endTime"),
                    (String)gudanceData.get("mediaUrl"), (int)sort);
            gudanceList.add(g);
//            downloadList.add(new DownloadEntity("v", g.getUrl(), g.getFileName()));
        }
        return gudanceList;
    }

}
