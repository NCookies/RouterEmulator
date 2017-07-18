cmake_minimum_required(VERSION 3.7)
project(Raspi_Server)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        Headers/json/json-forwards.h
        Headers/json/json.h
        Headers/connectionservice.h
        Headers/defs.h
        Headers/equipment.h
        Headers/eventservice.h
        Headers/jsonservice.h
        Headers/serverstarter.h
        Headers/service.h
        Sources/connectionservice.cpp
        Sources/equipment.cpp
        Sources/eventservice.cpp
        Sources/jsoncpp.cpp
        Sources/jsonservice.cpp
        Sources/serverstarter.cpp
        main.cpp
        makefile)

add_executable(Raspi_Server ${SOURCE_FILES})

TARGET_LINK_LIBRARIES( Raspi_Server
    pthread
)
