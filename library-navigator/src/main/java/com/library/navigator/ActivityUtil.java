package com.library.navigator;

import android.app.Activity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivityUtil {

    private List<Activity> activities;

    private static volatile ActivityUtil mInstance;

    private ActivityUtil() {
        this.activities = new ArrayList<Activity>();
    }

    public static ActivityUtil getInstance() {
        if (mInstance == null) {
            synchronized (ActivityUtil.class) {
                if (mInstance == null) {
                    mInstance = new ActivityUtil();
                }
            }
        }
        return mInstance;
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activities.remove(activity);
        }
    }

    public Activity currentActivity() {
        Activity activity = null;
        int size = activities.size();
        if (!activities.isEmpty()) {
            activity = activities.get(size - 1);
        }
        return activity;
    }

    public void removeActivity(int count) {
        for (int i = 0; i < count; i++) {
            removeActivity(currentActivity());
        }
    }

    public void finish(Class<?> clazz) {
        if (clazz == null) return;
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity == null) {
                iterator.remove();
                continue;
            }
            if (clazz == activity.getClass()) {
                activity.finish();
                iterator.remove();
                break;
            }
        }
    }

    public void finishAllActivity() {
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity == null) {
                iterator.remove();
                continue;
            }
            activity.finish();
        }
    }

    public void finishAllExcept(Class<?>... clazz) {
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null) {
                if (clazz != null) {
                    if (clazz.length > 0) {
                        for (Class<?> activityClass : clazz) {
                            if (!activity.getClass().equals(activityClass)) {
                                activity.finish();
                                iterator.remove();
                            }
                        }
                    }
                }
            }
        }
    }
}
