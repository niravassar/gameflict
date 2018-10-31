package org.gssa.gameflict

import grails.gorm.transactions.Transactional

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Transactional
class GameService {

    LocalDate parseDate(String dateAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        LocalDate localDate = LocalDate.parse(dateAsString, formatter)
        localDate
    }

    LocalTime parseTime(String timeAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
        LocalTime localTime = LocalTime.parse(timeAsString, formatter)
        localTime
    }

    AgeGroup matchAgeGroup(String ageGroupAsString) {
        AgeGroup ageGroup = ageGroupAsString as AgeGroup
        ageGroup
    }

    protected Game createOrUpdate(Integer gameNumber, LocalDate date, LocalTime time, AgeGroup ageGroup,
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
