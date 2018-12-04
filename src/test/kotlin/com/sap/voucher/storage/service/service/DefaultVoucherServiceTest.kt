package com.sap.voucher.storage.service.service

import com.google.common.truth.Truth.assertThat
import com.sap.voucher.storage.service.model.Group
import com.sap.voucher.storage.service.model.Voucher
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
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
    val sanitizedCode = voucherService.sanitizeCode(dirtyCode)

    // then
    assertThat(sanitizedCode).isEqualTo(expectedCode)
  }

  @Test @Parameters("CLICK10", "CLICK15", "CLICK20")
  fun shouldGetVoucherFromClickGroup(group: String) {
    shouldGetVoucherFromClickGroup(Group.valueOf(group))
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

  @Test fun shouldNotGetVoucherFromClickGroupWhenVouchersAreUsed() {
    // given
    prepareTestVouchers()

    // when
    voucherService.get("CLICK10")
    voucherService.get("CLICK10")
    val voucher = voucherService.get("CLICK10")

    // then
    assertThat(voucher).isNull()
  }

  @Test fun shouldGetAllVouchers() {
    // given
    prepareTestVouchers()

    // when
    val vouchers = voucherService.getAll()

    // then
    assertThat(vouchers.size).isEqualTo(20)
  }

  private fun prepareTestVouchers() {
    for (i in 1..2) {
      voucherService.add(Voucher(Group.CLICK10, "code$i", true))
    }

    for (i in 1..8) {
      voucherService.add(Voucher(Group.CLICK15, "code$i", true))
    }

    for (i in 1..10) {
      voucherService.add(Voucher(Group.CLICK20, "code$i", true))
    }
  }
}