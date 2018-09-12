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
  3. Example

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

### 3. Example

- Install mender demo server

Reference [Install Demo Mender Server](https://docs.mender.io/getting-started/create-a-test-environment)

- Prepare yocto project

Reference [meta-tangleid Example](https://github.com/DLTcollab/meta-dltcollab/tree/master/meta-tangleid#3-example)

- Check layers

```
$bitbkae-layers show-layers
layer                 path                                      priority
==========================================================================
meta                  $HOME/yocto/poky/meta        5
meta-poky             $HOME/yocto/poky/meta-poky   5
meta-yocto-bsp        $HOME/yocto/poky/meta-yocto-bsp  5
meta-tangleid         $HOME/TangleID/meta-dltcollab/meta-tangleid  6
meta-mender-core      $HOME/yocto/poky/meta-mender/meta-mender-core  6
meta-tangleid-with-mender  $HOME/meta-dltcollab/meta-tangleid-with-mender  6
meta-mender-demo      $HOME/yocto/poky/meta-mender/meta-mender-demo  6
meta-mender-qemu      $HOME/yocto/poky/meta-mender/meta-mender-qemu  6
```

- Config `conf/local.conf`

Reference [Build mender yocto image](https://docs.mender.io/artifacts/building-mender-yocto-image) and [meta-tangleid Config](https://github.com/DLTcollab/meta-dltcollab/tree/master/meta-tangleid#2-config-variables)

e.g. :
```shell
MENDER_ARTIFACT_NAME = "example"

INHERIT += "mender-full"

MACHINE = "qemux86-64"

DISTRO_FEATURES_append = " systemd"
VIRTUAL-RUNTIME_init_manager = "systemd"
DISTRO_FEATURES_BACKFILL_CONSIDERED = "sysvinit"
VIRTUAL-RUNTIME_initscripts = ""

ARTIFACTIMG_FSTYPE = "ext4"

MENDER_DEMO_HOST_IP_ADDRESS = "{YOUR IP}"

TANGLEID_UUID = "THISISTESTUUID"

TANGLEID_BACKEND = "http://node0.puyuma.org:8000"
```

- Build image

`$ bitbake tangleid-mender-state-scripts`
`$ bitbake core-image-minimal`

- Run image

`$ {meta-mender directory}/meta-mender-qemu/scripts/mender-qemu core-image-minimal
`
- Open Mender web page

Aceept pending device, you will see device info

Device identity & Device inventory

![](https://i.imgur.com/DssWVIA.png)

- Prepare new artifact

Modify artifact name at `conf/local.conf`

e.g. :
```
MENDER_ARTIFACT_NAME = "example-update"
```

- Rebuild image

`$ bitbake core-image-minimal`

- Upload new `.mender` artifact to Mender server

Find `.mender` fiie at `{build directory}/tmp/deploy/images/qemux86-64/core-image-minimal-qemux86-64.mender`

- Create deployment for device

Get update claim on Tangle when deployment successful

[Update claim on Tangle devnet](https://devnet.thetangle.org/transaction/JTJYGBRUQFLDX9NYKPCHORXYBGRCHOZHBOWWGSMYPCK9SMBUTRNQBXWXPXUQJNHIBTOQOWCBYDSGJP999)