package com.taobaoke.cms.crawlers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import net.paoding.rose.scanning.context.RoseAppContext;

public class CacheFacade {
    private ExecutorService threadPool = Executors.newSingleThreadExecutor();
    
    private boolean isRun = false;
    
    private AtomicInteger runingThread = new AtomicInteger(0);
    
    public CacheFacade(){
        
    }
    
    public void start(){
        if( isRun ){
            return ;
        }
        isRun = true;
        runingThread.incrementAndGet();
        threadPool.execute(new SingleCachInitialer(this));
        
    }
    
    public void callMeWhenFinished(){
        int remainder = runingThread.decrementAndGet();
        if( remainder == 0 ){
            isRun = false;
        }
    }
    
    public boolean isRun(){
        return isRun;
    }
    
    public static void main(String []args){
        @SuppressWarnings("unused")
        RoseAppContext context = new RoseAppContext();
        CacheFacade facade = new CacheFacade();
        facade.start();
    }
}
