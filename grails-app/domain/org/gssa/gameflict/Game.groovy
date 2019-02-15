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
    Team homeTeam
    Team visitorTeam
    String homeCoach
    String visitorCoach

    static constraints = {
        gameNumber()
        date()
        time()
        ageGroup()
        field()
        league()
        homeTeam nullable: true
        visitorTeam nullable: true
        homeCoach nullable: true
        visitorCoach nullable: true
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

        boolean homeCoachInvolved = false
        boolean visitorCoachInvolved = false

        if (otherGame.homeCoach) {
            homeCoachInvolved = otherGame.homeCoach.equals(this.homeCoach) || otherGame.homeCoach.equals(this.visitorCoach)
        }

        if (otherGame.visitorCoach) {
            visitorCoachInvolved = otherGame.visitorCoach.equals(this.homeCoach) || otherGame.visitorCoach.equals(this.visitorCoach)
        }

        homeCoachInvolved || visitorCoachInvolved
    }

    @Override
    String toString() {
        gameNumber
    }

    String constructRow() {
        [gameNumber, date, time, ageGroup, field, league, homeCoach, visitorCoach].join(",")
    }
}
