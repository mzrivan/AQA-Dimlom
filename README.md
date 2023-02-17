# Дипломный проект по профессии «Тестировщик» [![Build status](https://ci.appveyor.com/api/projects/status/jut8c448ab9mi8ra?svg=true)](https://ci.appveyor.com/project/mzrivan/aqa-diplom)

Бизнес-часть
Приложение — это веб-сервис, который предлагает купить тур по определённой цене двумя способами:

* Обычная оплата по дебетовой карте.
* Выдача кредита по данным банковской карты.

![image](https://user-images.githubusercontent.com/104098185/212548995-1d246f5f-b383-45a9-965a-b44d98489e9c.png)

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей, далее Payment Gate;
* кредитному сервису, далее Credit Gate.

Приложение в собственной СУБД должно сохранять информацию о том, успешно ли был совершён платёж и каким способом. Данные карт при этом сохранять не допускается.

<details>
<summary>Техническая часть</summary>

Само приложение расположено в файле [`aqa-shop.jar`](aqa-shop.jar) и запускается стандартным способом `java -jar aqa-shop.jar` на порту 8080.

В файле [`application.properties`](application.properties) приведён ряд типовых настроек:
* учётные данные и URL для подключения к СУБД;
* URL-адреса банковских сервисов.

**СУБД**

Заявлена поддержка двух СУБД. Вы должны это проверить:

* MySQL;
* PostgreSQL.

Учётные данные и URL для подключения задаются в файле [`application.properties`](application.properties).

**Банковские сервисы**

Доступ к реальным банковским сервисам не даётся, поэтому разработчики подготовили для вас симулятор банковских сервисов, который может принимать запросы в нужном формате и генерировать ответы.

Симулятор написан на Node.js, поэтому для запуска вам нужен либо Docker, либо установленный Node.js. Симулятор расположен в каталоге [gate-simulator](gate-simulator). Для запуска нужно перейти в этот каталог. 

Симулятор запускается командой `npm start` на порту 9999. Он позволяет генерировать предопределённые ответы для заданного набора карт. Набор карт представлен в формате JSON в файле [`data.json`](gate-simulator/data.json).
</details>

## Задача

Ваша ключевая задача — автоматизировать позитивные и негативные сценарии покупки тура.

Задача разбита на 4 этапа:

1. [Планирование автоматизации тестирования.](https://github.com/mzrivan/AQA-Diplom/blob/main/reports/TestPlan.md)
2. Непосредственно сама автоматизация.
3. [Подготовка отчётных документов по итогам автоматизированного тестирования.](https://github.com/mzrivan/AQA-Diplom/blob/main/reports/Report.md)
4. [Подготовка отчётных документов по итогам автоматизации.](https://github.com/mzrivan/AQA-Diplom/blob/main/reports/Summary.md)

## Инструкция по запуску тестов и окружения 
**Необходимо наличие установленных приложений:**
* Git
* Docker, Docker compose
* Java 11

**Выполнить команды в терминале:**
* `git clone https://github.com/mzrivan/AQA-Diplom`
* `cd AQA-Diplom`
* `docker-compose build`
* `docker-compose up`

для запуска с postresql

* `java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar ./artifacts/aqa-shop.jar`

для запуска с mysql

* `java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar ./artifacts/aqa-shop.jar`

в новом окне терминала в папке проекта выполнить (предварительно выбрав необходимый url базы данных в файле DBhelper)
* `./gradlew clean test --info`

после выполнения тестов сформировать и открыть отчёт

* `./gradlew allureServe`  
