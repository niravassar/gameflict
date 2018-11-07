package org.gssa.gameflict

/**
 * Gathers together a coach conflict information
 */
class CoachConflict {
    String key
    Game game1
    Game game2

    String getConflictMessage() {
        "Coach Conflict ${key} Game #${game1.gameNumber} has ${game1.coachName1} and ${game1.coachName2} while #${game2.gameNumber} has ${game2.coachName1} and ${game2.coachName2}"
    }

    @Override
    String toString() {
        "${game1} vs ${game2}"
    }

    String constructRow() {
        ["COACH CONFLICT", "${game1.date}", "${game1.time}", "${game1.ageGroup}", "${game1.field}", getConflictMessage()].join(",")
    }
}
