package com.swapnilsankla.jwt.ui.controller

import com.swapnilsankla.jwt.ui.model.Customer
import io.kotlintest.shouldBe
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.Repeat
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
@TestPropertySource(
	properties = ["token.signing.method=Symmetric"])
class UIControllerIntegrationTestSymmetricKeyEncryption {

	@Autowired
	private lateinit var restTemplate: TestRestTemplate

	@Test
	fun `UI should be able to fetch the customer details who exists within the system without passing any authorization token`() {
		val entity = restTemplate.getForEntity("/ui/customers/1", Customer::class.java)

		entity.statusCode shouldBe HttpStatus.OK
		entity.body!!.id shouldBe "1"
		entity.body!!.name shouldBe "Swapnil"
	}

	@Test
	fun `UI should get not found while fetching the customer details who does not exist within the system without passing any authorization token`() {
		val entity = restTemplate.getForEntity("/ui/customers/3", Customer::class.java)

		entity.statusCode shouldBe HttpStatus.NOT_FOUND
	}

	@Test
	fun `UI should be able to fetch the customer details who exists within the system with acquired authorization token`() {
		val entity1 = restTemplate.getForEntity("/ui/customers/1", Customer::class.java)
		val token = entity1.headers["authorization"]!!.first()
		val headers = HttpHeaders()
		headers.add("authorization", token)
		val requestEntity = HttpEntity(null, headers)

		val entity2 = restTemplate.exchange("/ui/customers/1", HttpMethod.GET, requestEntity, Customer::class.java)

		entity2.statusCode shouldBe HttpStatus.OK
	}

	@Test
	fun `UI should get Unauthorized response to fetch the customer details who exists within the system when acquired authorization token is expired`() {
		val entity1 = restTemplate.getForEntity("/ui/customers/1", Customer::class.java)
		val token = entity1.headers["authorization"]!!.first()
		Thread.sleep(2000)
		val headers = HttpHeaders()
		headers.add("authorization", token)
		val requestEntity = HttpEntity(null, headers)

		val entity2 = restTemplate.exchange("/ui/customers/1", HttpMethod.GET, requestEntity, Customer::class.java)

		entity2.statusCode shouldBe HttpStatus.UNAUTHORIZED
	}

	@Test
	fun `UI should get Unauthorized response to fetch the customer details who exists within the system with someone else's acquired authorization token`() {
		val entity1 = restTemplate.getForEntity("/ui/customers/1", Customer::class.java)
		val token = entity1.headers["authorization"]!!.first()
		val headers = HttpHeaders()
		headers.add("authorization", token)
		val requestEntity = HttpEntity(null, headers)

		val entity2 = restTemplate.exchange("/ui/customers/2", HttpMethod.GET, requestEntity, Customer::class.java)

		entity2.statusCode shouldBe HttpStatus.UNAUTHORIZED
	}
}