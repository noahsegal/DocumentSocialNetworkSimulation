package main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Justin Fleming
 * Create and maintain the GUI for the user
 */
public class MainWindow extends JFrame {
	private JTextField numberOfTurnsField; // text field to get # of turns in sim
	private JTextField numberOfProdsField; // text field to get # producers in sim
	private JTextField numberOfConsField; // text field to get # of consumers in sim
	private JTextField numberOfTagsField; // text field to get # of tags in sim
	private JTextField numberOfSearchResultsField; // text field to get # of search results returned each turn

	private JButton startButton;

	private DefaultTableModel documentsTableModel;
	private DefaultTableModel consumersTableModel;
	private DefaultTableModel producersTableModel;

	private int numberOfTurns;	// number of turns in the sim
	private int numberOfProds;  // number of Producers in sim
	private int numberOfCons;   // number of Consumers in sim
	private int numberOfTags;   // number of Tags in sim
	private int numberOfSearchResults; // number of Search Results each turn

	public MainWindow(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints con = new GridBagConstraints();
		JPanel panel = new JPanel(layout);
		// Initialize Components
		numberOfTurnsField 			= new JTextField(80);
		numberOfProdsField 			= new JTextField(80);
		numberOfConsField 			= new JTextField(80);
		numberOfTagsField 			= new JTextField(80);
		numberOfSearchResultsField 	= new JTextField(80);
		startButton					= new JButton("Start");
		documentsTableModel			= new DefaultTableModel(5, 4);
		consumersTableModel			= new DefaultTableModel();
		producersTableModel			= new DefaultTableModel();

		// Other Components		-- Ones we arn't keeping track of
		JLabel documentTableLabel 	= new JLabel("Documents");
		JLabel consumerTableLabel	= new JLabel("Consumers");
		JLabel producerTableLabel 	= new JLabel("Producer");
		String documentTableHeader[] = {"ID", "Tag(s)", "Likes", "ProducerID"};
		String consumerTableHeader[] = {"ID", "Tag(s)", "Followers", "Following", "Payoff"};
		String producerTableHeader[] = {"ID", "Tag(s)", "Followers", "Following", "Payoff", "Documents"};

		// Build Top Display Bar -- First Row
		con.weightx = 0.0;
		con.gridwidth = 1;
		panel.add(buildLabelField("Turns:", numberOfTurnsField), con);
		panel.add(buildLabelField("Tags:", numberOfTagsField), con);
		panel.add(buildLabelField("Producers:", numberOfProdsField), con);
		panel.add(buildLabelField("Consumers:", numberOfConsField), con);
		panel.add(buildLabelField("Search Results:", numberOfSearchResultsField), con);

		con.gridwidth = GridBagConstraints.REMAINDER;	// End of Row
		panel.add(startButton, con);

		// Build Table Headers -- Second Row
		con.weightx = 0.33;
		con.gridwidth = 2;
		panel.add(documentTableLabel, con);
		panel.add(consumerTableLabel, con);

		con.gridwidth = GridBagConstraints.REMAINDER;	// End of row
		panel.add(producerTableLabel, con);

		//Build Tables 	-- Third to eighth rows
		con.gridheight = 5;
		con.gridwidth = 2;
		panel.add(buildTable(documentsTableModel, documentTableHeader), con);
		panel.add(buildTable(consumersTableModel, consumerTableHeader), con);

		con.gridwidth = GridBagConstraints.REMAINDER;  // End of row
		panel.add(buildTable(producersTableModel, producerTableHeader), con);

		//Build Graph   -- Ninth to  rows
		//con.gridheight = 4;
		
		getContentPane().add(panel);
		setSize(600,400);
		setVisible(true);
	}

	public static void main(String[] args) {
		System.out.println("In main");
		MainWindow win = new MainWindow();
	}

	/**
	 * Create a Text Field with a JLabel beside it
	 * @param label, text for the JLabel
	 * @param field, JTextField to add a label to
	 * @return JPanel containing the two elements
	 */
	private JPanel buildLabelField(String label, JTextField field) {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints con = new GridBagConstraints();
		field.setMinimumSize(new Dimension(35, 25));
		con.fill = GridBagConstraints.BOTH;
		con.weightx = 0.0;

		JPanel labelField = new JPanel(layout);
		labelField.add(new JLabel(label), con);

		con.weighty = 1.0;
		labelField.add(field, con);

		return labelField;
	}


	/**
	 * Creates a ScrollPane containing a JTable with tableModel and header
	 * @param tableModel, DefaultTableModel to be used by the JTable
	 * @param header, strings to be set as the header for the JTable
	 * @return JScrollPane, containing a JTable using tableModel and header
	 */
	private JScrollPane buildTable(DefaultTableModel tableModel, String[] header) {
		tableModel.setColumnIdentifiers(header);
		JTable table = new JTable(tableModel);
		JScrollPane pane = new JScrollPane(table);
		pane.setMinimumSize(new Dimension(300,200));
		return pane;
	}
}
