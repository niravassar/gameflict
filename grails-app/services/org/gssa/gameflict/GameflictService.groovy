package org.gssa.gameflict

import grails.gorm.transactions.Transactional

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Orchestrates all the operations
 *
 * Creates game entries, calculates conflicts and queries games
 *
 */
@Transactional
class GameflictService {

    public static final String DATE_FORMAT = "M/d/[uuuu][uu]"

    FieldService fieldService
    GameflictCsvImportService gameflictCsvImportService

    /****************** import games *******************************************/

    int importAndSaveGames(String fileName, String leagueName) {
        List<String[]> gameCsvValues = gameflictCsvImportService.parseCsv(fileName)
        List<GameRow> gameRows = gameflictCsvImportService.convertIntoGameRows(gameCsvValues)
        List<Game> games = []
        for (gameRow in gameRows) {
            games << gameEntry(gameRow.gameNumber,gameRow.dateAsString,gameRow.timeAsString,gameRow.ageGroupAsString,
                                    gameRow.fieldName, leagueName, gameRow.homeCoach, gameRow.visitorCoach)
        }
        games.size()
    }

    GamesExport createGamesExport(Date date = null) {
        List<Game> games = findAllGamesOrAfterDate(date)
        List<GameConflict> gameConflicts = calculateGameConflictsAfterDate(date)
        List<CoachConflict> coachConflicts = calculateCoachConflictsAfterDate(date)


        GamesExport gamesExport = new GamesExport()
        gamesExport.games.addAll(games)
        gamesExport.gameConflicts.addAll(gameConflicts)
        gamesExport.coachConflicts.addAll(coachConflicts)
        gamesExport
    }

    Game gameEntry(Integer gameNumber, String dateAsString, String timeAsString, String ageGroupAsString, String fieldName,
                   String leagueName, String homeCoach, String visitorCoach) {
        LocalDate date = parseDate(dateAsString)
        AgeGroup ageGroup = matchAgeGroup(ageGroupAsString)
        Field field = fieldService.findFieldByName(fieldName)
        League league = League.findByName(leagueName)

        return createOrUpdateGame(gameNumber, date, timeAsString, ageGroup, field, league, homeCoach, visitorCoach)
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
            List<Game> games = Game.findAllByDateGreaterThanEquals(date)
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

    protected AgeGroup matchAgeGroup(String ageGroupAsString) {
        AgeGroup ageGroup = AgeGroup.findByName(ageGroupAsString)
        ageGroup
    }

    protected Game createOrUpdateGame(Integer gameNumber, LocalDate date, String timeAsString, AgeGroup ageGroup,
                                      Field field, League league, String homeCoach, String visitorCoach) {
        Game game = Game.findOrCreateByGameNumberAndLeague(gameNumber, league)
        game.gameNumber = gameNumber
        game.date = java.sql.Date.valueOf(date)
        game.time = timeAsString
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
            "${game.field}-${game.dateAsLocalDate}"
        }
        def groups = games.groupBy(byFieldAndLocalDate)
        groups
    }

    protected Map<String, List<Game>> findAllGamesAndGroupByDate(Date date = null) {
        List<Game> games = findAllGamesOrAfterDate(date)
        def byLocalDate= { game ->
            "${game.dateAsLocalDate}"
        }
        def groups = games.groupBy(byLocalDate)
        groups
    }

    protected List<GameConflict> calculateGameConflictsForOneGroup(String key, List<Game> games) {
        List<GameConflict> gameConflicts = []
        for (x in games) {
            for (y in games) {
                // if its not the same game then try the overlap
                if (x.gameNumber != y.gameNumber) {
                    if (x.isGameOverlapping(y)) {
                        GameConflict gameConflict = new GameConflict(key: key, game1: x, game2: y)
                        gameConflicts << gameConflict
                    }
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
