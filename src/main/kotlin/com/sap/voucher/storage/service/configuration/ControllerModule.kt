package com.sap.voucher.storage.service.configuration

import com.sap.voucher.storage.service.service.DefaultVoucherService
import com.sap.voucher.storage.service.service.VoucherService
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ControllerModule {
  @Binds
  @Singleton
  abstract fun voucherService(service : DefaultVoucherService): VoucherService
}