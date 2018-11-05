package org.gssa.gameflict

/**
 * Gathers together a game conflict information
 */
class GameConflict {
    String key
    Game game1
    Game game2

    String getConflictMessage() {
        "Conflict ${key} Field ${game1.field} Date ${game1.date} Game #${game1.gameNumber} ${game1.time} vs #${game2.gameNumber} ${game2.time}"
    }

    @Override
    String toString() {
        "${game1} vs ${game2}"
    }

    String constructRow() {
        ["CONFLICT", "${game1.date}", "${game1.time}", "${game1.ageGroup}", "${game1.field}", getConflictMessage()].join(",")
    }
}
