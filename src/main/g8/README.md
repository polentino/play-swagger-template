# $name$

## How to build the project

Open a terminal and execute the following command

> ./gradlew build

:warning: **Angular users** :warning: if you've enabled Angular frontend, you will notice multiple tasks named like so
`task_0X_...` : these are required in order to pull the necessary Angular runtime for you OS/Arch, install required
dependencies etc; however, they're a one-time operation that you may want to disable in order to speedup compilation time.
The only tasks you may need to run, i.e. to install extra dependencies or rebuild the frontend, are
  - `task_04_installFrontendDependencies`
  - `task_05_buildFrontend`

## How to run the project

Open a terminal and execute the following command

> ./gradlew runPlay

and then:
  - open a terminal and run `curl -X GET "http://localhost:9000/count?name=test" -H "accept: application/json"`
$if(use_swagger_ui.truthy)$
  - or head to `localhost:9000/api` to see swaggerUI in action and play with it, or
$endif$
$if(use_angular_frontend.truthy)$
  - head to `localhost:9000` to see the frontend application and use it
$endif$








<sub><sub>built with love using https://github.com/polentino/play-gradle-template.g8</sub></sub>