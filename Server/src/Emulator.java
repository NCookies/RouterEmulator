import com.ncookie.origin.Equipment;
import com.ncookie.routeremulator.APManager;
import com.ncookie.routeremulator.DHCPServer;
import com.ncookie.simulator.DeviceManager;

/**
 * Created by ryu on 17. 5. 21.
 */
public class Emulator extends Equipment {
    private APManager apManager;
    private DHCPServer dhcpServer;
    private DeviceManager deviceManager;

    private final int cabledLimit = 4;
    private final int wiredLimit = 8;

    public Emulator() {
        apManager = new APManager(false, true, "G0170HS",
                "123456", 100);

        dhcpServer = new DHCPServer(false,1, 254,8000 );

        deviceManager = new DeviceManager();
    }
}
