# OnConnectAuthenticate
The **OnConnectAuthenticate** modules for [Wowza Streaming Engine™ media server software](https://www.wowza.com/products/streaming-engine) add file-based authentication for Flash applications. OnConnectAuthenticate uses NetConnection.connect parameters for authentication while OnConnectAuthenticate2 uses query parameters in the RTMP URL.

This repo includes a [compiled version](/lib/wse-plugin-onconnectauthenticate.jar).

## Prerequisites
Wowza Streaming Engine 4.0.0 or later is required.

## Usage
These modules allows you to:

* Define a specific authentication file path.
* Create a custom user authentication provider class.
* Provide a allowlist of encoders.

## More resources
To use the compiled versions of the OnConnectAuthenticate modules, see [Do file-based RTMP authentication with NetConnection connect using a Wowza Streaming Engine Java module](https://www.wowza.com/docs/how-to-do-file-based-rtmp-authentication-with-netconnection-connect-onconnectauthenticate) or [Do file-based RTMP authentication with URL query strings using a Wowza Streaming Engine Java module](https://www.wowza.com/docs/how-to-do-file-based-rtmp-authentication-with-url-query-strings-onconnectauthenticate2).

[Wowza Streaming Engine Server-Side API Reference](https://www.wowza.com/resources/serverapi/)

[How to extend Wowza Streaming Engine using the Wowza IDE](https://www.wowza.com/docs/how-to-extend-wowza-streaming-engine-using-the-wowza-ide)

Wowza Media Systems™ provides developers with a platform to create streaming applications and solutions. See [Wowza Developer Tools](https://www.wowza.com/developers) to learn more about our APIs and SDK.

## Contact
[Wowza Media Systems, LLC](https://www.wowza.com/contact)

## License
This code is distributed under the [Wowza Public License](/LICENSE.txt).
