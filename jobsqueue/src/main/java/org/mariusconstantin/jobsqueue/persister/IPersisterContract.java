package org.mariusconstantin.jobsqueue.persister;

import android.net.Uri;

/**
 * Created by MConstantin on 4/11/2016.
 */
public interface IPersisterContract {

    String AUTHORITY = "org.mariusconstantin.jobsqueue";
    Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

}
