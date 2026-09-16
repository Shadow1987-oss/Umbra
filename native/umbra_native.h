#ifndef UMBRA_NATIVE_H
#define UMBRA_NATIVE_H

#ifndef WIN32_LEAN_AND_MEAN
#define WIN32_LEAN_AND_MEAN
#endif
#include <windows.h>
#include <jni.h>
#include <jvmti.h>

#ifdef __cplusplus
extern "C" {
#endif

extern JavaVM *g_vm;
extern jvmtiEnv *g_jvmti;
extern HMODULE g_module;

void umbra_log(const wchar_t *format, ...);
void umbra_log_pending_exception(JNIEnv *env, const wchar_t *context);
jint umbra_initialize_jvmti(JavaVM *vm);
jint umbra_register_native_bridge(JNIEnv *env, jclass bridge_class);
void umbra_release_native_bridge(JNIEnv *env);

#ifdef __cplusplus
}
#endif

#endif
