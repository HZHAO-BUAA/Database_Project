load('Merge2DcompN.mat')
testinglength=[500000,1000000,5000000,10000000];
bufferRange=[100000];
mergetimes=[25];
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

errorbar(testinglength,reconstructedRes,reconstructedVars,'linewidth',1.5)
xlabel('File size N'),ylabel('Merging time (milisecond)');
title("Merging time varies with N given M = 1 \times 10^5, D = 25")
set(gca,'FontSize',16);


                