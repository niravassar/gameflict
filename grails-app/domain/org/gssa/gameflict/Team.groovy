package org.gssa.gameflict

/**
 * Team with coach name and age
 */
class Team {

    String teamName
    AgeGroup ageGroup
    String coachName

    @Override
    String toString() {
        "$ageGroup - $teamName - $coachName"
    }
}
