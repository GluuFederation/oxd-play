package org.xdi.oxd.client.callbacks;

import org.xdi.oxd.common.response.RegisterSiteResponse;

/**
 * Created by lcom15 on 31/5/16.
 */
public interface RegisterSiteCallback {
    /**
     * reruns RegisterSiteResponse on success on RegisterSite command
     * @param registerSiteResponse
     */
    public void success(RegisterSiteResponse registerSiteResponse);


    /**
     * retuns error message
     * @param error
     */
    public void error(String error);
}
