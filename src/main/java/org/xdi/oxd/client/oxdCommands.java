package org.xdi.oxd.client;

import com.google.common.base.Splitter;
import org.xdi.oxd.client.callbacks.*;
import org.xdi.oxd.common.Command;
import org.xdi.oxd.common.CommandType;
import org.xdi.oxd.common.params.*;
import org.xdi.oxd.common.response.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 *This class will be useful to crate connection with oxd-server and calling required commands to oxd-server
 */
public class oxdCommands {

    /**
     * use to call Register site commad
     * @param host oxd-server host eg.localhost or 127.0.0.1
     * @param port oxd-server listing port default  port is 8099
     * @param commandParams params for register site command
     * @param callback return success or error
     */
    public static void registerSite(String host, int port, RegisterSiteParams commandParams, RegisterSiteCallback callback) {
        CommandClient client = null;
        RegisterSiteResponse respRegisterSIte = null;

        if (commandParams.getAuthorizationRedirectUri() == null) {
            callback.error("AuthorizationRedirectUri is required");
        }

        try {
            client = new CommandClient(host, port);


            final Command command = new Command(CommandType.REGISTER_SITE);
            command.setParamsObject(commandParams);

            respRegisterSIte = client.send(command).dataAsResponse(RegisterSiteResponse.class);
            if (respRegisterSIte != null) {
                callback.success(respRegisterSIte);
            } else {
                callback.error("invalid params internal server error");

            }

        } catch (IOException e) {
            e.printStackTrace();
            callback.error(e.getMessage());
        } finally {
            CommandClient.closeQuietly(client);

        }
    }


    /**
     * use to  update site configuration
     * @param host oxd-server host eg.localhost or 127.0.0.1
     * @param port oxd-server listing port default  port is 8099
     * @param commandParams params for Update site command
     * @param callback return success or error
     */
    public static void updateSite(String host, int port, UpdateSiteParams commandParams, UpdateSiteCallback callback) {
        CommandClient client = null;
        UpdateSiteResponse respUpdateSite = null;
        if (commandParams.getAuthorizationRedirectUri() == null) {
            callback.error("AuthorizationRedirectUri is required");
        }

        try {
            client = new CommandClient(host, port);


            final Command command = new Command(CommandType.UPDATE_SITE);
            command.setParamsObject(commandParams);
            respUpdateSite = client.send(command).dataAsResponse(UpdateSiteResponse.class);
            if (respUpdateSite != null) {
                callback.success(respUpdateSite);
            } else {
                callback.error("invalid params internal server error");

            }

        } catch (IOException e) {
            e.printStackTrace();
            callback.error(e.getMessage());

        } finally {
            CommandClient.closeQuietly(client);
        }
    }

    /**
     * use to get Authorization URl
     * @param host oxd-server host eg.localhost or 127.0.0.1
     * @param port oxd-server listing port default  port is 8099
     * @param commandParams params for getiing Authorization URL
     * @param callback return success or error
     */
    public static void getAuthorizationUrl(String host, int port, GetAuthorizationUrlParams commandParams, GetAuthorizationUrlCallback callback) {
        CommandClient client = null;
        GetAuthorizationUrlResponse respGetAuthoUrl = null;

        if(commandParams.getOxdId() == null || commandParams.getOxdId().length()==0)
        {
            callback.error("oxd_id is null or empty.");

        }

        try {
            client = new CommandClient(host, port);

            final Command command = new Command(CommandType.GET_AUTHORIZATION_URL);
            command.setParamsObject(commandParams);

            respGetAuthoUrl = client.send(command).dataAsResponse(GetAuthorizationUrlResponse.class);
            if (respGetAuthoUrl != null) {
                callback.success(respGetAuthoUrl);
            } else {
                callback.error("invalid params internal server error");

            }

        } catch (IOException e) {
            e.printStackTrace();
            callback.error(e.getMessage());

        } finally {
            CommandClient.closeQuietly(client);

        }
    }


    /**
     * use to get token on successful login
     * @param host oxd-server host eg.localhost or 127.0.0.1
     * @param port oxd-server listing port default  port is 8099
     * @param commandParams params for getiing Token after successful login and redirect to authorize url
     * @param callback return success or error
     */

    public static void  getToken(String host, int port, GetTokensByCodeParams commandParams, GetTokensByCodeCallback callback) {
        CommandClient client = null;
        GetTokensByCodeResponse respGetTokensByCodeResponse = null;


        if(commandParams.getOxdId() == null || commandParams.getOxdId().length()==0)
        {
            callback.error("oxd_id is null or empty.");

        }
        if(commandParams.getCode()== null || commandParams.getCode().length()==0)
        {
            callback.error("code is null or empty.");

        }
        if(commandParams.getScopes()== null || commandParams.getScopes().size()==0)
        {
            callback.error("code is null or empty.");

        }


        try {
            client = new CommandClient(host, port);


            final Command command = new Command(CommandType.GET_TOKENS_BY_CODE);
            command.setParamsObject(commandParams);
            respGetTokensByCodeResponse = client.send(command).dataAsResponse(GetTokensByCodeResponse.class);
            if (respGetTokensByCodeResponse != null) {
                callback.success(respGetTokensByCodeResponse);
            } else {
                callback.error("invalid params internal server error");

            }

        } catch (IOException e) {
            e.printStackTrace();
            callback.error(e.getMessage());

        } finally {
            CommandClient.closeQuietly(client);

        }

    }

    /**
     * use to get User info
     * @param host oxd-server host eg.localhost or 127.0.0.1
     * @param port oxd-server listing port default  port is 8099
     * @param commandParams params for getting User Info using user token
     * @param callback return success or error
     */

    public static void getUserInfo(String host, int port, GetUserInfoParams commandParams, GetUserInfoCallback callback) {
        CommandClient client = null;

        GetUserInfoResponse respGetUserInfoResponse = null;
        if(commandParams.getOxdId() == null || commandParams.getOxdId().length()==0)
        {
            callback.error("oxd_id is null or empty.");

        }
        if(commandParams.getAccessToken() == null || commandParams.getAccessToken().length()==0)
        {
            callback.error("AccessToken is null or empty.");

        }
        try {

            client = new CommandClient(host, port);


            final Command command = new Command(CommandType.GET_USER_INFO);
            command.setParamsObject(commandParams);
            respGetUserInfoResponse = client.send(command).dataAsResponse(GetUserInfoResponse.class);
            if (respGetUserInfoResponse != null) {
                callback.success(respGetUserInfoResponse);
            } else {
                callback.error("invalid params internal server error");

            }


        } catch (Exception e) {
            e.printStackTrace();
            callback.error(e.getMessage());
        } finally {
            CommandClient.closeQuietly(client);

        }


    }


    /**
     * use to get logout uri
     * @param host oxd-server host eg.localhost or 127.0.0.1
     * @param port oxd-server listing port default  port is 8099
     * @param commandParams params for getting  Logout URI to logout user from application and close user session on server site.
     * @param callback return success or error
     */


    public static void getLogoutUri(String host, int port, GetLogoutUrlParams commandParams, GetlogoutUrlCallback callback) {
        CommandClient client = null;

        LogoutResponse logoutResponse = null;
        if(commandParams.getOxdId() == null || commandParams.getOxdId().length()==0)
        {
            callback.error("oxd_id is null or empty.");

        }
        try {

            client = new CommandClient(host, port);


            final Command command = new Command(CommandType.GET_LOGOUT_URI);
            command.setParamsObject(commandParams);
            logoutResponse = client.send(command).dataAsResponse(LogoutResponse.class);
            if (logoutResponse != null) {
                callback.success(logoutResponse);
            } else {
                callback.error("invalid params internal server error");

            }


        } catch (Exception e) {
            e.printStackTrace();
            callback.error(e.getMessage());
        } finally {
            CommandClient.closeQuietly(client);

        }


    }


    public Map<String, String> splitQuery(String uri) throws UnsupportedEncodingException {
        String query = uri.split("\\?")[1];
        final Map<String, String> map = Splitter.on('&').trimResults().withKeyValueSeparator("=").split(query);
        return map;
    }

    public String codeRequest(CommandClient client, String siteId, String userId, String userSecret) {
        GetAuthorizationCodeParams params = new GetAuthorizationCodeParams();
        params.setOxdId(siteId);
        params.setUsername(userId);
        params.setPassword(userSecret);

        final Command command = new Command(CommandType.GET_AUTHORIZATION_CODE).setParamsObject(params);
        return client.send(command).dataAsResponse(GetAuthorizationCodeResponse.class).getCode();
    }

}
