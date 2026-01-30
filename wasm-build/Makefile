
WASI_SDK_VERSION := 29
PWD := $(CURDIR)

.PHONY: clean
clean:
	rm -rf target wasi-sdk*

.PHONY: get-wasisdk
get-wasisdk:
	wget https://github.com/WebAssembly/wasi-sdk/releases/download/wasi-sdk-${WASI_SDK_VERSION}/wasi-sdk-${WASI_SDK_VERSION}.0-x86_64-linux.tar.gz
	tar xvf wasi-sdk-${WASI_SDK_VERSION}.0-x86_64-linux.tar.gz
	rm wasi-sdk-${WASI_SDK_VERSION}.0-x86_64-linux.tar.gz
	mv wasi-sdk-${WASI_SDK_VERSION}.0-x86_64-linux ${PWD}/wasi-sdk

.PHONY: build
build:
	WASI_SDK_PATH=$(PWD)/wasi-sdk \
	CC_wasm32_wasip1=$(PWD)/wasi-sdk/bin/clang \
	CFLAGS_wasm32_wasip1="--sysroot=$(PWD)/wasi-sdk/share/wasi-sysroot" \
	cargo build --target wasm32-wasip1 --release
	rm -f ../lumis.wasm
	cp target/wasm32-wasip1/release/lumis.wasm ../lumis.wasm

.PHONY: all
all: get-wasisdk build
