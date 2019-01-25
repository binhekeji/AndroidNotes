package com.cainiao.basicslibrary.chapter_nine.thread_pool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.cainiao.baselibrary.base.BaseActivity;
import com.cainiao.baselibrary.listener.OnItemClickListener;
import com.cainiao.basicslibrary.ChapterEightAdapter;
import com.cainiao.basicslibrary.R;
import com.cainiao.basicslibrary.R2;
import com.cainiao.basicslibrary.chapter_nine.thread.ThreadActivity;
import com.orhanobut.logger.Logger;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author liangtao
 * @date 2018/12/3 11:45
 * @describe 线程池
 */
public class ThreadPoolActivity extends BaseActivity implements OnItemClickListener {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    private ChapterEightAdapter adapter;
    private String[] data;

    private ThreadPoolExecutor threadPoolExecutor;
    private ExecutorService fixedThreadPool;
    private ExecutorService cachedThreadPool;
    private ScheduledExecutorService scheduledThreadPool;
    private ExecutorService singleThreadExecutor;

    /**
     * 什么是线程池：
     * 线程池的有点：1.重用线程池中的线程，避免因为线程的创建和销毁所带来的性能开销。能有效的控制线程池的最大并发数，
     * 避免大量的线程之间因相互抢占系统资源而导致的堵塞线程。
     * 2.能够对线程进行简单的管理，并提供定时执行已经指定间隔循环执行等功能。
     * 3.相对于AsyncTask来说，最大的优势在于：线程可控!比如在离开了某个页面，提交到AsyncTask的任务不能撤销
     * ，线程池可以在不需要的时候将某个线程移除。
     */

    @Override
    protected View addView() {
        return View.inflate(mContext, R.layout.activity_thread_pool, null);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        mTitleBar.setTitleRightVisibility(View.INVISIBLE);
        mTitleBar.setTitle(getResources().getStringArray(R.array.chapter_nine)[1]);
        data = getResources().getStringArray(R.array.thread_pool_type);
        adapter = new ChapterEightAdapter(data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        //设置为垂直布局，这个是默认的
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置adapter
        mRecyclerView.setAdapter(adapter);
        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        //设置增加或者删除条目的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /**
         * CorePoolSize 核心线程数
         *              默认情况下，核心线程数会在线程中一直存活，即使他们处于闲置状态。
         *              如果将ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，
         *              那么核心线程就会存在超时策略，这个时间间隔由keepAliveTime所决定，
         *              当等待时间超过keepAliveTime所指定的时长后，核心线程就会被停止。
         * maximumPoolSize 线程池所能容纳的最大线程数。
         *                 当活动线程数达到这个数值后，后续的新任务将会被阻塞。
         * keepAliveTime 非核心线程闲置时的超时时长，超过这个时长，非核心线程就会被回收，
         *               当ThreadPoolExector的allowCoreThreadTimeOut属性设置为True时，
         *               keepAliveTime同样会作用于核心线程。
         * unit          用于指定keepAliveTime参数的时间单位，这是一个枚举，
         *               常用的有TimeUnit.MILLISECONDS（毫秒）、TimeUnit.SECONDS(秒)以及TimeUnit.MINUTES(分钟)等。
         *               TimeUnit.NANOSECONDS  纳秒
         *               TimeUnit.MICROSECONDS 微秒
         *               TimeUnit.MILLISECONDS 毫秒
         *               TimeUnit.SECONDS      秒
         *               TimeUnit.MINUTES      分钟
         *               TimeUnit.HOURS        小时
         *               TimeUnit.DAYS         天
         * workQueue     线程池中的任务队列，通过线程池execute方法提交的Runnable对象会存储在这个参数中。
         *               这个任务队列是BlockQueue类型，属于阻塞队列，就是当队列为空的时候，此时取出任务的操作会被阻塞，
         *               等待任务加入队列中不为空的时候，才能进行取出操作，而在满队列的时候，添加操作同样被阻塞。
         *               SynchronousQueue：（同步队列）这个队列接收到任务的时候，会直接提交给线程处理，而不保留它（名字定义为 同步队列）。
         *                                  但有一种情况，假设所有线程都在工作怎么办？
         *                                  这种情况下，SynchronousQueue就会新建一个线程来处理这个任务。所以为了保证不出现（线程数达到了maximumPoolSize而不能新建线程）
         *                                  的错误，使用这个类型队列的时候，maximumPoolSize一般指定成Integer.MAX_VALUE，即无限大，去规避这个使用风险。
         *               LinkedBlockingQueue(链表阻塞队列)：这个队列接收到任务的时候，如果当前线程数小于核心线程数，则新建线程(核心线程)处理任务；如果当前线程数等于核心线程数，
         *                                                  则进入队列等待。由于这个队列没有最大值限制，即所有超过核心线程数的任务都将被添加到队列中，这也就导致了maximumPoolSize
         *                                                  的设定失效，因为总线程数永远不会超过corePoolSize
         *               ArrayBlockingQueue（数组阻塞队列）：可以限定队列的长度（既然是数组，那么就限定了大小），接收到任务的时候，如果没有达到corePoolSize的值，
         *                                                   则新建线程(核心线程)执行任务，如果达到了，则入队等候，如果队列已满，则新建线程(非核心线程)执行任务，
         *                                                   又如果总线程数到了maximumPoolSize，并且队列也满了，则发生错误
         *               DelayQueue（延迟队列）：队列内元素必须实现Delayed接口，这就意味着你传进去的任务必须先实现Delayed接口。这个队列接收到任务时，首先先入队，
         *                                       只有达到了指定的延时时间，才会执行任务
         * threadFactory 线程工厂，为线程池提供创建新线程的功能。ThreadFactory是一个接口，它只有一个方法，newThread（Runnable r），用来创建线程。
         * RejectedExecutionHandler handler : 这个主要是用来抛异常的，当线程无法执行新的任务时(一般是由于线程池中的线程数量已经达到最大数或者线程池关闭导致的)，
         *                                    默认情况下，当线程池无法处理新线程时，会抛出一个RejectedExecutionException
         *
         * 线程执行过程：1.execute一个线程之后，如果线程池中的线程数未达到核心线程数，则会立马启用一个核心线程去执行。
         *               2.execute一个线程之后，如果线程池中的线程数已经达到核心线程数，且workQueue未满，则将新线程放入workQueue中等待执行。
         *               3.execute一个线程之后，如果线程池中的线程数已经达到核心线程数但未超过非核心线程数，且workQueue已满，则开启一个非核心线程来执行任务。
         *               4.execute一个线程之后，如果线程池中的线程数已经超过非核心线程数，则拒绝执行该任务，采取饱和策略，并抛出RejectedExecutionException异常。
         */
        threadPoolExecutor = new ThreadPoolExecutor(5,
                5,
                1,
                SECONDS,
                new LinkedBlockingDeque<Runnable>(100),
                new ThreadFactoryBuilder()
                        .setNameFormat("ThreadPool-%d").build());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public void onItemClick(View view) {
        int position = mRecyclerView.getChildAdapterPosition(view);
        switch (position) {
            //FixedThreadPool
            case 0:
                FixedThreadPool();
                break;
            //CacheThreadPool
            case 1:
                CacheThreadPool();
                break;
            //ScheduledThreadPool
            case 2:
                ScheduledThreadPool();
                break;
            //SingleThreadExecutor
            case 3:
                SingleThreadExecutor();
                break;

            //只有条件不满足的时候才会执行这个方法
            default:
                showToast(data[position]);
                break;
        }
    }

    /**
     * 可重用固定线程数，定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
     * ExecutorService fixedThreadPool= Executors.newFixedThreadPool(5);
     * 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，
     * 这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险
     * 特点：参数为核心线程数，只有核心线程，无非核心线程，并且阻塞队列无界。
     *
     * 这是一种数量固定的线程池,当线程处于空闲的时候,并不会被回收,除非线程池被关闭.
     * 当所有的线程都处于活动状态时,新任务都会处于等待状态,直到有线程空闲出来.
     * 由于FixedThreadPool中只有核心线程并且这些核心线程不会被回收,这意味着它能够更加快速地响应外界的请求.
     * 只有核心线程,并且超时时间为0(即无超时时间),所以不会被回收.
     * <p>
     * new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build()
     * <p>
     * 注：作为固定线程数 就是核心线程数和最大线程数相同，这样的话 就不存在非核心线程了
     * 并且阻塞队列无界
     */
    private void FixedThreadPool() {

        fixedThreadPool = new ThreadPoolExecutor(5,
                5,
                0L,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<Runnable>()
                , new ThreadFactoryBuilder().setNameFormat("ThreadPool-%d").build()
        );
        for (int i = 0; i < 30; i++) {
            final int finali = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        Logger.t("Thread:   " + Thread.currentThread().getName()).i("run： " + finali);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            fixedThreadPool.execute(runnable);

        }
    }

    /**
     * 可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
     * 特点：没有核心线程，只有非核心线程，并且每个非核心线程空闲等待的时间为60s，采用SynchronousQueue队列。
     *       比较适合执行大量的耗时较少的任务.
     *       当整个线程都处于闲置状态时,线程池中的线程都会超时而被停止,这时候的CacheThreadPool几乎不占任何系统资源的.
     * 1.因为没有核心线程，其他全为非核心线程，SynchronousQueue是不存储元素的，每次插入操作必须伴随一个移除操作，
     * 一个移除操作也要伴随一个插入操作。
     * 2.当一个任务执行时，先用SynchronousQueue的offer提交任务，如果线程池中有线程空闲，
     * 则调用SynchronousQueue的poll方法来移除任务并交给线程处理；如果没有线程空闲
     * 则开启一个新的非核心线程来处理任务。
     * 3.由于maximumPoolSize是无界的，所以如果线程处理任务速度小于提交任务的速度，
     * 则会不断地创建新的线程，这时需要注意不要过度创建，应采取措施调整双方速度，
     * 不然线程创建太多会影响性能
     * 4.从其特点可以看出，CachedThreadPool适用于有大量需要立即执行的耗时少的任务的情况。
     */
    private void CacheThreadPool() {
        cachedThreadPool = new ThreadPoolExecutor(0,
                Integer.MAX_VALUE,
                60L,
                SECONDS,
                new SynchronousQueue<Runnable>(),
                new ThreadFactoryBuilder().setNameFormat("ThreadPool-%d").build());
        for (int i = 0; i < 100; i++) {
            final int finali = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                        Logger.t("Thread:   " + Thread.currentThread().getName()).i("run： " + finali);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            cachedThreadPool.execute(runnable);

        }
    }

    /**
     * 定时延时执行
     * ExecutorService scheduledThreadPool  = Executors.newScheduledThreadPool(3);
     * 注意：上面的写法错误，必须是其子类才行
     *       ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
     * 核心线程数是固定的,非核心线程无限大,并且非核心线程数有10s的空闲存活时间
     * 当非核心线程闲置时会被立即回收
     * 主要用于执行定时任务和具有固定周期的重复任务.
     */
    private void ScheduledThreadPool() {
       // ScheduledExecutorService scheduledThreadPool  = Executors.newScheduledThreadPool(3);
        scheduledThreadPool = new ScheduledThreadPoolExecutor(3);
        /*scheduledThreadPool = new ScheduledThreadPoolExecutor(3,
                Integer.MAX_VALUE,
                10L,
                TimeUnit.MICROSECONDS,
                new BlockingQueue<Runnable>(),
                new ThreadFactoryBuilder().setNameFormat("ThreadPool-%d").build());*/
        for (int i = 0; i < 30; i++) {
            final int finali = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        Logger.t("Thread:   " + Thread.currentThread().getName()).i("run： " + finali);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            //延迟启动任务
            scheduledThreadPool.schedule(runnable, 10, SECONDS);
            //延迟5s后启动，每1s执行一次
            //scheduledThreadPool.scheduleAtFixedRate(runnable,5,1,TimeUnit.SECONDS);
            //启动后第一次延迟5s执行，后面延迟1s执行
            // scheduledThreadPool.scheduleWithFixedDelay(runnable,5,1,TimeUnit.SECONDS);

        }
    }

    /**
     * 单个核心线程数
     * ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
     * 特点： 一个核心线程数，一个最大线程数，没有非核心线程数,并且无超时时间
     * 由于只有一个核心线程，当被占用时，其他的任务需要进入队列等待。
     * 它确保所有的任务都在同一个线程中按顺序执行.
     * 统一外界所有任务到一个线程,这使得这些任务之间不需要处理线程同步的问题.
     */
    private void SingleThreadExecutor() {
        singleThreadExecutor = new ThreadPoolExecutor(1,
                1,
                0L,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<Runnable>(),
                new ThreadFactoryBuilder().setNameFormat("ThreadPool-%d").build());
        for (int i = 0; i < 30; i++) {
            final int finali = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        Logger.t("Thread:   " + Thread.currentThread().getName()).i("run： " + finali);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            singleThreadExecutor.execute(runnable);

        }
    }


}
