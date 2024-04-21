package kr.stonecold.leicacameraregionchanger

import android.os.Bundle
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Main : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
		XposedBridge.log("Loaded App: " + lpparam.packageName)

		val packageName = "com.android.camera"
		val classToHook = "com.mi.device.DataItemFeature"
		val functionToHook = "w4"

		if (lpparam.packageName.equals(packageName, true)) {
			findAndHookMethod(
				classToHook,
				lpparam.classLoader,
				functionToHook,
				String::class.java,
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
