package org.gssa.gameflict

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

import java.time.LocalDate
import java.time.LocalTime

class GameServiceSpec extends HibernateSpec implements ServiceUnitTest<GameService>{

    FieldCreator fieldCreator = new FieldCreator()

    List<Class> getDomainClasses() { [Field, FieldNickName, Game, League]}

    def setup() {
        fieldCreator.createFields()
        fieldCreator.createLeagues()
    }

    void "test create game"() {
        when:
        League gssaRec = League.findByName("GSSA Rec")
        Field mm1 = Field.findByName("MM1")
        Game game = service.createGame(409, LocalDate.now(), LocalTime.now(), AgeGroup.U9, mm1, gssaRec)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 1
        game.gameNumber == 409
        game.field.name == "MM1"
    }

}
