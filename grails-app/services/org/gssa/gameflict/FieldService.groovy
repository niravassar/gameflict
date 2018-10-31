package org.gssa.gameflict

import grails.gorm.transactions.Transactional

@Transactional
class FieldService {

    /**
     * Finds a field by name or nickname
     * @param name
     * @return
     */
    Field findFieldByName(String name) {
        Field field = Field.findByName(name)
        if (field) {
            return field
        } else {
            FieldNickName fieldNickName = FieldNickName.findByName(name)
            if (fieldNickName) {
                return fieldNickName.field
            }
        }
    }
}
