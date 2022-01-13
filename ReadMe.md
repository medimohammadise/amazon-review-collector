# Product Review Collector Microservice For Amazon

Hello, this is [Review Collector Microservice ](http://www.elegantsoftware.de/), A reactive rest api for crawling product reviews from Amazon

Created by [Mehdi](http://www.elegantsoftware.de/). Originally this was a hands-on development for my university dissertation

## Docker image

The official Docker image is available at []

You can use JDL-Studio offline with :

```
DOCKER_HUB_USER_NAME=YourUser DOCKER_HUB_PASSWORD=Yourpassword ./gradlew bootBuildImage

```

The Microservice should be available at: http://localhost:8080

## Log tracing

The Microservice sends log samples to Zipkin 

### Exception tracing

The Microservice sends exceptions samples to Sentry

### Metrics


## Contributing

If you want to contribute to the project please contact me [via email](m.mohammadi.se@gmail.com).

## Development

This project heavily used Kotlin, Spring boot, Spring Sleuth,JSoup 

## Deploy to production

## Learn More

