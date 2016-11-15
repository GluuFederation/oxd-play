package org.xdi.oxd.client.callbacks;

import org.xdi.oxd.common.params.RsCheckAccessParams;
import org.xdi.oxd.common.response.RsCheckAccessResponse;

/**
 * Created by lcom76 on 14/11/16.
 */
public interface RsCheckAccessCallback {

    public void success(RsCheckAccessResponse rsCheckAccessResponse);

    public void error(String error);
}
