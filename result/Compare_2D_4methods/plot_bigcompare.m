fileLgh1=[5000,10000,100000];
filelghOt=[5000,10000,100000,500000,1000000];
kvaries=[1,5,10,15,20,25,30];
load('bestBufferest1.mat')
orig1=doubleArray;
load('bestBufferest2.mat')
orig2=doubleArray;
load('bestBufferest3.mat')
orig3=doubleArray;
load('bestBufferest4.mat')
orig4=doubleArray;

counter=1;

rearrangedResult1=zeros(length(kvaries),length(fileLgh1),4);
for idStream=1:length(kvaries)
    for idN=1:length(fileLgh1)
        rearrangedResult1(idStream,idN,1)=orig1(1,counter);
        rearrangedResult1(idStream,idN,2)=orig1(2,counter);
        rearrangedResult1(idStream,idN,3)=orig1(3,counter);
        rearrangedResult1(idStream,idN,4)=orig1(4,counter);
        counter=counter+1;
    end
    
end

counter=1;

rearrangedResult2=zeros(length(kvaries),length(filelghOt),4);
rearrangedResult3=zeros(length(kvaries),length(filelghOt),4);
rearrangedResult4=zeros(length(kvaries),length(filelghOt),4);

for idStream=1:length(kvaries)
    for idN=1:length(filelghOt)
        rearrangedResult2(idStream,idN,:)=orig2(:,counter);
        rearrangedResult3(idStream,idN,:)=orig3(:,counter);
        rearrangedResult4(idStream,idN,:)=orig4(:,counter);
        counter=counter+1;
    end
    
end


idN=5;
if idN<=3
subplot(2,2,1)

errorbar(kvaries,rearrangedResult1(:,idN,1),rearrangedResult1(:,idN,2),'linewidth',1.2)
hold on
errorbar(kvaries,rearrangedResult2(:,idN,1),rearrangedResult2(:,idN,2),'linewidth',1.2)
errorbar(kvaries,rearrangedResult3(:,idN,1),rearrangedResult3(:,idN,2),'linewidth',1.2)
errorbar(kvaries,rearrangedResult4(:,idN,1),rearrangedResult4(:,idN,2),'linewidth',1.2)
xlabel('Stream number K'),ylabel('Writing time (milisecond)');
legend("Method 1","Method 2","Method 3","Method 4")
title("Writing time of 1 to 4 given N="+num2str(filelghOt(idN)))
set(gca,'FontSize',12);


subplot(2,2,2)
errorbar(kvaries,rearrangedResult2(:,idN,1),rearrangedResult2(:,idN,2),'linewidth',1.2)
hold on
errorbar(kvaries,rearrangedResult3(:,idN,1),rearrangedResult3(:,idN,2),'linewidth',1.2)
errorbar(kvaries,rearrangedResult4(:,idN,1),rearrangedResult4(:,idN,2),'linewidth',1.2)
xlabel('Stream number K'),ylabel('Writing time (milisecond)');
legend("Method 2","Method 3","Method 4")
title("Writing time without 1 given N="+num2str(filelghOt(idN)))
set(gca,'FontSize',12);

subplot(2,2,3)

errorbar(kvaries,rearrangedResult1(:,idN,3),rearrangedResult1(:,idN,4),'linewidth',1.2)
hold on
errorbar(kvaries,rearrangedResult2(:,idN,3),rearrangedResult2(:,idN,4),'linewidth',1.2)
errorbar(kvaries,rearrangedResult3(:,idN,3),rearrangedResult3(:,idN,4),'linewidth',1.2)
errorbar(kvaries,rearrangedResult4(:,idN,3),rearrangedResult4(:,idN,4),'linewidth',1.2)
xlabel('Stream number K'),ylabel('Reading time (milisecond)');
legend("Method 1","Method 2","Method 3","Method 4")
title("Reading time of 1 to 4 given N="+num2str(filelghOt(idN)))
set(gca,'FontSize',12);


subplot(2,2,4)
errorbar(kvaries,rearrangedResult2(:,idN,3),rearrangedResult2(:,idN,4),'linewidth',1.2)
hold on
errorbar(kvaries,rearrangedResult3(:,idN,3),rearrangedResult3(:,idN,4),'linewidth',1.2)
errorbar(kvaries,rearrangedResult4(:,idN,3),rearrangedResult4(:,idN,4),'linewidth',1.2)
xlabel('Stream number K'),ylabel('Reading time (milisecond)');
legend("Method 2","Method 3","Method 4")
title("Reading time without 1 given N="+num2str(filelghOt(idN)))
set(gca,'FontSize',12);
end
if idN>3

subplot(1,2,1)
errorbar(kvaries,rearrangedResult2(:,idN,1),rearrangedResult2(:,idN,2),'linewidth',1.2)
hold on
errorbar(kvaries,rearrangedResult3(:,idN,1),rearrangedResult3(:,idN,2),'linewidth',1.2)
errorbar(kvaries,rearrangedResult4(:,idN,1),rearrangedResult4(:,idN,2),'linewidth',1.2)
xlabel('Stream number K'),ylabel('Reading time (milisecond)');
legend("Method 2","Method 3","Method 4")
title("Writing time without 1 given N="+num2str(filelghOt(idN)))
set(gca,'FontSize',12);


subplot(1,2,2)
errorbar(kvaries,rearrangedResult2(:,idN,3),rearrangedResult2(:,idN,4),'linewidth',1.2)
hold on
errorbar(kvaries,rearrangedResult3(:,idN,3),rearrangedResult3(:,idN,4),'linewidth',1.2)
errorbar(kvaries,rearrangedResult4(:,idN,3),rearrangedResult4(:,idN,4),'linewidth',1.2)
xlabel('Stream number K'),ylabel('Reading time (milisecond)');
legend("Method 2","Method 3","Method 4")
title("Reading time without 1 given N="+num2str(filelghOt(idN)))
set(gca,'FontSize',12);
end