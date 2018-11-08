package org.gssa.gameflict

/**
 * Gathers together a game conflict information
 */
class GameConflict {
    String key
    Game game1
    Game game2

    String getConflictMessage() {
        "#${game1.gameNumber} ${game1.league} / #${game2.gameNumber} ${game2.league}"
    }

    @Override
    String toString() {
        "${game1} vs ${game2}"
    }

    String constructRow() {
        ["GAME CONFLICT", "${game1.date}", "${game1.time}", "${game1.ageGroup}", "${game1.field}", getConflictMessage()].join(",")
    }
}
