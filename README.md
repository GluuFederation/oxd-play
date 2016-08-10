## oxd-play

## what is oxd-play?

>oxd-play is Oxd Server client implemented in JAVA, using it you can integrate oxD server in your Play frame work applications easily.


# Installation
----

**Note : you have to install gluu server and oxd-server in your hosting server to use oxd-play library with your application.**

---

You can install oxd-play by adding following line in build.sbt :

    resolvers += "Gluu repository" at "http://ox.gluu.org/maven" 
    
    libraryDependencies += "org.xdi" % "oxd-java" % "2.4.4.Final"
    
    libraryDependencies += "oxd.play.java" % "oxd-play" % "1.0-FINAL"


---

**Note :- empty line required between every single line because sbt build use empty line as line separator**  


## How to use: 

---

>1 **Import Oxd-Command class** (all are static methods of "oxdCommands" class.) 

---

    import static org.xdi.oxd.client.oxdCommands.*;

---

>2 **register_site**

---
1 - create registerSiteParams

    RegisterSiteParams registerSiteParams = new RegisterSiteParams();

    registerSiteParams.setAuthorizationRedirectUri("public address of the site") //Required




2 - Call "registerSite" method using created registerSiteParams

    registerSite(host,port, registerSiteParams, new RegisterSiteCallback() {
                    @Override
                    public void success(RegisterSiteResponse registerSiteResponse) {
    //this is your successful response for register_site command 
                    }

                    @Override
                    public void error(String s) {
    //returns error message
                    }
                });

***host - oxd-server host eg.localhost or 127.0.0.1 port - oxd-server listing port (default port is 8099)***

---

>3 **update_site__registration**

---
   1- create UpdateSiteParams

    UpdateSiteParams params = new UpdateSiteParams();

    params.setOxdId("Registered Sites Oxd-id");




2 - Call "updateSite" method using created registerSiteParams

    updateSite(host, port, UpdateSiteParams, new UpdateSiteCallback() {
            @Override
            public void success(UpdateSiteResponse updateSiteResponse) {
                //this is your successful response for update_site__registration command 
            }

            @Override
            public void error(String s) {
            }
        });

---

>4 **get_authorization_url**

---
1- create GetAuthorizationUrlParams

    GetAuthorizationUrlParams commandParams = new GetAuthorizationUrlParams();

        commandParams.setOxdId("Registered Sites Oxd-id");
        commandParams.setAcrValues(Lists.newArrayList("basic", "duo")); //optional

2 - Call "getAuthorizationUrl" method using created GetAuthorizationUrlParams


        getAuthorizationUrl(host, port,GetAuthorizationUrlParams, new GetAuthorizationUrlCallback() {
            @Override
            public void success(GetAuthorizationUrlResponse getAuthorizationUrlResponse) {
           //successful  call will return getAuthorizationUrlResponse
            }

            @Override
            public void error(String s) {
                error = s;
            }
        });


---

>5 **get_tokens_by_code**

---
 1- create GetTokensByCodeParams


    GetTokensByCodeParams commandParams = new GetTokensByCodeParams();

        commandParams.setOxdId("Registered Site oxd-id code");//required

        commandParams.setState("State from redirected uri");

        commandParams.setScopes("Scope from redirected uri");//required

        commandParams.setCode("Code from redirected uri");//required

2 - Call "getToken" method using created GetTokensByCodeParams

    getToken(host, port, GetTokensByCodeParams, new GetTokensByCodeCallback() {
                 public void success(GetTokensByCodeResponse getTokensByCodeResponse) {
                   //successful  call will return GetTokensByCodeResponse
                }

                @Override
                public void error(String s) {
    //will return error message if any
                }
            });

---

>6 **get_user_info**

---
 1- create GetUserInfoParams
 
     GetUserInfoParams getUserInfoParams = new GetUserInfoParams();
        getUserInfoParams.setOxdId("Regitered site's oxd-id");
        getUserInfoParams.setAccessToken("Access token from GetTokensByCode call");



2 - Call "getUserInfo" method using created GetTokensByCodeParams

    getUserInfo(host, port, getUserInfoParams, new GetUserInfoCallback() {
            @Override
            public void success(GetUserInfoResponse getUserInfoResponse) {
                   //successful  call will return GetUserInfoResponse
                }
                @Override
                public void error(String s) {
    //will return error message if any
                }
            });

---

>7 **Getlogouturi**

---
   1- create GetLogoutUrlParams
  
    GetLogoutUrlParams getLogoutUrlParams = new GetLogoutUrlParams();
    getLogoutUrlParams.setOxdId("Registered site's oxd-id");

2 - Call "getLogoutUri" method using created GetLogoutUrlParams

        getLogoutUri(host, port, getLogoutUrlParams, new GetlogoutUrlCallback() {
            @Override
            public void success(LogoutResponse AlogoutResponse) {
                //successful  call will return LogoutResponse
            }

            @Override
            public void error(String s) {
    //will return error message if any
            }
        });




----
