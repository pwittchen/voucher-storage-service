package com.sap.voucher.storage.service.service

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DefaultVoucherServiceTest {

  private val voucherService = DefaultVoucherService()

  @Test fun shouldSanitizeCode() {
    // given
    val dirtyCode = "[\uFEFF-\uFFFF] sample"
    val expectedCode = "sample"

    // when
    val sanitizeCode = voucherService.sanitizeCode(dirtyCode)

    // then
    assertThat(sanitizeCode).isEqualTo(expectedCode)
  }

}