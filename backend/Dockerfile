# Copyright 2026 上海如静知华信息科技有限公司
FROM maven:3.9.11-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2,sharing=locked sh -c 'for attempt in 1 2 3; do mvn -B -q -Dmaven.wagon.http.retryHandler.count=5 dependency:go-offline && exit 0; sleep $((attempt * 5)); done; exit 1'
COPY src src
RUN --mount=type=cache,target=/root/.m2,sharing=locked sh -c 'for attempt in 1 2 3; do mvn -B -q -Dmaven.wagon.http.retryHandler.count=5 clean package && exit 0; sleep $((attempt * 5)); done; exit 1'
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/target/zhuatech-plm.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
