FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append =    " file://mender-inventory-tangleid \
                      file://mender-device-identity-tangleid \
                    "

do_install_append() {
    bbwarn "You are installing with tangleid"
    install -t ${D}/${datadir}/mender/inventory -m 0755 \
            ${WORKDIR}/mender-inventory-tangleid
    
    cat ${WORKDIR}/mender-device-identity-tangleid >> ${D}/${datadir}/mender/identity/mender-device-identity 
}