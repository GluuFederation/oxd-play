package org.xdi.oxd.client.callbacks;

import org.xdi.oxd.common.response.GetTokensByCodeResponse;

/**
 * Created by lcom15 on 31/5/16.
 */
public interface GetTokensByCodeCallback {

    /**will return  getTokensByCodeResponse on success of GetTokensByCode command
     * @param getTokensByCodeResponse
     */
    public void success(GetTokensByCodeResponse getTokensByCodeResponse);


    /**
     * retuns error message
     * @param error
     */
    public void error(String error);
}
