# Reactive way to access Withings Heart API 
The example application demonstrates how to access the [Withings](https://developer.withings.com/api-reference) health API in reactive way
by using [Spring Webflux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html).

The client can call the
* /heart
* /ecg
* /bp

REST endpoints.

The client can fetch Blood Pressure measurement results in a given period by sending
a GET request from a console - for example using [httpie](https://httpie.org/) - like
```
http GET localhost:8080/heart from==2020-07-01 to==2020-07-05 offset==0 page==1
```
where
* if there are more results use _offset_ value to retrieve next available rows
* page is the current page number.

The client gets the response in JSON like
```html
HTTP/1.1 200 OK
Content-Length: 386
Content-Type: application/json
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
{
    "hearts": [
        {
            "deviceName": "BPM Core",
            "diastole": 84,
            "heartRate": 53,
            "signalId": 5382498,
            "systole": 124,
            "timestamp": 1593879223
        },
        {
            "deviceName": "BPM Core",
            "diastole": 76,
            "heartRate": 63,
            "signalId": 5352489,
            "systole": 109,
            "timestamp": 1593772606
        },
        {
            "deviceName": "BPM Core",
            "diastole": 78,
            "heartRate": 55,
            "signalId": 5346785,
            "systole": 121,
            "timestamp": 1593756340
        }
    ],
    "offset": 0,
    "pageNumber": 1,
    "pageSize": 100
}
```

The user can fetch only the systolic and diastolic values without
signals and devices by calling the _bp_ API, for example
```
http GET localhost:8080/bp from==2020-09-02 to==2020-09-03
```
The client gets the response in JSON like
```html
HTTP/1.1 200 OK
Content-Length: 205
Content-Type: application/json
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

[
    {
        "diastole": 84,
        "heartRate": 50,
        "systole": 129,
        "timestamp": 1599039396
    },
    {
        "diastole": 83,
        "heartRate": 48,
        "systole": 131,
        "timestamp": 1599075737
    },
    {
        "diastole": 80,
        "heartRate": 51,
        "systole": 124,
        "timestamp": 1599075996
    }
]
```

In addition to the client can get a full data set of the ECG recordings by sending a GET request like
```
http GET localhost:8080/ecg signalid==5982490
```
All result types can be used by the downstream applications like UI or reporting.

## Requirements
* Java 11+
* Maven
* Register as Withings API partner [here](https://account.withings.com/partner/account_create?b=dashboard_oauth2).

## How to build
Execute the following command
```
mvn clean package
```
## Configuration
* Follow the instruction about OAuth setup [here](https://developer.withings.com/api-reference#tag/oauth2).
* Copy the access and refresh tokens to the _application.yml_ file.
```yaml
myhealth:
    withings:
        baseUri: https://wbsapi.withings.net/v2
        tokenFile: file:/your_withings_token_json_path

withings:
    api:
        tokenHost: https://wbsapi.withings.net
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
