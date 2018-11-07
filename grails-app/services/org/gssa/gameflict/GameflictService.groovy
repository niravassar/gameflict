package org.gssa.gameflict

import grails.gorm.transactions.Transactional

/**
 * Orchestrates all the operations
 */
@Transactional
class GameflictService {

    GameCsvImportService gameCsvImportService
    GameService gameService

    int importAndSaveGames(String fileName, String leagueName) {
        List<String[]> gameCsvValues = gameCsvImportService.parseCsv(fileName)
        List<GameRow> gameRows = gameCsvImportService.convertIntoGameRows(gameCsvValues)
        List<Game> games = []
        for (gameRow in gameRows) {
            games << gameService.gameEntry(gameRow.gameNumber,gameRow.dateAsString,gameRow.timeAsString,gameRow.ageGroupAsString,
                                    gameRow.fieldName, leagueName, gameRow.coachName1, gameRow.coachName2)
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
