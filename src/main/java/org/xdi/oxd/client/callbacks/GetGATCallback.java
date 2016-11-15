package org.xdi.oxd.client.callbacks;

import org.xdi.oxd.common.response.RpGetRptResponse;

/**
 * Created by lcom76 on 15/11/16.
 */
public interface GetGATCallback {

    public void success(RpGetRptResponse rpGetRptResponse);

    public void error(String error);
}
