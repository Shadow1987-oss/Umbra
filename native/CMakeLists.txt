cmake_minimum_required(VERSION 3.21)
project(UmbraNative VERSION 1.0 LANGUAGES C RC)

if(NOT WIN32)
    message(FATAL_ERROR "UmbraNative is a Windows-only test bridge")
endif()
if(NOT MSVC)
    message(FATAL_ERROR "The audited test bridge requires the MSVC x64 toolchain")
endif()
if(NOT CMAKE_SIZEOF_VOID_P EQUAL 8)
    message(FATAL_ERROR "Only x64 Minecraft/JVM targets are supported")
endif()

set(UMBRA_JAVA_HOME "$ENV{JAVA_HOME}" CACHE PATH "JDK containing JNI/JVMTI headers")
if(NOT UMBRA_JAVA_HOME)
    foreach(candidate
            "C:/Program Files/Java/jdk1.8.0_301"
            "C:/Program Files/Java/jdk-17")
        if(EXISTS "${candidate}/include/jni.h")
            set(UMBRA_JAVA_HOME "${candidate}")
            break()
        endif()
    endforeach()
endif()

find_path(UMBRA_JNI_INCLUDE jni.h
        HINTS "${UMBRA_JAVA_HOME}/include" REQUIRED)
find_path(UMBRA_JNI_MD_INCLUDE jni_md.h
        HINTS "${UMBRA_JAVA_HOME}/include/win32" REQUIRED)
find_path(UMBRA_JVMTI_INCLUDE jvmti.h
        HINTS "${UMBRA_JAVA_HOME}/include" REQUIRED)

set(UMBRA_PRODUCT_JAR "" CACHE FILEPATH
        "Product JAR embedded into UmbraNative.dll")
if(NOT UMBRA_PRODUCT_JAR OR NOT EXISTS "${UMBRA_PRODUCT_JAR}")
    message(FATAL_ERROR
            "UMBRA_PRODUCT_JAR must name an existing product JAR")
endif()
get_filename_component(UMBRA_PRODUCT_JAR
        "${UMBRA_PRODUCT_JAR}" ABSOLUTE)
file(TO_CMAKE_PATH "${UMBRA_PRODUCT_JAR}" UMBRA_PRODUCT_JAR_RC_PATH)
configure_file(payload.rc.in generated/payload.rc @ONLY)
set_property(SOURCE "${CMAKE_CURRENT_BINARY_DIR}/generated/payload.rc"
        APPEND PROPERTY OBJECT_DEPENDS "${UMBRA_PRODUCT_JAR}")

configure_file(injector_version.rc.in generated/injector_version.rc @ONLY)

add_library(UmbraNative SHARED
        dllmain.c
        loader_bootstrap.c
        native_bridge.c
        "${CMAKE_CURRENT_BINARY_DIR}/generated/payload.rc")
target_include_directories(UmbraNative PRIVATE
        "${CMAKE_CURRENT_SOURCE_DIR}"
        "${UMBRA_JNI_INCLUDE}"
        "${UMBRA_JNI_MD_INCLUDE}"
        "${UMBRA_JVMTI_INCLUDE}")
target_compile_definitions(UmbraNative PRIVATE
        WIN32_LEAN_AND_MEAN
        _CRT_SECURE_NO_WARNINGS)
target_compile_options(UmbraNative PRIVATE /W4)
target_link_libraries(UmbraNative PRIVATE user32 ws2_32)

add_executable(UmbraInjector injector.c
        "${CMAKE_CURRENT_BINARY_DIR}/generated/injector_version.rc")
target_compile_definitions(UmbraInjector PRIVATE
        WIN32_LEAN_AND_MEAN
        _CRT_SECURE_NO_WARNINGS)
target_compile_options(UmbraInjector PRIVATE /W4)
target_link_libraries(UmbraInjector PRIVATE psapi)

include(CTest)
if(BUILD_TESTING)
    add_executable(UmbraBootstrapTest
            tests/loader_bootstrap_test.c
            loader_bootstrap.c)
    target_include_directories(UmbraBootstrapTest PRIVATE
            "${CMAKE_CURRENT_SOURCE_DIR}")
    target_compile_definitions(UmbraBootstrapTest PRIVATE
            WIN32_LEAN_AND_MEAN
            _CRT_SECURE_NO_WARNINGS)
    target_compile_options(UmbraBootstrapTest PRIVATE /W4)
    target_link_libraries(UmbraBootstrapTest PRIVATE ws2_32)
    add_test(NAME UmbraBootstrapStandalone
            COMMAND UmbraBootstrapTest standalone)
    add_test(NAME UmbraBootstrapHandoff COMMAND UmbraBootstrapTest)
    add_test(NAME UmbraBootstrapRejectsInvalid
            COMMAND UmbraBootstrapTest invalid)
endif()

set_property(TARGET UmbraNative UmbraInjector PROPERTY
        MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>DLL")

set_target_properties(UmbraNative UmbraInjector PROPERTIES
        RUNTIME_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/dist")
foreach(configuration Debug Release RelWithDebInfo MinSizeRel)
    string(TOUPPER "${configuration}" configuration_upper)
    set_target_properties(UmbraNative UmbraInjector PROPERTIES
            RUNTIME_OUTPUT_DIRECTORY_${configuration_upper}
            "${CMAKE_BINARY_DIR}/dist")
endforeach()
