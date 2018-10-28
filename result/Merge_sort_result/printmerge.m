load('MergeResult3.mat')
testinglength=[10000,1000000];
bufferRange=[0.01,0.05,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,1];
mergetimes=[2,5,10,50,100];
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
resTL2=zeros(11,5);

resTL2(:,:)=reconstructedRes(1,:,:);
resVL2(:,:)=reconstructedVars(1,:,:);
[listzeros]=find(resTL2==0);
resTL2(listzeros)=10000;
resmin=resTL2(1:7,:);
[minx,miny]=find(resTL2==min(min(resmin)));
resTL2(listzeros)=0;
for idx=1:5
    for idl=1:11
        if(resTL2(idl,idx)==0)
            resTL2(idl,idx)=resTL2(idl,idx-1);
        end
    end
    
end
localvar=resTL2(1:11,2);
resVL2(7:11,2)=resVL2(7:11,1);
errorbar(bufferRange(1:11).*100,localvar,resVL2(1:11,2),'linewidth',1.5)
xlabel('Buffer size M (% file size)'),ylabel('Merging time (milisecond)');
title("Best buffer size given file size = 1 \times 10^4")
set(gca,'FontSize',16);

[Xg,Yg]=meshgrid(bufferRange(1:11).*100,mergetimes);
bs1=resTL2(1:11,:);
surf(Xg,Yg,resTL2(1:11,:)');
hold on
bufferRange(minx(1))
mergetimes(miny(1))
plot(bufferRange(minx(1)).*100,mergetimes(miny(1)),'-rx');
xlabel('Buffer size M (% file size)'),ylabel('Merging streams D'),zlabel('Merging time (milisecond)');
title("Merging time distribution given file size = 1 \times 10^4")
set(gca,'FontSize',16);
                