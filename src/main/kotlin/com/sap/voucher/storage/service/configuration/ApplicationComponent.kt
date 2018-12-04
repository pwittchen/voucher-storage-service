package com.sap.voucher.storage.service.configuration

import com.sap.voucher.storage.service.controller.VoucherController
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ControllerModule::class])
interface ApplicationComponent {
  fun voucherController(): VoucherController
}