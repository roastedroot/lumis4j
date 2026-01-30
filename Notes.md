
Lumis looks promising
- The first execution is VERY slow, but the subsequent are fast, let's try to use wizer - with Wizer we are down to less than 100ms first execution and 20/30 subsequents
- Adding more languages cause the build to take forever, also I'm unsure about performance - added a couple initialization looks critical
- Check the Rust API to be exposed, complete it - bascis covered
- make Terminal output/html etc configurable - done
- wrap the result? or just return byte[] and throw exceptions, try it a bit to see the best design - first attempt done
- get-binaryen and full toolchain - done
- The Wizer initialization step looks fragile and is basically "magic"
- add rendered examples to the readme

low priority:
- nice to have: automate Theme enum generation
- nice to have: automate Lang enum generation
- stdout/stderr from module can/should be captured?
