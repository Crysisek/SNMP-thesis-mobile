package pl.edu.pb.common.network

import okhttp3.Credentials
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import pl.edu.pb.common.extensions.multiLet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DynamicUrlInterceptor: Interceptor {
    private var scheme: String? = null
    private var host: String? = null
    private var port: Int? = null
    private var credentials: String? = null

    fun setUrl(url: String) = url.toHttpUrlOrNull()?.let {
        scheme = it.scheme
        host = it.host
        port = it.port
    }

    fun setCredentials(name: String, password: String) {
        credentials = Credentials.basic(name, password)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var original: Request = chain.request()
        // If new Base URL is properly formatted than replace with old one
        multiLet(scheme, host, port) { s, h, p ->
            val newUrl: HttpUrl = original.url.newBuilder()
                .scheme(s)
                .host(h)
                .port(p)
                .build()
            original = original.newBuilder().apply {
                url(newUrl)
                credentials?.let {
                    if (!h.contains("actuator")) header("Authorization", it)
                }
            }.build()
        }
        return chain.proceed(original)
    }
}