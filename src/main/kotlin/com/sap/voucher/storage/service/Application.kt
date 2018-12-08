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

    @JvmStatic fun main(args: Array<String>) {
      Javalin
          .create()
          .enableCorsForAllOrigins()
          .requestLogger { ctx, executionTime ->
            logger.info("${ctx.method()} ${ctx.path()} ${ctx.status()} took $executionTime ms")
          }.routes {
            get("/voucher") { component.voucherHttpFacade().getAll(it) }
            get("/voucher/:group") { component.voucherHttpFacade().getGroup(it) }
            get("/health") { it.result("UP").status(HttpStatus.OK_200) }
            get("/") { it.status(HttpStatus.FORBIDDEN_403) }
          }
          .event(JavalinEvent.SERVER_STARTING) { component.voucherController().loadVouchers() }
          .start(7000)
    }
  }
}