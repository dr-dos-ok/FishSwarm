package com.fishswarm;

import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ControlPanel extends Frame implements ActionListener
{
	Aquarium a;
	Button b =new Button();

	int  step=1;
	ControlPanel(Aquarium m)
	{
		this.a =m;
		this.setLayout(null);

		this.add(b);
		b.setBounds(0,0,100,50);
		b.addActionListener(this);

		setBounds( m.getWidth(),0, 200,100);

				setResizable(false);

				setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{

	}
}