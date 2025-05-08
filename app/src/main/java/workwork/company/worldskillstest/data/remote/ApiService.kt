package workwork.company.worldskillstest.data.remote

import kotlinx.coroutines.delay
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import workwork.company.worldskillstest.domain.models.remote.Event

interface ApiService {
    suspend fun fetchEvents(): List<Event>
}