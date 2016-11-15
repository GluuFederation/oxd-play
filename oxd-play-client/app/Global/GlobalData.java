package Global;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class GlobalData {
    /*
     * Required global parameters to Register site .
     * Always Required HTTPS URIs.
     */



    public static String host = "localhost";
    public static int port = 8099;
    public static String opHost = "https://ce-dev.gluu.org";
    public static String AuthorizationRedirectUri = "https://myappplay.com:9005/getTokenbyCodeCall";
    public static String PostLogoutRedirectUri = "https://myappplay.com:9005";
    public static String ApplicationType = "web";
    public static ArrayList<String> RedirectUris = Lists.newArrayList("https://myappplay.com:9005/getTokenbyCodeCall");
    public static ArrayList<String> Arcvalues = Lists.newArrayList("basic", "duo");
    public static ArrayList<String> Contacts = Lists.newArrayList("ldeveloperl1985@gmail.com");
    public static ArrayList<String> ClientLogoutUri = (Lists.newArrayList("https://myappplay.com:9005/"));
    public static ArrayList<String> scopes = (Lists.newArrayList("openid", "profile", "email"));
    public static ArrayList<String> ResponseType = Lists.newArrayList("code");
    public static ArrayList<String> GrantType = Lists.newArrayList("authorization_code");


}
