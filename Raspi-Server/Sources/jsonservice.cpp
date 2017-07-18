#include "../Headers/jsonservice.h"

JsonService::JsonService() {

}

JsonService::~JsonService() {

}

ssize_t JsonService::parse(char *json, char *send_buff) {
    Json::Reader reader;
    Json::Value root;  // 여기에 json을 파싱하기 위한 값이 저장됨
    std::string str(json);
    bool parsingRet = reader.parse(str, root);
    // char to string
    if (!parsingRet) {
		std::cout << "Failed to parse Json : " << reader.getFormattedErrorMessages();
		return -1;
	}

    // body 안에 들어갈 result와 subValues 값
    Json::Value body;
    Json::Value sub_values = root["body"]["subValues"];
    bool result;
    
    std::string operation = root["body"]["operation"].asString();
    std::string message;
    
    ssize_t str_size;

    if (operation == "SET_AP_POWER") {
        // AP 전원 설정
        std::string power = sub_values[0].asString();
        Equipment::set_ap_power_state(
            power == "true" ? true : false);

        body["operation"] = operation;
        body["result"] = true;  // 에러가 생겼을 때 처리해야 함
    } else if (operation == "GET_AP_POWER") {
        // AP 전원 상태 전송
        bool power = Equipment::get_ap_power_state();
        result = true;

        body["operation"] = operation;
        body["subValues"].append(power);
        body["result"] = result;
    } else if (operation == "SET_AP_SETTINGS") {
        // AP 설정(SSID, PASSWORD) 변경
        std::string ssid = sub_values[0].asString();
        std::string password = sub_values[1].asString();

        Equipment::set_ap_settings(ssid, password);

        body["operation"] = operation;
        body["result"] = true;
    } else if (operation == "GET_AP_SETTINGS") {
        // AP 설정(SSID, PASSWORD) 전송
        char (*settings)[100];
        settings = Equipment::get_ap_settings();

        std::cout << settings[0] << std::endl;
        std::cout << settings[1] << std::endl;

        result = true;

        body["operation"] = operation;
        // body["subValues"].append();
        // body["subValues"].append();
        body["result"] = result;
    }

    // json 메시지 생성
    message = this->create(body);
    message += '\n';

    // send_buff 에 메시지 string 복사
    message.copy(send_buff, message.length());
    send_buff[max_buff_size - 1] = '\0';

    return message.size();
}

std::string JsonService::create(Json::Value body) {
    Json::Value root;
    Json::Value head;
    Json::Value seq;

    root["header"]["src"] = "127.0.0.1";
    root["header"]["dest"] = "127.0.0.1";
    root["header"]["type"] = "res";

    root["body"] = body;

    root["seq"]["cur"] = "0";
    root["seq"]["end"] = "0";

    Json::FastWriter writer;
    return writer.write(root);
}
