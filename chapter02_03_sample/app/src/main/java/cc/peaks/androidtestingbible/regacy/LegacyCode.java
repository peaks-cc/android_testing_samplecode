package cc.peaks.androidtestingbible.regacy;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

public class LegacyCode {
    LocalDataFetcher localDataFetcher = new LocalDataFetcher();
    RemoteDataFetcher remoteDataFetcher = new RemoteDataFetcher();
    NetworkUtilsWrapper networkUtils = new NetworkUtilsWrapper();

    private DataConverter converter = new DataConverter();

    void loadData(String param, Context context, Callback<NewData> callback) {
        OldData oldData = load(param, context);
        NewData newData = converter.convert(oldData);
        callback.onSuccess(newData);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    OldData load(String param, Context context) {
        if (networkUtils.isOnline(context)) {
            return remoteDataFetcher.fetch(param);
        } else {
            return localDataFetcher.fetch(param);
        }
    }

    interface Callback<T> {
        void onSuccess(T data);
    }
}
