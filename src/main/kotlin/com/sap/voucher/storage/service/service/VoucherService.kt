package com.sap.voucher.storage.service.service

import com.sap.voucher.storage.service.model.Voucher

interface VoucherService {
  fun loadVouchers()
  fun getAll(): List<Voucher>
  fun get(groupName: String): Voucher?
}