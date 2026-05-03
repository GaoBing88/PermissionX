package com.permissionx.guolindev

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

    private var callback: PermissionCallback? = null

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap: Map<String, Boolean> ->
        // 权限授予结果 Map：key = 权限名，value = 是否授予
        val deniedList = resultMap.filter { !it.value }.keys.toList()
        val allGranted = deniedList.isEmpty()
        callback?.invoke(allGranted, deniedList)
        callback = null // 避免多次回调或内存泄漏
    }

    fun requestNow(cb: PermissionCallback, vararg permissions: String) {
        callback = cb
        requestPermissionsLauncher.launch(permissions.toList().toTypedArray())
    }
}