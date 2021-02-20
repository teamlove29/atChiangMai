package org.xms.f;


import android.app.Activity;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

public class ExtensionApp extends org.xms.g.utils.XObject {
    
    private interface Constant {
        String DEFAULT_APP_NAME = "[DEFAULT]";
    }
    private static Activity getCurrentActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(
                    null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (ClassNotFoundException e) {
            org.xms.g.utils.XmsLog.e("getApplicationContext", "", e);
        } catch (InvocationTargetException e) {
            org.xms.g.utils.XmsLog.e("getApplicationContext", "", e);
        } catch (NoSuchMethodException e) {
            org.xms.g.utils.XmsLog.e("getApplicationContext", "", e);
        } catch (NoSuchFieldException e) {
            org.xms.g.utils.XmsLog.e("getApplicationContext", "", e);
        } catch (IllegalAccessException e) {
            org.xms.g.utils.XmsLog.e("getApplicationContext", "", e);
        }
        return null;
    }
    
    public ExtensionApp(org.xms.g.utils.XBox param0) {
        super(param0);
    }
    
    public java.lang.String getDEFAULT_APP_NAME() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.getDEFAULT_APP_NAME");
            return Constant.DEFAULT_APP_NAME;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.FirebaseApp) this.getGInstance()).DEFAULT_APP_NAME");
            return ((com.google.firebase.FirebaseApp) this.getGInstance()).DEFAULT_APP_NAME;
        }
    }
    
    public boolean equals(java.lang.Object param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.equals()");
            return false;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.FirebaseApp) this.getGInstance()).equals(param0)");
            return ((com.google.firebase.FirebaseApp) this.getGInstance()).equals(param0);
        }
    }
    
    public android.content.Context getApplicationContext() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.getApplicationContext()");
            Activity activity = getCurrentActivity();
            return activity == null ? null : activity.getApplicationContext();
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.FirebaseApp) this.getGInstance()).getApplicationContext()");
            return ((com.google.firebase.FirebaseApp) this.getGInstance()).getApplicationContext();
        }
    }
    
    public static java.util.List<org.xms.f.ExtensionApp> getApps(android.content.Context param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.getApps(param0)");
            java.util.List<org.xms.f.ExtensionApp> xReturn = new ArrayList<>();
            xReturn.add(new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(null, null)));
            return xReturn;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.FirebaseApp.getApps(param0)");
            java.util.List gReturn = com.google.firebase.FirebaseApp.getApps(param0);
            return ((java.util.List) org.xms.g.utils.Utils.mapCollection(gReturn, new org.xms.g.utils.Function<com.google.firebase.FirebaseApp, org.xms.f.ExtensionApp>() {
                
                public org.xms.f.ExtensionApp apply(com.google.firebase.FirebaseApp param0) {
                    return new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(param0, null));
                }
            }));
        }
    }
    
    public static org.xms.f.ExtensionApp getInstance(java.lang.String param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.getInstance(java.lang.String)");
            return new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(null, null));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.FirebaseApp.getInstance(param0)");
            com.google.firebase.FirebaseApp gReturn = com.google.firebase.FirebaseApp.getInstance(param0);
            return ((gReturn) == null ? null : (new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public static org.xms.f.ExtensionApp getInstance() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.getInstance()");
            return new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(null, null));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.FirebaseApp.getInstance()");
            com.google.firebase.FirebaseApp gReturn = com.google.firebase.FirebaseApp.getInstance();
            return ((gReturn) == null ? null : (new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public java.lang.String getName() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.getName()");
            return "";
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.FirebaseApp) this.getGInstance()).getName()");
            return ((com.google.firebase.FirebaseApp) this.getGInstance()).getName();
        }
    }
    
    public org.xms.f.ExtensionOptions getOptions() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.getOptions()");
            return new org.xms.f.ExtensionOptions(new org.xms.g.utils.XBox(null, null));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.FirebaseApp) this.getGInstance()).getOptions()");
            com.google.firebase.FirebaseOptions gReturn = ((com.google.firebase.FirebaseApp) this.getGInstance()).getOptions();
            return ((gReturn) == null ? null : (new org.xms.f.ExtensionOptions(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public int hashCode() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.hashCode()");
            return 0;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.FirebaseApp) this.getGInstance()).hashCode()");
            return ((com.google.firebase.FirebaseApp) this.getGInstance()).hashCode();
        }
    }
    
    public static org.xms.f.ExtensionApp initializeApp(android.content.Context param0, org.xms.f.ExtensionOptions param1) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.initializeApp(android.content.Context, org.xms.f.ExtensionOptions)");
            return new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(null, null));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.FirebaseApp.initializeApp(param0, ((com.google.firebase.FirebaseOptions) ((param1) == null ? null : (param1.getGInstance()))))");
            com.google.firebase.FirebaseApp gReturn = com.google.firebase.FirebaseApp.initializeApp(param0, ((com.google.firebase.FirebaseOptions) ((param1) == null ? null : (param1.getGInstance()))));
            return ((gReturn) == null ? null : (new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public static org.xms.f.ExtensionApp initializeApp(android.content.Context param0, org.xms.f.ExtensionOptions param1, java.lang.String param2) throws java.lang.IllegalStateException {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.initializeApp(android.content.Context, org.xms.f.ExtensionOptions, java.lang.String)");
            return new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(null, null));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.FirebaseApp.initializeApp(param0, ((com.google.firebase.FirebaseOptions) ((param1) == null ? null : (param1.getGInstance()))), param2)");
            com.google.firebase.FirebaseApp gReturn = com.google.firebase.FirebaseApp.initializeApp(param0, ((com.google.firebase.FirebaseOptions) ((param1) == null ? null : (param1.getGInstance()))), param2);
            return ((gReturn) == null ? null : (new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public static org.xms.f.ExtensionApp initializeApp(android.content.Context param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.initializeApp(android.content.Context)");
            return new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(null, null));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.FirebaseApp.initializeApp(param0)");
            com.google.firebase.FirebaseApp gReturn = com.google.firebase.FirebaseApp.initializeApp(param0);
            return ((gReturn) == null ? null : (new org.xms.f.ExtensionApp(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public void setAutomaticResourceManagementEnabled(boolean param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.setAutomaticResourceManagementEnabled(boolean)");
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.FirebaseApp) this.getGInstance()).setAutomaticResourceManagementEnabled(param0)");
            ((com.google.firebase.FirebaseApp) this.getGInstance()).setAutomaticResourceManagementEnabled(param0);
        }
    }
    
    public java.lang.String toString() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.ExtensionApp.toString()");
            return "";
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.FirebaseApp) this.getGInstance()).toString()");
            return ((com.google.firebase.FirebaseApp) this.getGInstance()).toString();
        }
    }
    
    public static org.xms.f.ExtensionApp dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.ExtensionApp) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XGettable)) {
            return false;
        }
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            throw new java.lang.RuntimeException("AppGallery-connect does not support this API.");
        } else {
            return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.FirebaseApp;
        }
    }
}