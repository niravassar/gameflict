package org.gssa.gameflict

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class GameCsvImportServiceSpec extends Specification implements ServiceUnitTest<GameCsvImportService>{

    static final String SAMPLE_XLS_PATH = "src/integration-test/resources"

    void "test parse csv"() {
        when:
        File gameXls = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        List<String[]> gameCsvValues = service.parseCsv(gameXls.path)

        then:
        gameCsvValues.size() == 6
        gameCsvValues[0].toString() == "[935, 10/27/2018, 9:00 AM, U8, GSSA Bob Jones #1A]"
        gameCsvValues[5].toString() == "[971, 11/06/2018, 7:15 PM, U8, GSSA Meadowmere #4C]"
    }
}
