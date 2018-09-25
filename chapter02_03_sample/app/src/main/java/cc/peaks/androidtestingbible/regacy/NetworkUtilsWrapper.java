package cc.peaks.androidtestingbible.regacy;

import android.content.Context;

public class NetworkUtilsWrapper {
    public boolean isOnline(Context context) {
        return NetworkUtils.isOnline(context);
    }
}
