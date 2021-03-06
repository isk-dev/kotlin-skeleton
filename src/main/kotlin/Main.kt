import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import data.ExampleStorage
import org.apache.logging.log4j.LogManager
import java.io.File
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

fun main() {
    val logger = LogManager.getLogger("main")
    logger.info("Started")

    val mysqlHost = Key("mysql.host", stringType)
    val mysqlUser = Key("mysql.user", stringType)
    val mysqlPassword = Key("mysql.password", stringType)
    val mysqlDatabase = Key("mysql.database", stringType)

    val config = systemProperties() overriding
        EnvironmentVariables() overriding
        ConfigurationProperties.fromFile(File("/etc/kotlin-skeleton/config.properties"))

    val notificationRepository = ExampleStorage(
        config[mysqlHost],
        config[mysqlUser],
        config[mysqlPassword],
        config[mysqlDatabase]
    )

    val notificationTask = ExampleTask()

    val timer = Timer("notify", false)
    timer.scheduleAtFixedRate(1000, 60000) {
        notificationTask.run()
    }
}


