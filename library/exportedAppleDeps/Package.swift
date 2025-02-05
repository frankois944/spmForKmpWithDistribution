// swift-tools-version: 5.9
import PackageDescription

let package = Package(
  name: "exportedAppleDeps",
  platforms: [.iOS("15.0"), .macOS("10.15"), .tvOS("13.0"), .watchOS("2.0")],
  products: [
    .library(
      name: "exportedAppleDeps",
      type: .static,
      targets: ["exportedAppleDeps"])
  ],
  dependencies: [
    .package(url: "https://github.com/kingslay/KSPlayer", branch: "main")
  ],
  targets: [
    .target(
      name: "exportedAppleDeps",
      dependencies: [
        .product(name: "KSPlayer", package: "KSPlayer")
      ],
      path: "Sources")

  ]
)
