import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class main {
    public static double StandardDiviation(long[] x) {
        int m=x.length;
        double sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x[i];
        }
        double dAve=sum/m;//求平均值
        double dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(x[i]-dAve)*(x[i]-dAve);
        }
        return Math.sqrt(dVar/m);
    }

    public static void main(String[] args) throws IOException {
/*
        int fileLength=111;

        testFileGen testFileGenerator=new testFileGen();
        testFileGenerator.generateFile("test.bin",fileLength);
        Read_four fileReader4=new Read_four();
        Read_one  fileReader1 = new Read_one();
        Read_three  fileReader3 = new Read_three();
        fileReader4.open("test.bin",10);
        fileReader1.open("test.bin");
        fileReader3.open("test.bin",10);
        Integer counter=0;


        System.out.println("\n result from 1:");
        counter=0;
        while(counter<fileLength){
            int timint=fileReader1.read_next();
            counter+=1;
        }
        System.out.println("\n result from 3:");
        counter=0;
        while(counter<fileLength){
            int timint=fileReader3.read_next();
            counter+=1;
        }

*/


        int[] testinglength=new int[]{500000,1000000,5000000,10000000};
        double[] bufferRange=new double[]{0.02};
        int[] Mergetimes=new int[]{25};
        double[][] matTest=new double[bufferRange.length*2][testinglength.length*Mergetimes.length];//生成待存储的矩阵


        //int fileLength=1000000;

        for(int idbuffer=0;idbuffer<bufferRange.length;idbuffer++){
            int counter=0;
            for(int idlgh=0;idlgh<testinglength.length;idlgh++){
                boolean exceedonce=false;
                for(int idmgt=0;idmgt<Mergetimes.length;idmgt++){

                    int filesize=testinglength[idlgh];
                    //int buffersize=(int)(filesize*bufferRange[idbuffer]);
                    int buffersize=100000;
                    int mergeD=Mergetimes[idmgt];
                    long[] runingTime = new long[10];
                    System.out.print("Merge test: FileSize= "+filesize+" BufferSize= "+buffersize+" D= "+mergeD);
                    if(bufferRange[idbuffer]*mergeD<1 | !exceedonce){
                        if(bufferRange[idbuffer]*mergeD>=1){
                            exceedonce=true;
                        }

                        for(int idtest=0;idtest<10;idtest++){



                            testFileGen testFileGenerator = new testFileGen();
                            testFileGenerator.generateFile("test.bin",testinglength[idlgh]);

                            MergeSort testMergeSort = new MergeSort();
                            long start= System.currentTimeMillis();
                            testMergeSort.MergeSort("test.bin","sorted.bin",buffersize,mergeD,3);
                            long end = System.currentTimeMillis();
                            runingTime[idtest]=end-start;

                            File toBeDEL=new File("test.bin");
                            toBeDEL.delete();

                        }
                        double stdrun=StandardDiviation(runingTime);
                        long sumavg=0;
                        for(int idx=0;idx<10;idx++){
                            sumavg=sumavg+runingTime[idx]; }
                        double avertime = sumavg/10;

                        System.out.println("merging" + avertime);
                        matTest[idbuffer*2][counter]=avertime;
                        matTest[idbuffer*2+1][counter]=stdrun;
                        System.gc();


                    }
                    counter++;
                    }

            }
        }
        MLDouble mlDouble=new MLDouble("doubleArray",matTest);//doubleArray就是matlab中上述矩阵的标示符，load()之后，在matlab中使用doubleArray访问此矩阵
        ArrayList list=new ArrayList();//由于MatFileWriter()构造函数的参数为list类型，所以需要创建一个ArrayList
        list.add(mlDouble);
        new MatFileWriter("result/MergeResult.mat",list);//将矩阵写入到.mat文件中，文件名为matTest.mat
        System.out.println("mat writer done!");

    }
}
