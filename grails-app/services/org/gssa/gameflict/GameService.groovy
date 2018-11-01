package org.gssa.gameflict

import grails.gorm.transactions.Transactional

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Transactional
class GameService {

    FieldService fieldService

    Game gameEntry(Integer gameNumber, String dateAsString, String timeAsString, String ageGroupAsString, String fieldName,
                   String leagueName) {
        LocalDate date = parseDate(dateAsString)
        LocalTime time = parseTime(timeAsString)
        AgeGroup ageGroup = matchAgeGroup(ageGroupAsString)
        Field field = fieldService.findFieldByName(fieldName)
        League league = League.findByName(leagueName)

        return createOrUpdate(gameNumber, date, time, ageGroup, field, league)
    }

    List<Game> findAllGamesAfterDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        List<Game> games = Game.findAllByDateGreaterThanEquals(localDate)
        games
    }

    List<Game> findAllGamesByFieldAndDate(Date date, Field field) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        List<Game> games = Game.findAllByDateAndField(localDate, field)
        games
    }

    Map<String, List<Game>> findAllAndGroupByFieldAndDate() {
        List<Game> games = Game.list()
        def byFieldAndLocalDate= { game ->
            "${game.field}-${game.date}"
        }
        def groups = games.groupBy(byFieldAndLocalDate)
        groups
    }

    List<GameConflict> calculateAllGameConflicts(Map<String, List<Game>> groups) {

    }

    /**************************** protected **************************************************/

    protected LocalDate parseDate(String dateAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        LocalDate localDate = LocalDate.parse(dateAsString, formatter)
        localDate
    }

    protected LocalTime parseTime(String timeAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
        LocalTime localTime = LocalTime.parse(timeAsString, formatter)
        localTime
    }

    protected AgeGroup matchAgeGroup(String ageGroupAsString) {
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

    protected List<GameConflict> calculateGameConflictsForOneGroup(String key, List<Game> games) {
        List<GameConflict> gameConflicts = []
        for (x in games) {
            for (y in games) {
                if (x.gameNumber == y.gameNumber) {
                    // do nothing
                } else if (x.isGameOverlapping(y)) {
                    GameConflict gameConflict = new GameConflict(key: key, game1: x, game2: y)
                    gameConflicts << gameConflict
                }
            }
        }
        gameConflicts
    }
}
