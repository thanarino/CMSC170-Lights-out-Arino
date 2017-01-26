import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

public class Button extends AbstractAction{
	
	private JButton button;
	private int light;
	private Button[][] array;
	private int x;
	private int y;
	private int check = 0;
	
	public Button() {
		button = new JButton();
		button.setPreferredSize(new Dimension(80,80));
		light = 1;
		button.setBackground(Color.WHITE);
		button.setAction(this);
		array = Main.get_array();
		x = 0;
		y = 0;
	}
	
	public JButton get_button(){
		return button;
	}
	
	public int get_light(){
		return light;
	}
	
	public void set_coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void toggle(){
		if(light == 1) {
			light = 0;
			button.setBackground(Color.BLACK);
		} else {
			light = 1;
			button.setBackground(Color.WHITE);
		}
	}
	
	public void set_state(int x){
		if(x == 0) {
			light = 0;
			button.setBackground(Color.BLACK);
		} else {
			light = 1;
			button.setBackground(Color.WHITE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.toggle();
		if(this.x+1 < 5){
			array[x+1][y].toggle();
		}
		if(this.x-1 > -1){
			array[x-1][y].toggle();
		}
		if(this.y-1 > -1){
			array[x][y-1].toggle();
		}
		if(this.y+1 < 5){
			array[x][y+1].toggle();
		}
		Main.checkwin();
	}

}
