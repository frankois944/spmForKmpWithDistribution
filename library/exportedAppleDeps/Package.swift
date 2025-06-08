// swift-tools-version: 5.9
import PackageDescription

let package = Package(
  name: "exportedAppleDeps",
  platforms: [.iOS("15.0"), .macOS("10.13"), .tvOS("12.0"), .watchOS("4.0")],
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
      path: "Sources"

    )

  ]
)
