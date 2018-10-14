DESCRIPTION = "Hardware-accelerated implementation for IOTA PearlDiver, which utilizes multi-threaded SIMD, FPGA and GPU."

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

SRC_URI = "git://github.com/DLTcollab/dcurl;protocol=https;branch=dev"

# commit Merge pull request #79 from marktwtn/hash-rate-distribution
SRCREV = "09f441641e40e9a330e2f0f680ec838819a92309"

LIC_FILES_CHKSUM = "file://LICENSE;md5=816873469d552c57bb31d967ce623f4f"
LICENSE = "MIT"

RDEPENDS_${PN} = ""


# using Makefile tools
inherit cmake

CLEANBROKEN = "1"
OECMAKE_GENERATOR = "Unix Makefiles"

do_configure() {
    bbwarn "hi i am configure"
}

do_compile() {
    bbwarn " hi i am compile!"
    cd ${S}
    # like using make
    oe_runmake
}

do_install() {
    bbwarn " hi i am install "
    install -d ${D}${libdir}
    install -m 0755 ${B}/libdcurl.so ${D}${bindir}
}