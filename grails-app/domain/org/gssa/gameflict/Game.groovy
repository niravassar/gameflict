package org.gssa.gameflict

import java.time.LocalDate
import java.time.LocalTime

class Game {

    Integer gameNumber
    LocalDate date
    LocalTime time
    AgeGroup ageGroup
    Field field
    League league

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
}
