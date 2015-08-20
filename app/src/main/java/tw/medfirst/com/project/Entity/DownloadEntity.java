package tw.medfirst.com.project.Entity;

/**
 * Created by KCTsai on 2015/8/5.
 */
public class DownloadEntity {
    private String type;
    private String path;
    private String name;

    public DownloadEntity(String type, String path, String name){
        this.type = type;
        this.path = path;
        this.name = name;
    }

    public String getType(){return type;}
    public String getPath(){return path;}
    public String getName(){return name;}

    //for download check
    private boolean isDownloading = false;
    private int tryCount = 0;

    public void setIsDownloading(boolean b){this.isDownloading = b;}
    public void setTryCount(int c){this.tryCount = c;}

    public boolean getIsDownloading(){return isDownloading;};
    public int getTryCount(){return tryCount;}

}
