package com.sap.voucher.storage.service.controller

import com.sap.voucher.storage.service.service.VoucherService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class VoucherControllerTest {

  private var controller: VoucherController? = null
  private val service: VoucherService = Mockito.mock(VoucherService::class.java)

  @Before fun setUp() {
    controller = VoucherController(service)
  }

  @Test fun shouldLoadVouchers() {
    // when
    controller?.loadVouchers()

    // then
    verify(service).loadVouchers()
  }

  @Test fun shouldGetAllVouchers() {
    // when
    controller?.getAll()

    // then
    verify(service).getAll()
  }

  @Test fun shouldGetGroupAndConvertParamToUpperCase() {
    // when
    controller?.getGroup("click10")

    // then
    verify(service).get("CLICK10")
  }
}