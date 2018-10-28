import java.io.*;

public class Write_one {
    private OutputStream ost=null;
    private DataOutputStream dost=null;

    public void create(String FileName){
        File newFile=new File(FileName);

        try {
            this.ost=new FileOutputStream(newFile);
            this.dost=new DataOutputStream(this.ost);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    public void write(Integer intToWrite){
        try {
            this.dost.writeInt(intToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void close(){
        try {
            this.dost.close();
            this.ost.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
