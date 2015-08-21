package tw.medfirst.com.project.Entity;

import tw.medfirst.com.project.baseunit.Logger;

/**
 * Created by KCTsai on 2015/6/26.
 */
public class ProductMenuEntity {
    protected long id;
    protected long topId;
    protected long subId;
    protected String pId;
    protected String subPicPath;
//    protected long time;
    protected String id2;
    protected long beginDate;
    protected long endDate;
    protected int sort;
    protected boolean check = false;
    private String PicName = null;

    /*
    * get funcation
    * */

    public ProductMenuEntity() {
    }

    public ProductMenuEntity(long topId, long subId, String pId, String subPicPath, int sort, String id2,
                             long beginDate, long endDate) {
        this.topId = topId;
        this.subId = subId;
        this.pId = pId;
        this.subPicPath = subPicPath;
//        this.time = time;
        this.sort = sort;
        this.id2 = id2;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }
    @Override
    public String toString(){
        return "id: " + id + ", TopId: " + topId + ", subId: " + subId+ ", pId: "+ pId +
                ", id2: " + id2 + ", beginDate: " + beginDate + ", endDate: " + endDate +
                ", subPicPath: " + subPicPath + ", sort: " + sort + ", check: " + check;
    }

    public String getId2(){
        return id2;
    }
    public long getBeginDate(){
        return beginDate;
    }
    public long getEndDate(){
        return endDate;
    }
    public long getId(){
        return id;
    }
    public long getTopId(){
        return topId;
    }
    public long getSubId(){
        return subId;
    }
    public String getPId(){
        return pId;
    }
    public String getSubPicPath(){
        return subPicPath;
    }
    public int getSort(){
        return sort;
    }
    public boolean getCheck(){
        return check;
    }
    public String getPicName(){return PicName;}
    /*
    * set function
    * */
    public void setId(long id){
        this.id = id;
    }
    public void setTopId(long topId){
        this.topId = topId;
    }
    public void setSubId(long subId){
        this.subId = subId;
    }
    public void setPId(String pId){
        this.pId = pId;
    }
    public void setSubPicPath(String subPicPath) {
        this.subPicPath = subPicPath;
    }
//    public void setTime(long time){
//        this.time = time;
//    }
    public void setSort(int sort){
        this.sort = sort;
    }
    public void setCheck(boolean b){
        this.check = b;
    }
    public void setId2(String id2){
        this.id2 = id2;
    }
    public void setBeginDate(long beginDate){
        this.beginDate = beginDate;
    }
    public void setEndDate(long endDate){
        this.endDate = endDate;
    }

    public void setPicName(String name) {
        this.PicName = name;
    }

    private String parserName() {
        if(subPicPath == null)
            return null;
        String str[] = subPicPath.split("/");
        return str[str.length - 1];
    }
}
