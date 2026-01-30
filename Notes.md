
Lumis looks promising
- The first execution is VERY slow, but the subsequent are fast, let's try to use wizer - with Wizer we are down to less than 100ms first execution and 20/30 subsequents
- Adding more languages cause the build to take forever, also I'm unsure about performance
- Check the Rust API to be exposed, complete it
- make Terminal output/html etc configurable
- wrap the result? or just return byte[] and throw exceptions, try it a bit to see the best design
- get-binaryen and full toolchain

low priority:
- nice to have: automate Theme enum generation
- stdout/stderr from module can be captured...
