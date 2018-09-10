This README file contains information on the contents of the meta-tangleid-with-mender layer.

Please see the corresponding sections below for details.

# Dependencies

```
URI : https://github.com/DLTcollab/meta-dltcollab
layer : meta-tangleid
recipes : tangleid
```
```
URI : https://github.com/mendersoftware/meta-mender
layer : meat-mender-core
recipes : mender
```

# Table of Contents

  1. Quick usage
  2. Function of recipes 

### 1. Quick usage

- Add dependent layers
`$ bitbake-layer add-layer {your path}/meta-dltcollab/meta-tangleid`
`$ bitbake-layer add-layer {your path}/meta-mender/meta-mender-core`
- Config tangleid and mender
`$ vi conf/local.conf`
- Add layers
`$ bitbake-layers add-layer {meta-tangleid-with-mender directory path}`
- Prepare state scripts
`$ bitbake tangleid-mender-state-scripts`
- Build image
`$ bitbake {image recipe}`

### 2. Function of recipes 

- `mender_%.bbappend`
    - Add TangleID `UUID` in Mender device `identity` and `inventory`
    - Add TangleID `backend` in Mender device `inventory`
- `tangleid-mender-state-scripts.bb`
    - Send TangleID claim for OTA info by using mender state script