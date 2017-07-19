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
    std::string create(Json::Value body, std::string flag);
    static std::map<std::string, int > operation_map;

public:
    JsonService();
    ~JsonService();

    static void initialize();
    bool parse(char *json, char *res_buff, char *rep_buff, size_t *res_buff_size, size_t *rep_buff_size);
};

#endif
