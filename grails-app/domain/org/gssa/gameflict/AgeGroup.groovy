package org.gssa.gameflict;

enum AgeGroup {

    U4(90),
    U5(90),
    U6(60),
    U7(60),
    U8(60),
    U9(75),
    U10(75),
    U11(90),
    U12(90),
    U13(90),
    U14(90),
    U15(90),
    U16(90),
    U17(90),
    U18(90),
    ADULT4HR(240),
    ADULT8HR(440)

    Long durationMinutes

    AgeGroup(Long durationMinutes) {
        this.durationMinutes = durationMinutes
    }

}