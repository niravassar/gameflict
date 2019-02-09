package org.gssa.gameflict

import grails.gorm.transactions.Transactional

/**
 * Orchestrates all the operations
 */
@Transactional
class GameflictService {

    GameflictCsvImportService gameflictCsvImportService
    GameService gameService

    int importAndSaveGames(String fileName, String leagueName) {
        List<String[]> gameCsvValues = gameflictCsvImportService.parseCsv(fileName)
        List<GameRow> gameRows = gameflictCsvImportService.convertIntoGameRows(gameCsvValues)
        List<Game> games = []
        for (gameRow in gameRows) {
            games << gameService.gameEntry(gameRow.gameNumber,gameRow.dateAsString,gameRow.timeAsString,gameRow.ageGroupAsString,
                                    gameRow.fieldName, leagueName, gameRow.homeCoach, gameRow.visitorCoach)
        }
        games.size()
    }

    GamesExport createGamesExport(Date date = null) {
        List<Game> games = gameService.findAllGamesOrAfterDate(date)
        List<GameConflict> gameConflicts = gameService.calculateGameConflictsAfterDate(date)
        List<CoachConflict> coachConflicts = gameService.calculateCoachConflictsAfterDate(date)


        GamesExport gamesExport = new GamesExport()
        gamesExport.games.addAll(games)
        gamesExport.gameConflicts.addAll(gameConflicts)
        gamesExport.coachConflicts.addAll(coachConflicts)
        gamesExport
    }
}
