package kurly.android.pre.assignment.data.repository

import kurly.android.pre.assignment.data.di.IoDispatcher
import kurly.android.pre.assignment.data.dto.SectionProductResponse
import kurly.android.pre.assignment.data.dto.SectionResponse
import kurly.android.pre.assignment.data.service.MockService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MockRepository @Inject constructor(
    private val mockService: MockService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {

    suspend fun getSections(page: Int): Result<SectionResponse> =
        withContext(context = dispatcher) {
            runCatching { mockService.getSections(page) }
        }

    suspend fun getSectionProducts(sectionId: Int): Result<SectionProductResponse> =
        withContext(context = dispatcher) {
            runCatching { mockService.getSectionProducts(sectionId) }
        }
}