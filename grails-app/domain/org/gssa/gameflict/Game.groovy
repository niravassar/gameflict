package org.gssa.gameflict

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Information for a game entry
 */
class Game {

    public static final String TIME_FORMAT = "h:mm a"

    Integer gameNumber
    Date date
    String time
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
        gameBlockTime.startTime = timeAsLocalTime
        gameBlockTime.endTime = timeAsLocalTime.plusMinutes(ageGroup.durationMinutes)
        gameBlockTime
    }

    boolean isGameOverlapping(Game otherGame) {
        GameBlockTime thisGameBlockTime = this.gameBlockTime
        GameBlockTime otherGameBlockTime = otherGame.gameBlockTime

        boolean startOverLap = this.gameBlockTime.startTime <= otherGameBlockTime.startTime && otherGameBlockTime.startTime < thisGameBlockTime.endTime

        startOverLap
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
        [gameNumber, dateAsLocalDate, timeAsLocalTime, ageGroup, field, league, homeCoach, visitorCoach].join(",")
    }

    LocalDate getDateAsLocalDate() {
        new java.sql.Date(this.date.getTime()).toLocalDate()
    }

    LocalTime getTimeAsLocalTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT)
        LocalTime localTime = LocalTime.parse(time, formatter)
        localTime
    }
}
