package lab3.Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import lab3.Unity.grammerTable;

public class grammerTablePanel {

	JFrame frame;
	private DefaultTableModel ruleListTbModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					grammerTablePanel window = new grammerTablePanel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public grammerTablePanel() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 1353, 820);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		frame.setLocation((screensize.width - frameSize.width) / 2,
				(screensize.height - frameSize.height) / 2);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240,255,240));
		panel.setBounds(0, 0, 1335, 774);
		frame.getContentPane().add(panel);
		
		ruleListTbModel = new DefaultTableModel(new Object[][] {},
				new String[] { "产生式左部", "产生式右部"});
		
		JTable ruleListTb = new JTable();
		ruleListTb.setFont(new Font("宋体", Font.PLAIN, 15));
		ruleListTb.setBackground(new Color(224,238,224));
		ruleListTb.setFillsViewportHeight(true);
		ruleListTb.setModel(ruleListTbModel);
		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(ruleListTbModel);
		panel.setLayout(null);
		ruleListTb.setRowSorter(sorter);
		JScrollPane ruleSP = new JScrollPane();
		ruleSP.setViewportView(ruleListTb);
		ruleSP.setBounds(14, 120, 1307, 634);
		panel.add(ruleSP);
		
		JLabel lblDfaTable = new JLabel("GrammerTable");
		lblDfaTable.setFont(new Font("宋体", Font.BOLD, 55));
		lblDfaTable.setBounds(565, 13, 344, 106);
		panel.add(lblDfaTable);
		
		grammerTable[]  result=new readDFATable().Wenfa();
		
		for(int j=0;j<result.length;j++)
		 {
			String nextString="";
			String[] showtest = result[j].getValue();
			 for(int i = 0;i<showtest.length;i++)
			 {
				 nextString+=showtest[i]+" ";
			 }
			 ruleListTbModel.addRow(new Object[] { j+":"+result[j].getName(),nextString});
		 }
	}

}
