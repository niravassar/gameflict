package org.gssa.gameflict

import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZoneId

/**
 * End to end tests on the service layer
 */
@Integration
@Rollback
class GameFlictIntSpec extends Specification {

    static final String SAMPLE_XLS_PATH = "src/integration-test/resources"

    GameFlictService gameFlictService
    GameService gameService

    def setup() {
    }

    def cleanup() {
    }

    void "test import and save csv files"() {
        when:
        String gssaRec = "GSSA Rec Fall 2018"
        String gssaNmcsl = "GSSA NMCSL Fall 2018"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample.csv")
        gameFlictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameFlictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)
        List<Game> games = Game.list()

        then:
        games.size() == 12
    }

    void "test one game is updated"() {
        when:
        String gssaRec = "GSSA Rec Fall 2018"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        gameFlictService.importAndSaveGames(gameCsv1.path, gssaRec)
        List<Game> games = Game.list()

        then:
        games.size() == 6
        games[0].gameNumber == 935
        games[0].date.toString() == "2018-10-27"
        games[0].time.toString() == "09:00"

        when:
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample_game_moved.csv")
        gameFlictService.importAndSaveGames(gameCsv2.path, gssaRec)
        games = Game.list()

        then:
        games.size() == 6
        games[0].gameNumber == 935
        games[0].date.toString() == "2018-12-01"
        games[0].time.toString() == "17:00"

    }

    void "test query by date"() {
        when:
        String gssaRec = "GSSA Rec Fall 2018"
        String gssaNmcsl = "GSSA NMCSL Fall 2018"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample.csv")
        gameFlictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameFlictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)

        LocalDate localDate = gameService.parseDate("11/03/2018")
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        List<Game> games = gameService.findAllGamesOrAfterDate(date)
        List<Game> gamesSorted = games.toSorted { it.gameNumber }

        then:
        gamesSorted.size() == 4
        gamesSorted[0].gameNumber == 273
        gamesSorted[1].gameNumber == 573
    }

    void "test no conflicts identified"() {
        when:
        String gssaRec = "GSSA Rec Fall 2018"
        String gssaNmcsl = "GSSA NMCSL Fall 2018"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample.csv")
        gameFlictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameFlictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)
        List<GameConflict> gameConflicts = gameService.calculateGameConflictsAfterDate()


        then:
        gameConflicts.size() == 0
    }

    void "test conflicts identified"() {
        when:
        String gssaRec = "GSSA Rec Fall 2018"
        String gssaNmcsl = "GSSA NMCSL Fall 2018"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample_with_conflict.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample.csv")
        gameFlictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameFlictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)
        List<GameConflict> gameConflicts = gameService.calculateGameConflictsAfterDate()


        then:
        gameConflicts.size() == 2
        gameConflicts[0].key == "MM2A-2018-10-28"
        gameConflicts[0].game1.gameNumber == 515
    }
}
