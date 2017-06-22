package com.hacker.managed;

import com.google.inject.Inject;
import com.hacker.worker.TwitterWorker;
import io.dropwizard.lifecycle.Managed;

/**
 * Created by dinesh.rathore on 22/06/17.
 */
public class TwitterStream implements Managed {
    @Inject
    private TwitterWorker twitterWorker;




    @Override public void start() throws Exception {

        (new Thread(twitterWorker)).start();
    }

    @Override public void stop() throws Exception {

    }
}
