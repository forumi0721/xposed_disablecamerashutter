package kr.stonecold.disablecamerashutter

import android.os.Bundle
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Main : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
		XposedBridge.log("Loaded App: " + lpparam.packageName)

		handleLoadComAndroidCamera(lpparam)
		handleLoadComZuiCamera(lpparam)
    }

    fun handleLoadComAndroidCamera(lpparam: XC_LoadPackage.LoadPackageParam) {
		val packageName = "com.android.camera"
		val classToHook = "java.util.Locale"
		val functionToHook = "getCountry"

		if (lpparam.packageName.equals(packageName, true)) {
			findAndHookMethod(
				classToHook,
				lpparam.classLoader,
				functionToHook,
				object : XC_MethodHook() {
					override fun beforeHookedMethod(param: MethodHookParam) {
						//super.beforeHookedMethod(param)
						param.setResult("US")
					}
				}
			)
		}

		val classToHook2 = "com.xiaomi.camera.util.SystemProperties"
		val functionToHook2 = "get"
		if (lpparam.packageName.equals(packageName, true)) {
			findAndHookMethod(
				classToHook2,
				lpparam.classLoader,
				functionToHook2,
				String::class.java,
				object : XC_MethodHook() {
					override fun beforeHookedMethod(param: MethodHookParam) {
						//super.beforeHookedMethod(param)
						if ("ro.miui.region".equals(param.args[0])) {
							param.setResult("US")
						}
					}
				}
			)
		}
	}

    fun handleLoadComZuiCamera(lpparam: XC_LoadPackage.LoadPackageParam) {
		val packageName = "com.zui.camera"
		val classToHook = "com.zui.camera.developer.common.ApiHelper"
		val functionToHook = "isForceCameraSound"

		if (lpparam.packageName.equals(packageName, true)) {
			findAndHookMethod(
				classToHook,
				lpparam.classLoader,
				functionToHook,
				object : XC_MethodHook() {
					override fun beforeHookedMethod(param: MethodHookParam) {
						//super.beforeHookedMethod(param)
						param.setResult(false)
					}
				}
			)
		}
	}
}
