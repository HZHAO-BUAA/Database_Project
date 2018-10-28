import java.io.*;

public class Write_two {
	
	private OutputStream ost;
	private BufferedOutputStream bost;
	private DataOutputStream dost;
		
	public void create(String FileName){
		File newFile=new File(FileName);
		try {
            this.ost=new FileOutputStream(newFile);
            this.bost = new BufferedOutputStream( this.ost );
            this.dost=new DataOutputStream(this.bost);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

	}
	
	public void write(Integer intToWrite) {
		try {
            this.dost.writeInt(intToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	public void close() throws IOException{
        try {
            this.dost.close();
            this.ost.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
