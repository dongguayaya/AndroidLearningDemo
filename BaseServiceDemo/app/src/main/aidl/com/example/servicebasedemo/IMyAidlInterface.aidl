// IMyAidlInterface.aidl
package com.example.servicebasedemo;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     //可删掉，删掉之后就不会重载
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            //定义自己所需要的方法：显示当前服务进度
            void showProgress();

}
