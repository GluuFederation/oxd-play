package org.xdi.oxd.client.callbacks;

import org.xdi.oxd.common.response.RegisterSiteResponse;
import org.xdi.oxd.common.response.RsCheckAccessResponse;
import org.xdi.oxd.common.response.RsProtectResponse;

/**
 * Created by lcom76 on 14/11/16.
 */
public interface RsResourceProtectCallback {

    public void success(RsProtectResponse rsProtectResponse);


    /**
     * retuns error message
     *
     * @param error
     */
    public void error(String error);
}
