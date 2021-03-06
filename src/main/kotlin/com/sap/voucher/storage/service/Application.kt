package com.sap.voucher.storage.service

import com.sap.voucher.storage.service.configuration.DaggerApplicationComponent
import com.sap.voucher.storage.service.model.Health
import io.javalin.Javalin
import io.javalin.JavalinEvent
import io.javalin.apibuilder.ApiBuilder.get
import org.eclipse.jetty.http.HttpStatus
import org.slf4j.LoggerFactory

class Application {
  companion object {
    private val logger = LoggerFactory.getLogger(Application::class.java)
    private val component = DaggerApplicationComponent.create()
    private val voucherController = component.voucherController()
    private val voucherHttpFacade = component.voucherHttpFacade()

    @JvmStatic fun main(args: Array<String>) {
      Javalin
          .create()
          .enableCorsForAllOrigins()
          .requestLogger { ctx, time ->
            logger.info("${ctx.method()} ${ctx.path()} ${ctx.status()} took $time ms")
          }.routes {
            get("/voucher") { voucherHttpFacade.getAll(it) }
            get("/voucher/:group") { voucherHttpFacade.getGroup(it) }
            get("/health") { it.json(Health("UP")).status(HttpStatus.OK_200) }
            get("/") { it.status(HttpStatus.FORBIDDEN_403) }
          }
          .event(JavalinEvent.SERVER_STARTING) { voucherController.loadVouchers() }
          .start(7000)
    }
  }
}