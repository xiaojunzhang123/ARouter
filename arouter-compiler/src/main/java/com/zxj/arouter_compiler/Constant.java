package com.zxj.arouter_compiler;

import com.squareup.javapoet.ClassName;

public class Constant {

    public static final ClassName ROUTER = ClassName.get("com.zxj.arouter_core","ARouter");

    public static final String PROJECT = "ARouter";
    public static final String SEPARATOR = "_";

    public static final String ACTIVITY = "android.app.Activity";
    public static final String ISERVICE = "com.zxj.arouter_core.template.IService";
    public static final String IROUTE_GROUP = "com.zxj.arouter_core.template.IRouteGroup";
    public static final String IROUTE_ROOT = "com.zxj.arouter_core.template.IRouteRoot";

    public static final String ARGUMENTS_NAME = "moduleName";
    public static final String ANNOTATION_TYPE_ROUTE = "com.zxj.arouter_annotation.Route";
    public static final String ANN_TYPE_EXTRA = "com.zxj.arouter_annotation.Extra";

    public static final String NAME_OF_GROUP = PROJECT + SEPARATOR + "Group" + SEPARATOR;
    public static final String NAME_OF_ROOT = PROJECT + SEPARATOR + "Root" + SEPARATOR;
    public static final String PACKAGE_OF_GENERATE_FILE = "com.zxj.arouter.routes";


    public static final String IEXTRA = "com.zxj.arouter_core.template.IExtra";
    public static final String METHOD_LOAD_EXTRA = "loadExtra";
    public static final String METHOD_LOAD_INTO = "loadInto";

    private static final String LANG = "java.lang";
    public static final String PARCELABLE = "android.os.Parcelable";
    public static final String STRING = LANG + ".String";
    public static final String ARRAYLIST = "java.util.ArrayList";
    public static final String LIST = "java.util.List";
    public static final String INTEGER = LANG + ".Integer";

    public static final String BYTEARRAY = "byte[]";
    public static final String SHORTARRAY = "short[]";
    public static final String BOOLEANARRAY = "boolean[]";
    public static final String CHARARRAY = "char[]";
    public static final String DOUBLEARRAY = "double[]";
    public static final String FLOATARRAY = "float[]";
    public static final String INTARRAY = "int[]";
    public static final String LONGARRAY = "long[]";
    public static final String STRINGARRAY = "java.lang.String[]";

    public static final String NAME_OF_EXTRA = SEPARATOR + "Extra";


}
