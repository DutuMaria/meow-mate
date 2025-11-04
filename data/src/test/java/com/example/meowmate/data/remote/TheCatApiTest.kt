package com.example.meowmate.data.remote

import com.squareup.moshi.Moshi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class TheCatApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: TheCatApi

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()

        val moshi = Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().build())
            .build()

        api = retrofit.create(TheCatApi::class.java)
    }

    @After
    fun teardown() {
        server.shutdown()
    }

    @Test
    fun `getImages parseaza corect lista`() = runTest {
        val body = """
            [
              {
                "id": "1",
                "url": "https://x/cat.jpg",
                "breeds": [
                  {
                    "name": "Abyssinian",
                    "origin": "Egypt",
                    "temperament": "Active",
                    "life_span": "14 - 15",
                    "intelligence": 5,
                    "affection_level": 4,
                    "child_friendly": 4,
                    "social_needs": 5,
                    "description": "desc"
                  }
                ]
              }
            ]
        """.trimIndent()

        server.enqueue(MockResponse().setBody(body).setResponseCode(200))

        val list = api.getImages(limit = 20, hasBreeds = 1)

        assertEquals(1, list.size)
        assertEquals("1", list.first().id)
        assertEquals("Abyssinian", list.first().breeds?.first()?.name)
    }
}
