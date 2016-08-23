
#oxd-play-client
----
oxd-play-client is simple implementation of oxd-play library in playframe work.

##Application Setup
---

Prerequisite : Gluu server and oxd-server needs to be running in your machine

clone clinet project and run using activator with "-Dhttp.port=disabled -Dhttps.port=9005" option.
9005 is you port where you application will run on https protocal. This will make sure you app will always run on https instead http.

>note :- use this command only in development mode because "-Dhttps.port=9005" will generate selfsigned certification for https which may not work in real time. For prodcucation mode you need to generate you owen ssl certificate using keystore.

## Useage:

---

There is GlobalData class in .Global package where you can change various parameters according to your configurations.


##Tips:

---

   set lighttpd config file to set your local proxity for www.myappplay.com name resolving.

    server.modules = (
      "mod_access",
      "mod_proxy",
      "mod_accesslog"
      )


    $HTTP["host"] =~ "www.myappplay.com" {
    proxy.balance = "round-robin" proxy.server = ( "/" =>
        ( ( "host" => "your ip address", "port" => 9005 ) ) )
        }

