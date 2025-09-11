package com.spiritatlas.domain.paging

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
    Minimal cursor-based paging helper for domain layer.
    - CursorRequest<Key>: request containing optional cursor and page size
    - CursorPage<Key, Item>: response page with items and next cursor
    - CursorTask.fetch(...) returns a Flow that emits pages until no more pages
*/

data class CursorRequest<Key>(
    val cursor: Key?,
    val pageSize: Int = 20
)

data class CursorPage<Key, Item>(
    val items: List<Item>,
    val nextCursor: Key?,
    val hasMore: Boolean
)

class CursorTask<Key : Any, Item : Any>(
    private val pageSizeDefault: Int = 20,
    private val fetcher: suspend (request: CursorRequest<Key>) -> CursorPage<Key, Item>
) {
    /**
     * Emits pages starting from the given request cursor until hasMore == false or nextCursor == null.
     * Consumers collect the flow and handle pages as they arrive.
     */
    fun fetch(request: CursorRequest<Key>): Flow<CursorPage<Key, Item>> = flow {
        var currentRequest = request.copy(pageSize = request.pageSize.takeIf { it > 0 } ?: pageSizeDefault)
        while (true) {
            val page = fetcher(currentRequest)
            emit(page)
            if (!page.hasMore || page.nextCursor == null) break
            currentRequest = CursorRequest(page.nextCursor, currentRequest.pageSize)
        }
    }
}


