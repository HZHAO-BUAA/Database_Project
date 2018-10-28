load('30times1.mat');
fixedArray=doubleArray;
testinglength=[100,500,1000,2500,5000,7500,10000,25000,50000,75000,100000];
selectIDB=[1,2,3];

rearrangedResult=zeros(30,length(testinglength),2);
for idb=1:3
counter=1;
for idStream=1:30
    for idN=1:length(testinglength)
        rearrangedResult(idStream,idN,2*(idb-1)+1)=fixedArray(4*(selectIDB(idb)-1)+1,counter);
        rearrangedResult(idStream,idN,2*(idb-1)+2)=fixedArray(4*(selectIDB(idb)-1)+3,counter);
        counter=counter+1;
    end
    
end
end

writingTimes1=rearrangedResult(:,:,1);
readingTimes1=rearrangedResult(:,:,2);
writingTimes2=rearrangedResult(:,:,3);
readingTimes2=rearrangedResult(:,:,4);
writingTimes3=rearrangedResult(:,:,5);
readingTimes3=rearrangedResult(:,:,6);
[Xg,Yg]=meshgrid(1:30,testinglength);

subplot(1,2,1);
surf(Xg,Yg,writingTimes1');
hold on
surf(Xg,Yg,writingTimes2');
surf(Xg,Yg,writingTimes3');
%caxis([10 200])

xlabel('Stream number K'),ylabel('Repeating times N'),zlabel('Writing time (milisecond)');
legend("Writing time B=10 (above)","Writing time B=100 (middle)","Writing time B=100000 (below)")
title("Writing time consumption of method 4")
set(gca,'FontSize',16);

subplot(1,2,2);
surf(Xg,Yg,readingTimes1');
hold on
surf(Xg,Yg,readingTimes2');
surf(Xg,Yg,readingTimes3');
%caxis([10 200])

xlabel('Stream number K'),ylabel('Repeating times N'),zlabel('Reading time (milisecond)');
legend("Reading time B=10 (above)","Reading time B=100 (middle)","Reading time B=100000 (below)")
title("Reading time consumption of method 4")
set(gca,'FontSize',16);
