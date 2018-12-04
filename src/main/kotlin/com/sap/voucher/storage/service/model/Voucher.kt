package com.sap.voucher.storage.service.model

data class Voucher(
  val group: Group,
  val code: String,
  var active: Boolean = true
)