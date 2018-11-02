package org.gssa.gameflict

import grails.gorm.transactions.Transactional

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Creates game entries, calculates conflicts and queries games
 */
@Transactional
class GameService {

    public static final String DATE_FORMAT = "MM/dd/yyyy"
    public static final String TIME_FORMAT = "h:mm a"

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

    List<Game> findAllGamesOrAfterDate(Date date = null) {
        if (date) {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            List<Game> games = Game.findAllByDateGreaterThanEquals(localDate)
            games
        } else {
            Game.list()
        }

    }

    List<GameConflict> calculateAllGameConflicts(Date date = null) {
        Map<String, List<Game>> groups = findAllGamesAndGroupByFieldAndDate(date)
        List<GameConflict> allGameConflicts = []
        Set<String> keys = groups.keySet()
        for ( key in keys ) {
            List<Game> gamesByDayAndField = groups.get(key)
            List<GameConflict> gameConflicts = calculateGameConflictsForOneGroup(key, gamesByDayAndField)
            allGameConflicts.addAll(gameConflicts)
        }
        allGameConflicts
    }

    /**************************** protected **************************************************/

    protected LocalDate parseDate(String dateAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        LocalDate localDate = LocalDate.parse(dateAsString, formatter)
        localDate
    }

    protected LocalTime parseTime(String timeAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT)
        LocalTime localTime = LocalTime.parse(timeAsString, formatter)
        localTime
    }

    protected AgeGroup matchAgeGroup(String ageGroupAsString) {
        AgeGroup ageGroup = ageGroupAsString as AgeGroup
        ageGroup
    }

    protected Game createOrUpdate(Integer gameNumber, LocalDate date, LocalTime time, AgeGroup ageGroup,
                        Field field, League league) {
        Game game = Game.findOrCreateByGameNumberAndLeague(gameNumber, league)
        game.gameNumber = gameNumber
        game.date = date
        game.time = time
        game.ageGroup = ageGroup
        game.field = field
        game.league = league
        game.save()
        game
    }

    protected Map<String, List<Game>> findAllGamesAndGroupByFieldAndDate(Date date = null) {
        List<Game> games = findAllGamesOrAfterDate(date)
        def byFieldAndLocalDate= { game ->
            "${game.field}-${game.date}"
        }
        def groups = games.groupBy(byFieldAndLocalDate)
        groups
    }

    protected List<GameConflict> calculateGameConflictsForOneGroup(String key, List<Game> games) {
        List<GameConflict> gameConflicts = []
        for (x in games) {
            for (y in games) {
                if (x.isGameOverlapping(y)) {
                    GameConflict gameConflict = new GameConflict(key: key, game1: x, game2: y)
                    gameConflicts << gameConflict
                }
            }
        }
        gameConflicts
    }
}
