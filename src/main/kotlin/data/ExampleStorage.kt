package data

import model.ExampleDataClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ExampleStorage(
    mysqlHost: String,
    mysqlUser: String,
    mysqlPassword: String,
    mysqlDatabase: String
) {

    init {
        Database.connect(
            "jdbc:mysql://$mysqlHost:3306/$mysqlDatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
            driver = "com.mysql.cj.jdbc.Driver",
            user = mysqlUser,
            password = mysqlPassword
        )

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Notifications)
        }
    }

    class NotificationEntry(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<NotificationEntry>(
            Notifications
        )

        var type by Notifications.type
        var name by Notifications.name
        var description by Notifications.description
        var url by Notifications.url
    }

    object Notifications : IntIdTable() {
        val type = varchar("type", 1000)
        val name = varchar("name", 1000)
        val description = varchar("description", 1000)
        val url = varchar("url", 1000)
    }

    fun write(notification: ExampleDataClass) {
        transaction {
            NotificationEntry.new {
                type = notification.type
                name = notification.name
                description = notification.description
                url = notification.url
            }
        }
    }

    fun getRecent(): List<ExampleDataClass> {
        val recentNotifications = transaction {
            NotificationEntry.all().orderBy(Notifications.id to SortOrder.DESC).limit(500).toList()
        }

        return this.convertNotificationEntriesToNotifications(recentNotifications)
    }

    private fun convertNotificationEntriesToNotifications(notificationEntries: List<NotificationEntry>): List<ExampleDataClass> {
        val notifications = mutableListOf<ExampleDataClass>()
        for (notificationEntry in notificationEntries) {
            val notification = ExampleDataClass(
                notificationEntry.name,
                notificationEntry.type,
                notificationEntry.description,
                notificationEntry.url
            )

            notifications.add(notification)
        }

        return notifications
    }

}

