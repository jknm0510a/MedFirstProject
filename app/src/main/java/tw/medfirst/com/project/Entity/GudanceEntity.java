package tw.medfirst.com.project.Entity;

import com.google.gson.annotations.SerializedName;

import tw.medfirst.com.project.baseunit.Logger;

/**
 * Created by KCTsai on 2015/8/6.
 */
public class GudanceEntity {
    private long id;
    private String beginDate;
    private String endDate;
    private String beginTime;
    private String endTime;
    private String url;
    private int sort;
    private String fileName;

    public GudanceEntity(String bd, String ed, String bt, String et, String u, int s){
        this.beginDate = bd;
        this.endDate = ed;
        this.beginTime = bt;
        this.endTime = et;
        this.url = u;
        this.sort = s;
        this.fileName = parseName();
    }

    public GudanceEntity(){

    }

    private String parseName() {
        if(url == null)
            return null;
        String str[] = url.split("/");
        return str[str.length - 1];
    }
    public long getId(){return id;}
    public String getBeginDate(){return beginDate;}
    public String getEndDate(){return endDate;}
    public String getBeginTime(){return beginTime;}
    public String getEndTime(){return endTime;}
    public String getUrl(){return url;}
    public int getSort(){return sort;}
    public String getFileName(){return fileName;}

    public void setBeginDate(String beginDate){this.beginDate = beginDate;}
    public void setEndDate(String endDate){this.endDate = endDate;}
    public void setBeginTime(String beginTime){this.beginTime = beginTime;}
    public void setEndTime(String endTime){this.endTime = endTime;}
    public void setUrl(String url){this.url = url;}
    public void setSort(int sort){this.sort = sort;}
    public void setId(long id){this.id = id;}



}
