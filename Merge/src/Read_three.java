import java.io.*;
import static java.lang.Math.min;

public class Read_three {
    private InputStream ist=null;
    private DataInputStream dst=null;

    private Boolean FileEnded = false;
    private int BUFFER_SIZE=40;
    private byte[] bufferBlock = new byte[BUFFER_SIZE];
    private int inBufferOffset=BUFFER_SIZE+1;
    private long fileLength=0;
    private int fileOffset=0;

    public static int byteArrayToInt(byte b0, byte b1,byte b2, byte b3) {
        return   b3 & 0xFF |
                (b2 & 0xFF) << 8 |
                (b1 & 0xFF) << 16 |
                (b0 & 0xFF) << 24;
    }

    public void open(String Filename, int buffer_size){
        try {
            File isFile = new File(Filename);
            this.fileLength=isFile.length();

            this.ist = new FileInputStream(isFile);
            this.dst = new DataInputStream(this.ist);
            this.FileEnded=false;
            this.BUFFER_SIZE=min(buffer_size*4,(int)this.fileLength);
            this.bufferBlock = new byte[BUFFER_SIZE];
            this.inBufferOffset=BUFFER_SIZE+1;


        }catch (Exception e){
            System.out.println("File not found error");
            e.printStackTrace();
        }
    }

    public int read_next(){
        if(this.inBufferOffset>=BUFFER_SIZE && this.fileOffset<=this.fileLength){

            try {
                //for(int idx=0;idx<min(BUFFER_SIZE,(int)(fileLength-fileOffset));idx++){
                this.bufferBlock = new byte[min(BUFFER_SIZE,(int)(fileLength-fileOffset))];
                this.dst.read(bufferBlock);
                //this.dst.skipBytes(BUFFER_SIZE);
               // }

                this.fileOffset+=BUFFER_SIZE;
                this.inBufferOffset=0;

            } catch (IOException e) {
                this.closeFile();
                return -1;
                //e.printStackTrace();
            }
        }
        if(this.inBufferOffset<bufferBlock.length){
            int tempInt = byteArrayToInt(bufferBlock[inBufferOffset], bufferBlock[inBufferOffset + 1], bufferBlock[inBufferOffset + 2], bufferBlock[inBufferOffset + 3]);
            this.inBufferOffset += 4;
            return tempInt;
        }else{
            this.FileEnded=true;
        }

        return -1;

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
