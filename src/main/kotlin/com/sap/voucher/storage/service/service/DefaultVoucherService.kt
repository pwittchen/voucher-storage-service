package com.sap.voucher.storage.service.service

import com.sap.voucher.storage.service.model.Group
import com.sap.voucher.storage.service.model.Voucher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URI
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.util.HashMap

class DefaultVoucherService : VoucherService {
  private val vouchers: MutableList<Voucher>

  init {
    vouchers = arrayListOf()
  }

  override fun loadVouchers() {
    GlobalScope.launch {
      loadVouchers("9999-CLICK10.csv", Group.CLICK10)
      loadVouchers("9999-CLICK15.csv", Group.CLICK15)
      loadVouchers("9999-CLICK20.csv", Group.CLICK20)
    }
  }

  private fun loadVouchers(fileName: String, group: Group) {
    val (fileSystem, path) = getJarAwarePathAndFileSystem(fileName)
    Files.lines(path).forEach { vouchers.add(Voucher(group, sanitizeCode(it))) }
    fileSystem.close()
  }

  private fun getJarAwarePathAndFileSystem(fileName: String): Pair<FileSystem, Path> {
    val uri = ClassLoader.getSystemResource(fileName).toURI()
    val env = HashMap<String, String>()
    val array = uri.toString().split("!")
    val fs = FileSystems.newFileSystem(URI.create(array[0]), env)
    val path = fs.getPath(array[1])
    return Pair(fs, path)
  }

  private fun sanitizeCode(it: String) = it.replace("[\uFEFF-\uFFFF]", "").trim()

  override fun getAll(): List<Voucher> {
    return vouchers
  }

  override fun get(groupName: String): Voucher? {
    return vouchers
        .filter { it.active }
        .firstOrNull { it.group == Group.valueOf(groupName) }
        ?.apply { active = false }
  }
}