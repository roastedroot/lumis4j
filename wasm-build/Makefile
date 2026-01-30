
WASI_SDK_VERSION := 29
WIZER_VERSION := 10.0.0
BINARYEN_VERSION := 125
PWD := $(CURDIR)

.PHONY: clean
clean:
	rm -rf target wasi-sdk* wizer* binaryen*

.PHONY: get-wasisdk
get-wasisdk:
	wget https://github.com/WebAssembly/wasi-sdk/releases/download/wasi-sdk-${WASI_SDK_VERSION}/wasi-sdk-${WASI_SDK_VERSION}.0-x86_64-linux.tar.gz
	tar xvf wasi-sdk-${WASI_SDK_VERSION}.0-x86_64-linux.tar.gz
	rm wasi-sdk-${WASI_SDK_VERSION}.0-x86_64-linux.tar.gz
	mv wasi-sdk-${WASI_SDK_VERSION}.0-x86_64-linux ${PWD}/wasi-sdk

.PHONY: get-wizer
get-wizer:
	wget https://github.com/bytecodealliance/wizer/releases/download/v${WIZER_VERSION}/wizer-v${WIZER_VERSION}-x86_64-linux.tar.xz
	tar xvf wizer-v${WIZER_VERSION}-x86_64-linux.tar.xz
	rm wizer-v${WIZER_VERSION}-x86_64-linux.tar.xz
	mv wizer-v${WIZER_VERSION}-x86_64-linux ${PWD}/wizer

.PHONY: get-binaryen
get-binaryen:
	wget https://github.com/WebAssembly/binaryen/releases/download/version_${BINARYEN_VERSION}/binaryen-version_${BINARYEN_VERSION}-x86_64-linux.tar.gz
	tar xvf binaryen-version_${BINARYEN_VERSION}-x86_64-linux.tar.gz
	rm binaryen-version_${BINARYEN_VERSION}-x86_64-linux.tar.gz
	mv binaryen-version_${BINARYEN_VERSION} ${PWD}/binaryen

.PHONY: build
build:
	WASI_SDK_PATH=$(PWD)/wasi-sdk \
	CC_wasm32_wasip1=$(PWD)/wasi-sdk/bin/clang \
	CFLAGS_wasm32_wasip1="--sysroot=$(PWD)/wasi-sdk/share/wasi-sysroot" \
	cargo build --target wasm32-wasip1 --release

.PHONY: optimize
optimize:
	${PWD}/wizer/wizer --allow-wasi target/wasm32-wasip1/release/lumis.wasm -o target/wasm32-wasip1/release/lumis-initialized.wasm
	${PWD}/binaryen/bin/wasm-opt -Oz --strip-debug target/wasm32-wasip1/release/lumis-initialized.wasm -o target/wasm32-wasip1/release/lumis-opt.wasm

.PHONY: release
release:
	rm -f ../lumis.wasm
	cp target/wasm32-wasip1/release/lumis-opt.wasm ../lumis.wasm

.PHONY: all
all: get-wasisdk get-wizer get-binaryen build optimize release
