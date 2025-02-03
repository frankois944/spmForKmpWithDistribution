package fr.frankois944.mysampleplayer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform