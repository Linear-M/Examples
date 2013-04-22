package ms.aurora.rminer.gui;

import ms.aurora.rminer.RimmingtonMiner;
import ms.aurora.rminer.AbstractMiningStrategy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import static ms.aurora.rminer.RimmingtonMiner.State.CANCEL;
import static ms.aurora.rminer.RimmingtonMiner.State.IDLE;

public class AIOMinerGUI extends JFrame {
    private static final long serialVersionUID = 2601820298890550105L;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public AIOMinerGUI() {
        setTitle("Aurora AIO Miner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 642, 257);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(0, 0, 640, 85);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblAuroraAioMiner = new JLabel("Aurora AIO Miner");
        lblAuroraAioMiner.setBounds(6, 6, 628, 40);
        panel.add(lblAuroraAioMiner);
        lblAuroraAioMiner.setHorizontalAlignment(SwingConstants.CENTER);
        lblAuroraAioMiner.setFont(new Font("Kannada Sangam MN", Font.BOLD, 24));

        JLabel lblDevelopedAndMaintained = new JLabel("Developed and maintained by _override; bug reports to override@aurora.ms");
        lblDevelopedAndMaintained.setBounds(6, 47, 628, 16);
        panel.add(lblDevelopedAndMaintained);
        lblDevelopedAndMaintained.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblStrategy = new JLabel("Strategy:");
        lblStrategy.setBounds(36, 97, 108, 16);
        contentPane.add(lblStrategy);

        final JComboBox comboBox = new JComboBox();
        comboBox.setBounds(156, 93, 300, 27);
        contentPane.add(comboBox);

        JLabel lblBanking = new JLabel("On inventory full:");
        lblBanking.setBounds(36, 125, 147, 16);
        contentPane.add(lblBanking);

        final JRadioButton rdbtnBank = new JRadioButton("Bank");
        rdbtnBank.setSelected(true);
        rdbtnBank.setBounds(156, 121, 67, 23);
        contentPane.add(rdbtnBank);

        JRadioButton rdbtnDrop = new JRadioButton("Drop all");
        rdbtnDrop.setBounds(226, 121, 87, 23);
        contentPane.add(rdbtnDrop);

        ButtonGroup group = new ButtonGroup();
        group.add(rdbtnBank);
        group.add(rdbtnDrop);

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(561, 200, 75, 29);
        btnOk.setAction(new AbstractAction("OK") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RimmingtonMiner.banking = rdbtnBank.isSelected();
                RimmingtonMiner.strategy = (AbstractMiningStrategy) comboBox.getSelectedItem();
                RimmingtonMiner.state = IDLE;
                dispose();
            }
        });
        contentPane.add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(445, 200, 117, 29);
        btnCancel.setAction(new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RimmingtonMiner.state = CANCEL;
                dispose();
            }
        });
        contentPane.add(btnCancel);

        for(AbstractMiningStrategy strategy : RimmingtonMiner.getAvailableStrategies()) {
            comboBox.addItem(strategy);
        }
    }
}
