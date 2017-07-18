#include "../Headers/equipment.h"

// 생성자 호출 직전에 값이 변경됨
bool Equipment::ap_power = false;
std::string Equipment::ssid = "DEFAULT";
std::string Equipment::password = "admin1234";


// static은 붙이지 않음
void Equipment::set_ap_power_state(bool state) {
    Equipment::ap_power = state;
    std::cout << Equipment::ssid;
}

bool Equipment::get_ap_power_state() {
    return Equipment::ap_power;
}

void Equipment::set_ap_settings(std::string ssid, std::string password) {
    Equipment::ssid = ssid;
    Equipment::password = password;
}

std::vector<std::string> Equipment::get_ap_settings() {
    std::vector<std::string> v_settings(2);
    v_settings[0] = Equipment::ssid;
    v_settings[1] = Equipment::password;

    return v_settings;
}

// 배열 포인터
//char (*get_ap_settings())[100]

//static char settings[][100] = {"hello", "world"};
//return settings;

/*
헤더가 겹칠 때
참조 X
문제 원인 : makefile에서 링크를 해주지 않았음 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ

Objects/jsonservice.o: In function `JsonService::~JsonService()':
jsonservice.cpp:(.text+0x2c): undefined reference to `Equipment::~Equipment()'
Objects/jsonservice.o: In function `JsonService::parse(char*, long, char*)':
jsonservice.cpp:(.text+0x59e): undefined reference to `Equipment::getApPowerState()'
Objects/jsonservice.o: In function `JsonService::JsonService()':
jsonservice.cpp:(.text+0xc21): undefined reference to `Equipment::Equipment()'
jsonservice.cpp:(.text+0xc8b): undefined reference to `Equipment::~Equipment()'
collect2: error: ld returned 1 exit status
makefile:9: recipe for target 'server' failed
make: *** [server] Error 1
*/

/*
왜 오류가 발생하는지 모르곘음

Sources/equipment.cpp: In function ‘char (* get_ap_settings())[100]’:
Sources/equipment.cpp:5:13: error: ‘std::__cxx11::string Equipment::ssid’ is private
 std::string Equipment::ssid = "DEFAULT";
             ^
Sources/equipment.cpp:39:40: error: within this context
     char settings[][100] = {Equipment::ssid, Equipment::password};
*/