import Foundation

import KSPlayer

@objcMembers public class MEPlayerController: NSObject {
     private let player = IOSVideoPlayerView()

    override init() {
       super.init()
       KSOptions.secondPlayerType = KSMEPlayer.self
       player.delegate = self
       player.autoresizingMask = [.flexibleWidth, .flexibleHeight]
       //player.view?.frame = view.bounds
       player.contentMode = .scaleAspectFill
    }

    public func setMediaItem(videoUrl: URL) {
       player.set(
          url: videoUrl,
          options: KSOptions()
      )
    }

    public var playerView: NSObject {
       player
    }

    public func releasePlayer() {
       player.resetPlayer()
       player.removeFromSuperview()
    }

    public func testAsync() async -> String {
        return "41"
    }
}

extension MEPlayerController: PlayerControllerDelegate {
    public func playerController(state: KSPlayer.KSPlayerState) {
        print("state \(state)")
    }

    public func playerController(currentTime: TimeInterval, totalTime: TimeInterval) {
        print("currentTime \(currentTime) / totalTime \(totalTime)")
    }

    public func playerController(finish error: (any Error)?) {
        print("finish \(String(describing: error))")
    }

    public func playerController(maskShow: Bool) {
        print("maskShow \(maskShow)")
    }

    public func playerController(action: KSPlayer.PlayerButtonType) {
        print("action \(action)")
    }

    public func playerController(bufferedCount: Int, consumeTime: TimeInterval) {
    }

    public func playerController(seek: TimeInterval) {
        print("seek \(seek)")
    }

}
