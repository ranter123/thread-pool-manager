package com.eleven.base.manager.threadpool.core;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: eleven
 * @Date: 2021/9/9 11:48 下午
 * @Description: 线程池管理
 */
public class ExecutorsManager {

    /**
     * 公共线程池
     */
    private static volatile Map<String, ThreadPoolExecutor> executorsMap = new ConcurrentHashMap<>();

    public ExecutorsManager() {
        load();

    }

    /**
     * 读取配置重新加载线程池
     */
    public void refresh() {

    }

    /**
     * 线程池热切换
     */


    /**
     * 读取配置加载线程池
     */
    public void load() {

    }

    /**
     * 加入一个线程池
     * @param name 线程池名称
     * @param threadPoolExecutor 线程池实例
     */
    public static void put(String name, ThreadPoolExecutor threadPoolExecutor) {
        if (StringUtils.isBlank(name) || threadPoolExecutor == null) {
            throw new IllegalArgumentException("线程池名称或者线程池实例不能为null");
        }
        if (executorsMap.containsKey(name)) {
            throw new IllegalArgumentException("存在相同命名线程池");
        }
        executorsMap.put(name, threadPoolExecutor);
        //todo 持久化
    }

    /**
     * 执行一个异步任务
     * @param name 线程池名称
     * @param runnable 异步任务
     */
    public static void execute(String name, Runnable runnable) {
        ThreadPoolExecutor threadPoolExecutor = executorsMap.get(name);
        if (threadPoolExecutor == null) {
            throw new RuntimeException("请先初始化" + name + "线程池!");
        }
        //todo 持久化异步任务

        threadPoolExecutor.execute(runnable);
    }

    /**
     * 执行一个异步任务-callable
     * @param name 线程池名称
     * @param callable 异步任务
     * @return 执行future对象
     */
    public static Future<?> execute(String name, Callable callable) {
        ThreadPoolExecutor threadPoolExecutor = executorsMap.get(name);
        if (threadPoolExecutor == null) {
            throw new RuntimeException("请先初始化" + name + "线程池!");
        }
        Future future = threadPoolExecutor.submit(callable);
        return future;
    }
}
