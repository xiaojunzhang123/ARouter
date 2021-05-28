package com.zxj.arouter_core.template;

import java.util.Map;

import com.zxj.arouter_annotation.RouteMeta;

public interface IRouteGroup {
    void loadInto(Map<String, RouteMeta> atlas);
}
