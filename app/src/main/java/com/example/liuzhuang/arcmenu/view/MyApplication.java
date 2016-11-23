package com.example.liuzhuang.arcmenu.view;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by xiaoxin on 2016/11/21.
 *
 *
 * Base class for maintaining global application state. You can provide your own
 * implementation by creating a subclass and specifying the fully-qualified name
 * of this subclass as the <code>"android:name"</code> attribute in your
 * AndroidManifest.xml's <code>&lt;application&gt;</code> tag.
 * The Application
 * class, or your subclass of the Application class, is instantiated before any
 * other class when the process for your application/package is created.
 * 保持全局应用程序状态的基类。你可以提供一个你自己创建的子类，
 * 并在AndroidManifest. xml的 <code>&lt;application&gt;</code>标签下的<code>"android:name"</code>属性来指定合法的名称来实现。
 *这个应用程序类或者你的子类的应用程序类，在你的应用程序/包创建之前将被实例化

 * Note: There is normally no need to subclass Application.
 * In most situations, static singletons can provide the same
 * functionality in a more modular way.
 * If your singleton needs a global context
 * (for example to register broadcast receivers),
 * include{@link android.content.Context#getApplicationContext() Context.getApplicationContext()}
 * as a {@link android.content.Context} argument when invoking your singleton's
 * <code>getInstance()</code> method.
 * 注意：通常不需要子类应用程序。
 * 在大多数情况下，静态的单例可以以一个模块化的方式提供相同的功能。
 * 如果你的单例需要一个全局上下文（例如注册广播接收器），
 * 当调用你的单例getinstance()方法时可以引入 Context.getApplicationContext()。
 */






public class MyApplication extends Application {
    /**
            *在应用程序创建的时候被调用，可以实现这个这个方法来创建和实例化任何应用程序状态变量或共享资源。还可以在这个方法里面得到 Application 的单例。
            */
    @Override
    public void onCreate() {
        super.onCreate();
    }
    /**
     *当终止应用程序对象时调用，此方法不一定被调用，如果是程序被内核终止来为其他应用程序释放资源，那么将不会有提醒，并且不调用应用程序的对象的onTerminate方法而直接终止进程。
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**　
     * 当设备配置更改时，由系统调用组件运行。请注意，不像Activity，永远不会重新启动和终止。他们必须一直处理变化着的结果，例如通过重新检索的资源。
     * 在这个函数被调用的时候，你的资源对象将被更新并返回资源值来匹配新的配置。
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    /**
     *当系统资源匮乏的时候，我们可以在这里释放额外的内存， 这个方法一般只会在后台进程已经结束，但前台应用程序还是缺少内存时调用。可以重写这个方法来清空缓存或者释放不必要的资源。
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 当运行中决定当前应用程序应该减少其内存开销时（通常在进入后台运行的时候）调用，包含一个 level 参数，用于提供请求的上下文。
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

}
