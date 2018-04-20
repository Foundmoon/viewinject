package me.liuningning.complier.entry;

import javax.lang.model.type.TypeMirror;

/**
 * Created by liunn on 2018/4/18.
 * BindView 注解的相关的数据
 */

public class BindField {
    private String mFieldName;
    private TypeMirror mTypeMirror;
    private int mResId;

    public BindField(String name, TypeMirror typeMirror, int resId) {
        this.mFieldName = name;
        mTypeMirror = typeMirror;
        mResId = resId;
    }

    public String getFieldName() {
        return mFieldName;
    }

    public void setFieldName(String fieldName) {
        mFieldName = fieldName;
    }

    public TypeMirror getTypeMirror() {
        return mTypeMirror;
    }

    public void setTypeMirror(TypeMirror typeMirror) {
        mTypeMirror = typeMirror;
    }

    public int getResId() {
        return mResId;
    }

    public void setResId(int resId) {
        mResId = resId;
    }

    @Override
    public String toString() {
        return "BindField{" + "mFieldName='" + mFieldName + '\'' + ", mTypeMirror=" + mTypeMirror + ", mResId=" + mResId + ", typeMirror=" + getTypeMirror() + ", resId=" + getResId() + '}';
    }
}
