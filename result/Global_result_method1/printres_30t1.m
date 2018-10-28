load('30times1.mat', 'doubleArray');
fixedArray=doubleArray;
testinglength=[100,500,1000,2500,5000,7500,10000,25000,50000,75000,100000];
counter=1;

rearrangedResult=zeros(30,length(testinglength),2);
for idStream=1:30
    for idN=1:length(testinglength)
        rearrangedResult(idStream,idN,1)=fixedArray(1,counter);
        rearrangedResult(idStream,idN,2)=fixedArray(2,counter);
        counter=counter+1;
    end
    
end

writingTimes=rearrangedResult(:,:,1);
readingTimes=rearrangedResult(:,:,2);

[Xg,Yg]=meshgrid(1:30,testinglength);

surf(Xg,Yg,writingTimes');
hold on
surf(Xg,Yg,readingTimes');
%caxis([10 200])

xlabel('Stream number K'),ylabel('Repeating times N'),zlabel('Reading time (milisecond)');
legend("Writing time (above)","Reading time (below)")
title("Time consumption of method 1")
set(gca,'FontSize',16);

