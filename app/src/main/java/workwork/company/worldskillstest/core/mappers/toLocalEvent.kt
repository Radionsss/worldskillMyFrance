package workwork.company.worldskillstest.core.mappers

import workwork.company.worldskillstest.domain.models.local.LocalEvent
import workwork.company.worldskillstest.domain.models.remote.Event

fun Event.toLocalEvent(isRead: Boolean, viewCount: Int): LocalEvent {
    return LocalEvent(
        id = this.id,
        title = this.title,
        description = this.description,
        details = this.details,
        imageRes = this.imageRes,
        images = this.images,
        isRead = isRead,
        viewCount = viewCount
    )
}