package org.gssa.gameflict

/**
 * Gathers together a coach conflict information
 */
class CoachConflict {
    String key
    Game game1
    Game game2

    String getConflictMessage() {
        "#${game1.gameNumber} ${game1.league} has ${game1.homeCoach} and ${game1.visitorCoach} / #${game2.gameNumber} ${game2.league} " +
                "has ${game2.homeCoach} and ${game2.visitorCoach}"
    }

    @Override
    String toString() {
        "${game1} vs ${game2}"
    }

    String constructRow() {
        ["COACH CONFLICT", "${game1.date}", "${game1.time}", "${game1.ageGroup}", "${game1.field}", getConflictMessage()].join(",")
    }
}
