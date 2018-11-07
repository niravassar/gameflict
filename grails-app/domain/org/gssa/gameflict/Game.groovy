package org.gssa.gameflict

import java.time.LocalDate
import java.time.LocalTime

/**
 * Information for a game entry
 */
class Game {

    Integer gameNumber
    LocalDate date
    LocalTime time
    AgeGroup ageGroup
    Field field
    League league
    String coachName1
    String coachName2

    static constraints = {
        coachName1 nullable: true
        coachName2 nullable: true
    }
    GameBlockTime getGameBlockTime() {
        GameBlockTime gameBlockTime = new GameBlockTime()
        gameBlockTime.game = this
        gameBlockTime.startTime = time
        gameBlockTime.endTime = time.plusMinutes(ageGroup.durationMinutes)
        gameBlockTime
    }

    boolean isGameOverlapping(Game otherGame) {
        GameBlockTime thisGameBlockTime = this.gameBlockTime
        GameBlockTime otherGameBlockTime = otherGame.gameBlockTime

        boolean startOverLap = this.gameBlockTime.startTime < otherGameBlockTime.startTime && otherGameBlockTime.startTime < thisGameBlockTime.endTime
        boolean endOverLap = this.gameBlockTime.startTime < otherGameBlockTime.endTime && otherGameBlockTime.endTime < thisGameBlockTime.endTime

        startOverLap || endOverLap
    }

    boolean isCoachInvolved(Game otherGame) {

        boolean coach1Involved = false
        boolean coach2Involved = false

        if (otherGame.coachName1) {
            coach1Involved = otherGame.coachName1.equals(this.coachName1) || otherGame.coachName1.equals(this.coachName2)
        }

        if (otherGame.coachName2) {
            coach2Involved = otherGame.coachName2.equals(this.coachName1) || otherGame.coachName2.equals(this.coachName2)
        }

        coach1Involved || coach2Involved
    }

    @Override
    String toString() {
        gameNumber
    }

    String constructRow() {
        [gameNumber, date, time, ageGroup, field, league, coachName1, coachName2].join(",")
    }
}
