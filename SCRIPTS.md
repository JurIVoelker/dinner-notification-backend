### Start Database
``docker run --name postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -p 5432:5432 -d postgres``

### Build gradle
``gradlew build -x test``
### Build, tag and push docker image
``docker build -t dinner-notification-backend .``

``docker tag dinner-notification-backend docker.voelkerlabs.de/dinner-notification-backend``

``docker push docker.voelkerlabs.de/dinner-notification-backend``