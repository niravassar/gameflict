package org.gssa.gameflict

import grails.gorm.transactions.Transactional

import java.time.LocalDate
import java.time.LocalTime

@Transactional
class GameService {

    Game createGame(Integer gameNumber, LocalDate date, LocalTime time, AgeGroup ageGroup,
                    Field field, League league) {
        Game game = new Game(gameNumber: gameNumber, date: date, time: time, ageGroup: ageGroup, field: field, league: league).save()
        game
    }
}
