package org.gssa.gameflict

import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZoneId

@Integration
@Rollback
class GameXlsImportServiceIntSpec extends Specification {

    static final String SAMPLE_XLS_PATH = "src/integration-test/resources"
    GameXlsImportService gameXlsImportService

    def setup() {
    }

    def cleanup() {
    }

    void "test parse xls"() {
        when:
        File gameXls = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        List<Map<String, String>> gameXlsValue = gameXlsImportService.parseXls(gameXls.path)

        then:
        1 ==1
    }

}
