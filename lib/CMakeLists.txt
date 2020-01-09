cmake_minimum_required(VERSION 3.4.1)

# turn off FAST_MATH in the evm.
ADD_DEFINITIONS(-DIN3_MATH_LITE)

# loop through the required module and cretae the build-folders
foreach(module 
  core 
  verifier/eth1/nano 
  verifier/eth1/evm 
  verifier/eth1/basic 
  verifier/eth1/full 
  bindings/java
  third-party/crypto
  third-party/tommath 
  api/eth1)
        file(MAKE_DIRECTORY in3-core/src/${module}/outputs)
        add_subdirectory( in3-core/src/${module} in3-core/src/${module}/outputs )
endforeach()
      
