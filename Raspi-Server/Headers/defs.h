#ifndef _DEFS_H
#define _DEFS_H

const int max_buff_size = 1024;

enum ResultCode {
	ERROR_SOMETHING = -1,
	NOT_FOUND = 0
};

enum OperationCode {
    SET_AP_POWER = 0,
    GET_AP_POWER = 1,
    SET_AP_SETTINGS = 2,
    GET_AP_SETTINGS = 3
};

#endif
