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
Build the image using,
```
$ ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=cynergyruas/ruas-app:auth-service-0.0.1
```

To run the docker container,
```
docker run -p 8080:8080 -v $(pwd)/service-account.json:/workspace/service-account.json \
    -e GOOGLE_APPLICATION_CREDENTIALS=/workspace/service-account.json  \
    -e MONGODB_URI=<mongodb uri> \
    cynergyruas/ruas-app:auth-service-0.0.1
```