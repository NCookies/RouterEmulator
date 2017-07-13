#ifndef _EQUIPMENT_H
#define _EQUIPMENT_H

#include <iostream>
#include <cstdlib>
#include <cstring>
#include <cerrno>


class Equipment {
private:
    // static 변수는 선언과 동시에 초기화를 할 수 없음
    static bool ap_power;

public:
    static void set_ap_power_state(bool state);
    static bool get_ap_power_state();
};

#endif
