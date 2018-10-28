import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static java.lang.Math.min;


public class Write_four {
    private int BUFFER_SIZE = 40;
    private String fileName=null;
    private int offset=0;
    private int inbuffer_offest=0; //flag of reading progress in current buffer
    private MappedByteBuffer outputBuffer; //buffer for BUFFER_SIZE/4 integers
    private int writeTimes=0;
    private int fileSize;
    private byte[] byteToWrite=new byte[BUFFER_SIZE];
    RandomAccessFile theFile = null;
    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }



    public void create(String fileName,int bufferSize,int fileSize){
        this.fileName=fileName;

        this.fileSize=fileSize*4;
        this.BUFFER_SIZE =min(bufferSize*4,this.fileSize);



        //this.B =new byte[BUFFER_SIZE]; //buffer for  BUFFER_SIZE/4 integers


        try {
            this.theFile=new RandomAccessFile(fileName,"rw");

            this.outputBuffer =this.theFile.getChannel().map(FileChannel.MapMode.READ_WRITE,0, BUFFER_SIZE);// 读取大文件
            this.offset+=BUFFER_SIZE;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void write(int intToWrite){
        if(inbuffer_offest<BUFFER_SIZE){
            byte[] byteToWrite=intToByteArray(intToWrite);

            this.outputBuffer.put( byteToWrite);


            this.inbuffer_offest+=4;

        }
        else{


            this.inbuffer_offest=0;

            try {
                // read next buffer block
                this.outputBuffer =this.theFile.getChannel().map(FileChannel.MapMode.READ_WRITE,this.offset, min(BUFFER_SIZE,fileSize-offset));
                this.offset+=BUFFER_SIZE;

            } catch (IOException e) {
                e.printStackTrace();
            }
            this.write(intToWrite);
        }

    }
    public void close(){
        try {
            this.theFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
