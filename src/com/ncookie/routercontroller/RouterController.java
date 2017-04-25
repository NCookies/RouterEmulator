package com.ncookie.routercontroller;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by ryu on 17. 4. 19.
 */
public class RouterController {
    public JPanel mainPanel;

    /* PANEL */
    private JPanel apPanel;
    private JPanel dhcpPanel;
    private JPanel connectingPanel;
    private JPanel devicePanel;
    private JPanel blockingPanel;


    /* AP */
    private JTextField apSSID;
    private JTextField apPW;
    private JLabel apSSIDLabel;
    private JLabel apPWLabel;

    private JButton apSwitch;
    private JButton editAPBtn;


    /* DHCP */
    private JLabel leaseTimeLabel;
    private JTextField leaseTime;

    private JButton dhcpSwitch;
    private JButton editDHCPBtn;


    /* ADD DEVICE */
    private JLabel deviceNameLabel;
    private JLabel ipAddressLabel;
    private JLabel macAddressLabel;

    private JTextField deviceName;
    private JTextField ipAddress;
    private JTextField macAddress;

    private JButton addDeviceBtn;


    /* CONNECTING */
    private JLabel connectingState;
    private JTable connectingDevice;


    /* BLOCK */
    private JLabel blockingLabel;
    private JTextField blockingSite;
    private JButton addBlockingBtn;
    private JList blockList;
    private JScrollBar blockScroll;


    /* STATE */
    private JScrollBar showStateScroll;
    private JPanel showStatePanel;
    private JList showStateList;

    private JLabel buttonIcon;
    private JTextField routerName;
    private JButton btnEditRouterName;
    private JLabel routerNameLabel;
    private JPanel routerStatePanel;

    public RouterController() {
        buttonIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                System.out.println("Hello World");
            }
        });
    }
}
