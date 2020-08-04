# Reactive way to access Withings Heart API 
The example application demonstrates how to access the [Withings](https://developer.withings.com/) health API in reactive way
by using [Spring Webflux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html).

The client can fetch Blood Pressure measurement results in a given period by sending
a GET request - for example using [httpie](https://httpie.org/) - like
```
http GET localhost:8080/heart from=2020-07-01T10:25:00 to=2020-07-31T06:16:00
```
from a console and get the response like
```html
HTTP/1.1 200 OK
Content-Length: 591
Content-Type: application/json

[
    {
        "device": "BPM Core",
        "diastole": 76,
        "heartRate": 62,
        "signalId": 6049900,
        "systole": 119,
        "timestamp": "2020-07-30T08:46:49"
    },
    {
        "device": "BPM Core",
        "diastole": 86,
        "heartRate": 52,
        "signalId": 5982555,
        "systole": 132,
        "timestamp": "2020-07-27T22:20:59"
    },
    {
        "device": "BPM Core",
        "diastole": 85,
        "heartRate": 50,
        "signalId": 5982490,
        "systole": 136,
        "timestamp": "2020-07-27T22:18:36"
    }
]
```