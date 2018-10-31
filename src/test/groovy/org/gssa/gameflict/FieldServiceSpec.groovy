package org.gssa.gameflict

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import org.gssa.gameflict.mock.FieldMocker

class FieldServiceSpec extends HibernateSpec implements ServiceUnitTest<FieldService>{

    FieldMocker fieldMocker = new FieldMocker()

    List<Class> getDomainClasses() { [Field, FieldNickName]}

    def setup() {
        fieldMocker.mockFields()
    }

    void "test find field by nickname"() {
        when:
        Field mm1 = service.findFieldByName("GSSA Meadowmere 1")
        Field og1 = service.findFieldByName("Oak Grove 1")

        then:
        mm1.name == "MM1"
        og1.name == "OG1"

    }

    void "test find field by name"() {
        when:
        Field mm1 = service.findFieldByName("MM1")
        Field og1 = service.findFieldByName("OG1")

        then:
        mm1.name == "MM1"
        og1.name == "OG1"
    }
}
