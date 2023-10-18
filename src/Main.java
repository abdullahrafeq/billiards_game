import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JButton bEasy = new JButton("Easy");
		JButton bHard = new JButton("Hard");
		JPanel panel = new JPanel();
		JLabel l1 = new JLabel("");
		JLabel l2 = new JLabel("Choose level", SwingConstants.CENTER);
		JLabel l3 = new JLabel("");
		JLabel l4 = new JLabel("");
		JLabel l5 = new JLabel("");
		JLabel l6 = new JLabel("");
		JLabel l7 = new JLabel("");
		JLabel l8 = new JLabel("");
		JLabel l9 = new JLabel("");
		JLabel l10 = new JLabel("");
		JLabel l11 = new JLabel("");
		JLabel l12 = new JLabel("Billiards", SwingConstants.CENTER);
		JLabel l13 = new JLabel("");
		l2.setForeground(Color.white);
		l2.setFont(new Font("Helvetica Neue", Font.PLAIN, 30));
		l12.setForeground(Color.white);
		l12.setFont(new Font("Helvetica Neue", Font.PLAIN, 30));
		bEasy.setBackground(new Color(51, 204, 51));
		bHard.setBackground(new Color(51, 204, 51));
		bEasy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String level = "easy";
				GamePlay gamePlay = new GamePlay(level);
				gamePlay.setPreferredSize(new Dimension(800, 500));
				frame.add(gamePlay);
				frame.remove(panel);
			}
		});
		bHard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String level = "hard";
				GamePlay gamePlay = new GamePlay(level);
				gamePlay.setPreferredSize(new Dimension(800, 500));
				frame.add(gamePlay);
				frame.remove(panel);
			}
		});
		panel.setBackground(new Color(102, 153, 255));
		panel.setLayout(new GridLayout(5,3));
		l1.setLayout(new BorderLayout());
		panel.add(l1);
		panel.add(l2);
		panel.add(l3);
		panel.add(l4);
		panel.add(bEasy);
		panel.add(l5);
		panel.add(l6);
		panel.add(l7);
		panel.add(l8);
		panel.add(l9);
		panel.add(bHard);
		panel.add(l10);
		panel.add(l11);
		panel.add(l12);
		panel.add(l13);

		panel.setPreferredSize(new Dimension(800, 500));
		frame.setResizable(false);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
