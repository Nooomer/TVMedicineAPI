package com.tvmedicine

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class RouteTests {
        @Test
        fun getTreatmentTest(): Unit = runBlocking {
            val response = HttpClient()
                .get("http://185.87.48.154:8090/treatment/1/patient/4"){
                    headers {
                        append(HttpHeaders.Authorization, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3R2bWVkLmhlcm9rdWFwcC5jb20vIiwiaXNzIjoiaHR0cHM6Ly90dm1lZC5oZXJva3VhcHAuY29tLyIsImxvZ2luIjoidGVzdCIsImV4cCI6MTY3NTgzOTEzOH0.u3Jn7Ot2UZWoB45eaaB1J1TamC8IADUXTobiDP8N3Ng")
                    }
                }
            assertEquals(
                """[{"id":1,"patientSurename":"Ампилогов","doctorSurname":"Ампилогов","startdate":"2022-05-25 12:25-7"}]""",
                response.bodyAsText()
            )
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun getPatientTest(): Unit = runBlocking {
        val response = HttpClient()
            .get("http://185.87.48.154:8090/patient/4"){
                headers {
                    append(HttpHeaders.Authorization, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3R2bWVkLmhlcm9rdWFwcC5jb20vIiwiaXNzIjoiaHR0cHM6Ly90dm1lZC5oZXJva3VhcHAuY29tLyIsImxvZ2luIjoidGVzdCIsImV4cCI6MTY3NTgzOTEzOH0.u3Jn7Ot2UZWoB45eaaB1J1TamC8IADUXTobiDP8N3Ng")
                }
            }
        assertEquals(
            """[{"id":4,"Surename":"Ампилогов","name":"Константин","s_name":"Сергеевич","phone_number":"89534500038","insurance_policy_number":"1234","password":"1234","user_type":"doctor"}]""",
            response.bodyAsText()
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }
}