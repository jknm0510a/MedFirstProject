package tw.medfirst.com.project.Entity;

/**
 * Created by KCTsai on 2015/6/26.
 */
public class TopMenuEntity {
    protected long id;   // Database create ID
    protected String id2; //API ID
    protected long beginDate;
    protected long endDate;
    protected String text;
    protected int sort;
    protected boolean check = false;

    /*
    * get funcation
    * */

    public TopMenuEntity() {
    }

    public TopMenuEntity(String id2, String text, long beginDate, long endDate, int sort) {
        this.id2 = id2;
        this.text = text;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.sort = sort;
    }
    @Override
    public String toString(){
        return "id: " + id + ",id2: " + id2 + ", text: " + text + ", BeginDate: " + beginDate +
                 ", EndDate: " + endDate + ", sort: " + sort + ", isCheck: " + check;
    }

    public long getId(){
        return id;
    }

    public String getId2(){
        return id2;
    }

    public String getText(){
        return text;
    }

    public long getBeginDate(){
        return beginDate;
    }

    public long getEndDate(){
        return endDate;
    }

    public int getSort(){
        return sort;
    }
    public boolean getCheck(){
        return check;
    }
    /*
    * set function
    * */
    public void setId(long id){
        this.id = id;
    }
    public void setId2(String id2){
        this.id2 = id2;
    }
    public void setText(String text){
        this.text = text;
    }
    public void setBeginDate(long beginDate){
        this.beginDate = beginDate;
    }
    public void setEndDate(long endDate){
        this.endDate = endDate;
    }
    public void setSort(int sort){
        this.sort = sort;
    }
    public void setCheck(boolean b){
        this.check = b;
    }
}
