package org.gssa.gameflict.mock

import org.gssa.gameflict.Field
import org.gssa.gameflict.FieldNickName

class FieldMocker {

    void mockFields() {
        Field mm1 = new Field(name: "MM1").save()
        Field og1 = new Field(name: "OG1").save()

        new FieldNickName(name: "Meadowmere 1", field: mm1).save()
        new FieldNickName(name: "GSSA Meadowmere 1", field: mm1).save()
        new FieldNickName(name: "Oak Grove 1", field: og1).save()
        new FieldNickName(name: "GSSA OakGrove 1", field: og1).save()
    }
}
