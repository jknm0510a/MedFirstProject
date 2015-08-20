package tw.medfirst.com.project.Entity;

/**
 * Created by KCTsai on 2015/6/26.
 */
public class ProductEntity {
    protected long id;
    protected String pNo;
    protected String type;
    protected String name;
    protected String path;
    protected int sort;
    protected boolean check = false;

    /*
    * get funcation
    * */

    public ProductEntity() {
    }

    public ProductEntity(String pNo, String type, String name, String path, int sort) {
//        this.id = id;
        this.pNo = pNo;
        this.type = type;
        this.name = name;
        this.path = path;
        this.sort = sort;
    }
    @Override
    public String toString(){
        return "id: " + id + ", pid: " + pNo + ", type: " + type + ", FileName: " + name
                + ", FilePath: " + path + ", sort: " + sort;
    }

    public long getId(){
        return id;
    }
    public String getPNo(){
        return pNo;
    }
    public String getType(){
        return type;
    }
    public String getName(){
        return name;
    }
    public String getPath(){
        return path;
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
    public void setPNo(String pNo){
        this.pNo = pNo;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPath(String path){
        this.path = path;
        this.name = parseName();
    }
    public void setSort(int sort){
        this.sort = sort;
    }
    public void setCheck(boolean b){
        this.check = b;
    }

    private String parseName() {
        if(path == null)
            return null;
        String str[] = path.split("/");
        return str[str.length - 1];
    }
}
