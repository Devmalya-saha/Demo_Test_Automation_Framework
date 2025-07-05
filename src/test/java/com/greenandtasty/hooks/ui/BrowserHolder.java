package com.greenandtasty.hooks.ui;

public class BrowserHolder {
    private static final ThreadLocal<String> browserThreadLocal = new ThreadLocal<>();

    public static void setBrowser(String browser) {
        browserThreadLocal.set(browser);
    }

    public static String getBrowser() {
        return browserThreadLocal.get();
    }

    public static void clear() {
        browserThreadLocal.remove();
    }
}
