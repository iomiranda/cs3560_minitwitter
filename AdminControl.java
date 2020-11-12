package cs3560_twitter;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class AdminControl implements ActionListener, TreeSelectionListener {
	
	private static String TITLE = "Budget Twitter";
	private static AdminControl instance;
	private static JFrame frame; //Admin Control Frame
	private TreePanel treePane;
	private JPanel controlPane;
	private JTextPane userIDPane; //For Control Panel
	private JTextPane groupIDPane;
	private LinkedList<Integer> idCollection;
	private LinkedList<User> userCollection;
	private LinkedList<User> groupCollection;
	private HashMap<String, Integer> userMap;
	private HashMap<String, Integer> groupMap;


	private static int MIN_ID = 10000000;
	private static int MAX_ID = 50000000;

	
	private AdminControl(String title) {
		frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(700, 325);
		frame.setLayout(new GridLayout(0, 2));
		frame.setResizable(false);

		treePane = new TreePanel();
		controlPane = new JPanel();
		userIDPane = new JTextPane();
		groupIDPane = new JTextPane();
		idCollection = new LinkedList<>();
		userCollection = new LinkedList<>();
		groupCollection = new LinkedList<>();
		userMap = new HashMap<>();
		groupMap = new HashMap<>();
	}
	
	public void reloadFrame(String name) {
		String text = "ID: "+userMap.get(name)+"";
		if(name=="User ID") {
			text = name;
		}
		userIDPane.setText(text);
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
	
	public static AdminControl getInstance() {
		if(instance==null) {
			instance = new AdminControl(TITLE);
		} else {
//			System.out.println("AdminControl already has an instance");
		}
		return instance;
	}

	public void run() {
		treePane = treePanel();
		controlPane = controlPanel();

		//Add into frame
		frame.add(treePane);
		frame.add(controlPane);

		

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private TreePanel treePanel() {
		TreePanel pane = new TreePanel();
//		pane.addNode(null, "No User or Groups");
		return pane;
	}

	private JPanel controlPanel() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(3,0));
		
		pane.add(topControlPanel());
		pane.add(centerControlPanel());
		pane.add(bottomControlPanel());
		
		return pane;
	}
	
	private JPanel topControlPanel() {
		JButton button;
		
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(2,2));
		
		userIDPane = new JTextPane();
		userIDPane.setText("User ID");
		userIDPane.setEditable(false);
		pane.add(userIDPane);		
		
		button = new JButton("Add User"); 
		button.addActionListener(this);
		pane.add(button);
		
		groupIDPane = new JTextPane();
		groupIDPane.setText("Group ID");
		groupIDPane.setEditable(false);
		pane.add(groupIDPane);
		
		button = new JButton("Add Group");
		button.addActionListener(this);
		pane.add(button);
		return pane;
	}

	private JPanel centerControlPanel() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(3,0));
		pane.add(new JLabel(""));
		
		JButton button = new JButton("Open User View");
		button.addActionListener(this);
		pane.add(button);
		
		pane.add(new JLabel(""));
		return pane;
	}
	
	private JPanel bottomControlPanel() {
		JButton button;
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(2,2));
		
		button = new JButton("Show User Total");
		button.addActionListener(this);
		pane.add(button);
		
		button = new JButton("Show Group Total");
		button.addActionListener(this);
		pane.add(button);
		
		button = new JButton("Show Messages Total");
		button.addActionListener(this);
		pane.add(button);
		
		button = new JButton("Show Positive Percentage");
		button.addActionListener(this);
		pane.add(button);
		
		return pane;
	}

	private User getUser(String type) {
		String input = JOptionPane.showInputDialog("Enter "+ type + " Name");
		
		DefaultMutableTreeNode node = treePane.getSelectedPath();
		
		if(type == "User") {
			treePane.addNode(node, input);
		} else {
			treePane.addNode(null, input);
		}

		User user = new User();
		user.setName(input);
		user.setId(getRandomID(MIN_ID, MAX_ID));
		
		if (input == null) {
		    System.out.println("User closed window with no input");
		}
		return user;
	}
	
	public LinkedList<User> getUserList() {
		return userCollection;
	}
	
	private int getRandomID(int min, int max) {
		int randomNum = (int) ((Math.random() * (max - min)) + min);
		while(idCollection.contains(randomNum)) {
			randomNum = (int) ((Math.random() * (max - min)) + min);
		}
		idCollection.add(randomNum);
	    return randomNum;
	}
	
	private void addUser() {
		User use = getUser("User");		
		userCollection.add(use);
		userMap.put(use.getName(), use.getId());
	}
	
	private void addGroup() {
		User use = getUser("Group");		
		groupCollection.add(use);
		groupMap.put(use.getName(), use.getId());
		
	}
	
	private void openProfile() {
		if(treePane.getSelectedPath()!=null) {
			String name = "" + treePane.getSelectedPath();
			
			if(name=="No User or Groups") {
				//Do Nothing
			} else {
				User use = new User();
				
				for(int i=0 ; i<userCollection.size() ; ++i) {
					if(name.equals(userCollection.get(i).getName())) {
						use = userCollection.get(i);
					}
				}
						
				ProfileWindow profile = new ProfileWindow(use);
			}
			

		} else {
			System.out.println("Not Selected");

		}
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		case "Add User":	
			addUser();
			break;
		case "Add Group":
			addGroup();
			break;
		case "Open User View":
			openProfile();
			break;
		case "Show User Total":
			JOptionPane.showMessageDialog(new JFrame(), "Total Users: " + userCollection.size());
			break;
		case "Show Group Total":
			//Change userCollection to GroupCollection
			JOptionPane.showMessageDialog(new JFrame(), "Total Groups: " + groupCollection.size());
			break;
		case "Show Messages Total":
			JOptionPane.showMessageDialog(new JFrame(), "Total Messages: " + (TwitterFeed.getInstance().getTweetList().size()-1));
			break;
		case "Show Positive Percentage":
			break;
		default:
			System.out.println("Error: Unknown Button Selected");
		}		
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {

	}
	
}
