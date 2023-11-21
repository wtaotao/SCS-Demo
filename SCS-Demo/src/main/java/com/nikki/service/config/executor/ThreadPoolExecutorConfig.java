/**
 * nikki.com Inc.
 * Copyright (c) 2022-2023 All Rights Reserved.
 */
package com.nikki.service.config.executor;

import java.util.concurrent.RejectedExecutionHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * <pre>
 * sce的处理例子
 * </pre>
 *
 * @author Jesse Wang
 * @version $Id: ThreadPoolExecutorConfig.java, v 0.1 2023年10月8日 下午1:03:06 Jesse Wang Exp $
 */
@Configuration
@EnableAsync
public class ThreadPoolExecutorConfig {

    //工作线程数
    private static final int coreSize = 5;
    //最大工作线程数
    private static final int maxSize = 20;
    //工作线任务队列
    private static final int queueCapacity = 100;
    //允许线程的空闲时间
    private static final int freeTime = 30;
    //线程池名的前缀
    private static final String threadPrefixName = "Task_Executor_";
    //设置等待任务在关闭时完成
    private static final boolean waitForTasksToCompleteOnShutdown = true;
    //设置等待终止秒数
    private static final int awaitTerminationSeconds = 30;

    @Bean
    public ThreadPoolTaskExecutor threadTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数：线程池创建时候初始化的线程数
        executor.setCorePoolSize(coreSize);
        //最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(maxSize);
        //缓冲队列：用来缓冲执行任务的队列
        executor.setQueueCapacity(queueCapacity);
        //允许线程的空闲时间30秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(freeTime);
        //线程池名的前缀：设置好了之后可以方便定位处理任务所在的线程池
        executor.setThreadNamePrefix(threadPrefixName);
        //线程池对拒绝任务的处策略：CallerRunsPolicy策略，当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
        executor.setRejectedExecutionHandler(rejectedExecutionHandler());
        //设置等待任务在关闭时完成
        executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
        //设置等待终止秒数
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        //初始化
        executor.initialize();
        return executor;
    }

    //这个策略就是忽略缓冲队列限制，继续往里边塞
    public RejectedExecutionHandler rejectedExecutionHandler() {
        return (r, executor) -> {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
