package com.sap.voucher.storage.service.service

import com.google.common.truth.Truth.assertThat
import com.sap.voucher.storage.service.model.Group
import com.sap.voucher.storage.service.model.Voucher
import org.junit.After
import org.junit.Test

class DefaultVoucherServiceTest {

  private val voucherService = DefaultVoucherService()

  @After fun tearDown() {
    voucherService.clear()
  }

  @Test fun shouldSanitizeCode() {
    // given
    val dirtyCode = "[\uFEFF-\uFFFF] sample"
    val expectedCode = "sample"

    // when
    val sanitizeCode = voucherService.sanitizeCode(dirtyCode)

    // then
    assertThat(sanitizeCode).isEqualTo(expectedCode)
  }

  @Test fun shouldGetVoucherFromClick10Group() {
    shouldGetVoucherFromClickGroup(Group.CLICK10)
  }

  @Test fun shouldGetVoucherFromClick15Group() {
    shouldGetVoucherFromClickGroup(Group.CLICK15)
  }

  @Test fun shouldGetVoucherFromClick20Group() {
    shouldGetVoucherFromClickGroup(Group.CLICK20)
  }

  private fun shouldGetVoucherFromClickGroup(group: Group) {
    // given
    prepareTestVouchers()

    // when
    val voucher = voucherService.get(group.toString())

    // then
    assertThat(voucher?.group).isEqualTo(group)
    assertThat(voucher?.active).isFalse()
  }

  private fun prepareTestVouchers() {
    voucherService.add(Voucher(Group.CLICK10, "code1", true))
    voucherService.add(Voucher(Group.CLICK10, "code2", true))
    voucherService.add(Voucher(Group.CLICK15, "code3", true))
    voucherService.add(Voucher(Group.CLICK15, "code4", true))
    voucherService.add(Voucher(Group.CLICK20, "code5", true))
    voucherService.add(Voucher(Group.CLICK20, "code6", true))
  }
}