#include "../Headers/jsonservice.h"

JsonService::JsonService() {

}

JsonService::~JsonService() {

}

ssize_t JsonService::parse(char *json, ssize_t size, char *send_buff) {
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
    bool result;
    
    std::string operation = root["body"]["operation"].asString();
    std::string sub_values;
    std::string message;
    
    ssize_t str_size;

    if (operation == "SET_AP_POWER") {
        // AP 전원 설정
        sub_values = root["body"]["subValues"][0].asString();
        Equipment::set_ap_power_state(
            sub_values == "true" ? true : false);

        body["result"] = true;
    } else if (operation == "GET_AP_POWER") {
        // AP 전원 상태 전송
        bool power = Equipment::get_ap_power_state();

        if (power) {
            result = true;
        } else {
            result = false;
        }

        body["operation"] = operation;
        body["subValues"].append(power);
        body["result"] = true;
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

    root["seq"]["cur"] = "0";
    root["seq"]["end"] = "0";

    root["body"] = body;

    Json::FastWriter writer;
    return writer.write(root);
}
