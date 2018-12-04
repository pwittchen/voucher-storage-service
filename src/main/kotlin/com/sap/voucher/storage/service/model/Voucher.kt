package com.sap.voucher.storage.service.model

import java.util.UUID

data class Voucher(
  val group: Group,
  val code: String,
  var active: Boolean = true
)