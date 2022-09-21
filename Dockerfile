FROM gradle:7.4-jdk11 as build
WORKDIR /

COPY . .

RUN ./gradlew jar

FROM adoptopenjdk:11 as base
WORKDIR /godville-spy

COPY --from=build /telegram-bot/build/libs/*.jar ./godvillespy.jar
COPY --from=build /telegram-bot/build/resources/main/** ./resources/

ENTRYPOINT ["java", "-Dlogback.configurationFile=/godville-spy/resources/logback.xml", "-jar", "godvillespy.jar"]
