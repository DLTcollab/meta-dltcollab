This README file contains information on the contents of the meta-dcurl layer.

Please see the corresponding sections below for details.

# Table of Contents

  1. Quick usage
  2. Config variables
  3. Example

### 1. Quick usage

- Add layers
`$ bitbake-layers add-layer {meta-dcurl directory path}`
- Build dcurl
`$ bitbake dcurl`
- Find packages in build directory
`tmp/work/${machine}/dcurl/${PV}-${PR}/deploy-${package type}/${ARCH}/`

### 2. Config variables

default values of all variables are `0`

set `1` mean that enable this feature when compile

- `DCURL_BUILD_AVX`
- `DCURL_BUILD_SSE`
- `DCURL_BUILD_GPU`
- `DCURL_BUILD_FPGA_ACCEL`
- `DCURL_BUILD_JNI`
- `DCURL_BUILD_STAT`
- `DCURL_BUILD_COMPAT` 