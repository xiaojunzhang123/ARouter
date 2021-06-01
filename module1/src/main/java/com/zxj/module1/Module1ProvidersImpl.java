package com.zxj.module1;

import com.zxj.arouter_annotation.Route;
import com.zxj.base.Module1Providers;


@Route(path = "/module1/providers")
public class Module1ProvidersImpl implements Module1Providers {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
