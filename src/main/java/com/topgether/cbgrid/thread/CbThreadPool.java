package com.topgether.cbgrid.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
  
public class CbThreadPool extends ThreadPoolExecutor {  
	
	private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();  
    
    private final AtomicLong numTasks = new AtomicLong();  
    
    private final AtomicLong totalTime = new AtomicLong(); 
    
    private final BlockingQueue<Runnable> level2queue = new LinkedBlockingQueue<Runnable>();
    
    public CbThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,  
            BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {  
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);  
    }  
    
    public CbThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {  
        this(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(corePoolSize), new ThreadPoolExecutor.AbortPolicy());  
    } 
    
    public CbThreadPool(int corePoolSize, int maximumPoolSize, int queueSize, long keepAliveTime) {  
        this(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize), new ThreadPoolExecutor.AbortPolicy());  
    } 
  
    protected void beforeExecute(Thread t, Runnable r) {  
        super.beforeExecute(t, r);  
        System.out.println(String.format("Thread %s: start %s", t, r));  
        startTime.set(System.nanoTime());  
    }  
  
    protected void afterExecute(Runnable r, Throwable t) {  
        try {  
            long endTime = System.nanoTime();  
            long taskTime = endTime - startTime.get();  
            numTasks.incrementAndGet();  
            totalTime.addAndGet(taskTime);  
            System.out.println(String.format("Thread %s: end %s, time=%dns", t, r, taskTime));  
        } finally {  
            super.afterExecute(r, t);  
        }  
    }  
  
    protected void terminated() {  
        try {  
            System.out.println(String.format("Terminated: avg time=%dns", totalTime.get() / numTasks.get()));  
        } finally {  
            super.terminated();  
        }  
    }  
    
    public static int getCpuCoreNum () {
    	return Runtime.getRuntime().availableProcessors();
    }
    
    public boolean isAllTaskCompleted () {
    	return this.getTaskCount() == this.getCompletedTaskCount();
    }
    
    public BlockingQueue<Runnable> getLevel2Queue () {
    	return this.level2queue;
    }
    
} 
