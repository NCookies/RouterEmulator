package com.ncookie.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by ryu on 17. 4. 19.
 */
public class RouterController {
    public JPanel mainPanel;
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
}
