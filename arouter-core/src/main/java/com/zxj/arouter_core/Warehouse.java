package com.zxj.arouter_core;

import java.util.HashMap;
import java.util.Map;

import com.zxj.arouter_annotation.RouteMeta;
import com.zxj.arouter_core.template.IRouteGroup;
import com.zxj.arouter_core.template.IService;

public class Warehouse {

    //root 映射表，保存分组信息
    /**
     * [[module1 : class com.zxj.arouter.routes.ARouter_Group_module1],[module2 : class com.zxj.arouter.routes.ARouter_Group_module2]]
     */
    static Map<String, Class<? extends IRouteGroup>> groupsIndex = new HashMap<>();

    //group 映射表 保存组中所有的数据
    static Map<String, RouteMeta> routes = new HashMap<>();

    //group 映射表，保存组中所有的数据
    static Map<Class, IService> services = new HashMap<>();

}
