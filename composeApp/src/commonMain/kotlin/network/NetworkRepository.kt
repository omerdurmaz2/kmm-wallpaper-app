package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import models.ImagesResponse


class NetworkRepository(private val httpClient: HttpClient) {

    fun getImages(): Flow<NetWorkResult<ImagesResponse?>> {
        return toResultFlow {
            val response = httpClient.get("curated?page=2&per_page=40").body<ImagesResponse>()
            NetWorkResult.Success(response)
        }
    }
}