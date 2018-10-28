load('bestBufferest47c.mat')
fixedArray3104=doubleArray;
load('bestBufferest37c.mat')
fixedArray3103=doubleArray;
testinglength=[10000000];
selectIDB=[0.005,0.01,0.015,0.02,0.05,0.07,0.1,0.2,0.25,0.3,0.35,0.4,0.45,0.5,0.55,0.6,0.65,0.7,0.75,0.8,0.85,0.9,1];

rearrangedResult4=zeros(length(testinglength),2);
rearrangedResult3=zeros(length(testinglength),2);
rearrangedResultv4=zeros(length(testinglength),2);
rearrangedResultv3=zeros(length(testinglength),2);
counter=1;
for idb=1:length(selectIDB)

    for idN=1:length(testinglength)
        rearrangedResult4(idN,counter)=fixedArray3104(4*(idb-1)+1,idN);
        rearrangedResult3(idN,counter)=fixedArray3103(4*(idb-1)+1,idN);
        rearrangedResultv4(idN,counter)=fixedArray3104(4*(idb-1)+2,idN);
        rearrangedResultv3(idN,counter)=fixedArray3103(4*(idb-1)+2,idN);
        
    end
    counter=counter+1;
    
end

Xz1=selectIDB.*10^6;
Xz2=selectIDB.*10^7;
errorbar(Xz2,rearrangedResult4,rearrangedResultv4,'linewidth',1)
hold on
errorbar(Xz2,rearrangedResult3,rearrangedResultv3,'linewidth',1)
xlabel('Buffer size B '),ylabel('Writing time (milisecond)');
title("Writing time given N = 1 \times 10^7")
set(gca,'FontSize',16);
pos1=find(rearrangedResult4==min(rearrangedResult4));
pos2=find(rearrangedResult3==min(rearrangedResult3));

plot(Xz2(pos1),rearrangedResult4(pos1),'bx','linewidth',10);
plot(Xz2(pos2),rearrangedResult3(pos2),'rx','linewidth',10);
legend("Writing time (method 4)","Writing time (method 3)","Min time 4","Min time 3")
