# ---------- Stage 1 : build (Maven + Java 21) ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build

# Les dependances sont telechargees dans une couche dediee :
# elles ne sont re-telechargees que si pom.xml change.
COPY pom.xml ./
RUN mvn -B dependency:go-offline

COPY src ./src

RUN mvn -B clean package -DskipTests


# ---------- Stage 2 : runtime (JRE 21) ----------
FROM eclipse-temurin:21-jre

# curl est utilise par le healthcheck du conteneur
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# On ne copie que le jar executable genere par le stage build
COPY --from=build /build/target/ServiCasa-*.jar app.jar

EXPOSE 8080

HEALTHCHECK --interval=15s --timeout=5s --start-period=90s --retries=5 \
    CMD curl -fsS http://localhost:8080/v3/api-docs || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
