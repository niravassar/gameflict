package org.gssa.gameflict

import groovy.json.JsonSlurper

/**
 * Creates the default fields for GSSA
 */
class FieldCreator {

    JsonSlurper jsonSlurper = new JsonSlurper()

    static String fieldsString = '''
        [
            {"name": "OG1",  "fieldNickNames": ["GSSA Oak Grove Park #1", "Oakgrove #1"]},
            {"name": "OG2",  "fieldNickNames": ["GSSA Oak Grove Park #2", "Oakgrove #2"]},
            {"name": "OG3",  "fieldNickNames": ["GSSA Oak Grove Park #3", "Oakgrove #3"]},
            {"name": "OG4",  "fieldNickNames": ["GSSA Oak Grove Park #4", "Oakgrove #4"]},
            {"name": "OG5A",  "fieldNickNames": ["GSSA Oak Grove Park #5A", "Oakgrove #5A"]},
            {"name": "OG5B",  "fieldNickNames": ["GSSA Oak Grove Park #5B", "Oakgrove #5B"]},
            {"name": "OG6",  "fieldNickNames": ["GSSA Oak Grove Park #6", "Oakgrove #6"]}, 
            
            {"name": "MM1",  "fieldNickNames": ["GSSA Meadowmere #1", "Meadowmere #1"]},            
            {"name": "MM2A",  "fieldNickNames": ["GSSA Meadowmere #2A", "Meadowmere #2A"]},            
            {"name": "MM2B",  "fieldNickNames": ["GSSA Meadowmere #2B", "Meadowmere #2B"]},  
            {"name": "MM3",  "fieldNickNames": ["GSSA Meadowmere #3", "Meadowmere #3"]},           
            {"name": "MM4A",  "fieldNickNames": ["GSSA Meadowmere #4A", "Meadowmere #4A"]},            
            {"name": "MM4B",  "fieldNickNames": ["GSSA Meadowmere #4B", "Meadowmere #4B"]},            
            {"name": "MM4C",  "fieldNickNames": ["GSSA Meadowmere #4C", "Meadowmere #4C"]},            
            {"name": "MM5",  "fieldNickNames": ["GSSA Meadowmere #5", "Meadowmere #5", "Meadowmere #5A", "Meadowmere #5B"]},            
            {"name": "MM6",  "fieldNickNames": ["GSSA Meadowmere #6", "Meadowmere #6"]},
            
            {"name": "BJ1A",  "fieldNickNames": ["GSSA Bob Jones #1A"]},
            {"name": "BJ1B",  "fieldNickNames": ["GSSA Bob Jones #1B"]},
            {"name": "BJ2A",  "fieldNickNames": ["GSSA Bob Jones #2A"]},
            {"name": "BJ2B",  "fieldNickNames": ["GSSA Bob Jones #2B"]},
            {"name": "BJ3A",  "fieldNickNames": ["GSSA Bob Jones #3A"]},
            {"name": "BJ3B",  "fieldNickNames": ["GSSA Bob Jones #3B"]},
            {"name": "BJ3C",  "fieldNickNames": ["GSSA Bob Jones #3C"]},
            {"name": "BJ3D",  "fieldNickNames": ["GSSA Bob Jones #3D"]},
            {"name": "BJ3E",  "fieldNickNames": ["GSSA Bob Jones #3E"]},
            {"name": "BJ3F",  "fieldNickNames": ["GSSA Bob Jones #3F"]},
            {"name": "BJ3G",  "fieldNickNames": ["GSSA Bob Jones #3G"]},
            {"name": "BJ3H",  "fieldNickNames": ["GSSA Bob Jones #3H"]}                       
        ]'''

    static String leaguesString = '''
        [
            {"name": "GSSA Rec Fall 2018"},
            {"name": "GSSA NMCSL Fall 2018"},
            {"name": "GSSA Adult Fall 2018"},
            {"name": "U90C Fall 2018"},                     
            {"name": "GMSL Fall 2018"}                    
        ]'''

    void createFields() {
        List<Map> fieldsMap = jsonSlurper.parseText(fieldsString)

        for(f in fieldsMap) {
            Field field = new Field(name: f.name)
            for (fnickName in f.fieldNickNames) {
                new FieldNickName(name: fnickName, field: field).save()
            }
        }
    }

    void createLeagues() {
        List<Map> leaguesMap = jsonSlurper.parseText(leaguesString)

        for(l in leaguesMap) {
            new League(name: l.name, season: l.season).save()
        }
    }
}
