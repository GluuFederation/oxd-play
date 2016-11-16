package controllers;

import Global.GlobalData;
import com.google.common.collect.Lists;
import model.Profile;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.xdi.oxd.client.callbacks.*;
import org.xdi.oxd.client.oxdCommands;
import org.xdi.oxd.common.params.*;
import org.xdi.oxd.common.response.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Application extends Controller {


    @Inject
    FormFactory formFactory;

    static RegisterSiteResponse respRegisterSIte;
    static UpdateSiteResponse respUpdateSite;
    static GetAuthorizationUrlResponse respGetAuthoUrl;
    static GetTokensByCodeResponse respGetTokensByCodeResponse;
    static GetUserInfoResponse respGetUserInfoResponse;
    static LogoutResponse logoutResponse;
    static File file = new File("cache.txt");
    static File filescope = new File("scopes.txt");

    String error = "";
    public static String SESSION_STATE = "";
    public static String STATE = "";
    public static org.xdi.oxd.client.oxdCommands oxdCommands = new oxdCommands(GlobalData.host, GlobalData.port);


    public Result HomePage() {


        return ok(views.html.firstpage.render(getOxdid()));
    }

    public Result LoginPage() {
        return ok(views.html.login.render(getOxdid(), getOxdScopes()));
    }

    public Result RegisterPage() {
        return ok(views.html.registersite.render(getOxdid()));
    }

    public Result UpdatePage() {
        return ok(views.html.updatesite.render(getOxdid()));
    }

    private String getOxdid() {
        String id = "";
        if (file.exists()) {

            try {
                Scanner scan = new Scanner(file);
                while (scan.hasNextLine()) {
                    id = scan.nextLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return id;

    }


    private List<String> getOxdScopes() {
        List<String> scopes = new ArrayList<>();
        if (filescope.exists()) {


            try {
                FileInputStream fis = new FileInputStream(filescope);
                ObjectInputStream ois = new ObjectInputStream(fis);
                scopes = (List<String>) ois.readObject();
                ois.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return scopes;

    }

    /**
     * internal Api call to Oxd-server for for Registering Site
     *
     * @return Login page for Registered site
     */
    public Result RegisterSite() {
        DynamicForm form = formFactory.form().bindFromRequest();
        RegisterSiteParams params = new RegisterSiteParams();

        ArrayList<String> arcvalues = new ArrayList<>();
        if (form.get("oxd_openid_gplus_enable") != null && form.get("oxd_openid_gplus_enable").toString().equals("1")) {
            arcvalues.add("gplus");
        }
        if (form.get("oxd_openid_basic_enable") != null && form.get("oxd_openid_basic_enable").toString().equals("1")) {
            arcvalues.add("basic");
        }
        if (form.get("oxd_openid_duo_enable") != null && form.get("oxd_openid_duo_enable").toString().equals("1")) {
            arcvalues.add("duo");
        }
        if (form.get("oxd_openid_u2f_enable") != null && form.get("oxd_openid_u2f_enable").toString().equals("1")) {
            arcvalues.add("u2f");
        }


        ArrayList<String> scopes = new ArrayList<>();
        scopes.add("openid");
        if (form.get("scope_openid") != null && form.get("scope_openid").toString().equals("1")) {
            scopes.add("openid");
        }

        if (form.get("scope_profile") != null && form.get("scope_profile").toString().equals("1")) {
            scopes.add("profile");
        }

        if (form.get("scope_email") != null && form.get("scope_email").toString().equals("1")) {
            scopes.add("email");
        }

        if (form.get("scope_address") != null && form.get("scope_address").toString().equals("1")) {
            scopes.add("address");
        }
        if (form.get("scope_clientinfo") != null && form.get("scope_clientinfo").toString().equals("1")) {
            scopes.add("clientinfo");
        }
        if (form.get("scope_mobile") != null && form.get("scope_mobile").toString().equals("1")) {
            scopes.add("mobile_phone");
        }
        if (form.get("scope_phone") != null && form.get("scope_phone").toString().equals("1")) {
            scopes.add("phone");
        }

        scopes.add("uma_protection");
        scopes.add("uma_authorization");
        params.setScope(scopes);
        params.setAcrValues(arcvalues);
//        params.setOpHost(GlobalData.opHost);


        if (form.get("email") != null) {
            params.setContacts(Lists.newArrayList(form.get("email")));
        }
        oxdCommands.registerSite(getregisterSiteParams(params), new RegisterSiteCallback() {
            @Override
            public void success(RegisterSiteResponse registerSiteResponse) {
                respRegisterSIte = registerSiteResponse;

            }

            @Override
            public void error(String s) {
                error = s;
            }
        });


        if (respRegisterSIte == null) {
            return ok(views.html.registersite.render(error));

        } else {
            try {
                if (!file.exists()) {
                    if (file.createNewFile()) {
                        BufferedWriter out = new
                                BufferedWriter(new FileWriter(file));
                        out.write(respRegisterSIte.getOxdId());
                        out.close();
                    }
                } else {
                    if (file.delete()) {
                        if (file.createNewFile()) {
                            BufferedWriter out = new
                                    BufferedWriter(new FileWriter(file));
                            out.write(respRegisterSIte.getOxdId());
                            out.close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                if (!filescope.exists()) {
                    if (filescope.createNewFile()) {
                        FileOutputStream out = new
                                FileOutputStream((filescope));
                        ObjectOutputStream oos = new ObjectOutputStream(out);

                        oos.writeObject(params.getAcrValues());
                        oos.close();
                        out.close();
                    }
                } else {
                    if (filescope.delete()) {
                        if (filescope.createNewFile()) {
                            FileOutputStream out = new
                                    FileOutputStream(filescope);
                            ObjectOutputStream oos = new ObjectOutputStream(out);

                            oos.writeObject(params.getAcrValues());
                            oos.close();
                            out.close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ok(views.html.login.render("Your site is register with oxd-id :" + respRegisterSIte.getOxdId(), getOxdScopes()));
        }
    }


    /**
     * internal Api call to Oxd-server for for Updating  Site
     *
     * @return Login page for Updated site
     */
    public Result updateSiteCall() {
        String id = getOxdid();


        DynamicForm form = formFactory.form().bindFromRequest();
        UpdateSiteParams params = new UpdateSiteParams();

        params.setOxdId(id);

        ArrayList<String> arcvalues = new ArrayList<>();
        if (form.get("oxd_openid_gplus_enable") != null && form.get("oxd_openid_gplus_enable").toString().equals("1")) {
            arcvalues.add("gplus");
        }
        if (form.get("oxd_openid_basic_enable") != null && form.get("oxd_openid_basic_enable").toString().equals("1")) {
            arcvalues.add("basic");
        }
        if (form.get("oxd_openid_duo_enable") != null && form.get("oxd_openid_duo_enable").toString().equals("1")) {
            arcvalues.add("duo");
        }
        if (form.get("oxd_openid_u2f_enable") != null && form.get("oxd_openid_u2f_enable").toString().equals("1")) {
            arcvalues.add("u2f");
        }


        ArrayList<String> scopes = new ArrayList<>();
        scopes.add("openid");
        if (form.get("scope_openid") != null && form.get("scope_openid").toString().equals("1")) {
            scopes.add("openid");
        }

        if (form.get("scope_profile") != null && form.get("scope_profile").toString().equals("1")) {
            scopes.add("profile");
        }

        if (form.get("scope_email") != null && form.get("scope_email").toString().equals("1")) {
            scopes.add("email");
        }

        if (form.get("scope_address") != null && form.get("scope_address").toString().equals("1")) {
            scopes.add("address");
        }
        if (form.get("scope_clientinfo") != null && form.get("scope_clientinfo").toString().equals("1")) {
            scopes.add("clientinfo");
        }
        if (form.get("scope_mobile") != null && form.get("scope_mobile").toString().equals("1")) {
            scopes.add("mobile_phone");
        }
        if (form.get("scope_phone") != null && form.get("scope_phone").toString().equals("1")) {
            scopes.add("phone");
        }
        scopes.add("uma_protection");
        scopes.add("uma_authorization");
        params.setScope(scopes);
        params.setAcrValues(arcvalues);

//        params.setAuthorizationRedirectUri(GlobalData.AuthorizationRedirectUri);
        if (form.get("email") != null) {
            params.setContacts(Lists.newArrayList(form.get("email")));
        }


        oxdCommands.updateSite(params, new UpdateSiteCallback() {
            @Override
            public void success(UpdateSiteResponse updateSiteResponse) {
                respUpdateSite = updateSiteResponse;
                System.out.println("updateSiteResponse " + respUpdateSite.toString());
            }

            @Override
            public void error(String s) {
                error = s;
                System.out.println("updateSiteResponse Error " + error);

            }
        });


        if (respUpdateSite == null) {
            System.out.println(error.toString());
            return ok(views.html.registersite.render("updateSiteResponse Error " + error));


        } else {
            System.out.println(getOxdid());
            try {
                if (!filescope.exists()) {
                    if (filescope.createNewFile()) {
                        FileOutputStream out = new
                                FileOutputStream((filescope));
                        ObjectOutputStream oos = new ObjectOutputStream(out);

                        oos.writeObject(params.getAcrValues());
                        oos.close();
                        out.close();
                    }
                } else {
                    if (filescope.delete()) {
                        if (filescope.createNewFile()) {
                            FileOutputStream out = new
                                    FileOutputStream(filescope);
                            ObjectOutputStream oos = new ObjectOutputStream(out);

                            oos.writeObject(params.getAcrValues());
                            oos.close();
                            out.close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ok(views.html.login.render("updateSiteResponse oxd " + "Your site with oxd-id :" + getOxdid() + "is updated", getOxdScopes()));
        }
    }

    /**
     * internal call to get Authorization URL and redirect to that URL for Login in
     *
     * @return Redirect call to Authorize URL
     */
    public Result getAuthorizationUrlCall(String arcValue) {

        oxdCommands.getAuthorizationUrl(getAuthorizationUrlparamByArcValue(arcValue), new GetAuthorizationUrlCallback() {
            @Override
            public void success(GetAuthorizationUrlResponse getAuthorizationUrlResponse) {
                respGetAuthoUrl = getAuthorizationUrlResponse;
            }

            @Override
            public void error(String s) {
                error = s;
            }
        });

        if (respGetAuthoUrl == null) {
            return ok(views.html.login.render(error, getOxdScopes()));

        } else {
            return redirect(respGetAuthoUrl.getAuthorizationUrl());
        }
    }

    public GetAuthorizationUrlParams getAuthorizationUrlparamByArcValue(String arcValue) {
        if (arcValue.equals("basic")) {
            return getAuthorizationUrlparamsBasic(getOxdid());
        } else if ((arcValue.equals("duo"))) {
            return getAuthorizationUrlparamsDuo(getOxdid());
        } else if ((arcValue.equals("u2f"))) {
            return getAuthorizationUrlparamsU2f(getOxdid());
        } else if ((arcValue.equals("gplus"))) {
            return getAuthorizationUrlparamsGoogle(getOxdid());
        } else {
            return getAuthorizationUrlparamsBasic(getOxdid());
        }
    }

    /***
     * Will return AuthorizationUrlParams to login
     *
     * @param id - Registered sites OXD-Id
     * @return
     */

    public GetAuthorizationUrlParams getAuthorizationUrlparamsBasic(String id) {

        final GetAuthorizationUrlParams commandParams = new GetAuthorizationUrlParams();

        commandParams.setOxdId(id);
        commandParams.setAcrValues(Lists.newArrayList("basic"));
//        commandParams.setPrompt("login");
        return commandParams;

    }


    /***
     * Will return AuthorizationUrlParams to login
     *
     * @param id - Registered sites OXD-Id
     * @return
     */

    public GetAuthorizationUrlParams getAuthorizationUrlparamsDuo(String id) {

        final GetAuthorizationUrlParams commandParams = new GetAuthorizationUrlParams();

        commandParams.setOxdId(id);
        commandParams.setAcrValues(Lists.newArrayList("duo"));
//        commandParams.setPrompt("login");
        return commandParams;

    }


    /***
     * Will return AuthorizationUrlParams to login
     *
     * @param id - Registered sites OXD-Id
     * @return
     */

    public GetAuthorizationUrlParams getAuthorizationUrlparamsGoogle(String id) {

        final GetAuthorizationUrlParams commandParams = new GetAuthorizationUrlParams();

        commandParams.setOxdId(id);
        commandParams.setAcrValues(Lists.newArrayList("gplus"));
//        commandParams.setPrompt("login");

        return commandParams;

    }

    /***
     * Will return AuthorizationUrlParams to login
     *
     * @param id - Registered sites OXD-Id
     * @return
     */

    public GetAuthorizationUrlParams getAuthorizationUrlparamsU2f(String id) {

        final GetAuthorizationUrlParams commandParams = new GetAuthorizationUrlParams();

        commandParams.setOxdId(id);
        commandParams.setAcrValues(Lists.newArrayList("u2f"));
//        commandParams.setPrompt("login");

        return commandParams;

    }

    /***
     * Method will use to add  other params after successfully parsing register site form.
     *
     * @return
     */

    private RegisterSiteParams getregisterSiteParams(RegisterSiteParams commandParams) {


        commandParams.setAuthorizationRedirectUri(GlobalData.AuthorizationRedirectUri);
        commandParams.setPostLogoutRedirectUri(GlobalData.PostLogoutRedirectUri);
//        commandParams.setApplicationType(GlobalData.ApplicationType);
//        commandParams.setRedirectUris(GlobalData.RedirectUris);

//            commandParams.setClientLogoutUri("https://mag.gluu/index.php/customer/account/logout/");
//        commandParams.setClientLogoutUri(GlobalData.ClientLogoutUri);
//        commandParams.setGrantType(GlobalData.GrantType);

//        commandParams.setResponseTypes(GlobalData.ResponseType);


        return commandParams;
    }


    /***
     * Will return TokenParams to login
     *
     * @param id - Registered sites OXD-Id
     * @return GetTokensByCodeParams
     */


    public GetTokensByCodeParams getTokenParams(String id) {

        List<NameValuePair> data = URLEncodedUtils.parse(URI.create(Controller.request()._underlyingRequest().uri()), "UTF-8");

        final GetTokensByCodeParams commandParams = new GetTokensByCodeParams();

        commandParams.setOxdId(id);

        List<NameValuePair> params = data;

        try {
            if (params.get(2) != null)
                commandParams.setState(params.get(2).getValue());
            STATE = params.get(2).getValue();
            if (params.get(0) != null)
//                commandParams.setScopes(Arrays.asList(params.get(1).getValue().split(" ")));
                SESSION_STATE = params.get(0).getValue();
            if (params.get(3) != null)
                commandParams.setCode((params.get(3).getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commandParams;

    }


    /**
     * internal call to get Token from Redirected URl And call to Fetch profile command to get user Profile details
     *
     * @return Redirect call to Authorize URL
     */
    public Result getTokenbyCodeCall() {

        String id = getOxdid();

        if (respGetTokensByCodeResponse == null)
            oxdCommands.getToken(getTokenParams(id), new GetTokensByCodeCallback() {
                @Override
                public void success(GetTokensByCodeResponse getTokensByCodeResponse) {
                    respGetTokensByCodeResponse = getTokensByCodeResponse;

                }

                @Override
                public void error(String s) {
                    error = s;
                    System.out.print(" " + error);

                }
            });

        if (respGetAuthoUrl == null) {
            return ok(views.html.login.render(error, getOxdScopes()));

        }
        GetUserInfoParams getUserInfoParams = new GetUserInfoParams();
        getUserInfoParams.setOxdId(id);
        if (respGetTokensByCodeResponse != null) {
            System.out.print(respGetTokensByCodeResponse.toString());

            getUserInfoParams.setAccessToken(respGetTokensByCodeResponse.getAccessToken());
        }
        oxdCommands.getUserInfo(getUserInfoParams, new GetUserInfoCallback() {
            @Override
            public void success(GetUserInfoResponse getUserInfoResponse) {
                respGetUserInfoResponse = getUserInfoResponse;
            }

            @Override
            public void error(String s) {
                error = s;
            }
        });

        if (respGetUserInfoResponse == null) {
            return ok(views.html.login.render(error, getOxdScopes()));

        } else {
            Profile profile = new Profile();

            List<String> NAList = new ArrayList<>();

            NAList.add("N/A");

            if (respGetUserInfoResponse.getClaims().get("preferred_username") != null) {
                profile.setPreferred_username(respGetUserInfoResponse.getClaims().get("preferred_username"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("preferred_username") != null) {
//                profile.setPreferred_username(respGetUserInfoResponse.getClaims().get("preferred_username"));
//            }
            else {
                profile.setPreferred_username(NAList);

            }

            if (respGetUserInfoResponse.getClaims().get("picture") != null) {
                profile.setPicture(respGetUserInfoResponse.getClaims().get("picture"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("picture") != null) {
//                profile.setPicture(respGetUserInfoResponse.getClaims().get("picture"));
//            }
            else {
                profile.setPicture(NAList);

            }


            if (respGetUserInfoResponse.getClaims().get("zoneinfo") != null) {
                profile.setZoneinfo(respGetUserInfoResponse.getClaims().get("zoneinfo"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("zoneinfo") != null) {
//                profile.setZoneinfo(respGetUserInfoResponse.getClaims().get("zoneinfo"));
//            }
            else {
                profile.setZoneinfo(NAList);

            }

            if (respGetUserInfoResponse.getClaims().get("birthdate") != null) {
                profile.setBirthdate(respGetUserInfoResponse.getClaims().get("birthdate"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("birthdate") != null) {
//                profile.setBirthdate(respGetUserInfoResponse.getClaims().get("birthdate"));
//            }
            else {
                profile.setBirthdate(NAList);

            }

            if (respGetUserInfoResponse.getClaims().get("gender") != null) {
                profile.setGender(respGetUserInfoResponse.getClaims().get("gender"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("gender") != null) {
//                profile.setGender(respGetUserInfoResponse.getClaims().get("gender"));
//            }
            else {
                profile.setGender(NAList);

            }

            if (respGetUserInfoResponse.getClaims().get("profile") != null) {
                profile.setProfile(respGetUserInfoResponse.getClaims().get("profile"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("profile") != null) {
//                profile.setProfile(respGetUserInfoResponse.getClaims().get("profile"));
//            }
            else {
                profile.setProfile(NAList);

            }

            if (respGetUserInfoResponse.getClaims().get("given_name") != null) {
                profile.setGiven_name(respGetUserInfoResponse.getClaims().get("given_name"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("given_name") != null) {
//                profile.setGiven_name(respGetUserInfoResponse.getClaims().get("given_name"));
//            }
            else {
                profile.setGiven_name(NAList);

            }

            if (respGetUserInfoResponse.getClaims().get("nickname") != null) {
                profile.setNickname(respGetUserInfoResponse.getClaims().get("nickname"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("nickname") != null) {
//                profile.setNickname(respGetUserInfoResponse.getClaims().get("nickname"));
//            }
            else {
                profile.setNickname(NAList);

            }

            if (respGetUserInfoResponse.getClaims().get("email") != null) {
                profile.setEmail(respGetUserInfoResponse.getClaims().get("email"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("email") != null) {
//                profile.setEmail(respGetUserInfoResponse.getClaims().get("email"));
//            }
            else {
                profile.setEmail(NAList);

            }


            if (respGetUserInfoResponse.getClaims().get("family_name") != null) {
                profile.setFamily_name(respGetUserInfoResponse.getClaims().get("family_name"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("family_name") != null) {
//                profile.setFamily_name(respGetUserInfoResponse.getClaims().get("family_name"));
//            }
            else {
                profile.setFamily_name(NAList);

            }

            if (respGetUserInfoResponse.getClaims().get("name") != null) {
                profile.setName(respGetUserInfoResponse.getClaims().get("name"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("name") != null) {
//                profile.setName(respGetUserInfoResponse.getClaims().get("name"));
//            }
            else {
                profile.setName(NAList);

            }

            if (respGetUserInfoResponse.getClaims().get("locale") != null) {
                profile.setLocale(respGetUserInfoResponse.getClaims().get("locale"));
            }
//            else if (respGetTokensByCodeResponse.getIdTokenClaims().get("locale") != null) {
//                profile.setLocale(respGetUserInfoResponse.getClaims().get("locale"));
//            }
            else {
                profile.setLocale(NAList);

            }
            Http.Session session = Http.Context.current().session();
            Http.Context.current().session().put("Profile", String.valueOf(profile));


            return ok(views.html.profile.render(profile));
        }


    }


    public Result logout() {


        GetLogoutUrlParams getLogoutUrlParams = new GetLogoutUrlParams();
        getLogoutUrlParams.setOxdId(getOxdid());
        getLogoutUrlParams.setIdTokenHint(respGetTokensByCodeResponse.getIdToken());
        getLogoutUrlParams.setPostLogoutRedirectUri(GlobalData.PostLogoutRedirectUri);
        if (SESSION_STATE != null && SESSION_STATE.length() > 2)
            getLogoutUrlParams.setSessionState(SESSION_STATE);
        if (STATE != null && STATE.length() > 2)
            getLogoutUrlParams.setState(STATE);


        oxdCommands.getLogoutUri(getLogoutUrlParams, new GetlogoutUrlCallback() {
            @Override
            public void success(LogoutResponse AlogoutResponse) {
                respGetTokensByCodeResponse = null;
                respGetUserInfoResponse = null;
                respGetAuthoUrl = null;
                session().clear();
                flash().clear();
                Application.logoutResponse = AlogoutResponse;//successful  call will return LogoutResponse
            }

            @Override
            public void error(String s) {
                error = s;
            }
        });

        if (logoutResponse == null) {
            return ok(views.html.login.render(error, getOxdScopes()));
        } else {
            return redirect(logoutResponse.getUri());
        }
    }


    public Result FullUmaTest() {
        String message;
        try {
            message = oxdCommands.FullUmaTest(getOxdid());
        } catch (IOException e) {
            e.printStackTrace();
            return badRequest();
        }
        try {
            JSONObject jsonObject = new JSONObject(message);

            return ok(jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            return badRequest();
        }

    }
}