package org.xdi.oxd.client.callbacks;

import org.xdi.oxd.common.response.UpdateSiteResponse;

/**
 * Created by lcom15 on 31/5/16.
 */
public interface UpdateSiteCallback {

    /**
     * will return updateSiteResponse on successful command
     * @param updateSiteResponse
     */
    public void success(UpdateSiteResponse updateSiteResponse);


    /**
     * retuns error message
     * @param error
     */
    public void error(String error);
}
