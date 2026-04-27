@echo off
echo ==========================================
echo Iniciando sistema completo - PROFILE=prod
echo ==========================================

REM Statement Consumer
start "statement-consumer" cmd /k cd statement-consumer-service && mvn spring-boot:run -Dspring-boot.run.profiles=prod

timeout /t 10

REM Payment Service
start "payment-service" cmd /k cd payment-service && mvn spring-boot:run -Dspring-boot.run.profiles=prod

timeout /t 10

REM Notification Consumer
start "notification-consumer" cmd /k cd notification-consumer && mvn spring-boot:run -Dspring-boot.run.profiles=prod

timeout /t 10

REM Notification Update
start "notification-update" cmd /k cd notification-update && mvn spring-boot:run -Dspring-boot.run.profiles=prod

REM Notification Retry
start "notification-retry" cmd /k cd notification-retry && mvn spring-boot:run -Dspring-boot.run.profiles=prod

REM Create Push
start "create-push" cmd /k cd create-push && mvn spring-boot:run -Dspring-boot.run.profiles=prod

REM Statement Query
start "statement-query" cmd /k cd statement-query-service && mvn spring-boot:run -Dspring-boot.run.profiles=prod

REM Statement Sync
start "statement-sync" cmd /k cd statement-sync-service && mvn spring-boot:run -Dspring-boot.run.profiles=prod

echo ==========================================
echo Todos os servicos foram iniciados
echo ==========================================