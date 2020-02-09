## Kotlin Skeleton
A skeleton for basic Kotlin application with the dependencies I usually need for my projects.

#### Directories
Configuration file directory: `/etc/kotlin-skeleton/`

#### Production Deploy
```
scp src/main/config/production/config.properties isk@warden:/etc/kotlin-skeleton/config.properties \
&& mvn clean package \
&& scp target/kotlin-skeleton-1.0-SNAPSHOT-jar-with-dependencies.jar isk@warden:~/apps/kotlin-skeleton/kotlin-skeleton.jar \
&& ssh -t isk@warden 'sudo systemctl restart kotlin-skeleton'
```
