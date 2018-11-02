package org.gssa.gameflict

import grails.gorm.transactions.Transactional

/**
 * Orchestrates all the operations
 */
@Transactional
class GameFlictService {

    GameCsvImportService gameCsvImportService
    GameService gameService

    void importAndSaveGames(String fileName, String leagueName) {
        List<String[]> gameCsvValues = gameCsvImportService.parseCsv(fileName)
        List<GameRow> gameRows = gameCsvImportService.convertIntoGameRows(gameCsvValues)
        for (gameRow in gameRows) {
            gameService.gameEntry(gameRow.gameNumber,gameRow.dateAsString,gameRow.timeAsString,gameRow.ageGroupAsString,
                                    gameRow.fieldName, leagueName)
        }
    }

    GamesExport createGamesExport(Date date = null) {
        List<Game> games = gameService.findAllGamesOrAfterDate(date)
        List<GameConflict> gameConflicts = gameService.calculateGameConflictsAfterDate(date)
        GamesExport gamesExport = new GamesExport()
        gamesExport.games.addAll(games)
        gamesExport.gameConflicts.addAll(gameConflicts)
        gamesExport
    }
}
