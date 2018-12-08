package com.sap.voucher.storage.service.facade

import com.sap.voucher.storage.service.controller.VoucherController
import io.javalin.Context
import org.eclipse.jetty.http.HttpStatus
import javax.inject.Inject

class VoucherHttpFacade @Inject constructor(voucherController: VoucherController) {
  private val controller: VoucherController = voucherController

  fun getAll(context: Context) {
    context.json(controller.getAll())
  }

  fun getGroup(context: Context) {
    try {
      val voucher = controller.getGroup(context.pathParam("group"))
      if (voucher == null) context.status(HttpStatus.NO_CONTENT_204)
      else context.json(voucher).status(HttpStatus.OK_200)
    } catch (e: IllegalArgumentException) {
      context.status(HttpStatus.BAD_REQUEST_400)
    }
  }
}