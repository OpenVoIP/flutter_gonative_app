package com.example.flutter_gonative_app

import android.os.Bundle
import gonativelib.DataProcessor
import io.flutter.app.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugins.GeneratedPluginRegistrant


class MainActivity : FlutterActivity() {
    var goNativeDataProcessor = DataProcessor()

    fun configureFlutterEngine(flutterEngine: FlutterEngine) { // Use the GeneratedPluginRegistrant to add every plugin that's in the pubspec.
        GeneratedPluginRegistrant.registerWith(flutterEngine)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        GeneratedPluginRegistrant.registerWith(this)
        val methodChannel = MethodChannel(flutterView, "example.com/gonative")
        methodChannel.setMethodCallHandler(MethodCallHandler { methodCall, result ->
            if (methodCall.method == "dataProcessor_increment") {
                if (!methodCall.hasArgument("data")) {
                    result.error("dataProcessor_increment", "Send argument as Map<\"data\", int>", null)
                    return@MethodCallHandler
                }
                try {
                    val data = methodCall.argument<Int>("data")
                    result.success(goNativeDataProcessor.increment(data!!.toLong()))
                    return@MethodCallHandler
                } catch (e: Exception) {
                    result.error("dataProcessor_increment", e.message, null)
                }
            } else {
                result.notImplemented()
            }
        })
    }
}