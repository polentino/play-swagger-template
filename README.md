Play Gradle g8 Template
=========================
[![shields.io](https://img.shields.io/badge/CONTRIBUTORS-WELCOME&lt;3-blueviolet)](https://creativecommons.org/publicdomain/zero/1.0/)
[![shields.io](http://img.shields.io/badge/LICENSE-CC0-blue.svg)](https://creativecommons.org/publicdomain/zero/1.0/)
[![@polentino911](https://img.shields.io/badge/VERSION-0.0.2-brightgreen.svg?logo=scala)](https://twitter.com/polentino911)

## Introduction
A sample template project for a [Play!](https://www.playframework.com/) application written
in [Scala](https://www.scala-lang.org/), using [Gradle](https://gradle.org/) build tool.

It provides a basic, yet fully working application/microservice with persistence, db versioning,
generated client models, code coverage, swagger/openapi integration etc..
(see [Features](#Features) below).

 ### Application description
This template will create a simple Play application that listens to `GET` requests with query parameter 
`?name=<something>`, and returns how many times `something` was invoked. The number of invocations for each instance of
`name` values are persisted, such that the total frequency of occurrences is preserved across application restarts.

## Features
 - [x] Scala 2.12.10
 - [x] Play! framework 2.6.25 (*)
 - [x] Gradle 6.1.1
 - [x] [Slick](http://scala-slick.org/) as database library
 - [x] [Liquibase](https://www.liquibase.org/) for database versioning and schema management
 - [x] [H2](https://www.h2database.com/html/main.html) / [MySQL](https://www.mysql.com/it) as supported RDBMS 
   - but you can easily add others such as Oracle DB, PostgreSQL etc with few config lines and build dependency
 - [x] [ScalaTest](http://www.scalatest.org/) as testing library
 - [x] [Scoverage](http://scoverage.org/) for code coverage
 - [x] [Swagger Codegen](https://github.com/swagger-api/swagger-codegen) for model code generation
   - [ ] client api generation (contributions are welcome!)
 - [x] [Swagger UI](https://github.com/swagger-api/swagger-ui) for convenient access to the API of
   the microservice

(*) Play `2.6` is the latest version supported by `org.gradle.playframework` plugin; there is however some
[activity recently](https://github.com/gradle/playframework/commit/ee20b323b1a79f85f8261621272e4743e6476968) 
to support Play `2.7`.

## Run the application

To run the application, open a terminal and type

> ./gradlew runPlay

then, either:
  - head to `localhost:9000?name=Mary` to start counting instances of name `Mary`, or
  - head to `localhost:9000/api` to see swaggerUI in action and play with it, or
  - open a terminal and run `curl -X GET "http://localhost:9000/?name=Mary" -H "accept: application/json"`

Tests and code coverage are already linked to the `build` task, so all you need to
do is type
> ./gradlew build

and you'll get all tests running, results displayed and code coverage computed.

## Notes

If you're using H2 as production db, you'll notice between restarts this error on console:
> liquibase.exception.DatabaseException: Table "DATABASECHANGELOG" already exists; SQL statement:

It's not harmful, and seem caused by a bug when using H2. Changing to MySQL, PostgreSQL etc...
wont't cause the aforementioned exception.