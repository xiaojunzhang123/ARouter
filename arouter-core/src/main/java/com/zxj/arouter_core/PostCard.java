package com.zxj.arouter_core;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import com.zxj.arouter_annotation.RouteMeta;
import com.zxj.arouter_core.callback.NavigationCallback;
import com.zxj.arouter_core.template.IService;

public class PostCard extends RouteMeta {

    private Bundle mBundle;
    private int flags = -1;

    private Bundle optionsCompat;

    private int enterAnim;
    private int exitAnim;

    private IService service;

    public PostCard(String path,String group){
        this(path,group,null);
    }

    public PostCard(String path, String group, Bundle bundle) {
        setPath(path);
        setGroup(group);
        this.mBundle = (null == bundle ? new Bundle() : bundle);
    }

    public Bundle getExtras(){
        return mBundle;
    }

    public int getExitAnim() {
        return exitAnim;
    }

    public int getEnterAnim() {
        return enterAnim;
    }

    public IService getService() {
        return service;
    }

    public void setService(IService service) {
        this.service = service;
    }

    public PostCard withFlags(int flag){
        this.flags = flag;
        return this;
    }

    public int getFlags(){
        return flags;
    }

    public PostCard withTransition(int enterAnim, int exitAnim){
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    public PostCard withOptionsCompat(ActivityOptionsCompat compat){
        if (null != compat){
            this.optionsCompat = compat.toBundle();
        }
        return this;
    }

    public PostCard withString(@Nullable String key, @Nullable String value) {
        mBundle.putString(key, value);
        return this;
    }


    public PostCard withBoolean(@Nullable String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }


    public PostCard withShort(@Nullable String key, short value) {
        mBundle.putShort(key, value);
        return this;
    }


    public PostCard withInt(@Nullable String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }


    public PostCard withLong(@Nullable String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }


    public PostCard withDouble(@Nullable String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }


    public PostCard withByte(@Nullable String key, byte value) {
        mBundle.putByte(key, value);
        return this;
    }


    public PostCard withChar(@Nullable String key, char value) {
        mBundle.putChar(key, value);
        return this;
    }


    public PostCard withFloat(@Nullable String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }


    public PostCard withParcelable(@Nullable String key, @Nullable Parcelable value) {
        mBundle.putParcelable(key, value);
        return this;
    }


    public PostCard withStringArray(@Nullable String key, @Nullable String[] value) {
        mBundle.putStringArray(key, value);
        return this;
    }


    public PostCard withBooleanArray(@Nullable String key, boolean[] value) {
        mBundle.putBooleanArray(key, value);
        return this;
    }


    public PostCard withShortArray(@Nullable String key, short[] value) {
        mBundle.putShortArray(key, value);
        return this;
    }


    public PostCard withIntArray(@Nullable String key, int[] value) {
        mBundle.putIntArray(key, value);
        return this;
    }


    public PostCard withLongArray(@Nullable String key, long[] value) {
        mBundle.putLongArray(key, value);
        return this;
    }


    public PostCard withDoubleArray(@Nullable String key, double[] value) {
        mBundle.putDoubleArray(key, value);
        return this;
    }


    public PostCard withByteArray(@Nullable String key, byte[] value) {
        mBundle.putByteArray(key, value);
        return this;
    }


    public PostCard withCharArray(@Nullable String key, char[] value) {
        mBundle.putCharArray(key, value);
        return this;
    }


    public PostCard withFloatArray(@Nullable String key, float[] value) {
        mBundle.putFloatArray(key, value);
        return this;
    }


    public PostCard withParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        mBundle.putParcelableArray(key, value);
        return this;
    }

    public PostCard withParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends
        Parcelable> value) {
        mBundle.putParcelableArrayList(key, value);
        return this;
    }

    public PostCard withIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        mBundle.putIntegerArrayList(key, value);
        return this;
    }

    public PostCard withStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
        return this;
    }

    public Bundle getOptionsBundle() {
        return optionsCompat;
    }

    public Object navigation(){
        return ARouter.getInstance().navigation(null,this,-1,null);
    }

    public Object navigation(Context context, NavigationCallback callback){
        return  ARouter.getInstance().navigation(context,this,-1,callback);
    }



}
