Play Gradle g8 Template
=========================
[![shields.io](https://img.shields.io/badge/CONTRIBUTORS-WELCOME&lt;3-blueviolet)](https://creativecommons.org/publicdomain/zero/1.0/)
[![shields.io](http://img.shields.io/badge/LICENSE-CC0-blue.svg)](https://creativecommons.org/publicdomain/zero/1.0/)
[![@polentino911](https://img.shields.io/badge/VERSION-1.0.0-brightgreen.svg?logo=scala)](https://twitter.com/polentino911)

![Template examples](imgs/template_previews.png?raw=true "Template examples")


## Table of contents

 - [Introduction](#introduction)
 - [Application description](#application-description)
 - [Features](#features)
 - [How to use the template](#how-to-use-the-template)
 - [Run the application](#run-the-application)
 - [Notes](#notes)


## Introduction
A [g8](https://github.com/foundweekends/giter8.g8) template project for a [Play!](https://www.playframework.com/) application written
in [Scala](https://www.scala-lang.org/), using [Gradle](https://gradle.org/) build tool, and an optional [Angular](https://angular.io/)
frontend.

It provides a basic, yet fully working application/microservice with persistence, db versioning,
generated client models, code coverage, swagger/openapi integration etc.. depending on how you setup the project
(see [Features](#Features) below).


## Application description
Regardless of the configuration setup chosen by the user, this template will create a Play application that listens to `GET` requests
with query parameter`?name=<something>`, and returns how many times `something` was invoked. The number of invocations for each
instance of `name` values are persisted, such that the total frequency of occurrences is preserved across application restarts.

The default RDBMS is H2 or, if you choose so at configuration time, MySQL. Other databases can be added as well by changing
`application.conf` and adding the corresponding connector library in `build.gradle` : no code changes required at all :tada:


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
   - [x] backend client api generation (typescript)
 - [x] [Swagger UI](https://github.com/swagger-api/swagger-ui) for convenient access to the API of
   the microservice
 - [x] [Angular](https://angular.io/) frontend

(*) Play `2.6` is the latest version supported by `org.gradle.playframework` plugin; there is however some
[activity recently](https://github.com/gradle/playframework/commit/ee20b323b1a79f85f8261621272e4743e6476968) 
to support Play `2.7`.


## How to use the template

If you haven't done already, install [g8](https://github.com/foundweekends/giter8.g8), then open a terminal,  type

> g8 polentino/play-gradle-template.g8

and follow the on-screen instructions to create your project


## Run the application

To run the application, open a terminal and type

> ./gradlew runPlay

then, either:
  - head to `localhost:9000/count?name=Mary` to start counting instances of name `Mary`, or
  - open a terminal and run `curl -X GET "http://localhost:9000/count?name=Mary" -H "accept: application/json"`
  - (if you did enable swagger ui) head to `localhost:9000/api` to see swaggerUI in action and play with it, or
  - (if you did enable angular frontend) head to `localhost:9000` to see the frontend application and use it

Tests and code coverage are already linked to the `build` task, so all you need to
do is type
> ./gradlew build

and you'll get all tests running, results displayed and code coverage computed.

:warning: **Angular users** :warning: if you've enabled Angular frontend, you will notice multiple tasks named like so
`task_0X_...` : these are required in order to pull the necessary Angular runtime for you OS/Arch, install required
dependencies etc; however, they're a one-time operation that you may want to disable in order to speedup compilation time.
The only tasks you may need to run, i.e. to install extra dependencies or rebuild the frontend, are
  - `task_04_installFrontendDependencies`
  - `task_05_buildFrontend`

## Notes

If you're using H2 as production db, you'll notice between restarts this error on console:
> liquibase.exception.DatabaseException: Table "DATABASECHANGELOG" already exists; SQL statement:

It's not harmful, and is caused by a bug when using H2. Changing to MySQL, PostgreSQL etc...
wont't cause the aforementioned exception.
