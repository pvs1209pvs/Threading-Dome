cmake_minimum_required(VERSION 3.10)

project(sample)

set(CMAKE_CXX_FLAGS "-fopenmp")
set(CMAKE_CXX_FLAGS "-g")

include_directories(src)

add_executable(sample src/main.cpp)

