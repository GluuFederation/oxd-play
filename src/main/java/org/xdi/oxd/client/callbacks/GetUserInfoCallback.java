package org.xdi.oxd.client.callbacks;

import org.xdi.oxd.common.response.GetUserInfoResponse;

/**
 * Created by lcom15 on 31/5/16.
 */
public interface GetUserInfoCallback {
    /***
     * returns getUserInfoResponse on sucesss of GetUserInfo command
     * @param getUserInfoResponse
     */
    public void success(GetUserInfoResponse getUserInfoResponse);

    /**
     * retruns error message
     * @param error
     */
    public void error(String error);
}
