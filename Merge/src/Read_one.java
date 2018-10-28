import java.io.*;

public class Read_one {
    private InputStream ist=null;
    private DataInputStream dst=null;
    private int tempInt = 0 ;
    private Boolean FileEnded = true;
    public void open(String Filename){
        try {
            File isFile = new File(Filename);
            this.ist = new FileInputStream(isFile);
            this.dst = new DataInputStream(this.ist);
            this.FileEnded=false;
        }catch (Exception e){
            System.out.println("File not found error");
            e.printStackTrace();
        }
    }

    public int read_next(){
        try {

        this.tempInt=dst.readInt();
        //System.out.print(this.tempInt+", "); //wuliyuan

        }catch (IOException e){
            System.out.println("Read integer error");
            //e.printStackTrace();
            this.closeFile();
        }
        return this.tempInt;
    }

    public void closeFile(){
        try {
            this.FileEnded=true;
            this.dst.close();
            this.ist.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Boolean end_of_stream(){
        return this.FileEnded;
    }


}
