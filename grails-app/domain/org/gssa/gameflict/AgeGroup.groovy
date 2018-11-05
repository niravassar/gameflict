package org.gssa.gameflict;

enum AgeGroup {

    U4(90),
    U5(90),
    U6(90),
    U7(60),
    U8(60),
    U9(75),
    U10(75),
    U11(90),
    U12(90),
    U13(90),
    U14(120),
    U15(120),
    U16(120),
    U17(120),
    U18(120),
    ADULT3HR(180),
    ADULT6HR(360)

    Long durationMinutes

    AgeGroup(Long durationMinutes) {
        this.durationMinutes = durationMinutes
    }

}