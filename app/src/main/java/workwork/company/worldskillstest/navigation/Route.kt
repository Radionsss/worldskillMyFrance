package workwork.company.worldskillstest.navigation

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
sealed class Route {
    @Serializable
    data object SplashScreen : Route()
    @Serializable
    data object EventsListScreen : Route()

    @Serializable
    data class EventDetailsScreen(val eventDataJson: String?) : Route()

    @Serializable
    data object TicketsListScreen : Route()
   @Serializable
    data object TicketCreateScreen : Route()

    @Serializable
    data class  TicketDetailsScreen(val ticketDataJson: String?)  : Route()
    @Serializable
    data object RecordsListScreen : Route()

    @Serializable
    data class GroupsScreen(val dataInGroupsScreenJson: String?) : Route()
}
fun Route.toStringRoute(): String {
    val jsonString = Json.encodeToString(Route.serializer(), this)
    val type = Json.parseToJsonElement(jsonString).jsonObject["type"]?.jsonPrimitive?.content
    return type?.substringAfterLast('.') ?: "UnknownRoute"
}
// Конвертирует строку обратно в Route
fun String.toRoute(): Route {
    return Json.decodeFromString(Route.serializer(), this)
}
