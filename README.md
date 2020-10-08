# Reactive way to access Withings Heart API 
The example application demonstrates how to access the [Withings](https://developer.withings.com/) health API in reactive way
by using [Spring Webflux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html).

The client can call the
* /heart
* /ecg

REST API.

The client can fetch Blood Pressure measurement results in a given period by sending
a GET request from a console - for example using [httpie](https://httpie.org/) - like
```
http GET localhost:8080/heart from==2020-07-01 to==2020-07-31
```
and gets the response like
```html
HTTP/1.1 200 OK
Content-Length: 657
Content-Type: application/json
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
[
    {
            "deviceName": "BPM Core",
            "diastole": 87,
            "heartRate": 51,
            "signalId": 4860685,
            "systole": 126,
            "timestamp": 1592076032
        },
        {
            "deviceName": "BPM Core",
            "diastole": 82,
            "heartRate": 50,
            "signalId": 4860532,
            "systole": 119,
            "timestamp": 1592075619
        },
        {
            "deviceName": "BPM Core",
            "diastole": 85,
            "heartRate": 52,
            "signalId": -1,
            "systole": 126,
            "timestamp": 1592066588
        },
        {
            "deviceName": "BPM Core",
            "diastole": 83,
            "heartRate": 60,
            "signalId": 4848934,
            "systole": 128,
            "timestamp": 1592036329
        }
]
```
In addition to the client can get a full data set of the ECG recordings by sending a GET request like
```
http GET localhost:8080/ecg signalId==5982490
```
Both result types can be used by the downstream applications like UI or reporting.

## Requirements
* Java 11+
* Maven
* Register as Withings API partner [here](https://account.withings.com/connectionuser/account_create).

## How to build
Execute the following command
```
mvn clean package
```
## Configuration
Customize the _application.yml_ file with your settings
```yaml
myhealth:
    withings:
        baseUri: https://wbsapi.withings.net/v2
        tokenFile: file:/your_withings_token_json_path

withings:
    api:
        tokenHost: https://account.withings.com
        clientId: your_client_id
        clientSecret: your_client_secret
        redirectUri: your_redirect_uri
```
where the token JSON has the following content
```json
{
  "accessToken": "your_access_token",
  "refreshToken": "your_refresh_token",
  "expirationTime": ""
}
```
The application refreshes the token file automatically.

## How to run
Execute the following command
```
mvn spring-boot:run
```
