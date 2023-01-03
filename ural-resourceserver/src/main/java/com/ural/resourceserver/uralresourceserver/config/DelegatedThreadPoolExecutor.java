package com.ural.resourceserver.uralresourceserver.config;

import com.ural.resourceserver.uralresourceserver.util.ContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import java.util.concurrent.*;


public class DelegatedThreadPoolExecutor extends ThreadPoolExecutor {

    private SecurityContextHolderStrategy securityContextHolderStrategy;
    private static final Logger LOGGER = LoggerFactory.getLogger(DelegatedThreadPoolExecutor.class);


    public DelegatedThreadPoolExecutor(int corePoolSize,
                                       int maximumPoolSize,
                                       Integer keepAliveTime,
                                       Integer queueSize,
                                       ThreadFactory threadFactory,
                                       RejectedExecutionHandler rejectedExecutionHandler,
                                       Long maximumWaitTime,
                                       SecurityContextHolderStrategy securityContextHolderStrategy
                                      ) {
        super(corePoolSize, maximumPoolSize, (long)keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize),threadFactory,rejectedExecutionHandler);

        this.securityContextHolderStrategy=securityContextHolderStrategy;
    }


    @Override
    public void execute(Runnable runnable) {

        if(runnable==null){
            LOGGER.error("DelegatedThreadPoolExecutor null runnable");
            throw new NullPointerException();
        }else {

            DelegatedThreadPoolExecutor.CustomRunnable customRunnable=
                    new DelegatedThreadPoolExecutor.CustomRunnable(  this.securityContextHolderStrategy,runnable);

            super.execute(customRunnable);
        }

    }


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        ((DelegatedThreadPoolExecutor.CustomRunnable) r).delegateSecurityContext();
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        this.securityContextHolderStrategy.clearContext();
        super.afterExecute(r, t);
    }

    class CustomRunnable implements Runnable{

        private final SecurityContext sharedContext;
        private final Runnable delegate;

        CustomRunnable(SecurityContextHolderStrategy securityContextHolderStrategy,Runnable delegate) {
            this.sharedContext = securityContextHolderStrategy.getContext();
            this.delegate=delegate;
        }


        @Override
        public void run() {
            this.delegate.run();

        }

        void delegateSecurityContext(){

            DelegatedThreadPoolExecutor.this.securityContextHolderStrategy.setContext(this.sharedContext);




        }
    }
}
