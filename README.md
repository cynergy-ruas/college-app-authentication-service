# college-app-authentication-service
The microservice that handles authentication for the college app

## Development
Run the service by:

```
$ MONGODB_URI=<mongo-uri> GOOGLE_APPLICATION_CREDENTIALS=<path/to/service-accounts-file> ./mvnw spring-boot:run
```

contact repo admin for the google service accounts file.

## Production

Make sure the google service accounts file is present in the root directory of the project as `service-accounts.json`.
Build the application using,
```
$ ./mvnw package
```

Then run,
```
$ mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
$ docker build -t authservice:0.0.1 .
```

To run the docker container,
```
$ docker run -p 8080:8080 -e MONGODB_URI=<mongo-uri> authservice:0.0.1
```