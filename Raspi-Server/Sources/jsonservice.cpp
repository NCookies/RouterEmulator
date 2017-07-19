#include "../Headers/jsonservice.h"


std::map<std::string, int> JsonService::operation_map;

JsonService::JsonService() {

}

JsonService::~JsonService() {

}

void JsonService::initialize() {
    JsonService::operation_map["SET_AP_POWER"] = SET_AP_POWER;
    JsonService::operation_map["GET_AP_POWER"] = GET_AP_POWER;
    JsonService::operation_map["SET_AP_SETTINGS"] = SET_AP_SETTINGS;
    JsonService::operation_map["GET_AP_SETTINGS"] = GET_AP_SETTINGS;
}

bool JsonService::parse(char *json, char *res_buff, char *rep_buff, size_t *res_buff_size, size_t *rep_buff_size) {
    Json::Reader reader;
    std::string str(json);

    // json을 파싱하기 위한 값을 저장
    Json::Value root;
    Json::Value sub_values = root["body"]["subValues"];

    bool parsingRet = reader.parse(str, root);

    // char to string
    if (!parsingRet) {
		std::cout << "Failed to parse Json : " << reader.getFormattedErrorMessages();
		return 0;
	}

    // body 안에 들어갈 result와 subValues 값
    Json::Value res_body;
    Json::Value rep_body;

    bool result;
    bool is_report = false;
    std::string operation = root["body"]["operation"].asString();
    std::string res_message;
    std::string rep_message;

    switch (JsonService::operation_map[operation]) {
        case SET_AP_POWER: {
            // AP 전원 설정
            bool power = (sub_values[0].asString() == "true");

            Equipment::set_ap_power_state(power);

            res_body["operation"] = operation;
            res_body["result"] = true;  // 에러가 생겼을 때 처리해야 함

            rep_body["operation"] = operation;
            rep_body["subValues"].append(power);

            is_report = true;

            break;
        }

        case GET_AP_POWER: {
            // AP 전원 상태 전송
            bool power = Equipment::get_ap_power_state();
            result = true;

            res_body["operation"] = operation;
            res_body["subValues"].append(power);
            res_body["result"] = result;

            rep_body["operation"] = operation;

            break;
        }


        case SET_AP_SETTINGS: {
            // AP 설정(SSID, PASSWORD) 변경
            std::string ssid = sub_values[0].asString();
            std::string password = sub_values[1].asString();

            Equipment::set_ap_settings(ssid, password);

            res_body["operation"] = operation;
            res_body["result"] = true;

            rep_body["operation"] = operation;
            rep_body["subValues"].append(ssid);
            rep_body["subValues"].append(password);

            is_report = true;

            break;
        }

        case GET_AP_SETTINGS: {
            // AP 설정(SSID, PASSWORD) 전송
            std::vector<std::string> settings = Equipment::get_ap_settings();

            result = true;

            res_body["operation"] = operation;
            res_body["subValues"].append(settings[0]);
            res_body["subValues"].append(settings[1]);
            res_body["result"] = result;

            rep_body["operation"] = operation;

            break;
        }

        default: {
            res_body["result"] = false;
            break;
        }
    }
    // json 메시지 생성

    res_message = this->create(res_body, "res");
    res_message += '\n';

    rep_message = this->create(rep_body, "rep");
    rep_message += '\n';

    // res_buff 에 메시지 string 복사
    res_message.copy(res_buff, res_message.length());
    res_buff[max_buff_size - 1] = '\0';
    *res_buff_size = res_message.size();

    rep_message.copy(rep_buff, rep_message.length());
    rep_buff[max_buff_size - 1] = '\0';
    *rep_buff_size = rep_message.size();

//    return res_message.size();
    return is_report;
}

std::string JsonService::create(Json::Value body, std::string flag) {
    Json::Value root;
    Json::Value head;
    Json::Value seq;

    root["header"]["src"] = "127.0.0.1";
    root["header"]["dest"] = "127.0.0.1";
    root["header"]["type"] = flag;
    root["header"]["result"] = 1;

    root["body"] = body;

    root["seq"]["cur"] = "0";
    root["seq"]["end"] = "0";

    Json::FastWriter writer;
    return writer.write(root);
}
