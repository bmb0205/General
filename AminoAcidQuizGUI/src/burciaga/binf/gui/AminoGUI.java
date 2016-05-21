package burciaga.binf.gui;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import burciaga.binf.gui.AminoQuiz;

/*
 * !!! Come back to this and structure it better overall. Months later and I see improvements
 * 
 * AminoGUI class draws and renders GUI for a biological quiz.
 * The user can choose a time limit and begin the quiz as they wish.
 * The question will be in the form of a single letter amino acid abbreviation.
 * User must answer in the form of the full amino acid name.
 * The quiz will keep track of correct answers, and will end upon an incorrect answer or time out.
 */
public class AminoGUI extends JPanel {

	private static final long serialVersionUID = -234242342534234524L;	
	private static JPanel bottomPanel = new JPanel(new BorderLayout());
	private static JFrame frame = new JFrame("Super Awesome Amino Acid Quiz!!!");
	private static ImageIcon bgImage = new ImageIcon("dna.jpg");
	private static ImageIcon dnaIcon = new ImageIcon("dnaIcon.png");
	private static JTextField questionField = new JTextField("Are you ready?");
	private static JTextField inputField = new JTextField("Enter one letter answer here...");
	private static int count = 0;
	private static Color panelColor = new Color(0, 100, 240, 0x22);
	private static Color inputfieldColor = new Color(250 , 250, 250, 0x90);
	private static Font font2 = new Font("Serif", Font.BOLD, 24);
	private static GridBagLayout gbl = new GridBagLayout();
	private static GridBagConstraints gbc = new GridBagConstraints();
	private static final String[] times = {"Select your timelimit! (in seconds)", 
										   "10", "20", "30", "40", "50", "60"};

	public static Timer timer;
	public static AminoQuiz quiz = new AminoQuiz();
	public static JComboBox<String> timeBox = new JComboBox<String>(times);
	public static HashMap<String, Integer> map = new HashMap<String, Integer>();
	
	public void Color(int r, int g, int b, float a){}

	// Sets background image, overrides paintComponent()
	private static JTextField dnaField = new JTextField() {
		private static final long serialVersionUID = -234242342534234524L;
		Image img = bgImage.getImage(); {
			setOpaque(false);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			g.setFont(new Font("Serif", Font.BOLD, 24 ));
			g.drawImage(img, 10, 5, this);
			super.paintComponent(g);
		}
	};
	
	/*
	 *  Creates new timer using user selected seconds.
	 *  Calls CancelSummary() when timer is done and ends quiz.
	 */
	public AminoGUI(int numSeconds) {
		timer = new Timer();
		timer.schedule(new CancelSummary(), numSeconds *1000);
	}
	
	class CancelSummary extends TimerTask {
		@Override
		public void run() {
			timer.cancel();
			summary(count, timeBox, map);
			count = 0;
		}
	}
	
	/*
	 *  Creates new JButtons, sets size, color and restraints.
	 *  Adds new ActionListener() to each to give buttons functions.
	 *  Overrides actionPerformed().
	 *  Adds new functional buttons to bottom panel.
	 */
	protected static void makeButton(String buttonName, GridBagLayout gbl, GridBagConstraints gbc, 
	                                 final JComboBox<String> timeBox) {
		JButton newButton = new JButton(buttonName);
		newButton.setPreferredSize(new Dimension(250, 40));
		newButton.setForeground(Color.BLACK);
		Font font = new Font("Serif", Font.BOLD, 18);
		newButton.setFont(font);
		gbl.setConstraints(newButton, gbc);
		
		
		// !!! change this, pass in actionlistener to makeButton to avoid using 'if buttonName =='
		if (buttonName == "Start the quiz!") {
			newButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Object seconds = timeBox.getSelectedItem();
					int numSeconds = Integer.parseInt(seconds.toString());
					new AminoGUI(numSeconds);
 					String question = quiz.newQuestion();
 					questionField.setText(question); 
				}
			});
		}
		else if (buttonName == "Want to give up?") {
			newButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					timer.cancel();
					inputField.setText("Enter one letter answer here...");
					questionField.setText("Are you ready?");
					summary(count, timeBox, map);
					count = 0;
				}
			});
		}
		bottomPanel.add(newButton);
	}
	
	/*
	 *  Creates summary report pop up that appears upon time ending or incorrect answer.
	 *  Shows amino acid name and number of correct answer, along with AA answered incorrectly.
	 *  Includes two option buttons, to give up (closes quiz) or try again (refreshed and restarts quiz).
	 */
	public static void summary(int count, JComboBox<String> timeBox, HashMap<String, Integer> map) {
		Object[] buttons = {"Try again!", "Give up?"};
		StringBuilder builder = new StringBuilder();
		for ( Map.Entry<String, Integer> entry : map.entrySet()) {
			builder.append(entry.getKey() + " : " + entry.getValue() + "/" + entry.getValue());
			builder.append("\n");
		}
		String tempFail = questionField.getText();
		String failedOn = tempFail.substring(0, tempFail.length() - 4);
		String message = ("You got " + count + " correct!\n\t" + "Here is the breakdown: \n\n" + builder + 
				          "You failed on: " + failedOn + "\n\nKeep studying!" );
		int num = JOptionPane.showOptionDialog(null, message, "Game over! Score Summary", JOptionPane.YES_NO_OPTION, 
				  JOptionPane.QUESTION_MESSAGE, dnaIcon, buttons, buttons[0]);
		
		if (num == JOptionPane.YES_OPTION) {	
			map.clear();
			inputField.setText("Enter one letter answer here...");
			questionField.setText("Are you ready?");
		}
		else {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
		frame.setVisible(true);
	}
	
	/*
	 * Upon 'enter' or 'click', checks if answer is true or false.
	 * If true, increments correct answer count for specific AA, creates next question, empties answer field.
	 * If false, cancels timer, shows summary score, resets count.
	 */
	private static JTextField doTheThing(Font font2, Color inputfieldColor, final JComboBox<String> timeBox) {
		inputField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputField.setText("");
			}
		});
		inputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String answer = inputField.getText();
				String temp = questionField.getText();
				String question = temp.substring(0, temp.length() -4);
				Boolean check = quiz.checkAnswer(question, answer);
				if (check == true) {
					quiz.setCount(question, map);
					count++;
					String nextQuestion = quiz.newQuestion();
 					questionField.setText(nextQuestion);
 					inputField.setText("");
				}
				else {
					timer.cancel();
					summary(count, timeBox, map);
					count = 0;
				}
			}
		});
		inputField.setBackground(inputfieldColor);
		inputField.setFont(font2);
		return inputField;
	}
	
	/* Called from main(), returns JSplitPane after setting grid bag constraints, and
	 * adding everything to GUI by calling other methods to create buttons, panels etc.
	 */
	private static JSplitPane makeGUI() {
		bottomPanel.setLayout(gbl);
		
		// add timelist drop down
		timeBox.setPreferredSize(new Dimension(298, 40));
		timeBox.setForeground(Color.BLACK);
		gbc.gridy = 3;
		gbc.gridx = 0;

		gbl.setConstraints(timeBox, gbc);
		bottomPanel.add(timeBox);
		
		// add start quiz button
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.gridy = 3;
		AminoGUI.makeButton("Start the quiz!", gbl, gbc, timeBox);
		
		// add end quiz button
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.gridy = 3;
		AminoGUI.makeButton("Want to give up?", gbl, gbc, timeBox);
		
		// add panel to frame
		bottomPanel.setBackground(panelColor);
		
		inputField = doTheThing(font2, inputfieldColor, timeBox);
		
		questionField.setFont(font2);
		JSplitPane questionAnswer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, questionField, inputField);
		questionAnswer.setResizeWeight(0.5D);
		questionAnswer.setBorder(BorderFactory.createCompoundBorder(questionAnswer.getBorder(), 
				                 BorderFactory.createEmptyBorder(25, 5, 5, 5)));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dnaField, questionAnswer);
		splitPane.setResizeWeight(0.7D);
		return splitPane;
	}
	
	// Creates JSplitPane, adds that and other objects to JFrame 'frame'
	public static void main(String[] args) {
		JSplitPane splitPane = makeGUI();
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		frame.getContentPane().add(splitPane);
		frame.setSize(720, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}	
}
