FROM eclipse-temurin:21-jdk-alpine AS builder

RUN apk add --no-cache libc6-compat gcompat

WORKDIR /build

COPY . .

RUN ./gradlew :ars-ad-service:bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine AS runner

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=builder /build/ars-ad-service/build/libs/*.jar app.jar

RUN chown -R appuser:appgroup /app
USER appuser

EXPOSE 8081
EXPOSE 9081

ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]