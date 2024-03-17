// Write C++ code here.

#include <jni.h>

extern "C" JNIEXPORT jstring JNICALL
// Java_{package_name}_{class_name}_{method_name}
Java_com_nilkymo_milky_mo_Milkymo_apiTokenNative(JNIEnv *env, jobject object) {
    // API_TOKEN is a constant that will be passed from gradle
    return env->NewStringUTF("API_URL");
}
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("milky_mo");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("milky_mo")
//      }
//    }