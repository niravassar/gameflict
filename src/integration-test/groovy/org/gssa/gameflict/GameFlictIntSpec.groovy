package org.gssa.gameflict

import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

/**
 * End to end tests on the service layer
 */
@Integration
@Rollback
class GameFlictIntSpec extends Specification {

    static final String SAMPLE_XLS_PATH = "src/integration-test/resources"

    GameFlictService gameFlictService

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
        games.size() == 7
    }
}
