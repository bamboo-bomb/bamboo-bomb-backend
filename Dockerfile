# FROM openjdk:21-jdk-slim

# ENV JAVA_HOME=/usr/local/openjdk-21
# ENV PATH=$JAVA_HOME/bin:$PATH

# # 작업 디렉토리 설정
# # WORKDIR /BambooBomb

# # Gradle Wrapper 및 필요한 파일들 복사
# COPY ./BambooBomb/gradlew /app/gradlew
# COPY ./BambooBomb/build.gradle /app/build.gradle
# COPY ./BambooBomb/settings.gradle /app/settings.gradle
# COPY ./BambooBomb/gradle /app/gradle

# # 권한 부여
# RUN chmod +x /app/gradlew

# # 빌드 실행
# WORKDIR /app
# RUN ./gradlew build

# # 애플리케이션 실행
# # CMD ["sh", "-c", "java -jar build/libs/*.jar"]

# # JAR 파일을 복사 (plain.jar 제외)
# COPY ./BambooBomb/build/libs/BambooBomb-0.0.1-SNAPSHOT.jar app.jar

# # 실행 명령어
# CMD ["java", "-jar", "app.jar"]
FROM openjdk:21-jdk-slim

WORKDIR /app

# 미리 빌드한 JAR 파일을 복사
COPY ./BambooBomb/build/libs/BambooBomb-0.0.1-SNAPSHOT.jar app.jar

# 실행 명령어
CMD ["java", "-jar", "app.jar"]
