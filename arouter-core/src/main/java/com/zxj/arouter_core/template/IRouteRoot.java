package com.zxj.arouter_core.template;

import java.util.Map;

public interface IRouteRoot {
    void loadInto(Map<String,Class<? extends IRouteGroup>> routes);
}
