DESCRIPTION = "Hardware-accelerated implementation for IOTA PearlDiver, which utilizes multi-threaded SIMD, FPGA and GPU."

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "git://github.com/DLTcollab/dcurl;protocol=https;branch=dev \
           file://cpu-features.mk.patch;apply=yes \
"

# commit Merge pull request #79 from marktwtn/hash-rate-distribution
SRCREV = "09f441641e40e9a330e2f0f680ec838819a92309"

LIC_FILES_CHKSUM = "file://LICENSE;md5=816873469d552c57bb31d967ce623f4f"
LICENSE = "MIT"

RDEPENDS_${PN} = ""


# using Makefile tools
inherit cmake

CLEANBROKEN = "1"
#OECMAKE_GENERATOR = "Unix Makefiles"

do_configure() {
}

do_compile() {
    cd ${S}
    # like using make
    oe_runmake OUT=${B}
}

INSANE_SKIP_${PN} = "ldflags"
INSANE_SKIP_${PN}-dev = "ldflags"

do_install() {
    cp -a --no-preserve=ownership ${B}/libdcurl.so ${B}/libdcurl.so.1.0
    install -d ${D}${libdir}
    install -m 0755 ${B}/libdcurl.so.1.0  ${D}${libdir}
    ln -sf libdcurl.so.1.0 ${D}${libdir}/libdcurl.so.1
    ln -sf libdcurl.so.1 ${D}${libdir}/libdcurl.so
    install -d ${D}${bindir}
    install -m 0755 ${B}/test-* ${D}${bindir}

    FIND_PACKAGES="${IMAGE_INSTALL} ${PACKAGE_INSTALL} ${CORE_IMAGE_EXTRA_INSTALL}"
    if [ "${FIND_PACKAGES}/python//" != "python" ]; then
    rm ${D}${bindir}/test-multi-pow
    fi
}