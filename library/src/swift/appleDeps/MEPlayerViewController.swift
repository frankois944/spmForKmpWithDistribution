import Foundation
import Player

@objcMembers public class MEPlayerController: NSObject {
     private let player = Player()

    override init() {
       super.init()
        player.playerDelegate = self
        player.playbackDelegate = self
        player.view.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        player.view.contentMode = .scaleAspectFill
    }

    public func setMediaItem(videoUrl: URL) {
        player.url = videoUrl
    }

    public var playerView: NSObject {
       player.view
    }

    public func releasePlayer() {
       player.view.removeFromSuperview()
    }
}

extension MEPlayerController: PlayerPlaybackDelegate {

    public func playerCurrentTimeDidChange(_ player: Player) {
      //   print("playerCurrentTimeDidChange")
    }

    public func playerPlaybackWillStartFromBeginning(_ player: Player) {
        print("playerPlaybackWillStartFromBeginning")
    }

    public func playerPlaybackDidEnd(_ player: Player) {
        print("playerPlaybackDidEnd")
    }

    public func playerPlaybackWillLoop(_ player: Player) {
        print("playerPlaybackWillLoop")
    }

    public func playerPlaybackDidLoop(_ player: Player) {
        print("playerPlaybackDidLoop")
    }
}

extension MEPlayerController: PlayerDelegate {

    public func playerReady(_ player: Player) {
        print("\(#function) ready")
    }

    public func playerPlaybackStateDidChange(_ player: Player) {
        print("\(#function) \(player.playbackState.description)")
    }

    public func playerBufferingStateDidChange(_ player: Player) {
        print("\(#function) \(player.bufferingState.description)")
    }

    public func playerBufferTimeDidChange(_ bufferTime: Double) {
        print("\(#function) \(bufferTime)")
    }

    public func player(_ player: Player, didFailWithError error: Error?) {
        print("\(#function) error.description")
    }
}