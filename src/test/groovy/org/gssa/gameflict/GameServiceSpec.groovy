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
        League gssaRec = League.findByName("GSSA Rec")
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
        League gssaRec = League.findByName("GSSA Rec")
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

    /***********************************************/


}