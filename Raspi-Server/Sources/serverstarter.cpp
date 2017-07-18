#include "../Headers/serverstarter.h"

ServerStarter::ServerStarter() {

}

ServerStarter::~ServerStarter() {

}


// 소켓 서버 설정
int ServerStarter::initialize(int port) {
    std::cout << "Open Server..." << std::endl;
    if((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == -1){
		std::cout << "Cannot Create Socket..." << std::endl;
		return 0;
	}

    memset(&server_addr, 0x00, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
	server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	server_addr.sin_port = htons(port);

    if(bind(server_fd, (struct sockaddr *)&server_addr, sizeof(server_addr))){
		std::cout << "Cannot Bind Server Info..." << std::endl;
		return 0;
	}

    if (listen(server_fd, 5) < 0) {
		std::cout << "Cannot listen request" << std::endl;
        return 0;
	}

    FD_ZERO(&client_fds);
    FD_SET(server_fd, &client_fds);
    
    max_fd = server_fd;

    std::cout << "Initialize Connection Service..." << std::endl;
    connection_service.initialize(&client_fds, &server_fd, &max_fd);
    event_service.initialize(&client_fds, &server_fd, &max_fd);

    JsonService::initialize();
}

void ServerStarter::start() {
    connection_service.run();
    event_service.run();
}

void ServerStarter::stop() {
    std::cout << "Clean Up Services... Wait a sec" << std::endl;

	std::cout << "Stop ConnectionService";
	connection_service.stop();
	std::cout << "...................OK" << std::endl << "Stop EventService";
	event_service.stop();
	std::cout << "........................OK" << std::endl;
}