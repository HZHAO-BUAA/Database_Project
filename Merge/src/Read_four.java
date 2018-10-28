import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static java.lang.Math.min;


public class Read_four {

    private int BUFFER_SIZE = 40;

    private int offset=0;
    private int inbuffer_offest=0; //flag of reading progress in current buffer
    private long lastbufferLimit=BUFFER_SIZE+1;//length limit for last buffer at the end of file
    private long fileLength;
    private String fileName=null;
    private Boolean FileEnded=false;
    private RandomAccessFile theFile=null;

    private MappedByteBuffer inputBuffer;//buffer for BUFFER_SIZE/4 integers


    public static int byteArrayToInt(byte b0, byte b1,byte b2, byte b3) {
        return   b3 & 0xFF |
                (b2 & 0xFF) << 8 |
                (b1 & 0xFF) << 16 |
                (b0 & 0xFF) << 24;
    }

    public void open(String fileName,int bufferSize){

        this.fileName=fileName;
        File mapFile=new File(fileName);
        this.fileLength=mapFile.length();

        this.BUFFER_SIZE =min(bufferSize*4,(int)this.fileLength);


        this.lastbufferLimit=BUFFER_SIZE+1;//length limit for last buffer at the end of file


        try {
            this.theFile=new RandomAccessFile(this.fileName,"r");
            this.inputBuffer =this.theFile.getChannel().map(FileChannel.MapMode.READ_ONLY,0, BUFFER_SIZE);// 读取大文件
            this.offset+=BUFFER_SIZE;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int read_next(){
        if(this.inbuffer_offest<min(BUFFER_SIZE,lastbufferLimit)){
            int tempInt=byteArrayToInt(this.inputBuffer.get(this.inbuffer_offest),this.inputBuffer.get(this.inbuffer_offest+1),this.inputBuffer.get(this.inbuffer_offest+2),this.inputBuffer.get(this.inbuffer_offest+3));
            //System.out.print(tempInt +", ");
            this.inbuffer_offest+=4;
            return tempInt;
        }
        else{
            if(this.offset <this.fileLength ) {
                this.lastbufferLimit=this.fileLength - this.offset;

                this.inbuffer_offest=0;

                try {

                    this.inputBuffer =this.theFile.getChannel().map(FileChannel.MapMode.READ_ONLY,this.offset, min(BUFFER_SIZE,lastbufferLimit));// 读取大文件
                    this.offset+=BUFFER_SIZE;

                } catch (IOException e) {
                    closeFile();
                }


                int tempInt=byteArrayToInt(this.inputBuffer.get(this.inbuffer_offest),this.inputBuffer.get(this.inbuffer_offest+1),this.inputBuffer.get(this.inbuffer_offest+2),this.inputBuffer.get(this.inbuffer_offest+3));
                //System.out.print(tempInt +", ");
                this.inbuffer_offest+=4;

                return tempInt;
            }
        }
        return -1;

    }
    public void closeFile(){
        this.FileEnded=true;
        try {
            this.theFile.close();
            //System.gc();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Boolean end_of_stream(){
        return this.FileEnded;
    }

}
