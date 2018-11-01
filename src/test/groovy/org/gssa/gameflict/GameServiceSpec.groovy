package org.gssa.gameflict

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

import java.time.LocalDate
import java.time.LocalTime

class GameServiceSpec extends HibernateSpec implements ServiceUnitTest<GameService>{

    FieldCreator fieldCreator = new FieldCreator()
    LocalTime nineAm = LocalTime.of(9,00)
    LocalDate oct31 = LocalDate.of(2018, 10, 31)
    LocalTime tenAm = LocalTime.of(10,00)
    LocalDate nov1 = LocalDate.of(2018, 11, 1)

    List<Class> getDomainClasses() { [Field, FieldNickName, Game, League]}

    def setup() {
        fieldCreator.createFields()
        fieldCreator.createLeagues()
    }

    void "test create game"() {
        when:
        League gssaRec = League.findByName("GSSA Rec Fall 2018")
        Field mm1 = Field.findByName("MM1")
        Game game = service.createOrUpdate(409, oct31, nineAm,AgeGroup.U9, mm1, gssaRec)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 1
        game.gameNumber == 409
        game.field.name == "MM1"
        game.date.isEqual(oct31)
        game.time.equals(nineAm)
    }

    void "test game is updated when time is changed"() {
        when:
        League gssaRec = League.findByName("GSSA Rec Fall 2018")
        Field mm1 = Field.findByName("MM1")
        Game game = service.createOrUpdate(409, oct31, nineAm, AgeGroup.U9, mm1, gssaRec)
        game = service.createOrUpdate(409, nov1, tenAm, AgeGroup.U9, mm1, gssaRec)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 1
        game.gameNumber == 409
        game.field.name == "MM1"
        game.date.isEqual(nov1)
        game.time.equals(tenAm)
    }

    void "test match ageGroup"() {
        when:
        AgeGroup u9AgeGroup = service.matchAgeGroup("U9")
        AgeGroup u13AgeGroup = service.matchAgeGroup("U13")

        then:
        u9AgeGroup.name() == "U9"
        u9AgeGroup.durationMinutes == 75
        u13AgeGroup.name()  == "U13"
        u13AgeGroup.durationMinutes == 90
    }

    void "test parse localdate"() {
        when:
        LocalDate localDate1 = service.parseDate("08/15/2018")
        LocalDate localDate2 = service.parseDate("10/31/2018")

        then:
        localDate1.toString() == "2018-08-15"
        localDate2.toString() == "2018-10-31"
    }

    void "test parse localtime"() {
        when:
        LocalTime localTime1 = service.parseTime("09:00:00 AM")
        LocalTime localTime2 = service.parseTime("02:15:00 PM")

        then:
        localTime1.toString() == "09:00"
        localTime2.toString() == "14:15"
    }

    void "test gameEntry"() {
        given:
        def mockFieldService = Mock(FieldService)
        service.fieldService = mockFieldService

        when:
        service.gameEntry(205, "10/28/2018","09:00:00 AM","U9",
                "MM1","GSSA Rec Fall 2018")
        Game game = Game.findByGameNumber(205)

        then:
        1 * mockFieldService.findFieldByName("MM1") >> Field.findByName("MM1")
        game.gameNumber == 205
        game.date.toString() == "2018-10-28"
        game.ageGroup.durationMinutes == 75
    }

    void "test game block time"() {
        when:
        League gssaRec = League.findByName("GSSA Rec Fall 2018")
        Field mm1 = Field.findByName("MM1")
        Game game = service.createOrUpdate(409, oct31, nineAm,AgeGroup.U9, mm1, gssaRec)
        List<Game> gameList = Game.list()
        GameBlockTime gameBlockTime = game.gameBlockTime

        then:
        gameList.size() == 1
        gameBlockTime.game.gameNumber == 409
        gameBlockTime.startTime.toString() == "09:00"
        gameBlockTime.endTime.toString() == "10:15"

    }

}
