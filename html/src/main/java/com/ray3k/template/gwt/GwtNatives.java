package com.ray3k.template.gwt;

import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.google.gwt.user.client.Window;
import com.ray3k.template.Natives;
import regexodus.Matcher;
import regexodus.Pattern;

public class GwtNatives extends Natives {
    private boolean isMobile;

    public GwtNatives() {
        this.isMobile = isMobileDevice();
    }

    @Override
    public boolean isMobile() {
        return this.isMobile;
    }

    /** @return {@code true} if application runs on a mobile device, for realsies this time */
    public static boolean isMobileDevice () {
        // RegEx pattern from detectmobilebrowsers.com (public domain)
        String pattern = "(android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec"
                + "|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)"
                + "i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)"
                + "|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(Window.Navigator.getUserAgent().toLowerCase());
        return m.find();
    }
}
