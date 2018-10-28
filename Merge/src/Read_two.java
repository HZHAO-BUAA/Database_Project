import java.io.*;

public class Read_two{
	
	private InputStream ist;
	private BufferedInputStream bst;
	private DataInputStream dst;
	private int tempInt=0;
	private Boolean FileEnded = true;

	
	public void open(String Filename){
        try {
            File isFile = new File(Filename);
            this.ist = new FileInputStream(isFile);
            this.bst = new BufferedInputStream(this.ist);
            this.dst = new DataInputStream( this.bst );
            this.FileEnded=false;
        }catch (Exception e){
            System.out.println("File not found error");
            e.printStackTrace();
        }
    }

	public Integer read_next() throws IOException {
        try {
        	this.tempInt=dst.readInt();
            //System.out.print(this.tempInt+", ");
        }catch (IOException e){
            System.out.println("Read integer error");
            e.printStackTrace();
            this.closeFile();
        }
        return this.tempInt;
    }				
	
	
	public void closeFile() throws IOException{
        try {
            this.FileEnded=true;
            this.dst.close();
            this.ist.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public Boolean end_of_stream()throws IOException {
        return this.FileEnded;
    }

}
