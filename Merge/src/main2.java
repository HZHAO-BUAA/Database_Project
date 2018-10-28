import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class main2 {

	private String filename;
	private Random randomSeed = new Random();
	private int[] towrite = new int[100];
	private LinkedList<Integer>  toread1;
	private LinkedList<Integer> toread2;
	private long[] writetimearr = new long[10]; //must be the same as test times value in main function
	private long[] readtimearr = new long[10];
	private long start=0;
	private int bufferSize=0;


	public main2(String filename,int numInt,int bufferSize){
		this.filename=filename;
		this.toread1 = new LinkedList<Integer>();
		this.toread2 = new LinkedList<Integer>();
		this.towrite = new int[numInt];
		this.bufferSize=bufferSize;
		for(int idx=0;idx<numInt;idx++){
			this.towrite[idx]=(randomSeed.nextInt(2147483646));
		}
	}

	public static double StandardDiviation(long[] x) {
		int m=x.length;
		double sum=0;
		for(int i=0;i<m;i++){//compute sum
			sum+=x[i];
		}
		double dAve=sum/m;//compute average
		double dVar=0;
		for(int i=0;i<m;i++){//compute variance
			dVar+=(x[i]-dAve)*(x[i]-dAve);
		}
		return Math.sqrt(dVar/m);
	}


	public void test_Write_one(int k_streams, int N_times) throws Exception{
		LinkedList<Write_four> write_streams = new LinkedList<Write_four>();
		for (int j=0; j<k_streams;j++){
			write_streams.add(new Write_four());
			//write_streams.get(j).create(filename+j+".data",1000000,N_times);
			write_streams.get(j).create(filename+j+".data",this.bufferSize,N_times);
			for (int i=0; i<N_times;i++){
				//write_streams.get(j).write(randomSeed.nextInt(10));
				write_streams.get(j).write(this.towrite[i]);
			}
			write_streams.get(j).close();
		}
	}


	public void test_Read_one(int k_streams, int N_times) throws IOException{

		LinkedList<Integer> read = new LinkedList<Integer>();


		LinkedList<Read_four> read_streams = new LinkedList<Read_four>();
		for (int i=0; i<k_streams;i++){
			read_streams.add(new Read_four());
			//read_streams.get(i).open(filename+i+".data",1000000);
			read_streams.get(i).open(filename+i+".data",this.bufferSize);
		}
		for (int i=0; i<N_times;i++){
			for (int j=0; j<k_streams;j++){
				read.add(read_streams.get(j).read_next());

			}
		}
		for (int i=0; i<k_streams; i++){
			read_streams.get(i).closeFile();
			File toBeDEL=new File(filename+i+".data");
			toBeDEL.delete();
		}
	}


	//Test (read and write) for k streams opened and N values read/written
	public void test1_average(int k_streams, int N_times, int i) throws Exception{
		System.out.println("-----------Test_Read_one-----------");

		long end;
		long write_time = 0;
		long read_time = 0;


		System.out.println("Opening " + k_streams + " streams and writing " + N_times + " elements ");
		start= System.currentTimeMillis();
		test_Write_one(k_streams,N_times);
		end = System.currentTimeMillis();
		write_time = end - start;
		this.writetimearr[i] = write_time;
		System.out.println("Writing running time: " + write_time + " ms");
		//System.out.println();


		//System.out.println("Opening " + k_streams + " streams and reading " + N_times + " elements ");
		start= System.currentTimeMillis();
		test_Read_one(k_streams,N_times);
		end = System.currentTimeMillis();
		read_time= end - start;
		this.readtimearr[i]=read_time;
		//System.out.println(readtimearr[i]);
		System.out.println("Reading running time: " + read_time + " ms");
		System.out.println();
	}


	public static void main(String[] args) throws Exception {
		int test_times = 10;

		int[] testinglength=new int[]{100,500,1000,2500,5000,7500,10000,25000,50000,75000,100000,1000000};
		int[] k_range=new int[]{2,5,10,15,20,25,30};
		int[] bufferRange=new int[]{10,100,10000,1000000};
		//int[] testinglength=new int[]{100,500};
		long maxBytes = Runtime.getRuntime().maxMemory();
		System.out.println("Max memory: " + maxBytes / 1024 / 1024 + "M");



		//double[][] matTest=new double[4][k_range.length*testinglength.length];//generate matrix going to be stored
		double[][] matTest=new double[bufferRange.length*4][k_range.length*testinglength.length];//generate matrix going to be stored

		for(int bufferIdx=0;bufferIdx<bufferRange.length;bufferIdx++){
			int counter=0;
			for(int numstream=0;numstream<k_range.length;numstream++){


				for(int N=0;N<testinglength.length;N++){
					double avewritetime=0;
					double avereadtime=0;
					long sumwrite=0;
					double stdwrite=0;
					double stdread=0;
					long sumread=0;

					int test_lgh=testinglength[N];
					main2 M = new main2("WRtest/test",test_lgh,bufferRange[bufferIdx]);
					for(int idx=0;idx<test_times;idx++){
						M.test1_average(k_range[numstream],test_lgh, idx);
						//M.test1_average(numstream+1,test_lgh, idx);
						System.gc();

					}
					stdwrite=M.StandardDiviation(M.writetimearr);

					for(int idx=0;idx<test_times;idx++){
						sumwrite=sumwrite+M.writetimearr[idx]; }


					avewritetime = sumwrite/test_times;
					System.out.println("writing" + avewritetime);

					stdread=M.StandardDiviation(M.readtimearr);
					for(int idx=0;idx<test_times;idx++){
						sumread=sumread+M.readtimearr[idx]; }
					avereadtime = sumread/test_times;
					System.out.println("reading" + avereadtime);
					matTest[bufferIdx*4][counter]=avewritetime;
					matTest[bufferIdx*4+1][counter]=stdwrite;
					matTest[bufferIdx*4+2][counter]=avereadtime;
					matTest[bufferIdx*4+3][counter]=stdread;
					counter++;


				}

			}
		}


		MLDouble mlDouble=new MLDouble("doubleArray",matTest);//doubleArray is the matlab identifier of matTest defined above，after load()，we use doubleArray to access this matrix
		ArrayList list=new ArrayList();//during the parameter type of MatFileWriter() is list，so, we need to create an ArrayList
		list.add(mlDouble);
		new MatFileWriter("result/30times1.mat",list);//write matrix into .mat file, name it as matTest.mat
		System.out.println("mat writer done!");



        /*
        for (int i = 0; i < M.writetimearr.length; i++) {
            System.out.println(M.writetimearr[i]);
        }

        for (int i = 0; i < M.readtimearr.length; i++) {
            System.out.println(M.readtimearr[i]);
        }
        */
		//M.test1_average(1,10000);
		//M.test1_average(10, 10000);
		//M.test1_average(30, 1000000);
		//M.test1_average(5, 100);
		//M.test1_average(5, 10000);
		//M.test1_average(5, 100000);

	}

}
