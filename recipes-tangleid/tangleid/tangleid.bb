DESCRIPTION = "some scripts for tangleid"
LICENSE = "MIT"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI =   "file://tangleid-cli.sh;subdir=${PN}-${PV} \
            "

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS_${PN} = "bash curl"

do_compile() {
    #bbwarn " hi i am compile!"
    if [ "${TANGLEID_UUID}" != "" ]; then
        bbwarn "use uuid [${TANGLEID_UUID}] from local config"
        echo ${TANGLEID_UUID} > UUID
    fi
    if [ "${TANGLEID_BACKEND}" != "" ]; then
        bbwarn "use backend : ${TANGLEID_BACKEND}"
        echo ${TANGLEID_BACKEND} > backend
    fi
    cp tangleid-cli.sh tangleid
}

do_install() {
    #bbwarn " hi i am install "
    install -d ${D}${bindir}
    install -m 0755 tangleid ${D}${bindir}
    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/tangleid
    if [ -f UUID ]; then
        install -m 0644 UUID ${D}${sysconfdir}/tangleid
    fi
    if [ -f backend ]; then
        install -m 0644 backend ${D}${sysconfdir}/tangleid
    fi
}