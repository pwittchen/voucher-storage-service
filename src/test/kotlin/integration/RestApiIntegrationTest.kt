package integration

import com.google.common.truth.Truth.assertThat
import com.sap.voucher.storage.service.Application
import com.sap.voucher.storage.service.model.Group
import io.restassured.RestAssured
import io.restassured.RestAssured.get
import io.restassured.http.ContentType
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.apache.http.HttpStatus
import org.hamcrest.CoreMatchers.containsString
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class RestApiIntegrationTest {
  companion object {
    @JvmStatic
    @BeforeClass
    fun setUp() {
      configureHost()
      configurePort()
      configureBasePath()
      startServer()
    }

    private fun configureHost() {
      var baseHost: String? = System.getProperty("server.host")
      if (baseHost == null) {
        baseHost = "http://localhost"
      }
      RestAssured.baseURI = baseHost
    }

    private fun configurePort() {
      val port = System.getProperty("server.port")
      if (port == null) {
        RestAssured.port = Integer.parseInt("7000")
      } else {
        RestAssured.port = Integer.parseInt(port)
      }
    }

    private fun configureBasePath() {
      var basePath: String? = System.getProperty("server.base")
      if (basePath == null) {
        basePath = "/"
      }
      RestAssured.basePath = basePath
    }

    private fun startServer() {
      Application.main(arrayOf())
    }
  }

  @Test fun shouldRespondWithForbiddenStatus() {
    get("/").then().statusCode(403)
  }

  @Test fun shouldInvokeHealthCheck() {
    get("/health").then().body(containsString("UP")).statusCode(HttpStatus.SC_OK)
  }

  @Test fun shouldGetAllVouchers() {
    get("/voucher").then().statusCode(HttpStatus.SC_OK)
  }

  @Test @Parameters("CLICK10", "CLICK15", "CLICK20")
  fun shouldGetVoucherForClickGroup(givenGroup: String) {
    // given
    val expectedGroup = Group.valueOf(givenGroup)
    val expectedActiveStatus = "false"

    // when
    val response = get("/voucher/$givenGroup")
        .then()
        .contentType(ContentType.JSON).extract().response()

    val group = response.jsonPath().getString("group")
    val code = response.jsonPath().getString("code")
    val active = response.jsonPath().getString("active")

    // then
    assertThat(group).isEqualTo(expectedGroup.toString())
    assertThat(code).isNotEmpty()
    assertThat(code.substring(0, 7)).isEqualTo(group.toString())
    assertThat(active).isEqualTo(expectedActiveStatus)
    assertThat(response.statusCode).isEqualTo(HttpStatus.SC_OK)
  }

  @Test fun shouldNotGetVoucherForInvalidClickGroup() {
    // given
    val invalidGroup = "click150"

    // when and then
    get("/voucher/$invalidGroup")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST)
  }
}
