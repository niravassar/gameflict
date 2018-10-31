package org.gssa.gameflict

import grails.gorm.transactions.Transactional

import java.time.LocalDate
import java.time.LocalTime

@Transactional
class GameService {

    Game createOrUpdate(Integer gameNumber, LocalDate date, LocalTime time, AgeGroup ageGroup,
                        Field field, League league) {
        Game game = Game.findOrCreateByGameNumber(gameNumber)
        game.gameNumber = gameNumber
        game.date = date
        game.time = time
        game.ageGroup = ageGroup
        game.field = field
        game.league = league
        game.save()
        game
    }
}
