package com.ncookie.routercontroller;

import com.ncookie.routeremulator.Router;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

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
    private JLabel countConnecting;
    private JTable connectingDevice;


    /* BLOCK */
    private JLabel blockingLabel;
    private JTextField blockingSite;
    private JButton addBlockingBtn;
    private JList blockList;
    private JScrollPane blockScroll;

    private DefaultListModel blockUrlModel;


    /* STATE */
    private JScrollPane showStateScroll;
    private JPanel showStatePanel;
    private JList showStateList;

    private DefaultListModel stateModel; // model for JList


    /* ROUTER */
    private JLabel buttonIcon;
    private JTextField routerName;
    private JButton btnEditRouterName;
    private JLabel routerNameLabel;
    private JPanel routerStatePanel;


    /* ICON RESOURCE */
    private ImageIcon onIcon;   // router 전원 아이콘
    private ImageIcon offIcon;


    /* EXIT */
    private JButton exitButton;
    private JCheckBox publicAP;


    public RouterController(Router router) {
        $$$setupUI$$$();

        routerName.setText(router.getRouterName()); // router 이름 설정

        // 설정값 default 설정
        apSSID.setText(router.getSSIDName());
        apPW.setText(router.getPassword());
        leaseTime.setText(String.valueOf(router.getDhcpLeaseTime()));

        // switch 배경 색 설정
        apSwitch.setBackground(Color.red);
        apSwitch.setForeground(Color.white);
        dhcpSwitch.setBackground(Color.red);
        dhcpSwitch.setForeground(Color.white);

        setEnabledButton(false);    // router의 전원을 켜기 전에는 비활성화
        pushInfoMessage("버튼을 활성화하기 위해서는 공유기의 전원을 켜야합니다");

        /* router 전원 on/off */
        buttonIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    onIcon = new ImageIcon(ImageIO.read(new File("./res/toggle-on.png")));
                    offIcon = new ImageIcon(ImageIO.read(new File("./res/toggle-off.png")));

                } catch (IOException err) {
                    System.out.println(System.getProperty("user.dir"));
                    err.printStackTrace();
                }

                boolean powerState = router.getPowerState();    // 현재 router 의 전원 상태

                if (!powerState) {  // router 의 전원이 꺼져 있다면
                    buttonIcon.setIcon(onIcon);
                    pushInfoMessage("공유기의 전원을 켭니다");
                    setEnabledButton(true);
                } else {    // 켜져 있으면
                    buttonIcon.setIcon(offIcon);
                    pushInfoMessage("공유기의 전원을 끕니다");
                    setEnabledButton(false);
                }

                router.setPowerState(!powerState);  // router 의 전원 상태 변경
            }
        });

        /* router 이름 변경 */
        btnEditRouterName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String newName = routerName.getText();

                router.setRouterName(newName);
                pushInfoMessage("공유기의 이름이 [" + newName + "]으로 변경되었습니다");
            }
        });

        /* ap 전원 on/off */
        apSwitch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (!checkRouterOn(router.getPowerState())) return;
                // router 의 전원이 꺼져있으면 버튼이 동작하지 않게 함

                boolean apPowerState = router.getApPowerState();

                if (!apPowerState) {
                    apSwitch.setText("끄기");
                    apSwitch.setBackground(Color.green);

//                    Timer t = new Timer();

                    pushInfoMessage("AP의 전원을 켭니다");
                } else {
                    apSwitch.setText("켜기");
                    apSwitch.setBackground(Color.red);
                    pushInfoMessage("AP의 전원을 끕니다");
                }

                router.setApPowerState(!apPowerState);
            }
        });

        /* ap 설정 변경 */
        editAPBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (!checkRouterOn(router.getPowerState())) return;
                // router 의 전원이 꺼져있으면 버튼이 동작하지 않게 함

                String newSSID = apSSID.getText();
                String newPassword = apPW.getText();

                if (newSSID.equals(router.getSSIDName())
                        && newPassword.equals(router.getPassword())) {
                    pushWarnMessage("기존의 값과 같습니다. 다시 입력해주세요.");
                    return;
                }

                router.setSSIDName(newSSID);
                router.setPassword(newPassword);

                pushInfoMessage("SSID와 비밀번호가 변경되었습니다");
            }
        });

        /* DHCP 전원 on/off */
        dhcpSwitch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (!checkRouterOn(router.getPowerState())) return;
                // router 의 전원이 꺼져있으면 버튼이 동작하지 않게 함

                boolean dhcpPowerState = router.getDhcpPowerState();

                if (!dhcpPowerState) {
                    dhcpSwitch.setText("끄기");
                    dhcpSwitch.setBackground(Color.green);
                    pushInfoMessage("DHCP 서버를 켭니다");
                } else {
                    dhcpSwitch.setText("켜기");
                    dhcpSwitch.setBackground(Color.red);
                    pushInfoMessage("DHCP 서버를 끕니다");
                }

                router.setDhcpPowerState(!dhcpPowerState);
            }
        });

        /* DHCP IP 대여 시간 */
        editDHCPBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (!checkRouterOn(router.getPowerState())) return;
                // router 의 전원이 꺼져있으면 버튼이 동작하지 않게 함


                int lease = 0;  // IP 대여시간. 시간이 모두 지나면 IP 회수
                // 자바에서는 멤버 변수를 제외한 지역 변수는 초기화를 해주어야 함
                // 해주지 않으면, Error:(238, 21) java: variable lease might not have been initialized
                // 와 같은 에러 발생

                try {
                    lease = Integer.parseInt(leaseTime.getText());
                } catch (NumberFormatException err) {   // 숫자가 아닌 값이 입력되었을 때
                    err.printStackTrace();
                    pushErrorMessage("정수를 입력해주세요");

                    return;
                }

                if (lease == router.getDhcpLeaseTime()) {
                    pushWarnMessage("기존의 값과 같습니다. 다시 입력해주세요.");
                    return;
                }

                router.setDhcpLeaseTime(lease);
                pushInfoMessage("DHCP 서버의 IP 대여시간이 [" + lease + "초]로 변경되었습니다");
            }
        });

        /* 디바이스 추가 */
        addDeviceBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (!checkRouterOn(router.getPowerState())) return;
                // router 의 전원이 꺼져있으면 버튼이 동작하지 않게 함

                String newDeviceName = deviceName.getText();
                String newIpAddresss = ipAddress.getText();
                String newMacAddress = macAddress.getText();

                router.addDevice(newDeviceName, newIpAddresss, newMacAddress);
            }
        });

        // 프로그램 종료
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                router.setApPowerState(false);

                System.exit(0);
            }
        });

        // AP 공개 / 비공개 설정
        publicAP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (!checkRouterOn(router.getPowerState())) return;
                // router 의 전원이 꺼져있으면 버튼이 동작하지 않게 함

                if (publicAP.isSelected()) {    // AP를 비공개로 설정하기 원하면
                    router.setPublicAP(false);
                    pushInfoMessage("AP를 비공개로 설정합니다");
                } else {
                    router.setPublicAP(true);
                    pushInfoMessage("AP를 공개로 설정합니다");
                }

            }
        });

        /* 차단 사이트 추가 */
        addBlockingBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String url = blockingSite.getText();
                blockUrlModel.addElement(url);
                blockingSite.setText("");

                pushInfoMessage("차단할 사이트를 추가하였습니다");

                router.addBlockSite(url);
            }
        });
    }

    /* router 의 전원에 따라 버튼들의 활성/비활성 */
    public void setEnabledButton(boolean state) {
        apSwitch.setEnabled(state);
        publicAP.setEnabled(state);
        editAPBtn.setEnabled(state);

        dhcpSwitch.setEnabled(state);
        editDHCPBtn.setEnabled(state);

        addDeviceBtn.setEnabled(state);

        addBlockingBtn.setEnabled(state);
    }

    private boolean checkRouterOn(boolean state) {
        if (!state) {
            pushErrorMessage("기능을 사용하기 위해서는 먼저 공유기의 전원을 켜주세요. 버튼은 우측 하단에 있습니다.");
            return false;
        }

        return true;
    }

    private void pushInfoMessage(String msg) {
        stateModel.addElement("[INFO] " + msg);
    }

    private void pushWarnMessage(String msg) {
        stateModel.addElement("[WARN] " + msg);
    }

    private void pushErrorMessage(String msg) {
        stateModel.addElement("[ERROR] " + msg);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        showStateList = new JList(new DefaultListModel());
        stateModel = (DefaultListModel) showStateList.getModel();

        blockList = new JList(new DefaultListModel());
        blockUrlModel = (DefaultListModel) blockList.getModel();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        apPanel = new JPanel();
        apPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(apPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        apPanel.setBorder(BorderFactory.createTitledBorder("AP"));
        apSSIDLabel = new JLabel();
        apSSIDLabel.setText("네트워크 이름 : ");
        apPanel.add(apSSIDLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        apPWLabel = new JLabel();
        apPWLabel.setText("네트워크 암호 : ");
        apPanel.add(apPWLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        apSSID = new JTextField();
        apPanel.add(apSSID, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        apPW = new JTextField();
        apPW.setText("");
        apPanel.add(apPW, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        editAPBtn = new JButton();
        editAPBtn.setText("편집");
        apPanel.add(editAPBtn, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        apSwitch = new JButton();
        apSwitch.setText("켜기");
        apPanel.add(apSwitch, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        publicAP = new JCheckBox();
        publicAP.setText("비공개");
        apPanel.add(publicAP, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label1 = new JLabel();
        label1.setText("작동 시간 :");
        apPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        apPanel.add(textField1, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        dhcpPanel = new JPanel();
        dhcpPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(dhcpPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        dhcpPanel.setBorder(BorderFactory.createTitledBorder("DHCP"));
        leaseTimeLabel = new JLabel();
        leaseTimeLabel.setText("대여시간 :");
        dhcpPanel.add(leaseTimeLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        leaseTime = new JTextField();
        dhcpPanel.add(leaseTime, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        editDHCPBtn = new JButton();
        editDHCPBtn.setText("편집");
        dhcpPanel.add(editDHCPBtn, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dhcpSwitch = new JButton();
        dhcpSwitch.setText("켜기");
        dhcpPanel.add(dhcpSwitch, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        devicePanel = new JPanel();
        devicePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(devicePanel, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        devicePanel.setBorder(BorderFactory.createTitledBorder("디바이스 추가"));
        deviceNameLabel = new JLabel();
        deviceNameLabel.setText("디바이스 이름");
        devicePanel.add(deviceNameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deviceName = new JTextField();
        devicePanel.add(deviceName, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ipAddressLabel = new JLabel();
        ipAddressLabel.setText("IP 주소");
        devicePanel.add(ipAddressLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ipAddress = new JTextField();
        devicePanel.add(ipAddress, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        macAddressLabel = new JLabel();
        macAddressLabel.setText("MAC 주소");
        devicePanel.add(macAddressLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        macAddress = new JTextField();
        devicePanel.add(macAddress, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addDeviceBtn = new JButton();
        addDeviceBtn.setText("추가");
        devicePanel.add(addDeviceBtn, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        connectingPanel = new JPanel();
        connectingPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(connectingPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        connectingPanel.setBorder(BorderFactory.createTitledBorder("연결된 장치"));
        connectingState = new JLabel();
        connectingState.setText("/ 4");
        connectingPanel.add(connectingState, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        connectingPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        connectingDevice = new JTable();
        connectingPanel.add(connectingDevice, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        countConnecting = new JLabel();
        countConnecting.setText("");
        connectingPanel.add(countConnecting, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        blockingPanel = new JPanel();
        blockingPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(blockingPanel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        blockingPanel.setBorder(BorderFactory.createTitledBorder("인터넷 사용제한"));
        blockingLabel = new JLabel();
        blockingLabel.setText("차단할 사이트");
        blockingPanel.add(blockingLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        blockingSite = new JTextField();
        blockingPanel.add(blockingSite, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addBlockingBtn = new JButton();
        addBlockingBtn.setText("추가");
        blockingPanel.add(addBlockingBtn, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        blockScroll = new JScrollPane();
        blockingPanel.add(blockScroll, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 1, false));
        blockScroll.setViewportView(blockList);
        showStatePanel = new JPanel();
        showStatePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(showStatePanel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        showStateScroll = new JScrollPane();
        showStatePanel.add(showStateScroll, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        showStateList.setFocusCycleRoot(false);
        showStateScroll.setViewportView(showStateList);
        routerStatePanel = new JPanel();
        routerStatePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(routerStatePanel, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonIcon = new JLabel();
        buttonIcon.setFocusCycleRoot(false);
        buttonIcon.setIcon(new ImageIcon(getClass().getResource("/com/ncookie/routercontroller/toggle-off.png")));
        buttonIcon.setInheritsPopupMenu(true);
        buttonIcon.setText("");
        routerStatePanel.add(buttonIcon, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        routerNameLabel = new JLabel();
        routerNameLabel.setText("공유기 이름 : ");
        routerStatePanel.add(routerNameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        routerName = new JTextField();
        routerName.setText("");
        routerStatePanel.add(routerName, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btnEditRouterName = new JButton();
        btnEditRouterName.setText("변경");
        routerStatePanel.add(btnEditRouterName, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("종료");
        routerStatePanel.add(exitButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
