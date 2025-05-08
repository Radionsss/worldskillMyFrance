package com.table.navigation

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import workwork.company.worldskillstest.domain.models.local.LocalEvent
import workwork.company.worldskillstest.domain.models.local.ticket.TicketEntity
import workwork.company.worldskillstest.navigation.Route
import workwork.company.worldskillstest.presenter.createTicket.CreateTicketScreen
import workwork.company.worldskillstest.presenter.createTicket.CreateTicketScreenViewModel
import workwork.company.worldskillstest.presenter.eventDetails.EventDetailsScreen
import workwork.company.worldskillstest.presenter.eventDetails.EventDetailsViewModel
import workwork.company.worldskillstest.presenter.listEvents.ListEventsScreen
import workwork.company.worldskillstest.presenter.listEvents.ListEventsScreenViewModel
import workwork.company.worldskillstest.presenter.listTickets.ListTicketsScreen
import workwork.company.worldskillstest.presenter.listTickets.ListTicketsScreenViewModel
import workwork.company.worldskillstest.presenter.splash.SplashScreen
import workwork.company.worldskillstest.presenter.ticketDetails.TicketDetailsScreen
import workwork.company.worldskillstest.presenter.ticketDetails.TicketDetailsViewModel
import com.google.gson.*
import workwork.company.worldskillstest.presenter.audio.AudioScreen
import workwork.company.worldskillstest.presenter.audio.AudioViewModel
import java.lang.reflect.Type
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(startDestination: Route) {
    val navController = rememberNavController()

    val gson = Gson()
    NavHost(navController = navController, startDestination = startDestination) {
        composable<Route.EventsListScreen> { backStackEntry ->
            val viewModel: ListEventsScreenViewModel = hiltViewModel()
            ListEventsScreen(
                viewModel = viewModel,
                onClickDetailsEvent = {
                    navController.navigate(
                        Route.EventDetailsScreen(
                            gson.toJson(
                                it
                            )
                        )
                    )
                },
            )
        }
        composable<Route.TicketsListScreen> { backStackEntry ->
            val viewModel: ListTicketsScreenViewModel = hiltViewModel()

            ListTicketsScreen(
                viewModel = viewModel,
                onClickCreateTicket = {
                    navController.navigate(Route.TicketCreateScreen)
                },
                onClickDetailsTicket = {
                    navController.navigate(
                        Route.TicketDetailsScreen(
                            gson.toJson(it)
                        )
                    )
                },
            )
        }
        composable<Route.TicketCreateScreen> { backStackEntry ->
            val viewModel: CreateTicketScreenViewModel = hiltViewModel()

            CreateTicketScreen(
                viewModel = viewModel, onExitClick = { navController.popBackStack() },
            )
        }
        composable<Route.TicketDetailsScreen> { backStackEntry ->
            val viewModel: TicketDetailsViewModel = hiltViewModel()
            val args = backStackEntry.toRoute<Route.TicketDetailsScreen>()
            val ticketDataJson = args.ticketDataJson
            Log.d("fefewwefwf", "Navigation:$ticketDataJson ")
            val gson = GsonBuilder()
                .registerTypeAdapter(Uri::class.java, UriTypeAdapter()) // Регистрируем адаптер
                .create()
            val ticketData = ticketDataJson?.let {
                gson.fromJson(it, TicketEntity::class.java)
            }
            TicketDetailsScreen(
             //   viewModel = viewModel,
                ticketData = ticketData,
                onClickExit = { navController.popBackStack() },
            )
        }
        composable<Route.RecordsListScreen> { backStackEntry ->
            val viewModel: AudioViewModel = hiltViewModel()

            AudioScreen(
                viewModel = viewModel,
            )
        }
        composable<Route.EventDetailsScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.EventDetailsScreen>()
            val eventDataJson = args.eventDataJson

            val eventData = eventDataJson?.let {
                Gson().fromJson(it, LocalEvent::class.java)
            }
            val viewModel: EventDetailsViewModel = hiltViewModel()
            EventDetailsScreen(
                viewModel = viewModel,
                localEventData = eventData,
                onClickExit = { navController.popBackStack() },
            )
        }
        composable<Route.SplashScreen> {
            SplashScreen()
        }
    }
}
class UriTypeAdapter : JsonSerializer<Uri>, JsonDeserializer<Uri> {
    override fun serialize(src: Uri?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString()) // Преобразуем Uri в строку
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Uri {
        return Uri.parse(json?.asString) // Преобразуем строку обратно в Uri
    }
}
