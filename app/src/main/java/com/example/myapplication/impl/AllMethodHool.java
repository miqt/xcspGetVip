//package com.example.myapplication.impl;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//
//import de.robv.android.xposed.XC_MethodHook;
//import de.robv.android.xposed.XposedBridge;
//import de.robv.android.xposed.XposedHelpers;
//import de.robv.android.xposed.callbacks.XC_LoadPackage;
//
//public class AllMethodHool {
//    static String strClassName = "";
//
//    public static void hook(XC_LoadPackage.LoadPackageParam lpparam) {
//        XposedBridge.log("Loaded App:" + lpparam.packageName);
//
//        // 被Hook操作的目标Android应用的包名，进行Hook操作的过滤
//        String strPackageName = "me.weishu.exp";
//        if (lpparam.packageName.equals(strPackageName)) {
//
//
//            // 不在Android应用默认的classes.dex文件中的类方法的Hook操作，例如:
//            // 1.MultiDex情况下的，多dex文件中的类方法的Hook操作，例如:classes1.dex中的类方法
//            // 2.主dex加载的jar(包含dex)情况下的，类方法的的Hook操作
//
//            // Hook类方法ClassLoader#loadClass(String)
//            XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
//
//                // 在类方法loadClass执行之后执行的代码
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//
//                    // 参数的检查
//                    if (param.hasThrowable()) {
//                        return;
//                    }
//
//                    // 获取指定名称的类加载之后的Class<?>
//                    Class<?> clazz = (Class<?>) param.getResult();
//                    // 获取加载的指定类的名称
//                    String strClazz = clazz.getName();
//                    XposedBridge.log("["+lpparam.packageName + "] LoadClass : " + strClazz);
//
////                  // 被Hook操作的目标类名称
////                  String strClazzName = "";
////                  // 被Hook操作的类方法的名称
////                  String strMethodName = "";
//
////                    // 所有的类都是通过loadClass方法加载的
////                    // 过滤掉Android系统的类以及一些常见的java类库
////                    if (!strClazz.startsWith("android.")
////                            && !strClazz.startsWith(".system")
////                            && !strClazz.startsWith("java.")
////                            && !strClazz.startsWith("org.")
////                            && !strClazz.contains("umeng.")
////                            && !strClazz.contains("com.google")
////                            && !strClazz.contains(".alipay")
////                            && !strClazz.contains(".netease")
////                            && !strClazz.contains(".alibaba")
////                            && !strClazz.contains(".pgyersdk")
////                            && !strClazz.contains(".daohen")
////                            && !strClazz.contains(".bugly")
////                            && !strClazz.contains("mini")
////                            && !strClazz.contains("xposed")
////                    ) {
//                        print(clazz, strClazz);
//
////                    }
//                }
//
//                private void print(Class<?> clazz, String strClazz) {
//                    // 或者只Hook加密算法类、网络数据传输类、按钮事件类等协议分析的重要类
//
//                    // 同步处理一下
//                    synchronized (this.getClass()) {
//
//                        // 获取被Hook的目标类的名称
//                        strClassName = strClazz;
//                        //XposedBridge.log("HookedClass : "+strClazz);
//                        // 获取到指定名称类声明的所有方法的信息
//                        Method[] m = clazz.getDeclaredMethods();
//                        // 打印获取到的所有的类方法的信息
//                        for (int i = 0; i < m.length; i++) {
//
//                            //XposedBridge.log("HOOKED CLASS-METHOD: "+strClazz+"-"+m[i].toString());
//                            if (!Modifier.isAbstract(m[i].getModifiers())           // 过滤掉指定名称类中声明的抽象方法
//                                    && !Modifier.isNative(m[i].getModifiers())     // 过滤掉指定名称类中声明的Native方法
//                                    && !Modifier.isInterface(m[i].getModifiers())  // 过滤掉指定名称类中声明的接口方法
//                            ) {
//
//                                // 对指定名称类中声明的非抽象方法进行java Hook处理
//                                XposedBridge.hookMethod(m[i], new XC_MethodHook() {
//
//                                    // 被java Hook的类方法执行完毕之后，打印log日志
//                                    @Override
//                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//
//                                        // 打印被java Hook的类方法的名称和参数类型等信息
//                                        XposedBridge.log(lpparam.packageName + "HOOKED METHOD: " + strClassName + "-" + param.method.toString());
//                                    }
//                                });
//                            }
//                        }
//                    }
//                }
//            });
//        }
//
//    }
//
//    // 获取指定名称的类声明的类成员变量、类方法、内部类的信息
//    public void dumpClass(Class<?> actions) {
//
//        XposedBridge.log("Dump class " + actions.getName());
//        XposedBridge.log("Methods");
//
//        // 获取到指定名称类声明的所有方法的信息
//        Method[] m = actions.getDeclaredMethods();
//        // 打印获取到的所有的类方法的信息
//        for (int i = 0; i < m.length; i++) {
//
//            XposedBridge.log(m[i].toString());
//        }
//
//        XposedBridge.log("Fields");
//        // 获取到指定名称类声明的所有变量的信息
//        Field[] f = actions.getDeclaredFields();
//        // 打印获取到的所有变量的信息
//        for (int j = 0; j < f.length; j++) {
//
//            XposedBridge.log(f[j].toString());
//        }
//
//        XposedBridge.log("Classes");
//        // 获取到指定名称类中声明的所有内部类的信息
//        Class<?>[] c = actions.getDeclaredClasses();
//        // 打印获取到的所有内部类的信息
//        for (int k = 0; k < c.length; k++) {
//
//            XposedBridge.log(c[k].toString());
//        }
//    }
//}
//
///**
// * Look up a method and place a hook on it. The last argument must be the callback for the hook.
// *
// * @return the {@code Class} object.
// * @param className
// * the name of the class to look for.
// * @throws ClassNotFoundException
// * if the class can not be found.
// * @return the {@code Class} object.
// * @param className
// * the name of the class to look for.
// * @throws ClassNotFoundException
// * if the class can not be found.
// * @return the {@code Class} object.
// * @param className
// * the name of the class to look for.
// * @throws ClassNotFoundException
// * if the class can not be found.
// * @return the {@code Class} object.
// * @param className
// * the name of the class to look for.
// * @throws ClassNotFoundException
// * if the class can not be found.
// * @return the {@code Class} object.
// * @param className
// * the name of the class to look for.
// * @throws ClassNotFoundException
// * if the class can not be found.
// * @return the {@code Class} object.
// * @param className
// * the name of the class to look for.
// * @throws ClassNotFoundException
// * if the class can not be found.
// * @see #findMethodExact(Class, String, Object...)
// * @see #findAndHookMethod(Class, String, Object...)
// * Loads the class with the specified name. Invoking this method is
// * equivalent to calling {@code loadClass(className, false)}.
// * <p>
// * <strong>Note:</strong> In the Android reference implementation, the
// * second parameter of {@link #loadClass(String, boolean)} is ignored
// * anyway.
// * </p>
// * @see #findAndHookMethod(Class, String, Object...)
// * Loads the class with the specified name. Invoking this method is
// * equivalent to calling {@code loadClass(className, false)}.
// * <p>
// * <strong>Note:</strong> In the Android reference implementation, the
// * second parameter of {@link #loadClass(String, boolean)} is ignored
// * anyway.
// * </p>
// * @see #findAndHookMethod(Class, String, Object...)
// * Loads the class with the specified name. Invoking this method is
// * equivalent to calling {@code loadClass(className, false)}.
// * <p>
// * <strong>Note:</strong> In the Android reference implementation, the
// * second parameter of {@link #loadClass(String, boolean)} is ignored
// * anyway.
// * </p>
// * @see #findAndHookMethod(Class, String, Object...)
// * Loads the class with the specified name. Invoking this method is
// * equivalent to calling {@code loadClass(className, false)}.
// * <p>
// * <strong>Note:</strong> In the Android reference implementation, the
// * second parameter of {@link #loadClass(String, boolean)} is ignored
// * anyway.
// * </p>
// * @see #findAndHookMethod(Class, String, Object...)
// * Loads the class with the specified name. Invoking this method is
// * equivalent to calling {@code loadClass(className, false)}.
// * <p>
// * <strong>Note:</strong> In the Android reference implementation, the
// * second parameter of {@link #loadClass(String, boolean)} is ignored
// * anyway.
// * </p>
// * @see #findAndHookMethod(Class, String, Object...)
// * Loads the class with the specified name. Invoking this method is
// * equivalent to calling {@code loadClass(className, false)}.
// * <p>
// * <strong>Note:</strong> In the Android reference implementation, the
// * second parameter of {@link #loadClass(String, boolean)} is ignored
// * anyway.
// * </p>
// */
///* 目标java方法的Hook
//public static XC_MethodHook.Unhook findAndHookMethod(Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {
//    if (parameterTypesAndCallback.length == 0 || !(parameterTypesAndCallback[parameterTypesAndCallback.length-1] instanceof XC_MethodHook))
//        throw new IllegalArgumentException("no callback defined");
//
//    XC_MethodHook callback = (XC_MethodHook) parameterTypesAndCallback[parameterTypesAndCallback.length-1];
//    Method m = findMethodExact(clazz, methodName, getParameterClasses(clazz.getClassLoader(), parameterTypesAndCallback));
//
//    return XposedBridge.hookMethod(m, callback);
//}*/
//
///** @see #findAndHookMethod(Class, String, Object...) */
///* 目标java方法的Hook
// public static XC_MethodHook.Unhook findAndHookMethod(String className, ClassLoader classLoader, String methodName, Object... parameterTypesAndCallback) {
//    return findAndHookMethod(findClass(className, classLoader), methodName, parameterTypesAndCallback);
//}*/
//
//
///**
// * Loads the class with the specified name. Invoking this method is
// * equivalent to calling {@code loadClass(className, false)}.
// * <p>
// * <strong>Note:</strong> In the Android reference implementation, the
// * second parameter of {@link #loadClass(String, boolean)} is ignored
// * anyway.
// * </p>
// *
// * @return the {@code Class} object.
// * @param className
// *            the name of the class to look for.
// * @throws ClassNotFoundException
// *             if the class can not be found.
// */
////public Class<?> loadClass(String className) throws ClassNotFoundException {
////        return loadClass(className, false);
////    }