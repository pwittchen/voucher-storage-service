package com.sap.voucher.storage.service.controller

import com.sap.voucher.storage.service.model.Voucher
import com.sap.voucher.storage.service.service.DefaultVoucherService
import com.sap.voucher.storage.service.service.VoucherService

class VoucherController {
  private val service: VoucherService = DefaultVoucherService()

  fun loadVouchers() {
    service.loadVouchers()
  }

  fun getAll(): List<Voucher> {
    return service.getAll()
  }

  fun getGroup(id: String): Voucher? {
    return service.get(id.toUpperCase())
  }
}