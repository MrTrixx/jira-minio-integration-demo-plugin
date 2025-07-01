# Custom Jira Plugin written with usage Kotlin and Java to integrate with Minio

## Features

- Usage Minio Java SDK
- Usage Kotlin
- Usage Java
- Usage Spring Java Configuration
- Usage Testcontainers to test Minio integration

## Guide

- Start Docker Engine (on Windows launch Docker Desktop - need prepared WSL)
- Add JIRA DB credentials to [db.config](dev/config/dbconfig.xml)
- Add JIRA DB and Minio credentials to [.env](docker/.env)
- Run command: **docker-compose up** (*Optional add arg -d*)
- Wait start up of Jira and Minio (Jira not depends on Minio image)
- Configure Jira admin user
- Run command atlas-package (All tests must pass)
- Go to Jira and install manual jar of this plugin
- Use Rest API of plugin to check integration Jira custom plugin with Minio
- Use custom http client (OkHttp) if you need configure minio client during Jira runtime
  - **Note**: Not checked shutdown hook of default http client (OkHttp) using in Minio Client

## Useful

- Use debug mode on each Jira node by Intellij IDEA
- Enable logging on each Jira node using one of solution:
  - use Administration -> System -> Logging and Profiling -> enter plugin package name and level
    - **Note**: After rerun Jira need add again 
  - add properties to **log4j.properties** check official documentation for [log4j](https://confluence.atlassian.com/jirakb/change-logging-levels-in-jira-server-629178605.html)
      - **Note**: Need Jira restart
      - **Note**: If you want use Jira 9.x or 10.x - upgrade [pom](pom.xml) (each libs for your Jira version) and you can migrate to *log4j2* [documentation](https://support.atlassian.com/jira/kb/migrating-custom-logging-configurations-to-log4j-2/)
- Use commands to define address of Minio:
  - Find Minio container id: docker ps
  - Find address of Minio container: docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' minio_container_id 
