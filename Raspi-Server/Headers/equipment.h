#ifndef _EQUIPMENT_H
#define _EQUIPMENT_H

#include <iostream>
#include <vector>
#include <string>
#include <cstdlib>
#include <cerrno>


class Equipment {
private:
    // static 변수는 선언과 동시에 초기화를 할 수 없음
    static bool ap_power;
    static std::string ssid;
    static std::string password;

public:
    static void set_ap_power_state(bool state);
    static bool get_ap_power_state();

    static void set_ap_settings(std::string ssid, std::string password);
    static std::vector<std::string> get_ap_settings();
};

#endif
