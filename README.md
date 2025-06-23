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
  - **Notes**: Not checked shutdown hook of default http client (OkHttp) using in Minio Client