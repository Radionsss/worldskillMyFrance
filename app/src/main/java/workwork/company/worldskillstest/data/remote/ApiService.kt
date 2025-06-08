package workwork.company.worldskillstest.data.remote

import workwork.company.worldskillstest.domain.models.remote.Event

interface ApiService {
    suspend fun fetchEvents(): List<Event>
}