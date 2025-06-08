package workwork.company.worldskillstest.presenter.listEvents.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import workwork.company.worldskillstest.core.mappers.toLocalEvent
import workwork.company.worldskillstest.domain.MainRepository
import workwork.company.worldskillstest.domain.models.local.LocalEvent
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    fun getEvents(): Flow<List<LocalEvent>> = flow {
        mainRepository.getLocalEvents().collect { localEvents ->
            if (localEvents.isEmpty()) {
                val eventsFromApi = mainRepository.fetchEvents()
                val localEventsToSave = eventsFromApi.map { event ->
                    event.toLocalEvent(isRead = false, viewCount = 0)
                }
                mainRepository.saveLocalEvents(localEventsToSave)
            }
            emitAll(mainRepository.getLocalEvents())
        }
    }

}