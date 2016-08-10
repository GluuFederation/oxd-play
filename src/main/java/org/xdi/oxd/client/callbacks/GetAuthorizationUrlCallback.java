package org.xdi.oxd.client.callbacks;

import org.xdi.oxd.common.response.GetAuthorizationUrlResponse;

public interface GetAuthorizationUrlCallback {
    /**
     * will return GetAuthorizationUrlResponse on successful command
     * @param getAuthorizationUrlResponse
     */
    public void success(GetAuthorizationUrlResponse getAuthorizationUrlResponse);


    /**
     * retuns error message
     * @param error
     */
    public void error(String error);
}
