import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;


public class Main extends JPanel{
		
	private static Button[][] array;
	private int x = 0;
	private int y = 0;
	private static int check = 0;
	private static JFrame window;
	private static String[][] strings;
	private File file;
	private String[][] new_string;

    public Main(String[][] strings) {
        JPanel center_panel = new JPanel();
        JPanel bottom_panel = new JPanel();
        BFS bfs = new BFS("Solve! [by BFS]", strings);
        DFS dfs = new DFS("Solve! [by DFS]", strings);
        JButton reset = new JButton("reset");

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < 5; i++) {
                    for(int j = 0; j < 5; j++){
                        array[i][j].set_state(Integer.parseInt(strings[i][j]));
                    }
                }
            }
        });

        JButton add_file = new JButton("Open file...");
        add_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    System.out.println(file.getName());
                    try{
                        new_string = read_file(file);
                        update_buttons(new_string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

	    this.setLayout(new BorderLayout());
        this.add(center_panel, BorderLayout.CENTER);
        this.add(bottom_panel, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(400,400));
		this.setVisible(true);
		this.create_buttons();
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				center_panel.add(array[i][j].get_button());
				array[i][j].set_coordinates(i,j);
				array[i][j].set_state(Integer.parseInt(strings[i][j]));
			}
		}
		bottom_panel.add(bfs.getButton_bfs());
        bottom_panel.add(dfs.getButton_dfs());
        bottom_panel.add(reset);
        bottom_panel.add(add_file);
	}

	public void update_buttons (String[][] strings) {
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                array[i][j].set_state(Integer.parseInt(strings[i][j]));
                this.strings[i][j] = strings[i][j];
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
	
	public static String[][] read_file(File file) throws Exception{
		String[][] strings = new String[5][5];
		int index = 0;
		
		String line = null;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try{
			is = new FileInputStream(file);
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
		strings = new String[5][5];
		for(int i = 0; i < 5; i++){
		    for(int j = 0; j < 5; j++){
		        strings[i][j] = "0";
            }
        }
		window = new JFrame("Lights Out");
		window.setPreferredSize(new Dimension(500,500));
		window.add(new Main(strings));
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
