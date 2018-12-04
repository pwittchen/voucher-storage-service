package com.sap.voucher.storage.service.controller

import com.sap.voucher.storage.service.model.Voucher
import com.sap.voucher.storage.service.service.VoucherService
import javax.inject.Inject

class VoucherController @Inject constructor(voucherService: VoucherService) {
  private val service: VoucherService = voucherService

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