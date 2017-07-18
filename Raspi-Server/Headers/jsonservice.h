#ifndef _JSONSERVICE_H
#define _JSONSERVICE_H

#include <iostream>
#include <cstdlib>
#include <cstring>
#include <cerrno>

#include <string>
#include <vector>

#include "equipment.h"
#include "defs.h"
#include "json/json.h"

class JsonService {
private:
    std::string create(Json::Value body);

public:
    JsonService();
    ~JsonService();

    ssize_t parse(char *json, char *send_buff);
};

#endif
