###############################################################################
# This file is part of the Incubed project.
# Sources: https://github.com/slockit/in3-c
# 
# Copyright (C) 2018-2019 slock.it GmbH, Blockchains LLC
# 
# 
# COMMERCIAL LICENSE USAGE
# 
# Licensees holding a valid commercial license may use this file in accordance 
# with the commercial license agreement provided with the Software or, alternatively, 
# in accordance with the terms contained in a written agreement between you and 
# slock.it GmbH/Blockchains LLC. For licensing terms and conditions or further 
# information please contact slock.it at in3@slock.it.
# 	
# Alternatively, this file may be used under the AGPL license as follows:
#    
# AGPL LICENSE USAGE
# 
# This program is free software: you can redistribute it and/or modify it under the
# terms of the GNU Affero General Public License as published by the Free Software 
# Foundation, either version 3 of the License, or (at your option) any later version.
#  
# This program is distributed in the hope that it will be useful, but WITHOUT ANY 
# WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
# PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
# [Permissions of this strong copyleft license are conditioned on making available 
# complete source code of licensed works and modifications, which include larger 
# works using a licensed work, under the same license. Copyright and license notices 
# must be preserved. Contributors provide an express grant of patent rights.]
# You should have received a copy of the GNU Affero General Public License along 
# with this program. If not, see <https://www.gnu.org/licenses/>.
###############################################################################

OPTION(USE_SCRYPT "if scrypt is installed, it will link dynamicly to the shared scrypt lib." OFF)
IF (USE_SCRYPT)
  find_package(scrypt)

  if (${scrypt_FOUND})
    MESSAGE(STATUS "Found Scrypt in ${scrypt_INCLUDE_DIRS})")
    include_directories(${scrypt_INCLUDE_DIRS})
    set(LIBS ${LIBS} ${scrypt_LIBRARIES})
    ADD_DEFINITIONS(-DSCRYPT)
  endif()
endif()
add_library(eth_api_o OBJECT eth_api.c abi.c key.c rpc_api.c)
target_compile_definitions(eth_api_o PRIVATE -D_POSIX_C_SOURCE=199309L)

add_library(eth_api STATIC $<TARGET_OBJECTS:eth_api_o>)
target_link_libraries(eth_api eth_nano ${LIBS})