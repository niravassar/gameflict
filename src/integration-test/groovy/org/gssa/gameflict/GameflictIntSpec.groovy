package org.gssa.gameflict

import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZoneId

/**
 * End to end tests on the service layer with files
 */
@Integration
@Rollback
class GameflictIntSpec extends Specification {

    static final String SAMPLE_XLS_PATH = "src/integration-test/resources"

    GameflictService gameflictService
    GameService gameService

    def setup() {
    }

    def cleanup() {
    }

    void "test import and save csv files - formatted dates from xls"() {
        when:
        String gssaRec = "GSSA Rec"
        String gssaNmcsl = "GSSA NMCSL"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample.csv")
        gameflictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameflictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)
        List<Game> games = Game.list()

        then:
        games.size() == 12
    }

    void "test import and save csv files - just column adjustment and save"() {
        when:
        String gssaRec = "GSSA Rec"
        String gssaNmcsl = "GSSA NMCSL"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample_no_formatting.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample_no_formatting.csv")
        gameflictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameflictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)
        List<Game> games = Game.list()

        then:
        games.size() == 19
    }

    void "test one game is updated"() {
        when:
        String gssaRec = "GSSA Rec"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        gameflictService.importAndSaveGames(gameCsv1.path, gssaRec)
        List<Game> games = Game.list()

        then:
        games.size() == 6
        games[0].gameNumber == 935
        games[0].date.toString() == "2018-10-27"
        games[0].time.toString() == "09:00"

        when:
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample_game_moved.csv")
        gameflictService.importAndSaveGames(gameCsv2.path, gssaRec)
        games = Game.list()

        then:
        games.size() == 6
        games[0].gameNumber == 935
        games[0].date.toString() == "2018-12-01"
        games[0].time.toString() == "17:00"

    }

    void "test query by date"() {
        when:
        String gssaRec = "GSSA Rec"
        String gssaNmcsl = "GSSA NMCSL"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample.csv")
        gameflictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameflictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)

        LocalDate localDate = gameService.parseDate("11/03/2018")
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        List<Game> games = gameService.findAllGamesOrAfterDate(date)
        List<Game> gamesSorted = games.toSorted { it.gameNumber }

        then:
        gamesSorted.size() == 4
        gamesSorted[0].gameNumber == 273
        gamesSorted[1].gameNumber == 573
    }

    void "test no game conflicts identified"() {
        when:
        String gssaRec = "GSSA Rec"
        String gssaNmcsl = "GSSA NMCSL"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample.csv")
        gameflictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameflictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)
        List<GameConflict> gameConflicts = gameService.calculateGameConflictsAfterDate()


        then:
        gameConflicts.size() == 0
    }

    void "test game conflicts identified"() {
        when:
        String gssaRec = "GSSA Rec"
        String gssaNmcsl = "GSSA NMCSL"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample_with_conflict.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample.csv")
        gameflictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameflictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)
        List<GameConflict> gameConflicts = gameService.calculateGameConflictsAfterDate()


        then:
        gameConflicts.size() == 2
        gameConflicts[0].key == "MM2A-2018-10-28"
        gameConflicts[0].game1.gameNumber == 515
    }

    void "test no coach conflicts identified"() {
        when:
        String gssaRec = "GSSA Rec"
        String gssaNmcsl = "GSSA NMCSL"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample.csv")
        gameflictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameflictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)
        List<CoachConflict> coachConflicts = gameService.calculateCoachConflictsAfterDate()


        then:
        coachConflicts.size() == 0
    }

    void "test coach conflicts identified"() {
        when:
        String gssaRec = "GSSA Rec"
        String gssaNmcsl = "GSSA NMCSL"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample_with_conflict.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample.csv")
        gameflictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameflictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)
        List<CoachConflict> coachConflicts = gameService.calculateCoachConflictsAfterDate()


        then:
        coachConflicts.size() == 2
        coachConflicts[0].key == "2018-10-28"
        coachConflicts[0].game1.gameNumber == 515
        coachConflicts[0].conflictMessage == "#515 GSSA Rec has Steve and Mark / #1194 GSSA NMCSL has Steve and Nirav"
    }

    void "test all game export with conflicts"() {
        when:
        String gssaRec = "GSSA Rec"
        String gssaNmcsl = "GSSA NMCSL"
        File gameCsv1 = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample_with_conflict.csv")
        File gameCsv2 = new File("$SAMPLE_XLS_PATH/GSSANMCSL7_sample.csv")
        gameflictService.importAndSaveGames(gameCsv1.path, gssaRec)
        gameflictService.importAndSaveGames(gameCsv2.path, gssaNmcsl)
        GamesExport gamesExport = gameflictService.createGamesExport()


        then:
        gamesExport.games.size() == 12
        gamesExport.gameConflicts.size() == 2
        gamesExport.coachConflicts.size() == 2
    }
}
