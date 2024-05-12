package di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

 const val TOKEN = "SQ3umoj8o4o2I88FouMMAXJ30ey6zqixmF1S3ukiKrgkdKgopphAF8Cw"
private const val BASE_URL = "https://api.pexels.com/v1/"

val providehttpClientModule = module {
    single {
        HttpClient {
            defaultRequest {
                url(BASE_URL)
            }
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Application.Json)
            }
        }
    }
}