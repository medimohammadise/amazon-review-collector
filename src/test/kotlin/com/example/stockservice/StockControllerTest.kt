package com.example.stockservice


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockControllerTest {
	@Autowired
	private lateinit var client: WebTestClient

	@Autowired
	private lateinit var appConfiguration: AppConfiguration


	@Autowired
	private lateinit var stockController: StockController
	@Test
	fun `check the api returns result for a valid symbol`() {
		client.get().uri("&symbols=GBP").exchange().expectStatus().is2xxSuccessful.expectBody().consumeWith{
			assertThat(it.responseBody?.isNotEmpty())
		}

		}
	@BeforeAll
	fun initClient() {
		client = WebTestClient.bindToServer().baseUrl(appConfiguration.fixerApi.latestEndpoint).build()
	}


}
