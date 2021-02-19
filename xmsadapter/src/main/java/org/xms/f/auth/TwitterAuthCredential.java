package org.xms.f.auth;




public class TwitterAuthCredential extends org.xms.f.auth.AuthCredential {
    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {
        
        public org.xms.f.auth.TwitterAuthCredential createFromParcel(android.os.Parcel param0) {
            throw new java.lang.RuntimeException("Not Supported");
        }
        
        public org.xms.f.auth.TwitterAuthCredential[] newArray(int param0) {
            throw new java.lang.RuntimeException("Not Supported");
        }
    };
    
    
    
    public TwitterAuthCredential(org.xms.g.utils.XBox param0) {
        super(param0);
    }
    
    public java.lang.String getProvider() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.auth.TwitterAuthCredential.getProvider()");
            return String.valueOf(((com.huawei.agconnect.auth.AGConnectAuthCredential) this.getHInstance()).getProvider());
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.auth.TwitterAuthCredential) this.getGInstance()).getProvider()");
            return ((com.google.firebase.auth.TwitterAuthCredential) this.getGInstance()).getProvider();
        }
    }
    
    public java.lang.String getSignInMethod() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public int describeContents() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public void writeToParcel(android.os.Parcel param0, int param1) {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public static org.xms.f.auth.TwitterAuthCredential dynamicCast(java.lang.Object param0) {
        if (param0 instanceof org.xms.f.auth.TwitterAuthCredential) {
            return ((org.xms.f.auth.TwitterAuthCredential) param0);
        }
        if (param0 instanceof org.xms.g.utils.XGettable) {
            com.google.firebase.auth.TwitterAuthCredential gReturn = ((com.google.firebase.auth.TwitterAuthCredential) ((org.xms.g.utils.XGettable) param0).getGInstance());
            
            throw new java.lang.RuntimeException("AppGallery-connect does not support this API.");
        }
        return ((org.xms.f.auth.TwitterAuthCredential) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XGettable)) {
            return false;
        }
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            throw new java.lang.RuntimeException("AppGallery-connect does not support this API.");
        } else {
            return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.auth.TwitterAuthCredential;
        }
    }
}