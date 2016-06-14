package org.xdi.oxd.client.callbacks;

import org.xdi.oxd.common.response.LogoutResponse;

/**
 * Created by lcom15 on 31/5/16.
 */
public interface GetlogoutUrlCallback {

    /**
     * will return LogoutResponse on successful command
     * @param logoutResponse
     */
    public void success(LogoutResponse logoutResponse);

    /**
     * retuns error message
     * @param error
     */
    public void error(String error);
}
