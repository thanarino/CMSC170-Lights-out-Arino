import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Main extends JPanel{
		
	private static Button[][] array;
	private int x = 0;
	private int y = 0;
	private static int check = 0;
	private static JFrame window;
	private static String[][] strings;
	
	public Main() {
		this.setPreferredSize(new Dimension(400,400));
		this.setVisible(true);
		this.create_buttons();
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				this.add(array[i][j].get_button());
				array[i][j].set_coordinates(i,j);
				array[i][j].set_state(Integer.parseInt(strings[i][j]));
			}
		}
	}
	
	public static void checkwin(){
		check = 0;
		for(Button[] button_array : array){
			for (Button button: button_array){
				if(button.get_light() == 0){
					check++;
				}
			}
		}
		if(check == 25) {
			JOptionPane.showMessageDialog(window, "You won!");
			for(Button[] button_array : array){
				for (Button button: button_array){
					button.get_button().setEnabled(false);
				}
			}
		}
	};
	
	public static String[][] read_file() throws Exception{
		String[][] strings = new String[5][5];
		int index = 0;
		
		String line = null;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try{
			is = new FileInputStream("lightsout.in");
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			while((line = br.readLine()) != null){
				strings[index] = line.split(" ");
				index++;
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if(is!=null)
	            is.close();
	         if(isr!=null)
	            isr.close();
	         if(br!=null)
	            br.close();
		}
		
		return strings;
	}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		strings = read_file();
		window = new JFrame("Lights Out");
		window.setPreferredSize(new Dimension(500,500));
		window.add(new Main());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		window.pack();
	}
	
	public static Button[][] get_array () {
		return array;
	}
	
	public Button[][] create_buttons(){
		array = new Button[5][5];
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				Button button = new Button();
				array[i][j] = button;
			}
		}
		return array;
	}

}
