package kurly.android.pre.assignment.data.service

import kurly.android.pre.assignment.data.dto.SectionProductResponse
import kurly.android.pre.assignment.data.dto.SectionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MockService {

    @GET("sections")
    suspend fun getSections(
        @Query("page") page: Int,
    ): SectionResponse

    @GET("section/products")
    suspend fun getSectionProducts(
        @Query("sectionId") sectionId: Int,
    ): SectionProductResponse
}