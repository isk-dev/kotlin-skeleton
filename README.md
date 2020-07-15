## Kotlin Skeleton
A skeleton for basic Kotlin application with the dependencies I usually need for my projects.

#### Usage
Clone skeleton into a new directory:

`git clone https://github.com/isk-dev/kotlin-skeleton.git my-project`

Create a new repository on github. Set this repository as the new remote:
```
git remote remove origin
git remote add origin https://github.com/isk-dev/my-project.git`
```

Adapt pom.xml artifact name and IntelliJ.

`Go to File >> Project Structure >> Project > Project Name Update project name with its new name.`

#### Directories
Configuration file directory:

 `/etc/kotlin-skeleton/`

#### Production Deploy
This script is intentionally specific for my system to save work ;)
```
scp src/main/config/production/config.properties isk@warden:/etc/kotlin-skeleton/config.properties \
&& mvn clean package \
&& scp target/kotlin-skeleton-1.0-SNAPSHOT-jar-with-dependencies.jar isk@warden:~/apps/kotlin-skeleton/kotlin-skeleton.jar \
&& ssh -t isk@warden 'sudo systemctl restart kotlin-skeleton'
```
