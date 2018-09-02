DESCRIPTION = "some mender state scripts with tangleid"
LICENSE = "MIT"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI =   "file://updateClaim.sh;subdir=${PN}-${PV} \
            "

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS_${PN} = "tangleid mender"

inherit mender-state-scripts

do_compile() {
    cp updateClaim.sh ${MENDER_STATE_SCRIPTS_DIR}/ArtifactCommit_Leave_99
}