package me.liuningning.inject;

import android.view.View;

/**
 * Created by liunn on 2018/4/19.
 */

public class Utils {

    public static<T extends View> T findView(View view,int resId){
        return view.findViewById(resId);
    }
}
