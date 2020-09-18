package com.cainiao.basicslibrary.chapter_nine.thread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.cainiao.baselibrary.base.BaseActivity;
import com.cainiao.baselibrary.listener.OnItemClickListener;
import com.cainiao.basicslibrary.ChapterEightAdapter;
import com.cainiao.basicslibrary.R;
import com.cainiao.basicslibrary.R2;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

import butterknife.BindView;

/**
 * @author liangtao
 * @date 2018/12/3 11:42
 * @describe 线程
 */
public class ThreadActivity extends BaseActivity implements OnItemClickListener {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    private ChapterEightAdapter adapter;
    private String[] data;

    /**
     * 创建静态内部类 Handler 意在防止内存泄露
     * 注：同一个线程下的handler共享一个Looper对象，消息中保留了对handler的引用，
     * 只要有消息在队列中，那么handler便无法被回收，如果handler不是static，那么使用Handler
     * 的Service和Activity就也无法被回收，即便它们的onDestroy方法被调用。这就可能导致内存
     * 泄露。当然这通常不会发生，除非你发送了一个延时很长的消息。
     * 但把handler添加为static后，会发现在handler中调用外部类的方法和成员变量需要它们都定义为final，
     * 这显然是不大可能的。这里建议在你的Service或Activity中的增加一个内部static Handler类，
     * 这个内部类持有Service或Activity的弱引用,这样就可以解决final的问题。
     */
    private static class InteriorHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        InteriorHandler(Activity mActivity) {
            this.mActivity = new WeakReference<>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ThreadActivity threadActivity = (ThreadActivity) mActivity.get();
            switch (msg.what) {
                case 0:

                    Logger.t("Thread:   " + Thread.currentThread().getName()).i("run： " + Thread.currentThread().getName());
                    //threadActivity.showToast("当前线程：" + Thread.currentThread().getName());
                    break;
                case 1:
                    for (int i = 0; i < 10; i++) {
                        Logger.t("Thread:   " + Thread.currentThread().getName()).i("run： " + i);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private InteriorHandler interiorHandler;

    /**
     * 线程是什么：1.从底层角度来讲：一个线程就是在进程中的一个单一的顺序控制流。而单个进程可以拥有多个并发执行的任务，每个任务都好像有自己
     * 的CPU一样，而其底层的机制就是切分CPU的时间，也就是CPU将轮流给每个任务分配其占用时间。每个任务都觉得自己在
     * 一直占用CPU，而事实上是将CPU时间划分成片段分配给所有的任务。在多个CPU的环境下，多线程的运作，可以极大的提供
     * 程序的运行速度，这就是线程存在的意义。
     * 2.从Android的角度讲：进程：每个App运行时前首先创建一个进程，该进程是由Zygote fork出来的，用于承载App上运行的各种Activity/Service等组件
     * 进程对于上层应用来说是完全透明的，这也是Google有意为之，让App程序都是运行在Android Runtime。大多数情况一个App就运行在
     * 一个进程中，除非在AndroidManifest.xml中配置Android:process属性，或通过native代码fork进程。
     * 线程：线程对应用来说非常常见，比如每次new Thread().start都会创建一个新的线程。该线程与App所在进程之间资源共享，从
     * Liunx角度来说进程与线程除了是否共享资源外，并没有本质的区别，都是一个task_struct结构体，在CPU看来进程或者线程无非就是
     * 一段可执行的代码，CPU采用CFS调度算法，保证每个task都尽可能公平的享有CPU时间片。
     * 3.线程，是程序执行的最小单元。一个标准的线程由线程ID，当前指令指针，寄存器集合和堆栈组成。线程是进程中的一个实体，是被系统独立调度和分配的基本单位。
     * 线程自己不拥有系统资源，只拥有一点在运行中必不可少的资源，但它可与同属一个进程的其他进程共享进程所拥有的全部资源。
     * 线程在Android中的作用：1.在Android中线程分主线程和子线程，主线程也被称为UI线程，用来处理各种和界面相关的事情。
     * 例：界面的加载，Activity的生命周期这些都是主线程的范畴之内。
     * 2.由于主线程比较特殊，因为本身主线程在处理界面上，用了大部分的消耗，所以主线程不能再处理过于耗时的操作
     * (IO操作，网络请求，大量的数据操作)，否则就会造成ANR现象(程序卡死)。
     * 3.此时子线程就横空出世解决了这类问题，Android建议耗时操作必须放在子线程中运行。
     * 4.而在Android中可以解决耗时问题的角色除了Thread之外还有AsyncTask，HandlerThread，IntentService，都可以实现此类功能，
     * 而他们本质还是传统的线程。
     * <p>
     * 创建线程的三种方法：1.自定义一个类，去继承Thread类，重写run方法
     * 2.自定义一个类，去实现Runnable接口，重写run方法
     * 3.自定义一个类，实现Callable接口，重写call方法。
     * 线程的并发和并行：并发是两个任务可以在重叠的时间段内启动，运行和完成。
     * 并行是任务在同一时间运行，例如，在多核处理器上。
     * 并发是独立执行过程的组合，而并行是同时执行(可能相关的)计算。
     * 并发是一次处理很多事情，并行是同时做很多事情。
     * 应用程序可以是并发的，但不是并行的，这意味着它可以同时处理多个任务，但是没有两个任务在同一时刻执行。
     * 应用程序可以是并行的，但不是并发的，这意味着它同时处理多核CPU中的任务的多个子任务。
     * 一个应用程序可以既不是并行的也不是并发的，这意味着他一次一个地处理所有任务。应用程序可以即是
     * 并行的也是并发的，这意味着它同时在多核 CPU中同时处理多个任务。
     * 并发：Concurrency，是并发的意思。并发的实质是一个物理CPU(也可以多个物理CPU) 在若干道程序（或线程）之间多路复用，
     * 并发性是对有限物理资源强制行使多用户共享以提高效率。
     * 从微观角度来讲：所有的并发处理都有排队等候，唤醒，执行等这样的步骤，在微观上他们都是序列被处理的，如果是同一时刻到达的请求
     * （或线程）也会根据优先级的不同，而先后进入队列排队等候执行。
     * 从宏观角度来讲：多个几乎同时到达的请求（或线程）在宏观上看就像是同时在被处理。
     * 通俗点讲，并发就是只有一个CPU资源，程序（或线程）之间要竞争得到执行机会。图中的第一个阶段，在A执行的过程中B，C不会执行，
     * 因为这段时间内这个CPU资源被A竞争到了，同理，第二个阶段只有B在执行，第三个阶段只有C在执行。其实，并发过程中，
     * A，B，C并不是同时在进行的（微观角度）。但又是同时进行的（宏观角度）
     * 并行：Parallelism，翻译过来即并行，指两个或两个以上事件（或线程）在同一时刻发生，是真正意义上的不同事件或线程在同一时刻，
     * 在不同CPU资源上（多核），同时执行。
     * 并行，不存在像并发那样竞争，等待的概念。
     * 多线程：
     * 线程同步：指的是通过人为的控制和调度，保证共享资源的多线程访问成为线程安全，来保证结果的准确。
     * 线程状态转换：1.wait()：使一个线程处于等待状态，并且释放所有持有对象的lock锁，直到notify()/notifyAll()
     * 被唤醒后放到锁定池(lock blocked pool )，释放同步锁使线程回到可运行状态（Runnable）。
     * 2.sleep():使一个线程处于睡眠状态，是一个静态方法，调用此方法要捕捉Interrupted异常，醒来后进入runnable
     * 状态，等待JVM调度。
     * 3.notify()：使一个等待状态的线程唤醒，注意并不能确切唤醒等待状态线程，是由JVM决定且不按优先级
     * 4.notifyAll()：使所有的等待状态的线程唤醒，注意并不是给所有线程上锁，而是让他们竞争。
     * 5.join()：使一个线程中断，IO完成会回到Runnable状态，等待JVM的调度。
     * 6.Synchronized():使Running状态的线程加同步锁使其进入(lock blocked pool ),同步锁被释放进入可运行状态(Runnable)。
     * 注意：在runnable状态的线程是处于被调度的线程，此时的调度顺序是不一定的。Thread类中的yield方法可以让一个running状态的线程转入runnable。
     */

    @Override
    protected View addView() {
        return View.inflate(mContext, R.layout.activity_thread, null);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(this);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initData() {
        mTitleBar.setTitleRightVisibility(View.INVISIBLE);
        mTitleBar.setTitle(getResources().getStringArray(R.array.chapter_nine)[0]);
        data = getResources().getStringArray(R.array.thread_creation_method);
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
        interiorHandler=new InteriorHandler(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public void onItemClick(View view) {
        int position = mRecyclerView.getChildAdapterPosition(view);
        Thread thread;
        switch (position) {
            //继承Thread类
            case 0:
                thread = new MyThread();
                thread.start();
                break;
            //实现Runnable接口
            case 1:
                thread = new Thread(new MyRunnable());
                thread.start();
                break;
            //实现Callable接口
            case 2:
                new MyCallable();
                break;
            //线程的并发和并行
            case 3:

                break;
            //线程的同步
            case 4:
                synchronizedText01();
                break;

            //只有条件不满足的时候才会执行这个方法
            default:
                showToast(data[position]);
                break;
        }
        showToast(data[position]);
    }


    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            interiorHandler.sendEmptyMessage(0);
            Looper.loop();
        }
    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            Looper.prepare();
            interiorHandler.sendEmptyMessage(0);
            Looper.loop();
        }
    }

    /**
     * Callable的任务执行后可返回值,call()方法可抛出异常,
     * 运行Callable任务可拿到一个Future对象。
     */
    class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            return null;
        }
    }

    /**
     * synchronized
     * 由于java的每个对象都有一个内置锁，当用此关键字修饰方法时， 内置锁会保护整个方法。
     * 在调用该方法前，需要获得内置锁，否则就处于阻塞状态。
     * 注： synchronized关键字也可以修饰静态方法，此时如果调用该静态方法，将会锁住整个类。
     */
    /**
     * 同步方法：给一个方法增加synchronized修饰符之后就可以使它成为同步方法，
     * 这个方法可以是静态方法和非静态方法，但是不能是抽象类的抽象方法，也不能是接口中的接口方法。
     * <p>
     * 线程在执行同步方法时是具有排它性的。当任意一个线程进入到一个对象的任意一个同步方法时，
     * 这个对象的所有同步方法都被锁定了，在此期间，其他任何线程都不能访问这个对象的任意一个同步方法，
     * 直到这个线程执行完它所调用的同步方法并从中退出，从而导致它释放了该对象的同步锁之后。
     * 在一个对象被某个线程锁定之后，其他线程是可以访问这个对象的所有非同步方法的。
     */
    private synchronized void aMethod() {

    }

    private static synchronized void anotherMethod() {

    }

    /**
     * 同步块：同步块是通过锁定一个指定的对象，来对同步块中包含的代码进行同步
     * 而同步方法是对这个方法块里的代码进行同步，而这种情况下锁定的对象就是同步方法所属的主体对象自身。
     * 如果这个方法是静态同步方法呢？那么线程锁定的就不是这个类的对象了，也不是这个类自身，而是这个类对应的
     * java.lang.Class类型的对象。同步方法和同步块之间的相互制约只限于同一个对象之间，
     * 所以静态同步方法只受它所属类的其它静态同步方法的制约，而跟这个类的实例（对象）没有关系。
     * <p>
     * 如果一个对象既有同步方法，又有同步块，那么当其中任意一个同步方法或者同步块被某个线程执行时，
     * 这个对象就被锁定了，其他线程无法在此时访问这个对象的同步方法，也不能执行同步块。
     * <p>
     * synchronized 关键字用于保护共享数据
     */

    class ThreadTest implements Runnable {

        @Override
        public synchronized void run() {
            Looper.prepare();
            Message message = new Message();
            message.what = 1;
            interiorHandler.handleMessage(message);
            Looper.loop();
           /* for (int i = 0; i < 10; i++) {
                Logger.t("Thread:   " + Thread.currentThread().getName()).i("run： " + i);
            }*/

           //handler.sendEmptyMessage(0);
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for (int i = 0; i < 10; i++) {
                Logger.t("Thread:   " + Thread.currentThread().getName()).i("run： " + i);
            }
        }
    };

    /**
     * run()虽然被加上了synchronized 关键字，但保护的不是共享数据。
     * 因为这个程序中的thread1,thread2 是两个对象（runnable1,runnable2）的线程.
     * 而不同的对象的数据是不同的，thread1,thread2 有各自的run()方法，所以输出结果无法预知。
     * 打印结果是两个线程随机
     *
     * synchronized的目的是使同一个对象的多个线程，在某个时刻只有其中的一个线程可以访问这个对象的synchronized 数据。
     * 每个对象都有一个“锁标志”，当这个对象的一个线程访问这个对象的某个synchronized 数据时，
     * 这个对象的所有被synchronized 修饰的数据将被上锁（因为“锁标志”被当前线程拿走了），
     * 只有当前线程访问完它要访问的synchronized 数据时，当前线程才会释放“锁标志”，
     * 这样同一个对象的其它线程才有机会访问synchronized 数据。
     */
    private void synchronizedText01(){
        Runnable runnable1 = new ThreadTest();
        Runnable runnable2 = new ThreadTest();
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
    }

    /**
     * 如果你运行1000 次这个程序，它的输出结果也一定每次都是：0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9。
     * 因为这里的synchronized 保护的是共享数据。thread1,thread2 是同一个对象（runnable1）的两个线程，
     * 当其中的一个线程（例如：thread1）开始执行run()方法时，由于run()受synchronized保护，
     * 所以同一个对象的其他线程（thread2）无法访问synchronized 方法（run 方法）。只有当thread1执行完后thread2 才有机会执行。
     */
    private void synchronizedText02(){
        Runnable runnable1 = new ThreadTest();
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable1);
        thread1.start();
        thread2.start();
    }


}
