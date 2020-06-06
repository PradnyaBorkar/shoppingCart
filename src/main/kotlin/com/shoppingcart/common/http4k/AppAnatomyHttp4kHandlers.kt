package com.shoppingcart.common.http4k

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.io.Resources
import com.natpryce.krouton.http4k.ResourceRoutesBuilder
import com.natpryce.krouton.plus
import com.natpryce.krouton.root
import org.http4k.core.ContentType
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*

object AppAnatomyHttp4kHandlers {
    private val mapper = ObjectMapper()

    fun kroutons(): ResourceRoutesBuilder =
        ResourceRoutesBuilder().apply {
            root + "internal" + "status" methods {
                GET { resourceStream("contact.json")?.let {
                    jsonResponse(
                        it
                    )
                } ?: Response(Status.INTERNAL_SERVER_ERROR) }
            }
            root + "internal" + "version" methods {
                GET { resourceStream("version.json")?.let {
                    jsonResponse(
                        it
                    )
                } ?: Response(Status.INTERNAL_SERVER_ERROR) }
            }
            root + "internal" + "config" methods {
                GET { jsonResponse(config()) }
            }
        }

    private fun jsonResponse(json: ByteArray): Response {
        return Response(Status.OK)
            .header("Content-Type", ContentType.APPLICATION_JSON.toHeaderValue())
            .body(json.toString(UTF_8))
    }

    private fun resourceStream(resourceName: String): ByteArray? {
        return jsonResource(resourceName)
    }

    private fun jsonResource(resourceName: String): ByteArray? {
        return Resources.toByteArray(Resources.getResource(resourceName))
    }

    private fun config(): ByteArray {
        return mapper.writeValueAsBytes(
            linkedMapOf(
                "systemProperties" to System.getProperties().toSafeSortedMap(),
                "environmentVariables" to System.getenv().toSafeSortedMap()
            )
        )
    }
}

fun Map<*, *>.toSafeSortedMap(): SortedMap<String, String> {
    return map { entry -> safePair(entry.key.toString(), entry.value.toString()) }
        .toMap()
        .toSortedMap()
}

private fun safePair(key: String, value: String) =
    key to (if (key.isSecret()) "********" else value)

private fun String.isSecret(): Boolean {
    val uppercaseKey = toUpperCase(Locale.ROOT)
    return uppercaseKey.endsWith("PASSWORD") ||
           uppercaseKey.endsWith("_SECURITY_TOKEN") ||
           uppercaseKey.startsWith("ACCESS_CONTROL_")
}

object AppAnatomyRoutes{
    private val internal = "internal"
    val status = root + internal + "status"
    val version = root + internal + "version"
    val config = root + internal + "config"
}