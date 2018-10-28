load('result4.mat')
res4=doubleArray;
load('result3.mat')
res3=doubleArray;
res4(2,1)=0;
res3(2,1)=0;
res4(4,1)=0;
res3(4,1)=0;
filesize=[1000000,50000000,100000000];
errorbar(filesize,res3(3,:),res3(4,:),'linewidth',1.5)
hold on
errorbar(filesize,res4(3,:),res4(4,:),'linewidth',1.5)
xlabel('File size N'),ylabel('Reading time (milisecond)');
title("Reading time of 3 & 4 with large file size")
legend("Method 3","Method 4")
set(gca,'FontSize',16);

errorbar(filesize,res3(1,:),res3(2,:),'linewidth',1.5)
hold on
errorbar(filesize,res4(1,:),res4(2,:),'linewidth',1.5)
xlabel('File size N'),ylabel('Writing time (milisecond)');
title("Writing time of 3 & 4 with large file size")
legend("Method 3","Method 4")
set(gca,'FontSize',16);


