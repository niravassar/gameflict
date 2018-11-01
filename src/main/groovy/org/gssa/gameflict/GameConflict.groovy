package org.gssa.gameflict

/**
 * Gathers together a game conflict information
 */
class GameConflict {
    String key
    Game game1
    Game game2

    String getConflictMessage() {
        "Conflict: ${key} -- Game: #${game1.gameNumber} at ${game1.time} vs #${game2.gameNumber} at ${game2.time}"
    }
}
