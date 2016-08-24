#oxd-play

>oxd-play is Oxd Server client implemented in JAVA, using it you can integrate oxD server in your Play frame work applications easily.oxd-play provides easy way to communicate with oxd-server in play-framework.oxd-pay can perform six necessary function of Oauth2 authentication process on oxd-server.  

    For information about oxd, visit http://oxd.gluu.org

# Installation

Installation of oxd-play is very easy task with help of you can use Maven.
To use maven  adding following line in build.sbt and sbt build will do rest for you.

    resolvers += "Gluu repository" at "http://ox.gluu.org/maven"

    libraryDependencies += "org.xdi" % "oxd-java" % "2.4.4.Final"

    libraryDependencies += "oxd.play.java" % "oxd-play" % "2.4.4-FINAL"
-----------------------------------------------------------------------

GitHub source code :- [https://github.com/GluuFederation/oxd-play](https://github.com/GluuFederation/oxd-play)

For demo project :- [https://github.com/GluuFederation/oxd-play/tree/master/oxd-play-client](https://github.com/GluuFederation/oxd-play/tree/master/oxd-play-client)


**Note :- empty line required between every single line because sbt build use empty line as line separator**

#Configuration

We need nothing to configuration before start using oxd-play everything can be set on run time but still we can configure our oxd-server's default configurations. 

# Sample Code

Usage of Oxd-play is very simple as First of all we need to create parameter object related to command we are going to perform and pass to related method.
Check Sample code below we are creating commandParams object  related to commands and calling related method with created params.

If you check classes in details each class have different params where not all the params are required some are optional ,too.Required params are mentioned below check carefully before start. 

when you call method as you will also pass a callback which can return result of operation.callback have two methods success and error.In success you will get a response from server and if any error occurs you will get a error message to simplify error. 
 
>1 **Import Oxd-Command class** (all are static methods of "oxdCommands" class.)

---

Import oxdCommands class from oxd-play by adding this oxd. 

    import static org.xdi.oxd.client.oxdCommands.*;


>2 **register_site**

---

 Register site is very impotent task because it registers you site with oxd-Authentication server so you need to be care full while passing params with register site command.

1 - create registerSiteParams

     final RegisterSiteParams commandParams = new RegisterSiteParams();
         commandParams.setOpHost(opHost);//Optinal 
         commandParams.setAuthorizationRedirectUri(redirectUrl);//Required and must be https




2 - Call "registerSite" method using created registerSiteParams

    registerSite(host,port, registerSiteParams, new RegisterSiteCallback() {
                    @Override
                    public void success(RegisterSiteResponse registerSiteResponse) {
    //this is your successful response for register_site command
      //registerSiteResponse.getOxdId() to get oxdid returened by server.                  
                    }

                    @Override
                    public void error(String s) {
    //returns error message
                    }
                });

***host - oxd-server host eg.localhost or 127.0.0.1 port - oxd-server listing port (default port is 8099)***


>3 **update_site__registration**
   
---

At some point if you need to change configuration of register site you can perform.update site command.

   1- create UpdateSiteParams

    final UpdateSiteParams commandParams = new UpdateSiteParams();
                commandParams.setOxdId("Registered Sites Oxd-id");//Required




2 - Call "updateSite" method using created registerSiteParams

    updateSite(host, port, UpdateSiteParams, new UpdateSiteCallback() {
            @Override
            public void success(UpdateSiteResponse updateSiteResponse) {
                //this is your successful response for update_site__registration command 
                //updateSiteResponse.getOxdId() to get Oxd returened by server.
            }

            @Override
            public void error(String s) {
            }
        });


>4 **get_authorization_url**

---

get_authorization_url command will be useful to get Url to redirect user for login.So all you need to do is just call get_authorization_url command successfully and redirect to returned url in callback's success method.

1- create GetAuthorizationUrlParams

    GetAuthorizationUrlParams commandParams = new GetAuthorizationUrlParams();

        commandParams.setOxdId("Registered Sites Oxd-id");//required
        commandParams.setAcrValues(Lists.newArrayList("basic", "duo")); //optional

2 - Call "getAuthorizationUrl" method using created GetAuthorizationUrlParams


        getAuthorizationUrl(host, port,GetAuthorizationUrlParams, new GetAuthorizationUrlCallback() {
            @Override
            public void success(GetAuthorizationUrlResponse getAuthorizationUrlResponse) {
           //successful  call will return getAuthorizationUrlResponse
           //getAuthorizationUrlResponse.getAuthorizationUrl() will return authorization url to redirect
            }

            @Override
            public void error(String s) {
                error = s;
            }
        });



>5 **get_tokens_by_code**

---

On successful login server will redirect to "AuthorizationRedirectUri" given at time register site command.From AuthorizationRedirectUri You need to parse scope and code which Will be useful to "get_tokens_by_code".
 1- create GetTokensByCodeParams


    GetTokensByCodeParams commandParams = new GetTokensByCodeParams();

        commandParams.setOxdId("Registered Site oxd-id code");//required

        commandParams.setState("State from redirected uri");//optional

        commandParams.setScopes("Scope from redirected uri");//required

        commandParams.setCode("Code from redirected uri");//required

2 - Call "getToken" method using created GetTokensByCodeParams

    getToken(host, port, GetTokensByCodeParams, new GetTokensByCodeCallback() {
                 public void success(GetTokensByCodeResponse getTokensByCodeResponse) {
                   //successful  call will return GetTokensByCodeResponse
                   //getTokensByCodeResponse.getAccessToken() to get access Token
                }

                @Override
                public void error(String s) {
    //will return error message if any
                }
            });


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
                   //getUserInfoResponse.getClaims() Will return Hash map with calimed user informations.
                }
                @Override
                public void error(String s) {
    //will return error message if any
                }
            });


>7 **get_logout_uri**

---
   1- create GetLogoutUrlParams
  
       final GetLogoutUrlParams commandParams = new GetLogoutUrlParams();
                commandParams..setOxdId("Registered site's oxd-id"); //     required

2 - Call "getLogoutUri" method using created GetLogoutUrlParams

        getLogoutUri(host, port, getLogoutUrlParams, new GetlogoutUrlCallback() {
            @Override
            public void success(LogoutResponse AlogoutResponse) {
                //successful  call will return LogoutResponse
                //AlogoutResponse.getUri() will return uri to be redirected 
            }
            @Override
            public void error(String s) {
    //will return error message if any
            }
        });

----


**Note :- You can also refer "[OXD_JAVA](https://oxd.gluu.org/docs/libraries/java/)" for more details of java classes**
