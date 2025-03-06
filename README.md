Telegram Bot на Spring Boot

Простой Telegram-бот, который повторяет сообщения пользователей.

🚀 Запуск проекта

1. Требования

Java 17+

Maven

Git

2. Установка

git clone https://github.com/Ametovali/Telegram_Repeat_Bot.git

cd repeat-bot

mvn clean install

3. Настройка

Создайте application.properties в src/main/resources и укажите токен бота:
app.telegram.username = username_your_telegram_bot
app.telegram.token = token_your_telegram_bot

4. Запуск

mvn spring-boot:run

👨‍💻 Автор

Ametov Ali