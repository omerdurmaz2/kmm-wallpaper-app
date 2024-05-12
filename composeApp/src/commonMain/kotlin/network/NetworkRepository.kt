package network

import di.TOKEN
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.http.parameters
import kotlinx.coroutines.flow.Flow
import models.ImagesResponse


class NetworkRepository(private val httpClient: HttpClient) {

    fun getImages(): Flow<NetWorkResult<ImagesResponse?>> {
        return toResultFlow {
            val response = httpClient.get("curated") {
                headers.append(HttpHeaders.Authorization, TOKEN)
                url {
                    parameters.append("page", "1")
                    parameters.append("per_page", "20")
                }
            }.body<ImagesResponse>()
            NetWorkResult.Success(response)
        }
    }
}