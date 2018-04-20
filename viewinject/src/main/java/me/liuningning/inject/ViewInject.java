package me.liuningning.inject;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;


/**
 * Created by liunn on 2018/4/19.
 */

public class ViewInject {
    private static final String TAG = "ViewInject";
    public static final String SUFFIX = "ViewBind";
    private static  HashMap<Class, UnBinder> mCachedUnBinder = new HashMap<>();

    private static final UnBinder EMPTY_UNBINDER = new UnBinder() {
        @Override
        public void bind() {

        }
    };

    public static void inject(Activity activity) {
        //通过反射得到UnBinder
        Class<? extends Activity> clazz = activity.getClass();
        UnBinder unBinder = mCachedUnBinder.get(clazz);

        String binderName = clazz.getCanonicalName().toString() + SUFFIX;

        Log.d(TAG, "bindClassName:" + binderName);
        Log.d(TAG,unBinder==null?"is null":"not null");
        if (unBinder == null) {
            try {
                Class<UnBinder> targetClazz = (Class<UnBinder>) clazz.getClassLoader().loadClass(binderName);//
                Constructor<UnBinder> constructor = targetClazz.getConstructor(clazz, View.class);
                constructor.setAccessible(true);
                unBinder = constructor.newInstance(activity, activity.getWindow().getDecorView());//
                mCachedUnBinder.put(clazz, unBinder);
            } catch (Exception e) {
                Log.d(TAG, "bindClassName:" + e.toString());
                mCachedUnBinder.put(clazz,EMPTY_UNBINDER);
            }

        }
        unBinder.bind();


    }
}
