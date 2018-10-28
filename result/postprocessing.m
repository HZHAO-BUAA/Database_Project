%fixedArray=zeros(4,56);
%fixedArray(:,1)=doubleArray(:,1);
%for idx=2:56
%    fixedArray(1,idx)=doubleArray(1,idx)-doubleArray(1,idx-1);
%    fixedArray(2,idx)=doubleArray(2,idx);
%    fixedArray(3,idx)=doubleArray(3,idx)-doubleArray(3,idx-1);
%    fixedArray(4,idx)=doubleArray(4,idx);
%end
printAction=3;
fixedArray=doubleArray;

testinglength=[100,500,1000,2500,5000,7500,10000,25000];
k_range=[1,5,10,15,20,25,30];
counter=1;

rearrangedResult=zeros(length(k_range),length(testinglength),2);
for idStream=1:length(k_range)
    for idN=1:length(testinglength)
        rearrangedResult(idStream,idN,1)=fixedArray(1,counter);
        rearrangedResult(idStream,idN,2)=fixedArray(3,counter);
        counter=counter+1;
    end
    
end

writingTimes=rearrangedResult(:,:,1);
readingTimes=rearrangedResult(:,:,2);
[Xg,Yg]=meshgrid(k_range,testinglength);
if printAction==1
mesh(Xg,Yg,readingTimes');
caxis([10 200])
xlabel('Stream number K'),ylabel('Repeating times N'),zlabel('Reading time (milisecond)');
end

if printAction==2
writingTimes5=fixedArray(1,9:16);
writingVar5=fixedArray(2,9:16);

writingTimes15=fixedArray(1,25:32);
writingVar15=fixedArray(2,25:32);

writingTimes30=fixedArray(1,49:56);
writingVar30=fixedArray(2,49:56);

errorbar(testinglength,writingTimes5,writingVar5,'linewidth',1.5);

hold on
errorbar(testinglength,writingTimes15,writingVar15,'linewidth',1.5);
errorbar(testinglength,writingTimes30,writingVar30,'linewidth',1.5);
legend("Stream number = 5","Stream number = 15","Stream number = 30")
xlabel('Repeating times N'),ylabel('Writing time (milisecond)');
title("Writing time error bar of method 2")
axis([0 30000 0 30000])
end

if printAction==3
readingTimes5=fixedArray(3,9:16);
readingVar5=fixedArray(4,9:16);

readingTimes15=fixedArray(3,25:32);
readingVar15=fixedArray(4,25:32);

readingTimes30=fixedArray(3,49:56);
readingVar30=fixedArray(4,49:56);

errorbar(testinglength,readingTimes5,readingVar5,'linewidth',1.5);

hold on
errorbar(testinglength,readingTimes15,readingVar15,'linewidth',1.5);
errorbar(testinglength,readingTimes30,readingVar30,'linewidth',1.5);
legend("Stream number = 5","Stream number = 15","Stream number = 30")
xlabel('Repeating times N'),ylabel('Reading time (milisecond)');
title("Reading time error bar of method 2")
axis([0 30000 0 700])
end

