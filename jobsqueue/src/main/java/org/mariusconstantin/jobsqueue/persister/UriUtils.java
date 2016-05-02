package org.mariusconstantin.jobsqueue.persister;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by MConstantin on 4/26/2016.
 */
public class UriUtils {

    @NonNull
    public static Uri buildUriFromId(@NonNull Uri uri, @NonNull String id) {
        return uri.buildUpon().appendPath(id).build();
    }

    @Nullable
    public static String idFromUri(@NonNull Uri uri) {
        return uri.getPathSegments().size() >= 2 ? uri.getLastPathSegment() : null;
    }
}
