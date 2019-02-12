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

    public static final String DATE_FORMAT = "M/d/[uuuu][uu]"
    public static final String TIME_FORMAT = "h:mm a"

    FieldService fieldService

    Game gameEntry(Integer gameNumber, String dateAsString, String timeAsString, String ageGroupAsString, String fieldName,
                   String leagueName, String homeCoach, String visitorCoach) {
        LocalDate date = parseDate(dateAsString)
        LocalTime time = parseTime(timeAsString)
        AgeGroup ageGroup = matchAgeGroup(ageGroupAsString)
        Field field = fieldService.findFieldByName(fieldName)
        League league = League.findByName(leagueName)

        return createOrUpdateGame(gameNumber, date, time, ageGroup, field, league, homeCoach, visitorCoach)
    }

    Team teamEntry(String teamName, String ageGroupAsString, String coachName) {
        AgeGroup ageGroup = matchAgeGroup(ageGroupAsString)
        Team team = Team.findOrCreateByTeamNameAndCoachName(teamName, coachName)
        team.teamName = teamName
        team.ageGroup = ageGroup
        team.coachName = coachName
        team.save()
        team
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

    List<GameConflict> calculateGameConflictsAfterDate(Date date = null) {
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

    List<CoachConflict> calculateCoachConflictsAfterDate(Date date = null) {
        Map<String, List<Game>> groups = findAllGamesAndGroupByDate(date)
        List<CoachConflict> allCoachConflicts = []
        Set<String> keys = groups.keySet()
        for ( key in keys ) {
            List<Game> gamesByDay = groups.get(key)
            List<CoachConflict> coachConflicts = calculateCoachConflictsForOneDay(key, gamesByDay)
            allCoachConflicts.addAll(coachConflicts)
        }
        allCoachConflicts
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

    protected Game createOrUpdateGame(Integer gameNumber, LocalDate date, LocalTime time, AgeGroup ageGroup,
                                      Field field, League league, String homeCoach, String visitorCoach) {
        Game game = Game.findOrCreateByGameNumberAndLeague(gameNumber, league)
        game.gameNumber = gameNumber
        game.date = date
        game.time = time
        game.ageGroup = ageGroup
        game.field = field
        game.league = league
        game.homeCoach = homeCoach
        game.visitorCoach = visitorCoach
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

    protected Map<String, List<Game>> findAllGamesAndGroupByDate(Date date = null) {
        List<Game> games = findAllGamesOrAfterDate(date)
        def byLocalDate= { game ->
            "${game.date}"
        }
        def groups = games.groupBy(byLocalDate)
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

    protected List<CoachConflict> calculateCoachConflictsForOneDay(String key, List<Game> games) {
        List<CoachConflict> coachConflicts = []
        for (x in games) {
            for (y in games) {
                if (x.gameNumber != y.gameNumber) {
                    if (x.isCoachInvolved(y)) {
                        if (x.isGameOverlapping(y)) {
                            CoachConflict coachConflict = new CoachConflict(key: key, game1: x, game2: y)
                            coachConflicts << coachConflict
                        }
                    }
                }
            }
        }
        coachConflicts
    }
}
