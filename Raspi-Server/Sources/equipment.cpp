#include "../Headers/equipment.h"

// 생성자 호출 직전에 값이 변경됨
bool Equipment::ap_power = false;

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

// static은 붙이지 않음
void Equipment::set_ap_power_state(bool state) {
    Equipment::ap_power = state;
}


bool Equipment::get_ap_power_state() {
    return Equipment::ap_power;
}
