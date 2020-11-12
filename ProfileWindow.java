package cs3560_twitter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class ProfileWindow extends JFrame implements ActionListener {
	
	private User user;
	private DefaultListModel tweetList;
	private JScrollPane tweetScroll;
	private JList jListTweet;
	private JTextPane tweetTextPane;
	
	private DefaultListModel followListMode;
	private JList followList;
	private JScrollPane followScroll;
	
	private TwitterFeed twitterFeed;
	private int adminCount;
	
	public ProfileWindow(User user) {
		this.user = user;
		tweetList = new DefaultListModel();
		followListMode = new DefaultListModel();
		tweetScroll = new JScrollPane();
		followScroll = new JScrollPane();
		jListTweet = new JList();
		followList = new JList();
		tweetTextPane = new JTextPane();
		twitterFeed = TwitterFeed.getInstance();
		adminCount = 0;
		setTitle(user.getName() + "'s Profile");
		setSize(350, 450);
		setResizable(false);
		addPanel();
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(2, 0));
		setVisible(true);

	}
	
	private void addPanel() {
		this.add(topPanel());
		this.add(bottomPanel());
	}
	
	private JPanel topPanel() {
		JPanel pane = new JPanel();
		JButton button;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();


		JTextPane textPane = new JTextPane();
		textPane.setText("ID: " + user.getId());
		textPane.setEditable(false);
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 125;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(textPane, c);

		button = new JButton("Follow User");
		button.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		pane.add(button, c);

		followListMode.addElement("FOLLOWING:");
		
		for(int i=0 ; i<AdminControl.getInstance().getUserList().size() ; ++i) {
			TreePanel tree = new TreePanel();
			if(user.getName() != AdminControl.getInstance().getUserList().get(i).getName()) {
				followListMode.addElement(AdminControl.getInstance().getUserList().get(i).getName());
			}
		}
		
		followList = new JList(followListMode);
		followScroll.setViewportView(followList);
		followList.setLayoutOrientation(JList.VERTICAL);		
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 160; 
		c.weightx =1;
		c.weighty = 5;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(followScroll, c);

		return pane;
		
	}
	
	private JPanel bottomPanel() {
		JPanel pane = new JPanel();
		JButton button;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		tweetTextPane.setText("Tweet Message...");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx =50;
		c.weighty = 50;
		c.ipadx = 200;
		c.ipady = 200;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(tweetTextPane, c);

		button = new JButton("Post Tweet");
		button.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.ipadx = 10;
		c.ipady = 40;

		c.gridx = 1;
		c.gridy = 0;
		pane.add(button, c);

		tweetList.addElement("TWITTER FEED:");
		jListTweet = new JList(tweetList);
		tweetScroll.setViewportView(jListTweet);
		jListTweet.setLayoutOrientation(JList.VERTICAL);		
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 160; 
		c.weightx =1;
		c.weighty = 5;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(tweetScroll, c);

		return pane;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if(action=="Post Tweet") {
			
			tweetList.removeAllElements();
			
			twitterFeed.addTweet(user.getName() + ": " + tweetTextPane.getText());
			for(int i=0 ; i<twitterFeed.getTweetList().size() ; ++i) {
				tweetList.addElement(twitterFeed.getTweetList().get(i));
			}
			
			this.repaint();
			tweetTextPane.setText("");			
		} else if(action=="Follow User") {
			if(adminCount==0) {
				JOptionPane.showMessageDialog(new JFrame(), "You have followed "+ user.getName());
				followListMode.addElement("Admin");
				adminCount++;
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "You have already followed "+ user.getName(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
				
	}


}
