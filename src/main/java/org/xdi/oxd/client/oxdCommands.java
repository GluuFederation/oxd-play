package org.xdi.oxd.client;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.xdi.oxd.client.callbacks.*;
import org.xdi.oxd.common.Command;
import org.xdi.oxd.common.CommandResponse;
import org.xdi.oxd.common.CommandType;
import org.xdi.oxd.common.params.*;
import org.xdi.oxd.common.response.*;
import org.xdi.oxd.rs.protect.Jackson;
import org.xdi.oxd.rs.protect.RsResourceList;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * This class will be useful to crate connection with oxd-server and calling required commands to oxd-server
 */
public class oxdCommands {

    String host;
    int port;
    String RsProtectList = "{\"resources\":[{\"path\":\"/scim\",\"conditions\":[{\"httpMethods\":[\"GET\"],\"scopes\":[\"https://scim-test.gluu.org/identity/seam/resource/restv1/scim/vas1\"],\"ticketScopes\":[\"https://scim-test.gluu.org/identity/seam/resource/restv1/scim/vas1\"]}]}]}";
    private String message = "";
    private String ticket;

    public oxdCommands(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * use to call Register site commad
     *
     * @param commandParams params for register site command
     * @param callback      return success or error
     */
    public void registerSite(RegisterSiteParams commandParams, RegisterSiteCallback callback) {
        CommandClient client = null;
        RegisterSiteResponse respRegisterSIte = null;

        if (commandParams.getAuthorizationRedirectUri() == null) {
            callback.error("AuthorizationRedirectUri is required");
            callback.success(null);
            return;
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
     *
     * @param commandParams params for Update site command
     * @param callback      return success or error
     */
    public void updateSite(UpdateSiteParams commandParams, UpdateSiteCallback callback) {
        CommandClient client = null;
        UpdateSiteResponse respUpdateSite = null;
        if (commandParams.getAuthorizationRedirectUri() == null) {
            callback.error("AuthorizationRedirectUri is required");
            callback.success(null);
            return;
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
     *
     * @param commandParams params for getiing Authorization URL
     * @param callback      return success or error
     */
    public void getAuthorizationUrl(GetAuthorizationUrlParams commandParams, GetAuthorizationUrlCallback callback) {
        CommandClient client = null;
        GetAuthorizationUrlResponse respGetAuthoUrl = null;

        if (commandParams.getOxdId() == null || commandParams.getOxdId().length() == 0) {
            callback.error("oxd_id is null or empty.");
            callback.success(null);
            return;
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
     *
     * @param commandParams params for getiing Token after successful login and redirect to authorize url
     * @param callback      return success or error
     */

    public void getToken(GetTokensByCodeParams commandParams, GetTokensByCodeCallback callback) {
        CommandClient client = null;
        GetTokensByCodeResponse respGetTokensByCodeResponse = null;


        if (commandParams.getOxdId() == null || commandParams.getOxdId().length() == 0) {
            callback.error("oxd_id is null or empty.");
            callback.success(null);
            return;

        }


        if (commandParams.getCode() == null || commandParams.getCode().length() == 0) {
            callback.error("code is null or empty.");
            callback.success(null);
            return;

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
     *
     * @param commandParams params for getting User Info using user token
     * @param callback      return success or error
     */

    public void getUserInfo(GetUserInfoParams commandParams, GetUserInfoCallback callback) {
        CommandClient client = null;

        GetUserInfoResponse respGetUserInfoResponse = null;
        if (commandParams.getOxdId() == null || commandParams.getOxdId().length() == 0) {
            callback.error("oxd_id is null or empty.");
            callback.success(null);
            return;

        }
        if (commandParams.getAccessToken() == null || commandParams.getAccessToken().length() == 0) {
            callback.error("AccessToken is null or empty.");
            callback.success(null);
            return;

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
     *
     * @param commandParams params for getting  Logout URI to logout user from application and close user session on server site.
     * @param callback      return success or error
     */


    public void getLogoutUri(GetLogoutUrlParams commandParams, GetlogoutUrlCallback callback) {
        CommandClient client = null;

        LogoutResponse logoutResponse = null;
        if (commandParams.getOxdId() == null || commandParams.getOxdId().length() == 0) {
            callback.error("oxd_id is null or empty.");
            callback.success(null);
            return;

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


    public RsProtectResponse RsResourceProtect(String siteId, RsResourceProtectCallback rsResourceProtectCallback) throws IOException {
        CommandClient client;
        final RsProtectParams commandParams = new RsProtectParams();
        commandParams.setOxdId(siteId);
        commandParams.setResources(resourceList(RsProtectList).getResources());

        final Command command = new Command(CommandType.RS_PROTECT).setParamsObject(commandParams);


        client = new CommandClient(host, port);

        final RsProtectResponse resp = client.send(command).dataAsResponse(RsProtectResponse.class);

        if (resp == null) {
            rsResourceProtectCallback.error("error in Resource Protect");
        } else {
            rsResourceProtectCallback.success(resp);
        }
        return resp;
    }


    public RsCheckAccessResponse RsCheckAccessString(String siteId, String httpMethod, String path, String rpt, RsCheckAccessCallback rsCheckAccessCallback) throws IOException {
        CommandClient client;

        final RsCheckAccessParams params = new RsCheckAccessParams();
        params.setOxdId(siteId);
        params.setHttpMethod(httpMethod);
        params.setPath(path);
        params.setRpt(rpt);

        final Command command = new Command(CommandType.RS_CHECK_ACCESS).setParamsObject(params);

        client = new CommandClient(host, port);

        final RsCheckAccessResponse resp = client.send(command).dataAsResponse(RsCheckAccessResponse.class);

        if (resp == null) {
            rsCheckAccessCallback.error("error RsCheckAccess");
        } else {
            rsCheckAccessCallback.success(resp);
        }

        return resp;
    }


    public RpGetRptResponse GetRPT(String siteId, RpGetRptCallback rpGetRptCallback) throws IOException {
        CommandClient client;
        final RpGetRptParams params = new RpGetRptParams();
        params.setForceNew(true);
        params.setOxdId(siteId);
        client = new CommandClient(host, port);

        final Command command = new Command(CommandType.RP_GET_RPT);
        command.setParamsObject(params);
        final CommandResponse response = client.send(command);

        final RpGetRptResponse rptResponse = response.dataAsResponse(RpGetRptResponse.class);

        if (rpGetRptCallback != null)
            rpGetRptCallback.success(rptResponse);
        else
            rpGetRptCallback.error("error in GetRpt");

        return rptResponse;
    }

    public RpGetRptResponse GetGAT(String siteId, GetGATCallback getGATCallback) throws IOException {
        CommandClient client;
        final RpGetGatParams params = new RpGetGatParams();
        params.setOxdId(siteId);
        params.setScopes(Lists.newArrayList("uma_authorization"));

        final Command command = new Command(CommandType.RP_GET_GAT);
        command.setParamsObject(params);
        client = new CommandClient(host, port);
        final CommandResponse response = client.send(command);

        final RpGetRptResponse rptResponse = response.dataAsResponse(RpGetRptResponse.class);


        if (rptResponse != null) {
            getGATCallback.success(rptResponse);
        } else {
            getGATCallback.error("error in GetGAT");
        }
        return rptResponse;
    }


    public RpAuthorizeRptResponse authorizeRpt(String sideOxd, String rpt, String ticket) throws IOException {
        final RpAuthorizeRptParams params = new RpAuthorizeRptParams();
        params.setOxdId(sideOxd);
        params.setRpt(rpt);
        params.setTicket(ticket);

        CommandClient client = new CommandClient(host, port);
        final RpAuthorizeRptResponse resp = client.send(new Command(CommandType.RP_AUTHORIZE_RPT, params)).dataAsResponse(RpAuthorizeRptResponse.class);
        return resp;
    }


    public static RsResourceList resourceList(String rsProtect) throws IOException {
        rsProtect = StringUtils.replace(rsProtect, "'", "\"");
        return Jackson.createJsonMapper().readValue(rsProtect, RsResourceList.class);
    }


    public String FullUmaTest(String Siteid) throws IOException {

        final String oxdId = Siteid;
        RsCheckAccessResponse rsCheckAccessResponse;
        RpGetRptResponse getRPT;
        RpGetRptResponse getGAT;
        JSONObject jsonObject = new JSONObject();
        message = "";
        ticket = "";

        RsProtectResponse rsProtectResponse = RsResourceProtect(Siteid, new RsResourceProtectCallback() {
            @Override
            public void success(RsProtectResponse rsProtectResponse) {
                message = message + " 'success RsResourceProtect'";
            }

            @Override
            public void error(String error) {
                message = message + " 'error in RsResourceProtect'";

            }
        });

        rsCheckAccessResponse = RsCheckAccessString(oxdId, "GET", "/scim", "", new RsCheckAccessCallback() {
            @Override
            public void success(RsCheckAccessResponse rsCheckAccessResponse) {
                message = message + " 'success RsCheckAccessString'";

            }

            @Override
            public void error(String error) {
                message = message + " 'error in RsCheckAccessString'";

            }
        });

        ticket = rsCheckAccessResponse.getTicket();
        if (rsCheckAccessResponse.getAccess().equals("denied")) {
            message = message + " 'Access denied first time'";
        } else {
            message = message + " 'Access granted first time'";

        }

        getRPT = GetRPT(oxdId, new RpGetRptCallback() {
            @Override
            public void success(RpGetRptResponse rpGetRptResponse) {
                message = message + " 'success GetRPT'";

            }

            @Override
            public void error(String error) {
                message = message + " 'error in GetRPT'";

            }
        });

        if (getRPT != null) {
            rsCheckAccessResponse = RsCheckAccessString(oxdId, "GET", "/scim", getRPT.getRpt(), new RsCheckAccessCallback() {
                @Override
                public void success(RsCheckAccessResponse rsCheckAccessResponse) {
                    message = message + " 'success RsCheckAccessString'";

                }

                @Override
                public void error(String error) {
                    message = message + " 'error in RsCheckAccessString'";

                }
            });

            if (rsCheckAccessResponse.getAccess().equals("denied")) {
                message = message + " 'Access denied second time'";
            } else {
                message = message + " 'Access granted second time'";

            }
            RpAuthorizeRptResponse rsAuth = authorizeRpt(oxdId, getRPT.getRpt(), ticket);

            if (rsAuth != null) {
                rsCheckAccessResponse = RsCheckAccessString(oxdId, "GET", "/scim", getRPT.getRpt(), new RsCheckAccessCallback() {
                    @Override
                    public void success(RsCheckAccessResponse rsCheckAccessResponse) {

                    }

                    @Override
                    public void error(String error) {

                    }
                });

                if (rsCheckAccessResponse.getAccess().equals("denied")) {
                    try {
                        message = "can't authorized rpt";
                        jsonObject.put("status", "error");
                        jsonObject.put("details", message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        jsonObject.put("status", "success");
                        jsonObject.put("details", message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


//        getGAT = GetGAT(oxdId, new GetGATCallback() {
//            @Override
//            public void success(RpGetRptResponse rpGetRptResponse) {
//                message = message + " 'success GetGAT'";
//
//            }
//
//            @Override
//            public void error(String error) {
//                message = message + " 'error in GetGAT'";
//
//            }
//        });
        try {
            if (rsCheckAccessResponse != null &&
                    getRPT != null &&
                    rsProtectResponse != null) {
                message = "Test Passed " + message;
                jsonObject.put("status", "success");
                jsonObject.put("details", message);
            } else if (rsCheckAccessResponse == null) {
                message = message + " 'error in RsCheckAccessString'\n";
                jsonObject.put("status", "error");
                jsonObject.put("details", message);
            } else if (getRPT == null) {
                message = message + " 'error in getRPT'\n";
                jsonObject.put("status", "error");
                jsonObject.put("details", message);
            }
//            else if (getGAT == null) {
//                message = message + " 'error in getGAT'\n";
//                jsonObject.put("status", "error");
//                jsonObject.put("details", message);
//            }
            else if (rsProtectResponse == null) {
                message = message + " 'error in RsResourceProtect'\n";
                jsonObject.put("status", "error");
                jsonObject.put("details", message);

            }
            message = jsonObject.toString();
        } catch (JSONException e) {


        }


        return message;
    }


}
