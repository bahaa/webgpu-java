# WebGPU Java

Experimental Java binding for [wgpu-native](https://github.com/gfx-rs/wgpu-native)
[WebGPU API](https://developer.mozilla.org/en-US/docs/Web/API/WebGPU_API).

The project uses Java's FFM to access native libraries. So, JDK 25+ is required.

## Samples

The project contains some [samples](samples/src/main/java/io/github/bahaa/webgpu/samples) ported from JavaScript.

The library itself doesn't handle creating of native surfaces or any windowing related stuff, but the samples
use [GLFW](https://www.glfw.org/) to create the window, and Objective-C runtime with FFM to create the Metal Surface.

> [!IMPORTANT]
> Use these JVM flags `--enable-native-access=ALL-UNNAMED -XstartOnFirstThread`

