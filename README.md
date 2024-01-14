## Как запустить программу:
1. В данном репозитории нажать на зеленую кнопку "Code" и выбрать "Copy url to clipboard"
   ![image](https://github.com/JulyJulyZH/qa.diploma/assets/138996194/dd0aee07-ff5e-472e-89d1-5ae36e53ddea)

2.  Открыть Intellij Idea --> File --> New --> Project from version control
   ![image](https://github.com/JulyJulyZH/qa.diploma/assets/138996194/3b6bc45b-c8d2-49cb-8752-e5965ac2ec4c)

3.   В терминале ввести: docker image build -t node-app:1.0 .
4.   Запустить контейнеры из терминала командой: docker-compose up
5.   Запустить приложение одной из команд:
Для запуска базы данных MySQL         java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app
Для запуска базы данных PostgreSQL    java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app
6.   В браузере в адресную строку ввести http://localhost:8080/
     Должна открыться главная страница программы:
   ![image](https://github.com/JulyJulyZH/qa.diploma/assets/138996194/45682e6c-0b9c-4bb3-80a2-efc48e94a6bb)

## Как запустить тесты:
1. Для запуска тестов в терминал следует ввести команду: .\gradlew clean test
2. Для генерации отчета в Allure: allure serve "./build/allure-results"
