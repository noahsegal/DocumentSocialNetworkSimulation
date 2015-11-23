package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import Search.DumbSearch;
import Search.FollowingSearch;
import Search.HipsterSearch;
import Search.PopularitySearch;
import Search.RandomSearch;
import Search.Search;
import Search.SimilarLikeSearch;
import Search.UserPopularitySearch;
/**
 *
 * This Window allows the end user to view what search method is selected, how a producer is acting
 * and what a Document or User is following or being followed by.
 * 
 * @author Justin Fleming
 * @author Reid Cain-Mondoux
 * @author Noah Segal
 *
 */
public class DoubleClickWindow extends JFrame {
	private Document doc;
	private User user;
	private DefaultListModel<String> followingModel;
	private DefaultListModel<String> followerModel;
	private DefaultListModel<String> documentModel;
	private JList<String> followingList;
	private JList<String> followerList;
	private JList<String> documentList;
	private JScrollPane followerPane;
	private JScrollPane followingPane;
	private JScrollPane documentPane;
	private JPanel comboPanel; // Put all your stuff in this panel and rename
	private JComboBox<Search> strategiesBox;
	private Search[] searches;
	
	private JLabel selectSearch;
	private JRadioButton actAsConsRadio;
	private JRadioButton actAsProdRadio;
	private ButtonGroup radioGroup;
	private JComboBox<String> tagsBox;
	
	public DoubleClickWindow(Document doc) {
		this.doc = doc;
		this.setTitle("Document " + doc.getName());
		documentModel 	= new DefaultListModel<String>();
		documentList 	= new JList<String>(documentModel);
		documentPane	= new JScrollPane(documentList);
		JSplitPane split	= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,comboPanel, buildPanel(documentPane, " Users Who Like This Document "));
		this.populateDocModel();
		getContentPane().add(split);
		setSize(400,400);
		pack();
		setVisible(true);
	}
	
	public DoubleClickWindow(User user, String[] tags) {
		this.user = user;
		this.setTitle(user.getClass().getSimpleName() + " " + user.getID());
		
		followingModel 	= new DefaultListModel<String>();
		followerModel 	= new DefaultListModel<String>();
		followingList	= new JList<String>(followingModel);
		followerList	= new JList<String>(followerModel);
		followerPane	= new JScrollPane(followerList);
		followingPane	= new JScrollPane(followingList);
		selectSearch	= new JLabel("Select Search Method:");
		selectSearch.setHorizontalAlignment(JLabel.CENTER);
		searches = new Search[] {
				new DumbSearch(),
				new HipsterSearch(),
				new PopularitySearch(),
				new RandomSearch(),
				new FollowingSearch(),
				new UserPopularitySearch(),
				new SimilarLikeSearch()
			};
		comboPanel = new JPanel(new GridLayout(5,1));
		strategiesBox = new JComboBox<Search>(searches);
		JSplitPane leftCenterPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, comboPanel, buildPanel(followerPane,"Followers"));
		JSplitPane rightPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftCenterPanel, buildPanel(followingPane, "Following"));


		System.out.println(user);
		strategiesBox.setSelectedItem(user.getSearchMethod());
		strategiesBox.addActionListener(ae -> {
				JComboBox<Search> cb = (JComboBox<Search>) ae.getSource();
				user.setSearchMethod((Search)cb.getSelectedItem());
				System.out.println(user.getSearchMethod().toString());
			});
		comboPanel.add(selectSearch);
		comboPanel.add(strategiesBox);
		
		// Set whether Producer acts as a Consumer or Producer
		if (user instanceof Producer) {
			Producer prod = (Producer) user;
			buildProducerSpecificGUI(prod, tags);
		}
		
		this.populateUserModels();
		getContentPane().add(rightPanel);
		setSize(400,400);
		pack();
		setVisible(true);
	}
	
	/**
	 * populate the user models with the appropriate data
	 */
	private void populateUserModels(){
		if(user == null) 
			return;
		ArrayList<String> following = user.listOfFollowing();
		ArrayList<String> follower = user.listOfFollowers();
		populateList(following, followingModel);
		populateList(follower, followerModel);
		
	}
	
	/**
	 * populate the document model with the data
	 */
	private void populateDocModel(){
		if(doc == null)
			return;
		ArrayList<String> likes = doc.listOfLikers();
		populateList(likes, documentModel);
	}
	
	/**
	 * populate the model from the list provided
	 * @param list, the list to copy from
	 * @param model, the model to copy to
	 */
	private void populateList(ArrayList<String> list, DefaultListModel<String> model) {
		list.forEach((item)->{model.addElement(item);});
	}
	
	/**
	 * Return JPanel containing the scollPane and a title
	 * @param scroll, scrollPane to add title
	 * @param title, title of Scroll pane
	 * @return
	 */
	private JPanel buildPanel(JScrollPane scroll, String title) {
		JLabel label = new JLabel(title);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(label, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);
		return panel;
	}
	
	/**
	 * Set up the GUI with Producer-specific components:
	 * Toggle ActAsConsumer/Producer
	 * Change Producer's altTag 
	 * @param prod Producer that was double-clicked in the MainWindow
	 */
	private void buildProducerSpecificGUI(Producer prod, String[] tags) {
		actAsConsRadio = new JRadioButton("Act as Consumer");
		actAsConsRadio.setSelected(true);
		actAsProdRadio = new JRadioButton("Act as Producer (Select Tag)");
		
		actAsConsRadio.addActionListener(ae -> {
			if (!prod.getActAsConsumer()) prod.toggleActAsConsumer();
			System.out.println("Producer-" + prod.getID() + " actAsConsumer: " + prod.getActAsConsumer());
		});
		actAsProdRadio.addActionListener(ae -> {
			if (prod.getActAsConsumer()) prod.toggleActAsConsumer();
			System.out.println("Producer-" + prod.getID() + " actAsConsumer: " + prod.getActAsConsumer());
			
		});
		radioGroup = new ButtonGroup();
		radioGroup.add(actAsConsRadio);
		radioGroup.add(actAsProdRadio);
		
		tagsBox = new JComboBox<String>(tags);
		tagsBox.addItem(prod.getAltTag());
		tagsBox.setSelectedItem(prod.getAltTag());
		tagsBox.addActionListener(ae -> {
			JComboBox<String> cb = (JComboBox<String>) ae.getSource();
			prod.setAltTag( (String)cb.getSelectedItem() );
			System.out.println(prod.getAltTag());
		});
		
		comboPanel.add(actAsConsRadio);
		comboPanel.add(actAsProdRadio);
		comboPanel.add(tagsBox);
	}
	
	

}
