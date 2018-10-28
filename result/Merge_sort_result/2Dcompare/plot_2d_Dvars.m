load('Merge2Dcomp.mat')
testinglength=[1000000];
bufferRange=[0.02];
mergetimes=[2,5,10,15,20,25,30,35,40,45,50];
reconstructedRes=zeros(length(testinglength),length(bufferRange),length(mergetimes));
reconstructedVars=zeros(length(testinglength),length(bufferRange),length(mergetimes));
for idbuffer=1:length(bufferRange)
    counter=1;
    for idlgh=1:length(testinglength)
        for idmgt=1:length(mergetimes)
          
            reconstructedRes(idlgh,idbuffer,idmgt)=doubleArray((idbuffer-1)*2+1,counter);
            reconstructedVars(idlgh,idbuffer,idmgt)=doubleArray((idbuffer-1)*2+2,counter);
            counter=counter+1;
        end
        
    end


end
xx=zeros(1,11);
xx(1:11)=reconstructedRes(1,1,:);
xv=zeros(1,11);
xv(1:11)=reconstructedVars(1,1,:);
errorbar(mergetimes,xx,xv,'linewidth',1.5)
xlabel('Max merging streams D'),ylabel('Merging time (milisecond)');
title("Merging time varies with D given N = 1 \times 10^6, M = 2 \times 10^4")
set(gca,'FontSize',16);


                