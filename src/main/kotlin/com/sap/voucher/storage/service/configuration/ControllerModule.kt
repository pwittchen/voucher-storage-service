package com.sap.voucher.storage.service.configuration

import com.sap.voucher.storage.service.service.DefaultVoucherService
import com.sap.voucher.storage.service.service.VoucherService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ControllerModule {
  @Provides
  @Singleton
  fun voucherService(): VoucherService {
    return DefaultVoucherService()
  }
}