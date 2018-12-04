package com.sap.voucher.storage.service

import com.sap.voucher.storage.service.configuration.DaggerApplicationComponent
import io.javalin.Javalin
import io.javalin.JavalinEvent
import io.javalin.apibuilder.ApiBuilder.get
import org.eclipse.jetty.http.HttpStatus
import org.slf4j.LoggerFactory

class Application {
  companion object {
    private val logger = LoggerFactory.getLogger(Application::class.java)
    private val component = DaggerApplicationComponent.create()

    @JvmStatic
    fun main(args: Array<String>) {
      Javalin
          .create()
          .enableCorsForAllOrigins()
          .requestLogger { context, executionTimeMs ->
            logger.info(
                "{} {} {} took {} ms",
                context.method(),
                context.path(),
                context.status(),
                executionTimeMs
            )
          }.routes {
            get("/voucher") { it.json(component.voucherController().getAll()) }
            get("/voucher/:group") {
              try {
                val voucher = component.voucherController().getGroup(it.pathParam("group"))
                if (voucher == null) it.status(HttpStatus.NO_CONTENT_204)
                else it.json(voucher).status(HttpStatus.OK_200)
              } catch (e: IllegalArgumentException) {
                it.status(HttpStatus.BAD_REQUEST_400)
              }
            }
            get("/health") { it.result("UP").status(HttpStatus.OK_200) }
            get("/") { it.status(HttpStatus.FORBIDDEN_403) }
          }
          .event(JavalinEvent.SERVER_STARTING) { component.voucherController().loadVouchers() }
          .start(7000)
    }
  }
}