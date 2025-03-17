# OpenJDK 21을 베이스 이미지로 사용
FROM openjdk:21-jdk-slim

# 작업 디렉토리 설정
WORKDIR /BambooBomb

# 로컬 파일을 컨테이너로 복사
COPY . /BambooBomb

# 애플리케이션 빌드
RUN ./gradlew build

# 애플리케이션 실행
CMD ["java", "-jar", "build/libs/your-app.jar"]
