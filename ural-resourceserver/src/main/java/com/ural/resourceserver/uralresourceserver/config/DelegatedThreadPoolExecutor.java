package com.ural.resourceserver.uralresourceserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;

import java.util.concurrent.*;


public class DelegatedThreadPoolExecutor extends ThreadPoolExecutor {

    private SecurityContext securityContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(DelegatedThreadPoolExecutor.class);


    public DelegatedThreadPoolExecutor(int corePoolSize,
                                       int maximumPoolSize,
                                       Integer keepAliveTime,
                                       Integer queueSize,
                                       ThreadFactory threadFactory,
                                       RejectedExecutionHandler rejectedExecutionHandler,
                                       Long maximumWaitTime,
                                       SecurityContext securityContext
                                      ) {
        super(corePoolSize, maximumPoolSize, (long)keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize),threadFactory,rejectedExecutionHandler);

        this.securityContext=securityContext;
    }


    @Override
    public void execute(Runnable runnable) {

        if(runnable==null){
            LOGGER.error("DelegatedThreadPoolExecutor null runnable");
            throw new NullPointerException();
        }else {
            SecurityContext securityContext =this.securityContext;
            DelegatedThreadPoolExecutor.CustomRunnable customRunnable=
                    new DelegatedThreadPoolExecutor.CustomRunnable(securityContext,runnable);

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
        this.securityContext.setAuthentication(null);
        super.afterExecute(r, t);
    }

    class CustomRunnable implements Runnable{

        private final SecurityContext sharedSecurityContext;
        private final Runnable delegate;

        CustomRunnable(SecurityContext securityContext,Runnable delegate) {
            this.sharedSecurityContext = securityContext;
            this.delegate=delegate;
        }


        @Override
        public void run() {
            this.delegate.run();

        }

        void delegateSecurityContext(){

            DelegatedThreadPoolExecutor.this.securityContext
                    .setAuthentication(this.sharedSecurityContext.getAuthentication());
        }
    }
}
