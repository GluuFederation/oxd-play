package org.xdi.oxd.client.manual;

import org.xdi.oxd.client.CommandClient;
import org.xdi.oxd.common.Command;
import org.xdi.oxd.common.CommandType;
import org.xdi.oxd.common.params.RegisterSiteParams;
import org.xdi.oxd.common.response.RegisterSiteResponse;

import java.io.IOException;

/**
 * @author Yuriy Zabrovarnyy
 * @version 0.9, 27/07/2016
 */

public class LogoutUrlTest {
    public static void main(String[] args) throws IOException {
        CommandClient client = null;
        try {
            client = new CommandClient("localhost", 8099);

            final RegisterSiteParams commandParams = new RegisterSiteParams();
            commandParams.setAuthorizationRedirectUri("https://360.lobosstudios.com/callback");
            commandParams.setPostLogoutRedirectUri("https://360.lobosstudios.com/end_session");
//            commandParams.setClientLogoutUri(Lists.newArrayList(logoutUri));
//            commandParams.setClientId();
            final Command command = new Command(CommandType.REGISTER_SITE);
            command.setParamsObject(commandParams);

            final RegisterSiteResponse resp = client.send(command).dataAsResponse(RegisterSiteResponse.class);
        } finally {
            CommandClient.closeQuietly(client);
        }
    }
}
