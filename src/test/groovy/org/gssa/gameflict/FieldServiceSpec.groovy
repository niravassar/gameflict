package org.gssa.gameflict

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class FieldServiceSpec extends HibernateSpec implements ServiceUnitTest<FieldService>{

    FieldCreator fieldCreator = new FieldCreator()

    List<Class> getDomainClasses() { [Field, FieldNickName]}

    def setup() {
        fieldCreator.createFields()
    }

    void "test find field by nickname"() {
        when:
        Field mm1 = service.findFieldByName("GSSA Meadowmere Park #1")
        Field og1 = service.findFieldByName("Oakgrove #1")

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
