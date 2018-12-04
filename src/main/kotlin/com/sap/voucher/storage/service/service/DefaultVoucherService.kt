package com.sap.voucher.storage.service.service

import com.sap.voucher.storage.service.model.Group
import com.sap.voucher.storage.service.model.Voucher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URI
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.HashMap

class DefaultVoucherService : VoucherService {
  private val vouchers: MutableList<Voucher>

  init {
    vouchers = arrayListOf()
  }

  override fun loadVouchers() {
    GlobalScope.async { loadVouchersFromAllGroups() }
  }

  private fun loadVouchersFromAllGroups() {
    loadVouchersFromGroup("9999-CLICK10.csv", Group.CLICK10)
    loadVouchersFromGroup("9999-CLICK15.csv", Group.CLICK15)
    loadVouchersFromGroup("9999-CLICK20.csv", Group.CLICK20)
  }

  private fun loadVouchersFromGroup(fileName: String, group: Group) {
    val (fileSystem, path) = getPathAndFileSystem(fileName)
    Files.lines(path).forEach { vouchers.add(Voucher(group, sanitizeCode(it))) }
    fileSystem?.close()
  }

  private fun getPathAndFileSystem(fileName: String): Pair<FileSystem?, Path?> {
    var fileSystem: FileSystem? = null
    var path: Path?

    try {
      val pair = loadPathInJar(fileName)
      fileSystem = pair.first
      path = pair.second
    } catch (e: IllegalArgumentException) {
      path = loadPathInApp(fileName)
    }

    return Pair(fileSystem, path)
  }

  private fun loadPathInJar(fileName: String): Pair<FileSystem?, Path?> {
    val fileSystem: FileSystem?
    val path: Path?
    val uri = ClassLoader.getSystemResource(fileName).toURI()
    val env = HashMap<String, String>()
    val array = uri.toString().split("!")
    fileSystem = FileSystems.newFileSystem(URI.create(array[0]), env)
    path = fileSystem.getPath(array[1])
    return Pair(fileSystem, path)
  }

  private fun loadPathInApp(fileName: String): Path? {
    return Paths.get(ClassLoader.getSystemResource(fileName).toURI())
  }

  fun sanitizeCode(it: String) = it.replace("[\uFEFF-\uFFFF]", "").trim()

  override fun getAll(): List<Voucher> {
    return vouchers
  }

  override fun get(groupName: String): Voucher? {
    return vouchers
        .filter { it.active }
        .firstOrNull { it.group == Group.valueOf(groupName) }
        ?.apply { useVoucher() }
  }

  @Synchronized private fun Voucher.useVoucher() {
    active = false
  }

  fun add(voucher: Voucher) {
    vouchers.add(voucher)
  }

  fun clear() {
    vouchers.clear()
  }
}