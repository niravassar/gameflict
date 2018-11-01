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
}
