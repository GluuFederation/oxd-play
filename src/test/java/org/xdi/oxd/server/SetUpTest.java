package org.xdi.oxd.server;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.xdi.oxd.common.CoreUtils;

/**
 * @author Yuriy Zabrovarnyy
 * @version 0.9, 11/04/2016
 */

public class SetUpTest {

    @BeforeSuite
    public static void beforeSuite() {
        CoreUtils.createExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ServerLauncher.start();
            }
        });
        // from one side we should give time to start server, from other we can't start in current
        // thread because it will block suite thread, ugly but works...
        CoreUtils.sleep(7);
    }

    @AfterSuite
    public static void afterSuite() {
        ServerLauncher.shutdown();
    }
}
