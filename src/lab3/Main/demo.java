package lab3.Main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.RowSorter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JLabel;

import lab3.Unity.ACTIONTable;
import lab3.Unity.CharTable;
import lab3.Unity.DFATable;
import lab3.Unity.DFATableState;
import lab3.Unity.FourAddr;
import lab3.Unity.GOTOTable;
import lab3.Unity.SLRFormula;
import lab3.Unity.SLRTree;
import lab3.Unity.AddrNum;
import lab3.Unity.grammerSemanticLoca;
import lab3.Unity.grammerTable;
import lab3.Unity.RreeSemanticRecord;

import java.awt.Font;

public class demo {

	private JFrame frame;
	private JFrame frame1;
	private JFrame frame2;
	private JFrame frame3;
	private JTree tree;
	private JTextArea textArea;
	private DefaultTableModel tokenListTbModel;
	private DefaultTableModel errorListTbModel;
	private DefaultTableModel grammererrorListTbModel;
	private DefaultTableModel semerrorListTbModel;
	
	private DefaultTableModel syntaxListTbModel;
	private String filepath2;
	private ArrayList<ACTIONTable> slrTable;
	private DefaultTreeModel treeModel;
	
	private DefaultTableModel charListTbModel;
	private DefaultTableModel addrListTbModel;
	// ����� operaters
	// String[] operaters={
	// ">",">=","<","<=","==","!=","|","&","||","&&","!","^","+","-","*","/","%","++","--","+=","-=","*=","%="};
	// ��� divideLine
	// String[] divideLines={
	// ",","=",";","[","]","(",")","{",";","}",".","\"","\'"};
	// �ؼ��� keywords
	String[] keywords = { "char", "long", "short", "float", "double", "const",
			"boolean", "void", "null", "false", "true",  "int", "do",
			"while", "if", "else", "for", "then", "break", "continue", "class",
			"static", "final",  "return", "signed", "struct",
			"goto", "switch", "case", "default",
			"extern", "sizeof", "typedef", "proc","integer","record","real","call","and","or","not"};
	String[] tokens = { "char", "long", "short", "float", "double", "const",
			"boolean", "void", "null", "false", "true",  "int", "do",
			"while", "if", "else", "for", "then", "break", "continue", "class",
			"static", "final", "return", "signed", "struct",
			 "goto", "switch", "case", "default",
			"extern",  "sizeof", "typedef",   "proc","integer","record","real","call","and","or","not",">", ">=",
			"<", "<=", "==", "!=", "|", "&", "||", "&&", "!", "^", "+", "-",
			"*", "/", "%", "++", "--", "+=", "-=", "*=", "/=", ",", "=", ";",
			"[", "]", "(", ")", "{", "}", ".", "\"", "'"

	};

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		DFATable dfa[] = new readDFATable().addDFA();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					demo window = new demo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public demo() throws Exception {
		initialize();
	}

	/**
	 * ���дʷ����﷨���������
	 * @param t
	 */
	public void RUN(int t) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(t==1) {//�ʷ�
						frame1.setVisible(true);
					}
					if(t==2) {//�﷨
						frame2.setVisible(true);
					}
					if(t==3) {//����
						frame3.setVisible(true);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * �﷨��������
	 */
	private void YuFa() {
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 1023, 860);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame2.getSize();
		frame2.setLocation((screensize.width - frameSize.width) / 2,
				(screensize.height - frameSize.height) / 2);

		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(141,182,205));
		panel.setBounds(0, 0, 1307, 815);
		frame2.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(230, 230, 250));
		panel_3.setBounds(697, 70, 277, 702);
		panel.add(panel_3);
		panel_3.setLayout(null);
		//����������
		tree = new JTree();
		tree.setModel(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setRowHeight(20);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tree);
		panel_3.add(scrollPane);
		scrollPane.setBounds(0, 5, 275,697);
		
		// �﷨����������
		grammererrorListTbModel = new DefaultTableModel(new Object[][] {},new String[] { "�������", "����ط�", "����ԭ��" });
		// errorListTbModel.addRow(new Object[] { "����", "���","����ԭ��" ,"value"});
		RowSorter<DefaultTableModel> sorter7 = new TableRowSorter<DefaultTableModel>(grammererrorListTbModel);
		// �﷨�������
		syntaxListTbModel = new DefaultTableModel(new Object[][] {},new String[] { "��Լ���"});	
		RowSorter<DefaultTableModel> sorter6 = new TableRowSorter<DefaultTableModel>(syntaxListTbModel);
		
		JTable syntaxListTb = new JTable();
		syntaxListTb.setBackground(new Color(164,211,238));
		syntaxListTb.setFillsViewportHeight(true);
		syntaxListTb.setModel(syntaxListTbModel);
		syntaxListTb.setRowSorter(sorter6);
		
		JScrollPane syntaxSP = new JScrollPane();
		syntaxSP.setBounds(46, 72, 611, 314);
		panel.add(syntaxSP);
		syntaxSP.setViewportView(syntaxListTb);

		JTable grammererrorListTb = new JTable();
		grammererrorListTb.setBackground(new Color(164,211,238));
		grammererrorListTb.setFillsViewportHeight(true);
		grammererrorListTb.setModel(grammererrorListTbModel);
		grammererrorListTb.setRowSorter(sorter7);
		
		JScrollPane grammererrorSP = new JScrollPane();
		grammererrorSP.setBounds(46, 447, 611, 325);
		panel.add(grammererrorSP);
		grammererrorSP.setViewportView(grammererrorListTb);
		
		JLabel lblAnalysisTable = new JLabel("Analysis TABLE");
		lblAnalysisTable.setBounds(198, 19, 320, 40);
		panel.add(lblAnalysisTable);
		lblAnalysisTable.setFont(new Font("����", Font.BOLD, 40));
		
		JLabel lblErrorTable_1 = new JLabel("ERROR TABLE");
		lblErrorTable_1.setFont(new Font("����", Font.BOLD, 40));
		lblErrorTable_1.setBounds(219, 395, 254, 40);
		panel.add(lblErrorTable_1);
		
	}

	/**
	 * �ʷ�����
	 */
	private void CiFa() {
		frame1 = new JFrame();
		frame1.setBounds(100, 100, 1023, 860);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame1.getSize();
		frame1.setLocation((screensize.width - frameSize.width) / 2,
				(screensize.height - frameSize.height) / 2);

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255,228,225));
		panel.setBounds(0, 0, 1307, 815);
		frame1.getContentPane().add(panel);
		panel.setLayout(null);
		
		// �ֱ�����
		tokenListTbModel = new DefaultTableModel(new Object[][] {},
				new String[] { "�ַ���", "���", "�ֱ���", "value" });
		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tokenListTbModel);
	
		// �ʷ�����������
		errorListTbModel = new DefaultTableModel(new Object[][] {},
				new String[] { "�������", "����ط�", "����ԭ��" });
		// errorListTbModel.addRow(new Object[] { "����", "���","����ԭ��" ,"value"});
		RowSorter<DefaultTableModel> sorter2 = new TableRowSorter<DefaultTableModel>(errorListTbModel);
		
		
		JTable tokenListTb = new JTable();
		tokenListTb.setBackground(new Color(255,240,245));
		tokenListTb.setFillsViewportHeight(true);
		tokenListTb.setModel(tokenListTbModel);
		tokenListTb.setRowSorter(sorter);
		JScrollPane tokenSP = new JScrollPane();
		tokenSP.setBounds(14, 98, 487, 594);
		panel.add(tokenSP);
		tokenSP.setViewportView(tokenListTb);
		
		JLabel lblToken = new JLabel("TOKEN TABLE");
		lblToken.setBounds(125, 40, 254, 34);
		panel.add(lblToken);
		lblToken.setFont(new Font("����", Font.BOLD, 40));
		
		JTable errorListTb = new JTable();
		errorListTb.setBackground(new Color(255,240,245));
		errorListTb.setFillsViewportHeight(true);
		errorListTb.setModel(errorListTbModel);
		errorListTb.setRowSorter(sorter2);
		JScrollPane errorSP = new JScrollPane();
		errorSP.setBounds(571, 94, 403, 598);
		panel.add(errorSP);
		errorSP.setViewportView(errorListTb);
		
		JLabel lblErrorTable = new JLabel("ERROR TABLE");
		lblErrorTable.setBounds(676, 37, 248, 41);
		panel.add(lblErrorTable);
		lblErrorTable.setFont(new Font("����", Font.BOLD, 40));
		
	}
	
	/**
	 * �������
	 */
	private void YuYi() {
		frame3 = new JFrame();
		frame3.setBounds(100, 100, 1023, 860);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame3.getSize();
		frame3.setLocation((screensize.width - frameSize.width) / 2,
				(screensize.height - frameSize.height) / 2);

		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255,228,196));
		panel.setBounds(0, 0, 1307, 815);
		frame3.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		// �������������
		semerrorListTbModel = new DefaultTableModel(new Object[][] {},new String[] { "��������", "����ԭ��", "�����ַ�" });
		// errorListTbModel.addRow(new Object[] { "����", "���","����ԭ��" ,"value"});
		RowSorter<DefaultTableModel> sorter8 = new TableRowSorter<DefaultTableModel>(semerrorListTbModel);
		// ��ַ���
		addrListTbModel = new DefaultTableModel(new Object[][] {},new String[] { "���", "����ַ","��Ԫʽ"});
		RowSorter<DefaultTableModel> sorter10 = new TableRowSorter<DefaultTableModel>(addrListTbModel);
		// ���ű��
		charListTbModel = new DefaultTableModel(new Object[][] {},new String[] { "���", "����" , "ƫ��"});
		RowSorter<DefaultTableModel> sorter9 = new TableRowSorter<DefaultTableModel>(charListTbModel);
		
		//�������
		JTable semerrorListTb = new JTable();
		semerrorListTb.setBackground(new Color(255,239,213));
		semerrorListTb.setFillsViewportHeight(true);
		semerrorListTb.setModel(semerrorListTbModel);
		semerrorListTb.setRowSorter(sorter8);
		JScrollPane semerrorSP = new JScrollPane();
		semerrorSP.setBounds(528, 531, 446, 204);
		panel.add(semerrorSP);
		semerrorSP.setViewportView(semerrorListTb);
				
		JTable charListTb = new JTable();
		charListTb.setBackground(new Color(255,239,213));
		charListTb.setFillsViewportHeight(true);
		charListTb.setModel(charListTbModel);
		charListTb.setRowSorter(sorter9);
		JScrollPane charSP = new JScrollPane();
		charSP.setBounds(534, 76, 440, 369);
		panel.add(charSP);
		charSP.setViewportView(charListTb);
				
		JTable addrListTb = new JTable();
		addrListTb.setBackground(new Color(255,239,213));
		addrListTb.setFillsViewportHeight(true);
		addrListTb.setModel(addrListTbModel);
		addrListTb.setRowSorter(sorter10);
		JScrollPane addrSP = new JScrollPane();
		addrSP.setBounds(25, 76, 475, 659);
		panel.add(addrSP);
		addrSP.setViewportView(addrListTb);
		
		JLabel seAddrTable = new JLabel("Address TABLE");
		seAddrTable.setBounds(112, 19, 320, 40);
		panel.add(seAddrTable);
		seAddrTable.setFont(new Font("����", Font.BOLD, 40));
		
		JLabel charaTable = new JLabel("Symbol TABLE");
		charaTable.setBounds(616, 19, 320, 40);
		panel.add(charaTable);
		charaTable.setFont(new Font("����", Font.BOLD, 40));
		
		JLabel seErrorTable = new JLabel("Error TABLE");
		seErrorTable.setBounds(616, 478, 320, 40);
		panel.add(seErrorTable);
		seErrorTable.setFont(new Font("����", Font.BOLD, 40));
	}
	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws Exception
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 505, 475);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		frame.setLocation((screensize.width - frameSize.width) / 2,
				(screensize.height - frameSize.height) / 2);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//��ײ�
		JPanel panel = new JPanel();
		panel.setBackground(new Color(224,102,255));
		panel.setBounds(0, 0, 505, 475);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(221, 160, 221));
		panel_1.setBounds(5, 5, 480, 423);
		panel.add(panel_1);
		panel_1.setLayout(null);

		CiFa();
		YuFa();
		YuYi();
	
/**************��ʼ*************************************************/
	        
		new JScrollPane();
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
		textArea.setBackground(new Color(232, 232, 232));
		JScrollPane textAreaSP = new JScrollPane();
		textAreaSP.setBounds(14, 87, 275, 323);
		panel_1.add(textAreaSP);
		textAreaSP.setViewportView(textArea);
		
		final grammerTable[] grammerTable = new readDFATable().Wenfa();
		System.out.println("***********�ķ���***********");
		for(int i=0;i<grammerTable.length;i++)
		{
			System.out.print(i+" : ");
			System.out.print(grammerTable[i].getName()+"->");
			for(int j=0;j<grammerTable[i].getValue().length;j++)
			{
				System.out.print(grammerTable[i].getValue()[j]+" ");
			}
			System.out.println();
		}
		System.out.print("***********�ķ������***********");
		
		final JButton btnNewButton_2 = new JButton("ѡ���ļ�");
		btnNewButton_2.setFont(new Font("����", Font.BOLD, 15));
		btnNewButton_2.setBackground(new Color(216,191,216));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("D:\\eclipse\\BianYi\\src\\lab3"); // ����ѡ����
				chooser.setMultiSelectionEnabled(true); // ��Ϊ��ѡ
				int returnVal = chooser.showOpenDialog(btnNewButton_2);
				if (returnVal == JFileChooser.APPROVE_OPTION) { // ��������ļ�����
					String filepath = chooser.getSelectedFile()
							.getAbsolutePath(); // ��ȡ����·��
					System.out.println(filepath);
					filepath2 = filepath.replaceAll("\\\\", "/");
					File file = new File(filepath2);
					textArea.setText(txt2String(file));
				}
			}
		});
		btnNewButton_2.setBounds(14, 30, 107, 34);
		panel_1.add(btnNewButton_2);

		JButton btnNewButton_1 = new JButton("\u6D4B\u8BD5");//����
		btnNewButton_1.setFont(new Font("����", Font.BOLD, 15));
		btnNewButton_1.setBackground(new Color(216,191,216));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textArea.getText();
				String str2 = str.replaceAll("\r|\n", ""); // ȥ�����з��ո�
				try {
					ArrayList<String[]> a=lexicalAnalysis(str2);
			        ArrayList<SLRTree> slrTreeArray= slrTest(a,slrTable);
			        /**************��ʼ������*************************************************/
			        treeModel= getTreeModel(slrTreeArray,slrTreeArray.size()-1);
					tree.setModel(treeModel);
					/**************��ʼ�������*************************************************/
					System.out.println("********��ʼ�������*************");
					grammerSemanticLoca[] grammersemanticLoca=new readDFATable().GrammerSemanticLoca();
					ArrayList<RreeSemanticRecord> treeSemanticRecord=new ArrayList<RreeSemanticRecord>();
					ArrayList<String> addrList=new ArrayList<String>();//��ַ��L1 L2 L3
					ArrayList<String> addrResult=new ArrayList<String>();//������if a>b goto l1
					ArrayList<AddrNum> addrNum=new ArrayList<AddrNum>();//��ַ����ŵĶ�Ӧ��
					ArrayList<CharTable> charTable=new ArrayList<CharTable>();//���ű�
					ArrayList<FourAddr> fourAddr=new ArrayList<FourAddr>();//��ַ����ŵĶ�Ӧ��
					ArrayList<String> param=new ArrayList<String>();//������
					initRecord(treeSemanticRecord,slrTreeArray);
					semanticTest(grammerTable,slrTreeArray,treeSemanticRecord,grammersemanticLoca,addrList,addrResult ,addrNum,charTable,fourAddr,param, slrTreeArray.size()-1);
					ArrayList<String> b=change(addrResult,addrNum);
					System.out.println("********��ַ��*************");
					for(int i=0;i<addrList.size();i++)
					{
						System.out.println(addrList.get(i));
						//addrListTbModel.addRow(new Object[] { i+1,addrResult.get(i)});
					}
					System.out.println("********��ַ�����*************");
					for(int i=0;i<addrResult.size();i++)
					{
						System.out.println(addrResult.get(i));
						//addrListTbModel.addRow(new Object[] { i+1,addrResult.get(i)});
					}
					
					System.out.println(b.size()+" "+fourAddr.size());
					for(int i=0;i<b.size();i++)
					{
						String s="< "+fourAddr.get(i).getOp()+" , "+fourAddr.get(i).getParam1()+" , "+fourAddr.get(i).getParam2()+" , "+fourAddr.get(i).getToaddr()+" >";
						addrListTbModel.addRow(new Object[] { i+1,b.get(i),s});
					}
					System.out.println("********��ַ��Ӧ��*************");
					System.out.println("size="+addrNum.size());
					for(int i=0;i<addrNum.size();i++)
					{
						System.out.println(addrNum.get(i).getAddr()+" "+addrNum.get(i).getNum());
						
					}
					System.out.println("********�ĵ�ַ��*************");
					for(int i=0;i<fourAddr.size();i++)
					{
						System.out.println("< "+fourAddr.get(i).getOp()+" , "+fourAddr.get(i).getParam1()+" , "+fourAddr.get(i).getParam2()+" , "+fourAddr.get(i).getToaddr()+" >");
						
					}
					System.out.println("********���ű�*************");
					for(int i=0;i<charTable.size();i++)
					{
						System.out.println(charTable.get(i).getChara()+" "+charTable.get(i).getType()+" "+charTable.get(i).getOffset());
						charListTbModel.addRow(new Object[] { charTable.get(i).getChara(),charTable.get(i).getType(),charTable.get(i).getOffset()});
					}
					System.out.println("********��¼����Ϣ��*************");
					testRecord(treeSemanticRecord);
					System.out.println("********�����������*************");
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(171, 30, 92, 34);
		panel_1.add(btnNewButton_1);
		
		/**
		 * ����л��ķ�����д���ڲ����
		 * @author Administrator
		 *
		 */
		class ControlPanel {
			public void choose(int type) {
				frame1.setVisible(false);
				frame2.setVisible(false);
				frame3.setVisible(false);
			    switch (type) {
			    case 0 :
			    	RUN(1);
			    	break;
			    case 1 :
			    	RUN(2);
			    	break;
			    case 2 :
			    	RUN(3);
			    	break;
	
			    }
			}
		}
		
		JButton button = new JButton("\u8BCD\u6CD5\u5206\u6790");//�ʷ�������ť
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�л����
			    ControlPanel mcp = new ControlPanel();
			    mcp.choose(0);
			}
		});
		button.setBackground(new Color(216,191,216));
		button.setBounds(310, 70, 150, 40);
		panel_1.add(button);
		
		JButton button_1 = new JButton("\u8BED\u6CD5\u5206\u6790");//�﷨������ť
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�л����
			    ControlPanel mcp = new ControlPanel();
			    mcp.choose(1);
			}
		});
		button_1.setBackground(new Color(216,191,216));
		button_1.setBounds(310, 120, 150, 40);
		panel_1.add(button_1);
		
		JButton button_3 = new JButton("\u8BED\u4E49\u5206\u6790");//�������
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�л����
			    ControlPanel mcp = new ControlPanel();
			    mcp.choose(2);
			}
		});
		button_3.setBackground(new Color(216,191,216));
		button_3.setBounds(310, 170, 150, 40);
		panel_1.add(button_3);
				
		JButton btnDfa = new JButton("DFA\u8F6C\u6362\u8868");//DFA��
		btnDfa.setBounds(310, 220, 150, 40);
		panel_1.add(btnDfa);
		btnDfa.setBackground(new Color(216,191,216));
		btnDfa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DFAPanel window = null;
				try {
					window = new DFAPanel();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				window.frame.setVisible(true);
			}
		});
		
		JButton btnSlr = new JButton("SLR\u5206\u6790\u8868");//SLR������
		btnSlr.setBounds(310, 270, 150, 40);
		panel_1.add(btnSlr);
		btnSlr.setBackground(new Color(216,191,216));
		btnSlr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SLRPanel window = null;
				try {
					window = new SLRPanel();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				window.frame.setVisible(true);
			}
		});
		
		JButton button_2 = new JButton("\u8BED\u4E49\u89C4\u5219");//�������
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				semanticRulePanel window = null;
				try {
					window = new semanticRulePanel();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				window.frame.setVisible(true);
			}
		});
		button_2.setBackground(new Color(216,191,216));
		button_2.setBounds(310, 320, 150, 40);
		panel_1.add(button_2);
		
		JButton buttonWF = new JButton("�ķ���");//grammerTable�ķ���ť
		buttonWF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grammerTablePanel windowGT = null;
				try {
					windowGT = new grammerTablePanel();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				windowGT.frame.setVisible(true);
			}
		});
		buttonWF.setBackground(new Color(216,191,216));
		buttonWF.setBounds(310, 370, 150, 40);
		panel_1.add(buttonWF);
		
			
		slrTable=getSLRTable(grammerTable);	
	}

	// ���ļ�
	public static String txt2String(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// ����һ��BufferedReader������ȡ�ļ�
			String s = null;
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				result.append(s + System.lineSeparator());
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * �ʷ�������ʼ*************************************************
	 */
	public ArrayList<String[]> lexicalAnalysis(String str) throws Exception {
		while (tokenListTbModel.getRowCount() > 0) {
			tokenListTbModel.removeRow(0);
		}
		while (errorListTbModel.getRowCount() > 0) {
			errorListTbModel.removeRow(0);
		}
		while (addrListTbModel.getRowCount() > 0) {
			addrListTbModel.removeRow(0);
		}
		while (charListTbModel.getRowCount() > 0) {
			charListTbModel.removeRow(0);
		}
		while (semerrorListTbModel.getRowCount() > 0) {
			semerrorListTbModel.removeRow(0);
		}
		ArrayList<String[]> slrInput2=new ArrayList<String[]>();
		//String[][] slrInput= null;
		//int inputCount=0;
		// ���ַ���ת��Ϊ�ַ�����
		char[] strline = str.toCharArray();
		String currentString = "";
		int currentState = 0; // ��ǰ״̬
		int lastState = 0; // ��һ״̬

		DFATable dfa[] = new readDFATable().addDFA();
		DFATableState DFAstate[] = new readDFATable().showDFAState();

		for (int i = 0; i < strline.length; i++) {
			currentString = currentString.concat(String.valueOf(strline[i]));
			lastState = currentState;

			if (currentString == " " || strline[i] == ' ') {// �����ո�

				// �����ע����Ŀո񣬿��Խ���
				if (currentState == 9 || currentState == 10) {
					currentState = 9;
					System.out.println("newcurrentString: " + currentState);
					// �ж��Ƿ������һ������
					if (i == strline.length - 1) {
						if (!isFinishByState(currentState, DFAstate)) {
							// System.out.println("***\t"+read.isFinish(lState));
							errorListTbModel.addRow(new Object[] { currentString, "��" + i + "�ַ�", "ע��ע��δ���" });
							tokenListTbModel.addRow(new Object[] { currentString, "ע��ע��δ���", "��", "   " });
						} else {
							if (isKeyword(currentString)) {
								tokenListTbModel
										.addRow(new Object[] { currentString, "�ؼ���", tokenID(currentString), "   " });
							} else {
								tokenListTbModel.addRow(new Object[] { currentString,
										findTypeByState(currentState, DFAstate), tokenID(currentString),
										findTypeByState(currentState, DFAstate).equals("ע��")
												|| findTypeByState(currentState, DFAstate).equals("�����")
												|| findTypeByState(currentState, DFAstate).equals("���") ? "   "
														: currentString });
								// System.out.println("&&&&&&&&&&&&&&");
							}
						}
					}
					continue;
				}

				currentString = currentString.replaceAll(" ", "");// ȥ���ո�
				if (isKeyword(currentString)) {
					tokenListTbModel.addRow(new Object[] { currentString, "�ؼ���", tokenID(currentString), "   " });
					slrInput2.add(new String[] { currentString, "�ؼ���" });

				} else {
					tokenListTbModel.addRow(new Object[] { currentString, findTypeByState(lastState, DFAstate),
							tokenID(currentString),
							findTypeByState(lastState, DFAstate).equals("ע��")
									|| findTypeByState(lastState, DFAstate).equals("�����")
									|| findTypeByState(lastState, DFAstate).equals("���") ? "   " : currentString });
					slrInput2.add(new String[] { currentString, findTypeByState(lastState, DFAstate) });
				}
				currentString = "";
				currentState = 0;
				continue;
			}
			currentState = stateChange(strline[i], currentState, dfa);

			if (currentState == -1) {// ��������ת����˵��״̬�ѵ���ͷ
				currentString = currentString.substring(0, currentString.length() - 1);
				// �жϵ�ǰ״̬�Ƿ�Ϊ��ֹ״̬, �粻�� ,����
				if (!isFinishByState(lastState, DFAstate)) {
					errorListTbModel.addRow(new Object[] { currentString, "��" + i + "�ַ�", "�Ƿ��ַ�" });
					tokenListTbModel.addRow(new Object[] { currentString, "�Ƿ��ַ�", "��", "   " });
				} else {
					if (isKeyword(currentString)) {
						tokenListTbModel.addRow(new Object[] { currentString, "�ؼ���", tokenID(currentString), "    " });
						slrInput2.add(new String[] { currentString, "�ؼ���" });
					} else {
						tokenListTbModel.addRow(new Object[] { currentString, findTypeByState(lastState, DFAstate),
								tokenID(currentString),
								findTypeByState(lastState, DFAstate).equals("ע��")
										|| findTypeByState(lastState, DFAstate).equals("�����")
										|| findTypeByState(lastState, DFAstate).equals("���") ? "   " : currentString });
						slrInput2.add(new String[] { currentString, findTypeByState(lastState, DFAstate) });
					}
				}
				currentString = "";
				currentState = 0;
				i--;
			}
			if (currentState == -2) {// ����ǷǷ��ַ�

				if (lastState == 2) {// ˵��֮ǰʶ���Ϊ����
					currentString = currentString.substring(0, currentString.length() - 1);
					// System.out.println("����-1");
					tokenListTbModel.addRow(new Object[] { currentString + strline[i], "��ʽ����ȷ", "��", "   " });
					errorListTbModel.addRow(new Object[] { currentString + strline[i], "��" + i + "�ַ�", "��ʽ����ȷ" });
					i++;
				} else if (lastState == 9 || lastState == 10) {
					currentState = 9;
					// System.out.println("newcurrentString: "+currentState);
					// �ж��Ƿ������һ������
					if (i == strline.length - 1) {
						if (!isFinishByState(currentState, DFAstate)) {
							// System.out.println("***\t"+read.isFinish(lState));
							errorListTbModel.addRow(new Object[] { currentString, "��" + i + "�ַ�", "ע��ע��δ���" });
							tokenListTbModel.addRow(new Object[] { currentString, "ע��ע��δ���", "��", "   " });
						} else {
							if (isKeyword(currentString)) {
								tokenListTbModel
										.addRow(new Object[] { currentString, "�ؼ���", tokenID(currentString), "   " });
								slrInput2.add(new String[] { currentString, "�ؼ���" });
							} else {
								tokenListTbModel.addRow(new Object[] { currentString,
										findTypeByState(currentState, DFAstate), tokenID(currentString),
										findTypeByState(currentState, DFAstate).equals("ע��")
												|| findTypeByState(currentState, DFAstate).equals("�����")
												|| findTypeByState(currentState, DFAstate).equals("���") ? "   "
														: currentString });
								slrInput2.add(new String[] { currentString, findTypeByState(currentState, DFAstate) });
								// System.out.println("&&&&&&&&&&&&&&");
							}
						}
					}
					continue;
				} else {
					currentString = currentString.substring(0, currentString.length() - 1);
					if (!isFinishByState(lastState, DFAstate)) {
						errorListTbModel.addRow(new Object[] { currentString, "��" + i + "�ַ�", "�Ƿ��ַ�" });
						tokenListTbModel.addRow(new Object[] { currentString, "�Ƿ��ַ�", "��", "   " });
					} else {
						if (isKeyword(currentString)) {
							tokenListTbModel.addRow(new Object[] { currentString, "�ؼ���", tokenID(currentString), "-" });
						} else {
							tokenListTbModel.addRow(new Object[] { currentString, findTypeByState(lastState, DFAstate),
									tokenID(currentString),
									findTypeByState(lastState, DFAstate).equals("ע��")
											|| findTypeByState(lastState, DFAstate).equals("�����")
											|| findTypeByState(lastState, DFAstate).equals("���") ? "   "
													: currentString });
							slrInput2.add(new String[] { currentString, findTypeByState(lastState, DFAstate) });
						}
					}
					tokenListTbModel.addRow(new Object[] { strline[i], "�Ƿ��ַ�", "��", "   " });
					errorListTbModel.addRow(new Object[] { strline[i], "��" + i + "�ַ�", "�Ƿ��ַ�" });
					i++;
				}
				currentString = "";
				currentState = 0;
				i--;
			}

			// ���һ������
			if (i == strline.length - 1) {
				if (!isFinishByState(lastState, DFAstate)) {

					errorListTbModel.addRow(new Object[] { currentString, "��" + i + "�ַ�", "�Ƿ��ַ�" });
					tokenListTbModel.addRow(new Object[] { currentString, "�Ƿ��ַ�", "��", "   " });
				} else {
					if (isKeyword(currentString)) {
						tokenListTbModel.addRow(new Object[] { currentString, "�ؼ���", tokenID(currentString), "   " });
						slrInput2.add(new String[] { currentString, "�ؼ���" });
					} else {
						tokenListTbModel.addRow(new Object[] { currentString, findTypeByState(currentState, DFAstate),
								tokenID(currentString),
								findTypeByState(currentState, DFAstate).equals("ע��")
										|| findTypeByState(currentState, DFAstate).equals("�����")
										|| findTypeByState(currentState, DFAstate).equals("���") ? "   "
												: currentString });
						slrInput2.add(new String[] { currentString, findTypeByState(currentState, DFAstate) });
					}
				}
				if (currentState == -2) {
					tokenListTbModel.addRow(new Object[] { strline[i], "�Ƿ��ַ�", "��", "   " });
					errorListTbModel.addRow(new Object[] { strline[i], "��" + i + "�ַ�", "�Ƿ��ַ�" });
					i++;
				}
			}

		}
		return slrInput2;

	}

	public int stateChange(char currentChar, int currentState, DFATable[] dfa) {
		boolean isInput = false;
		for (int i = 0; i < dfa.length; i++) {
			if (isList(dfa[i].getInput(), currentChar)) {
				isInput = true;// ���ڸ�����
				if (dfa[i].getState() == currentState
						&& dfa[i].getNextState() != -1)// ��ǰ״̬һ�� ������ ��һ״̬��Ϊ��
														// ��Ȼ�ڵ�ǰ�Զ�����
				{
					return dfa[i].getNextState();
				}
			}
		}
		if (isInput == false) {
			return -2;
		}
		// ��״̬9 10 ��̫��û�н��� ע��δ��� ����
		return -1;
	}

	public static boolean isList(String[] arr, char currentChar) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(String.valueOf(currentChar))) {
				return true;
			}
		}
		return false;
	}

	// ����state ���ظ�״̬������
	public String findTypeByState(int state, DFATableState[] dfaState) {
		for (int i = 0; i < dfaState.length; i++) {
			if (dfaState[i].getState() == state) {
				return dfaState[i].getType();
			}
		}
		return "error";
	}

	// ����state ���ظ�״̬���Ƿ�Ϊ��ֹ״̬
	public boolean isFinishByState(int state, DFATableState[] dfaState) {
		for (int i = 0; i < dfaState.length; i++) {
			if (dfaState[i].getState() == state) {
				return dfaState[i].isFinish();
			}
		}
		return true;
	}

	// �жϵ�ǰ����Ҫ������ַ����Ƿ�Ϊ�ؼ���
	public boolean isKeyword(String str) {
		for (int i = 0; i < keywords.length; i++) {
			if (keywords[i].equals(str)) {
				return true;
			}
		}
		return false;
	}

	public int tokenID(String str) {
		boolean flag = false;
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].equals(str)) {
				flag = true;
				return i+1;
			}
		}
		if (flag == false) {
			tokens = Arrays.copyOf(tokens, tokens.length + 1);
			tokens[tokens.length - 1] = str;
			return tokens.length;
		}
		return -1;
	}

	/**
	 * �ķ�������ʼ*************************************************
	 */

	/**
	 * ���First��
	 * 
	 * @return first��
	 * @throws Exception
	 */
	public String[][] getFirstDroup(grammerTable[] grammerTable) throws Exception {
		
		String[][] firstGroup = new String[10][2];
		int x = 0;
		for (int k = 0; k < grammerTable.length; k++) {
			if (!isExitName(firstGroup, grammerTable[k].getName()))// first���Ѿ����ڸ�Ԫ�ص�first��
			{
				firstGroup[x][0] = grammerTable[k].getName();
				x++;
			}
		}
	//	System.out.println("===============================================grammerTable");
		/*for (int i = 0; i < 10; i++) {
			System.out.print(firstGroup[i][0]+" ");
		}*/
	//	System.out.println("===============================================firstGroup");
		boolean flagChange = true;// �ж�first���Ƿ��и��ģ�true��ʾ�и���

		while (flagChange) {
			flagChange = false;

			for (int k = 0; k < grammerTable.length; k++) {
				int flag = 0;
				for (int i = 0; i < firstGroup.length; i++) {
					if (firstGroup[i][0].equals(grammerTable[k].getName())) {
						flag = i;// �ҵ���ǰ�ַ���first������һ��
						// System.out.println("flag= " + flag);
						break;
					}
				}
				String firstValue = grammerTable[k].getValue()[0];
				if (isTerminator(firstValue))// ��һ�����ս��
				{
					// System.out.println("��һ��Ϊ�ս��");
					if (!isExitValue(firstGroup[flag][1], firstValue)) {
						// flagChange = true;
						// System.out.println("\thh\t"+flagChange);
						// System.out.println("��û�� Ҫ���");
						// addEx(firstGroup[flag][1],firstGroup[i][1]);
						if (firstGroup[flag][1] == null) {
							firstGroup[flag][1] = firstValue;
							flagChange = true;
						} else {

							if (!addEx(firstGroup[flag][1], firstValue).equals("")) {
								flagChange = true;
							}
							firstGroup[flag][1] += addEx(firstGroup[flag][1], firstValue);
						}
					}
					// System.out.println("\thh\t"+flagChange);
				} else {// ���ǽK�Y��
					/*
					 * for (int i = 0; i < firstGroup.length; i++) { if
					 * (firstGroup[i][0].equals(firstValue)) {
					 * 
					 * if (!isExitValue(firstGroup[flag][1], firstGroup[i][1])) { //flagChange =
					 * true;
					 * //System.out.println("\tttt\t"+flagChange+"\t"+firstGroup[flag][1]+"\t"+
					 * firstGroup[i][1]); if (firstGroup[flag][1] == null) { flagChange = true;
					 * firstGroup[flag][1] = firstGroup[i][1]; }
					 * 
					 * if(!addEx(firstGroup[flag][1], firstGroup[i][1]).equals("")) { flagChange =
					 * true; } firstGroup[flag][1] += addEx( firstGroup[flag][1], firstGroup[i][1]);
					 * 
					 * } //System.out.println("\tttt\t"+flagChange); } }
					 */
					boolean flagnnull = false;
					String fValue = firstValue;
					int flagnumber = 0;
					for (int i = 0; i < firstGroup.length; i++) {// �ҷ��ս����first��
						// System.out.println("\t"+firstGroup[i][0]+"\t"+firstGroup[i][1]+"\t"+fValue);
						if (firstGroup[i][0].equals(fValue) && !isExitValue(firstGroup[flag][1], firstGroup[i][1])) {// �ҵ�
							// System.out.println("\t"+"\t");
							String strnull[] = firstGroup[i][1].split(" ");
							String newFirst = "";
							for (int z = 0; z < strnull.length; z++) {
								if (strnull[z].equals("no")) {
									flagnnull = true;
								} else {
									newFirst += " " + strnull[z];
								}
							}
							newFirst = newFirst.substring(1, newFirst.length());
							if (!isExitValue(firstGroup[flag][1], newFirst)) {// �жϿ������
								if (firstGroup[flag][1] == null) {
									flagChange = true;
									firstGroup[flag][1] = newFirst;
									// System.out.println("\t"+null);
								}

								if (!addEx(firstGroup[flag][1], newFirst).equals("")) {
									flagChange = true;
									// System.out.println("add "+addEx(firstGroup[flag][1],
									// newFirst).equals("")+"//"+firstGroup[flag][1]+" /"+newFirst+"/");
								}
								firstGroup[flag][1] += addEx(firstGroup[flag][1], newFirst);

							}

						}
					}

					while (flagnnull) {// ����
						// System.out.println("ѭ��");
						if (flagnumber == grammerTable[k].getValue().length - 1) {// ��������ʽ������Ϊ��
							if (firstGroup[flag][1] == null) {
								flagChange = true;
								firstGroup[flag][1] = "no";
							}

							if (!addEx(firstGroup[flag][1], "no").equals("")) {
								flagChange = true;
							}
							firstGroup[flag][1] += addEx(firstGroup[flag][1], "no");
							break;
						}
						flagnumber = flagnumber + 1;
						fValue = grammerTable[k].getValue()[flagnumber];
						if (isTerminator(fValue)) {// ���ս��
							flagnnull = false;
							if (!isExitValue(firstGroup[flag][1], fValue)) {
								if (firstGroup[flag][1] == null) {
									firstGroup[flag][1] = fValue;
									flagChange = true;
								} else {

									if (!addEx(firstGroup[flag][1], fValue).equals("")) {
										flagChange = true;
									}
									firstGroup[flag][1] += addEx(firstGroup[flag][1], fValue);
								}
							}
						} else {// �Ƿ��ս��

							int location2 = this.findFirst(firstGroup, fValue);
							String strnull[] = firstGroup[location2][1].split(" ");
							String newFirst = "";
							for (int z = 0; z < strnull.length; z++) {
								if (strnull[z].equals("no")) {
									flagnnull = true;
								} else {
									flagnnull = false;
									newFirst += " " + strnull[z];
								}
							}
							if (!isExitValue(firstGroup[flag][1], newFirst)) {// �жϿ������
								if (firstGroup[flag][1] == null) {
									flagChange = true;
									firstGroup[flag][1] = newFirst;
								}

								if (!addEx(firstGroup[flag][1], newFirst).equals("")) {
									flagChange = true;
								}
								firstGroup[flag][1] += addEx(firstGroup[flag][1], newFirst);

							}
						}
					}

				}

			}

		}
		return firstGroup;
	}

	/**
	 * ��������ǲ����Ѿ����� ��first�����˵ľͲ����¼�һ����
	 * 
	 * @param first
	 * @param str
	 * @return
	 */
	public boolean isExitName(String[][] first, String str) {
		for (int i = 0; i < first.length; i++) {
			if (first[i][0] == null) {
				continue;
			}
			if (first[i][0].equals(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �жϵ�ǰ����Ҫ�����ֵ�Ƿ��Ѵ���
	 * 
	 * @param oldFirstValue
	 * @param newFirstValue
	 * @return
	 */
	public boolean isExitValue(String oldFirstValue, String newFirstValue) {

		if (oldFirstValue == null) {
			if (newFirstValue == null) {
				return true;
			}
			return false;
		} else {
			if (newFirstValue == null) {
				return true;
			}
			if (oldFirstValue.equals(newFirstValue)) {
				return true;
			}

			return false;

		}
	}

	/**
	 * ���µ�����ɵ�û�е��ַ�����ӽ��ɵ� �����е�value�� ���µ�����û�е�value�����ȥ
	 * 
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
	public String addEx(String oldStr, String newStr) {
		String[] newFlag = newStr.split(" ");
		String[] oldFlag = oldStr.split(" ");
		String a = "";
		// int[] flag;
		for (int j = 0; j < newFlag.length; j++) {
			boolean flag = false;
			for (int k = 0; k < oldFlag.length; k++) {
				if (newFlag[j].equals(oldFlag[k])) {
					flag = true;
				}
			}
			if (!flag) {
				a += " " + newFlag[j];
			}

		}
		return a;
	}

	/**
	 * �ж��Ƿ�Ϊ�ս��
	 * 
	 * @param str
	 * @return
	 */
	public boolean isTerminator(String str) {
		char ch = str.toCharArray()[0];
		if (ch >= 'A' && ch <= 'Z') {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ���follow��
	 * 
	 * @throws Exception
	 */
	public String[][] getFollowGroup() throws Exception {
		grammerTable[] grammerTable = new readDFATable().Wenfa();
		String[][] followGroup = new String[10][2];
		int x = 0;
		for (int k = 0; k < grammerTable.length; k++) {
			if (!isExitName(followGroup, grammerTable[k].getName()))// follow���Ѿ����ڸ�Ԫ�ص�first��
			{
				followGroup[x][0] = grammerTable[k].getName();
				x++;
			}
		}
		for (int i = 0; i < 10; i++) {
			if (followGroup[i][0].equals("P")) {
				followGroup[i][1] = "$";
			}
		}
		boolean flagChange = true;// �ж�follow���Ƿ��и��ģ�true��ʾ�и���

		while (flagChange) {
			flagChange = false;

			for (int k = 0; k < grammerTable.length; k++) {
				/*
				 * ����ÿһ�� ��ÿһ������ �ڶ�����ÿ����һ���ս�� �жϺ����Ƿ�Ϊ�ս�� ������� �����жϺ������ս���Ƿ�Ϊ��
				 */
				int y = grammerTable[k].getValue().length;

				for (int i = 0; i < y; i++) {
					if (i == y - 1)// A->aB ���һ�� follow(A)ȫ����follow��B����
					{
						if (!isTerminator(grammerTable[k].getValue()[i])) {
							int flag1 = findFollow(followGroup, grammerTable[k].getValue()[i]);// B��follow�����±�
							int flag2 = findFollow(followGroup, grammerTable[k].getName());// A��follow�����±�

							// follow(A)ȫ����follow��B����
							if (followGroup[flag1][1] == null) {
								followGroup[flag1][1] = followGroup[flag2][1];
								flagChange = true;
							} else {
								if (followGroup[flag2][1] == null)// AΪ��
								{
									continue;
								} else {
									if (!addEx(followGroup[flag1][1], followGroup[flag2][1]).equals("")) {
										flagChange = true;

									}
									followGroup[flag1][1] += addEx(followGroup[flag1][1], followGroup[flag2][1]);
								}

							}
						}
						continue;
					}
					// �������һ��
					if (!isTerminator(grammerTable[k].getValue()[i]))// ��ǰ�Ƿ��ս��
					{
						int flag1 = findFollow(followGroup, grammerTable[k].getValue()[i]);// B��follow�����±�
						if (isTerminator(grammerTable[k].getValue()[i + 1]))// �������ս�� ֱ�ӽ��ս�����뵽follow��B������
						{
							if (followGroup[flag1][1] == null) {
								followGroup[flag1][1] = grammerTable[k].getValue()[i + 1];
								flagChange = true;
							} else {
								if (!addEx(followGroup[flag1][1], grammerTable[k].getValue()[i + 1]).equals("")) {
									flagChange = true;
								}
								followGroup[flag1][1] += addEx(followGroup[flag1][1],
										grammerTable[k].getValue()[i + 1]);
							}
						} else {// �����Ƿ��ս�� ��������ս����first������follow����
							String[][] firstGroup = getFirstDroup(grammerTable);
							int flag3 = findFirst(firstGroup, grammerTable[k].getValue()[i + 1]);// C��first�����±�
							String[] str = firstGroup[flag3][1].split(" ");// ������ս����first��
							boolean isNo = false;
							String newFirst = "";
							for (int z = 0; z < str.length; z++) {
								if (str[z].equals("no")) {
									isNo = true;
								} else {
									newFirst += " " + str[z];
								}
							}
							if (isNo)// ����пյ� ��T->XC,c���пհ�T��follow�ӵ�x��follow���в���c��first�����ռӵ�x��follow����
							{
								// ��c��first�����ռӵ�x��follow����
								if (followGroup[flag1][1] == null) {
									followGroup[flag1][1] = newFirst;
									flagChange = true;
								} else {
									if (newFirst == "")// AΪ��
									{
										continue;
									} else {
										if (!addEx(followGroup[flag1][1], newFirst).equals("")) {
											flagChange = true;
										}
										followGroup[flag1][1] += addEx(followGroup[flag1][1], newFirst);
									}
								}
								// ��T��follow�ӵ�x��follow����
								int flag4 = findFollow(followGroup, grammerTable[k].getName());// T��follow���е��±�
								if (followGroup[flag1][1] == null) {
									followGroup[flag1][1] = followGroup[flag4][1];
									flagChange = true;
								} else {
									if (followGroup[flag4][1] == null)// AΪ��
									{
										continue;
									} else {
										if (!addEx(followGroup[flag1][1], followGroup[flag4][1]).equals("")) {
											flagChange = true;
										}
										followGroup[flag1][1] += addEx(followGroup[flag1][1], followGroup[flag4][1]);
									}
								}
							} else {// û�п�
								if (followGroup[flag1][1] == null) {
									followGroup[flag1][1] = firstGroup[flag3][1];
									flagChange = true;
								} else {
									if (firstGroup[flag3][1] == null)// AΪ��
									{
										continue;
									} else {
										if (!addEx(followGroup[flag1][1], firstGroup[flag3][1]).equals("")) {
											flagChange = true;
										}
										followGroup[flag1][1] += addEx(followGroup[flag1][1], firstGroup[flag3][1]);
									}
								}
							}
						}
					}
				}

			}
			// System.out.println("\tfollow\t"+flagChange);
		}
		return followGroup;
	}

	/**
	 * �ҷ��ս����follow�����±�
	 * 
	 * @param followGroup
	 * @param value
	 * @return
	 */
	public int findFollow(String[][] followGroup, String value) {
		for (int j = 0; j < followGroup.length; j++) {
			if (followGroup[j][0].equals(value)) {
				return j;
			}
		}
		return -1;

	}

	/**
	 * �ҷ��ս����first�����±�
	 * 
	 * @param firstGroup
	 * @param value
	 * @return
	 */
	public int findFirst(String[][] firstGroup, String value) {
		for (int j = 0; j < firstGroup.length; j++) {
			if (firstGroup[j][0].equals(value)) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * �������� ��һ��״̬��������һ����״̬
	 * 
	 * @param currentInput
	 * @param slrFormulaArray
	 *            ��ǰ�Ĳ���ʽ����
	 * @return �µĲ���ʽ����
	 */
	public ArrayList<SLRFormula> GOTO(String currentInput,
			ArrayList<SLRFormula> slrFormulaArray) {
		ArrayList<SLRFormula> slrFormulaArray2 = new ArrayList<SLRFormula>();
		int count = 0;
		// ��������ʽ���ϵ�ÿһ������ʽ ���������ʽ�ĵ������ǲ�����������ͬ
		for (int k = 0; k < slrFormulaArray.size(); k++) {
			int flag = slrFormulaArray.get(k).getFlag();
			if (flag < slrFormulaArray.get(k).getNextString().length)// ���ǹ�Լ״̬
			{
				if (slrFormulaArray.get(k).getNextString()[flag].equals(currentInput))// ��ͬ����� flag+1
				{
					SLRFormula slr = new SLRFormula();
					SLRFormula old = slrFormulaArray.get(k);
					slr.setBeforeString(old.getBeforeString());
					//slr.setFlag(old.getFlag() + 1);
					slr.setNextString(old.getNextString());
					slrFormulaArray2.add(count, slr);
					slrFormulaArray2.get(count).setFlag(flag + 1);
					count++;
				}
			}

		}
		return slrFormulaArray2;

	}

	/**
	 * �������е�״̬������Ŀ�հ�
	 * 
	 * @param initFormulaArray
	 *            �ʼ����Ŀ���հ�
	 * @param nowFormulaArray
	 *            ��ǰ�Ĳ���ʽ����
	 * @return ���ڵļ�����ӵĲ���ʽ����
	 */
	// ��ǰ��һ���Ƿ��ǳ�ʼ״̬��beforeString �������
	public ArrayList<SLRFormula> CLOSURE(
			ArrayList<SLRFormula> initFormulaArray,
			ArrayList<SLRFormula> nowFormulaArray) {
		int length = nowFormulaArray.size();
		// ������ǰ����ʽ���ϵ�ÿһ������ʽ ���������ʽ�ĵ�����ǲ��Ƿ��ս�� �ǵĻ���һ��һ����
		for (int k = 0; k < length; k++) {
			int flag = nowFormulaArray.get(k).getFlag();
			
			if (flag < nowFormulaArray.get(k).getNextString().length)// ���ǹ�Լ״̬
			{
				String next = nowFormulaArray.get(k).getNextString()[flag];// �������ַ�
				
				if (!isTerminator(next))// �����ս�� //���ÿ�����ĺ��棡������
				{
					
					for (int i = 0; i < initFormulaArray.size(); i++)// �ڳ�ʼ״̬��beforeStringһ����
					{
						if (initFormulaArray.get(i).getBeforeString().equals(next))// �ҵ�����Ӧ�ļ��뵽����ʽ������
						{
							boolean a=isExitFormula(nowFormulaArray,initFormulaArray.get(i));
							
							if(!a)
							{
								nowFormulaArray.add(length, initFormulaArray.get(i));
							}
							
						
						}
					}
					/*
					 * slrFormulaArray.get(k).setFlag(flag+1);
					 * slrFormulaArray2.add(count,slrFormulaArray.get(k));
					 */
				}
			}
		}
		return nowFormulaArray;

	}

	//����ʽ�Ƿ����
	public boolean isExitFormula(ArrayList<SLRFormula> theFormulaArray,SLRFormula theFormula)
	{
		for(int i=0;i<theFormulaArray.size();i++)
		{
			if(theFormulaArray.get(i).getBeforeString().equals(theFormula.getBeforeString()))
			{
				if(theFormulaArray.get(i).getFlag()==theFormula.getFlag())
				{
					String[] str1=theFormulaArray.get(i).getNextString();
					String[] str2=theFormula.getNextString();
					StringBuffer sb1 = new StringBuffer();
					StringBuffer sb2 = new StringBuffer();
					for (int y = 0; y < str1.length; y++) {
						sb1.append(str1[y]);
					}
					String s1 = sb1.toString();

					for (int y = 0; y < str2.length; y++) {
						sb2.append(str2[y]);
					}
					String s2 = sb2.toString();
					if (s1.equals(s2)) {
						return true;
					}
					
				}
			}
		}
		
		return false;
	}
	/**
	 * �ж��Ƿ����״̬���״̬һģһ�� �ǾͲ����½�һ��״̬��
	 * 
	 * @param allState
	 *            ���е�����״̬
	 * @param nowFormulaArray
	 *            Ҫ�жϵ�״̬
	 * @return ��ͬ��״̬�ţ�û����ͬ�ķ���-1
	 */
	public int isExitTheSameState(ArrayList<ArrayList<SLRFormula>> allState,
			ArrayList<SLRFormula> nowFormulaArray) {
		// ������״̬���� �ҵ�����ͬ����ʽ���ϵ�״̬ ����״̬���
		int len = nowFormulaArray.size();
		for (int i = 0; i < allState.size(); i++) {// ��i��״̬
			int[] flag = new int[len];
			for (int k = 0; k < len; k++)// ��ǰ����ʽ���ϵĵ�k������ʽ
			{
				for (int j = 0; j < allState.get(i).size(); j++) {// ״̬��ĵ�j������ʽ
																	// ����ҵ�����1
																	// ��ʼ��һ��ѭ��
					if (allState.get(i).get(j).getBeforeString()
							.equals(nowFormulaArray.get(k).getBeforeString())) {
						if (allState.get(i).get(j).getFlag() == nowFormulaArray
								.get(k).getFlag()) {
							String[] str1 = allState.get(i).get(j)
									.getNextString();
							String[] str2 = nowFormulaArray.get(k)
									.getNextString();
							StringBuffer sb1 = new StringBuffer();
							StringBuffer sb2 = new StringBuffer();
							for (int y = 0; y < str1.length; y++) {
								sb1.append(str1[y]);
							}
							String s1 = sb1.toString();

							for (int y = 0; y < str2.length; y++) {
								sb2.append(str2[y]);
							}
							String s2 = sb2.toString();
							if (s1.equals(s2)) {
								flag[k] = 1;// ��k�����ҵ�
								break;// ������������һ��
							}
						}
					} else {
						flag[k] = 0;
					}
				}
			}
			boolean f = true;
			for (int x = 0; x < len; x++) {
				if (flag[x] != 1) {
					f = false;
					break;
				}
			}
			if (f&&allState.get(i).size()==len) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * �ж�����״̬�Ƿ�һģһ�� �ǾͲ����½�һ��״̬��
	 * 
	 * @param allState
	 *            ���е�����״̬
	 * @param nowFormulaArray
	 *            Ҫ�жϵ�״̬
	 * @return ��ͬ��״̬�ţ�û����ͬ�ķ���-1
	 */
	public boolean isTheSameState(ArrayList<SLRFormula> FormulaArray1,
			ArrayList<SLRFormula> FormulaArray2) {
		// ������״̬���� �ҵ�����ͬ����ʽ���ϵ�״̬ ����״̬���
		int len = FormulaArray2.size();
		int[] flag = new int[len];
			for (int k = 0; k < len; k++)// ��ǰ����ʽ���ϵĵ�k������ʽ
			{
				for (int j = 0; j < FormulaArray1.size(); j++) {
					if (FormulaArray1.get(j).getBeforeString()
							.equals(FormulaArray2.get(k).getBeforeString())) {
						if (FormulaArray1.get(j).getFlag() == FormulaArray2
								.get(k).getFlag()) {
							String[] str1 =FormulaArray1.get(j)
									.getNextString();
							String[] str2 = FormulaArray2.get(k)
									.getNextString();
							StringBuffer sb1 = new StringBuffer();
							StringBuffer sb2 = new StringBuffer();
							for (int y = 0; y < str1.length; y++) {
								sb1.append(str1[y]);
							}
							String s1 = sb1.toString();

							for (int y = 0; y < str2.length; y++) {
								sb2.append(str2[y]);
							}
							String s2 = sb2.toString();
							if (s1.equals(s2)) {
								flag[k] = 1;// ��k�����ҵ�
								break;// ������������һ��
							}
						}
					} else {
						flag[k] = 0;
					}
				}
			}
			boolean f = true;
			for (int x = 0; x < len; x++) {
				if (flag[x] != 1) {
					f = false;
					break;
				}
			}
			if (f&&FormulaArray1.size()==len) {
				return true;
			}
		return false;
	}

	/**
	 * ������Լ״̬ �ҵ��涨�ı��ʽ
	 * 
	 * @param slrFormula
	 *            ��ǰҪ�жϵ�Ϊ��Լ״̬�Ĳ���ʽ
	 * @param grammerTable
	 *            �﷨��
	 * @return ��ԼΪ��һ���﷨����ʽ
	 */
	public int findActionNum(SLRFormula slrFormula, grammerTable[] grammerTable) {
		for (int i = 0; i < grammerTable.length; i++) {
			if (slrFormula.getBeforeString().equals(grammerTable[i].getName()))// ��ʼһ��
			{
				boolean flag = true;
				for (int j = 0; j < grammerTable[i].getValue().length; j++) {
					if (!grammerTable[i].getValue()[j].equals(slrFormula
							.getNextString()[j])) {
						flag = false;
						break;
					}
				}
				if(grammerTable[i].getValue().length!=slrFormula.getNextString().length)
				{
					flag = false;
				}
				if (flag) {
					return i;
				}
			}

		}
		return -1;
	}
	
	public ArrayList<ACTIONTable> getSLRTable(grammerTable[] grammerTable) throws Exception {
		int stateCount = 0;
		// new action��� goto��
		ArrayList<ACTIONTable> actionTable = new ArrayList<ACTIONTable>();
		ArrayList<ArrayList<SLRFormula>> slrStateArray = new ArrayList<ArrayList<SLRFormula>>();
		ArrayList<SLRFormula> slrFormulArray0 = new ArrayList<SLRFormula>();
		ArrayList<SLRFormula> slrFormulArray1 = new ArrayList<SLRFormula>();
		String[][] followGroup=getFollowGroup();
		
		// ��ʼ�� ��һ������ʽ����
		for (int i = 0; i < grammerTable.length; i++) {
			SLRFormula slrFormula = new SLRFormula();
			slrFormula.setBeforeString(grammerTable[i].getName());
			slrFormula.setNextString(grammerTable[i].getValue());
			slrFormula.setFlag(0);
			slrFormulArray0.add(i, slrFormula);
		}
		slrStateArray.add(stateCount, slrFormulArray0);// ��һ��״̬
		stateCount++;
		
		SLRFormula slrFormula2 = new SLRFormula();
		slrFormula2.setBeforeString("Z");//����ʽ Z->P
		String bb="P";
		slrFormula2.setNextString(bb.split(" "));
		slrFormula2.setFlag(0);
		slrFormulArray1.add(0, slrFormula2);
		boolean isEnough;
		int arrayLen=0;
		do{
			isEnough=true;
			arrayLen=slrFormulArray1.size();
			slrFormulArray1 = CLOSURE(slrStateArray.get(0), slrFormulArray1);
			if(arrayLen!=slrFormulArray1.size())
			{
				isEnough=false;
			}
		}while(!isEnough);
		
		slrStateArray.add(stateCount, slrFormulArray1);// ��һ��״̬
		stateCount++;
		
		for(int k=0;k<slrStateArray.size();k++)
		{ 
			for (int i = 0; i < slrStateArray.get(k).size(); i++) {
				//System.out.print(slrStateArray.get(k).get(i).getBeforeString() + " "+ " ");
				for (int j = 0; j < slrStateArray.get(k).get(i).getNextString().length; j++)
	            {
				//	System.out.print(slrStateArray.get(k).get(i).getNextString()[j] + " ");
				}
			  //  System.out.println(" " + slrStateArray.get(k).get(i).getFlag());
		   }
		//	System.out.println("==================================");
		}
	    // ��֤����
	//	System.out.println("==================================");

		String currentInput = null;
		// ��ÿһ��״̬ ������� ����һ��״̬
		//��ʼ��״̬Ϊ1
		for (int i = 1; i < slrStateArray.size(); i++) {
			for (int j = 0; j < slrStateArray.get(i).size(); j++) 
			{
				
				int flag = slrStateArray.get(i).get(j).getFlag();// ����ʽ�ı�ʶλ
				// flag �����һ�� ���ǹ�Լ״̬
				if(flag >= slrStateArray.get(i).get(j).getNextString().length)
				{
					//��Լ
					//��follow�еĶ���Լ
					//System.out.println("״̬ "+i+"��Լ");
					//System.out.println(slrStateArray.get(i).get(j).getBeforeString());
					if(slrStateArray.get(i).get(j).getBeforeString().equals("Z"))
					{
						ACTIONTable ac = new ACTIONTable();
						ac.setInput("$");
						ac.setState(i);
						String a = "acc "+0;
						// ���ݵ�ǰ��ʽ���ҵ���Լʽ�ӵ����
						ac.setAction(a.split(" "));
						actionTable.add(ac);
						continue;
					}
					int loca=findFollow(followGroup,slrStateArray.get(i).get(j).getBeforeString());
					//System.out.println(loca);
					String follow[]=followGroup[loca][1].split(" ");
					for(int q=0;q<follow.length;q++)
					{
						if(follow[q].equals(""))
						{
							continue;
						}
						ACTIONTable ac = new ACTIONTable();
						ac.setInput(follow[q]);
						ac.setState(i);
						int num = findActionNum(slrStateArray.get(i).get(j),grammerTable);
						String a = "r " + num;
						// ���ݵ�ǰ��ʽ���ҵ���Լʽ�ӵ����
						ac.setAction(a.split(" "));
						if(!isExitAction(actionTable,ac)){
							actionTable.add(ac);
						}
					}
					continue;
			  }
					// ����Ϊ�����һ���ַ� ����Ӧ���ж��Ƿ�������� ||֮���ж��Ƿ�������һģһ����״̬
					currentInput = slrStateArray.get(i).get(j).getNextString()[flag];// �����һ���ַ�
					//�Ƿ�Ϊ�� �� A->no  0
					if(currentInput.equals("no"))
					{
						//c->no �ҵ�c��follow�� ��ӹ�Լ
						int loca=findFollow(followGroup,slrStateArray.get(i).get(j).getBeforeString());
						
						String follow[]=followGroup[loca][1].split(" ");
						for(int q=0;q<follow.length;q++)
						{
							if(follow[q].equals(""))
							{
								continue;
							}
							ACTIONTable ac = new ACTIONTable();
							ac.setInput(follow[q]);
							ac.setState(i);
							int num = findActionNum(slrStateArray.get(i).get(j),grammerTable);
							String a = "r " + num;
							// ���ݵ�ǰ��ʽ���ҵ���Լʽ�ӵ����
							ac.setAction(a.split(" "));
							if(!isExitAction(actionTable,ac)){
								actionTable.add(ac);
							}
						}
						continue;
					}
					//��ǰ����ʽ����һ������
					/*ArrayList<SLRFormula> newFormulaArray=new ArrayList<SLRFormula>();
					newFormulaArray=nextFormulaArray(slrStateArray.get(i),slrStateArray.get(0));
					for(int x=0;x<newFormulaArray.size();x++)
					{
						newFormulaArray=nextFormulaArray(newFormulaArray,slrStateArray.get(0));
					}*/
					ArrayList<SLRFormula> slrFormulaDemo = GOTO(currentInput,
							slrStateArray.get(i));
					boolean isEnough2;
					ArrayList<SLRFormula> slrFormulaDemo2=new ArrayList<SLRFormula>();
					int arrayLen2=0;
					do{
						isEnough2=true;
						arrayLen2=slrFormulaDemo.size();
					//	System.out.println("��ǰ���� "+arrayLen2);
						slrFormulaDemo2 = CLOSURE(slrStateArray.get(0), slrFormulaDemo);
					//	System.out.println("֮�󳤶� "+slrFormulaDemo2.size());
						if(arrayLen2!=slrFormulaDemo2.size())
						{
							isEnough2=false;
						}
					}while(!isEnough2);
					//ArrayList<SLRFormula> slrFormulaDemo2 = CLOSURE(slrStateArray.get(0), slrFormulaDemo);
					/*for(int x=0;x<slrFormulaDemo2.size();x++)
					{
						slrFormulaDemo2=CLOSURE(slrStateArray.get(0),slrFormulaDemo2);
					}*/
					//�������ʽ�����Ƿ��Ѿ�������ͬ�ļ�����  �Ƿ���״̬��  û�з���-1
					int sameState = isExitTheSameState(slrStateArray,slrFormulaDemo2);
					
					if (sameState == -1)// �ж����״̬�ǲ����Ѿ����� û��
					{
						//System.out.println("��������ͬ״̬=====================================");
						slrStateArray.add(stateCount, slrFormulaDemo2);// ��һ��״̬
						stateCount++;
					} else {
						//System.out.println("������ͬ״̬====================================="+ sameState);
					}
					if (isTerminator(currentInput))// ���ս�� ����action��
					{
						ACTIONTable ac = new ACTIONTable();
						ac.setInput(currentInput);
						ac.setState(i);
						String a;
						if (sameState == -1) {
							a = "s " + (stateCount-1);
						} else {
							a = "s " + sameState;
						}
						ac.setAction(a.split(" "));
						if(!isExitAction(actionTable,ac)){
							actionTable.add(ac);
						}
					} else {// Ϊ���ս������goto��
						ACTIONTable ac = new ACTIONTable();
						ac.setInput(currentInput);
						ac.setState(i);
						String a;
						if (sameState == -1) {
							a = "h " + (stateCount-1);
						} else {
							a = "h " + sameState;
						}
						ac.setAction(a.split(" "));
						if(!isExitAction(actionTable,ac)){
							actionTable.add(ac);
						}
					}
			}

		}
		for (int i = 0; i < slrStateArray.size(); i++) {
		//	System.out.println("״̬ " + i);
			for (int j = 0; j < slrStateArray.get(i).size(); j++) {
			//	System.out.print("|  "+ slrStateArray.get(i).get(j).getBeforeString()+ "-> ");
				int a = slrStateArray.get(i).get(j).getNextString().length;
				for (int k = 0; k < a; k++) {
			//		System.out.print(slrStateArray.get(i).get(j).getNextString()[k]+ " ");
				}
			//	System.out.print(slrStateArray.get(i).get(j).getFlag() + "  |");
			}
		//	System.out.println();
		}
	//	System.out.println("actionTable.size()========"+actionTable.size());
		actionTable=addError(actionTable,slrStateArray);
		ActionTest(actionTable);
		return actionTable;
	}
	
	public ArrayList<ACTIONTable> addError(ArrayList<ACTIONTable> actionTable,ArrayList<ArrayList<SLRFormula>> slrStateArray){
		for(int k=1;k<slrStateArray.size();k++)
		{
			boolean isExit1=false;
			boolean isExit11=false;
			boolean isExit111=false;
			boolean isExit2=false;
			boolean isExit3=false;
			boolean isExit33=false;
			boolean isExit333=false;
			//boolean isExit4=false;
			for(int q=0;q<actionTable.size();q++)
			{
				if(actionTable.get(q).getState()==k)
				{
					if(actionTable.get(q).getInput().equals("+"))
					{
						isExit1=true;
					}
					if(actionTable.get(q).getInput().equals("*"))
					{
						isExit11=true;
					}
					if(actionTable.get(q).getInput().equals("$"))
					{
						isExit111=true;
					}
					else if(actionTable.get(q).getInput().equals(")"))
					{
						isExit2=true;
					}
					if(actionTable.get(q).getInput().equals("id")||actionTable.get(q).getInput().equals("digit")||actionTable.get(q).getInput().equals("("))
					{
						isExit3=true;
					}
					if(actionTable.get(q).getInput().equals("digit"))
					{
						isExit33=true;
					}
					if(actionTable.get(q).getInput().equals("("))
					{
						isExit333=true;
					}
					
			
				}
				
			}
			if(!isExit1)
			{
				ACTIONTable table=new ACTIONTable();
				table.setState(k);
				table.setInput("+");
				String a = "e " +1;
				table.setAction(a.split(" "));
				actionTable.add(table);
			}
			if(!isExit11)
			{
				ACTIONTable table2=new ACTIONTable();
				table2.setState(k);
				table2.setInput("*");
				String b = "e " +1;
				table2.setAction(b.split(" "));
				actionTable.add(table2);
			}
			if(!isExit111)
			{
				ACTIONTable table3=new ACTIONTable();
				table3.setState(k);
				table3.setInput("$");
				String c = "e " +1;
				table3.setAction(c.split(" "));
				actionTable.add(table3);
			}
			if(!isExit2)
			{
				ACTIONTable table=new ACTIONTable();
				table.setState(k);
				table.setInput(")");
				String a = "e " +2;
				table.setAction(a.split(" "));
				actionTable.add(table);
			}
			if(!isExit3)
			{
				ACTIONTable table=new ACTIONTable();
				table.setState(k);
				table.setInput("id");
				String a = "e " +3;
				table.setAction(a.split(" "));
				actionTable.add(table);
				
				
			}
			if(!isExit33)
			{
				ACTIONTable table2=new ACTIONTable();
				table2.setState(k);
				table2.setInput("digit");
				String b = "e " +3;
				table2.setAction(b.split(" "));
				actionTable.add(table2);
			}
			if(!isExit333)
			{
				ACTIONTable table3=new ACTIONTable();
				table3.setState(k);
				table3.setInput("(");
				String c = "e " +3;
				table3.setAction(c.split(" "));
				actionTable.add(table3);
			}
			
		}
		return actionTable;
	}
	public void ActionTest(ArrayList<ACTIONTable> actionTable){
		for (int i = 0; i < actionTable.size(); i++) {
			//System.out.print("״̬: " + actionTable.get(i).getState());
		//	System.out.print("  ���� :" + actionTable.get(i).getInput());
			String[] str1 =actionTable.get(i).getAction();
			StringBuffer sb1 = new StringBuffer();
			for (int y = 0; y < str1.length; y++) {
				if(!str1[y].equals("h"))
				{
					sb1.append(str1[y]);
				}
				
			}
		//	String s1 = sb1.toString();
		//	System.out.println("  ת��:" + s1);
		//	System.out.println("  ����   " + actionTable.get(i).getState());
		//	System.out.println("  ����   " + getColumn(actionTable.get(i).getInput()));
		//	slrListTbModel.setValueAt(s1,actionTable.get(i).getState(), getColumn(actionTable.get(i).getInput()));
		//	System.out.println();
		}
	}

	public int getColumn(String str){
		switch (str){
		case "+":
			return 1;
		case "*":
			return 2;
		case "-":
			return 3;
		case "(":
			return 4;
		case "id":
			return 5;
		case "digit":
			return 6;
		case "=":
			return 7;
		case "call":
			return 8;
		case ")":
			return 9;
		case "[":
			return 10;
		case "]":
			return 11;
		case ",":
			return 12;
		case ";":
			return 13;
		case ">":
			return 14;	
		case "true":
			return 15;
		case "false":
			return 16;
		case "not":
			return 17;
		case "and":
			return 18;
		case "or":
			return 19;
		case "if":
			return 20;
		case "then":
			return 21;
		case "else":
			return 22;
		case "while":
			return 23;
		case "do":
			return 24;
		case "proc":
			return 25;
		case "record":
			return 26;
		case "integer":
			return 27;
		case "real":
			return 28;
		case "$":
			return 29;
		case "S":
			return 30;
		case "E":
			return 31;
		case "L":
			return 32;
		case "F":
			return 33;
		case "B":
			return 34;
		case "P":
			return 35;
		case "D":
			return 36;
		case "X":
			return 37;
		case "C":
			return 38;
		case "T":
			return 39;
		default:
			return 0;
		}
		//return -1;
	}
	public boolean isExitAction(ArrayList<ACTIONTable> actionTable,ACTIONTable action)
	{
		for(int i=0;i<actionTable.size();i++)
		{
			if(actionTable.get(i).getState()==action.getState()&&actionTable.get(i).getInput().equals(action.getInput()))
			{
				String[] str1 =actionTable.get(i).getAction();
				StringBuffer sb1 = new StringBuffer();
				for (int y = 0; y < str1.length; y++) {
					sb1.append(str1[y]);
				}
				String s1 = sb1.toString();
				String[] str2 =action.getAction();
				StringBuffer sb2 = new StringBuffer();
				for (int y = 0; y < str2.length; y++) {
					sb2.append(str2[y]);
				}
				String s2 = sb2.toString();
				if(s2.equals(s1))
				{
					return true;
				}
			}
		}
        return false;
	}
	public boolean isExitGoto(ArrayList<GOTOTable> gotoTable ,GOTOTable g)
	{
		for(int i=0;i<gotoTable.size();i++)
		{
			if(gotoTable.get(i).getState()==g.getState()&&gotoTable.get(i).getInput().equals(g.getInput())&&g.getNextState()==gotoTable.get(i).getNextState())
			{
					return true;
			}
		}
		return false;
	}
	
	
	public String[] findNext (ArrayList<ACTIONTable> slrTable,int state,String input)
	{
		String[] a=new String[2];
		if(state==77)
		{
			for(int i=slrTable.size()-1;i>=0;i--)
			{
				if(slrTable.get(i).getState()==state&&slrTable.get(i).getInput().equals(input))
				{
					for(int j=0;j<slrTable.get(i).getAction().length;j++)
					{
						a[j]=slrTable.get(i).getAction()[j];
					}
					
					return a; 
				}
			}
		}
		for(int i=0;i<slrTable.size();i++)
		{
			if(slrTable.get(i).getState()==state&&slrTable.get(i).getInput().equals(input))
			{
				//System.out.println("i="+i);
			//	System.out.println("slrTable.get(i).getInput()"+slrTable.get(i).getInput());
				for(int j=0;j<slrTable.get(i).getAction().length;j++)
				{
					//System.out.println("action wei");
					//System.out.println(slrTable.get(i).getAction()[j]);
				//	System.out.println(slrTable.get(i).getAction()[j]);
					a[j]=slrTable.get(i).getAction()[j];
				}
				
				return a; 
			}
		}
		
		//System.out.println("û�ҵ�");
		return a;
	}
	
	public ArrayList<SLRTree> slrTest(ArrayList<String[]> input,ArrayList<ACTIONTable> slrTable) throws Exception{
		while (syntaxListTbModel.getRowCount() > 0) {
			syntaxListTbModel.removeRow(0);
		}
		while (grammererrorListTbModel.getRowCount() > 0) {
			grammererrorListTbModel.removeRow(0);
		}
		//addrArea.setText("");
		ArrayList<SLRTree> slrTreeArray=new ArrayList<SLRTree>();
		String inputStr=" ";
		for(int i=0;i<input.size();i++){
		 SLRTree slrTree =new SLRTree();
		 slrTree.setName(input.get(i)[0]);
		 String a="-1";
		 String[] b=a.split(" ");
		 slrTree.setChildId(b);
		 slrTreeArray.add(slrTree);
	     }
		 for(int i=0;i<input.size();i++){
			 if(input.get(i)[0].equals("$")||isKeyword(input.get(i)[0]))//call interger real and not or proc record
			 {
				 inputStr +=input.get(i)[0]+" ";
			 }
			 else if(input.get(i)[1].equals("�ؼ���")||input.get(i)[1].equals("��ʶ��")){
				 inputStr +="id"+" ";
				 SLRTree slrTree2 =new SLRTree();
				 slrTree2.setName("id");
				 String aa=String.valueOf(i);
				 String[] bb=aa.split(" ");
				 slrTree2.setChildId(bb);
				 slrTreeArray.add(slrTree2);
			 }
			 else if(input.get(i)[1].indexOf("��")!=-1)
			 {
				 inputStr +="digit"+" ";
				 SLRTree slrTree2 =new SLRTree();
				 slrTree2.setName("digit");
				 String aa=String.valueOf(i);
				 String[] bb=aa.split(" ");
				 slrTree2.setChildId(bb);
				 slrTreeArray.add(slrTree2);
			 }
			 /*else if(input.get(i)[1].equals("�����"))
			 {
				 inputStr +="relop"+" ";
			 }*/
			 else if(input.get(i)[1].equals("ע��"))
			 {
				 inputStr +=input.get(i)[0]+" ";
			 }
			 else{
				 inputStr +=input.get(i)[0]+" ";
			 }
		 }
		 System.out.println("inputStr��"+inputStr);
		// System.out.println(inputStr);
		 grammerTable[] grammerTable = new readDFATable().Wenfa();
		//���ڱ������ջ
		 Stack<Integer>  treeStack = new Stack<Integer>(); 
		 treeStack.push(-1);
		 //״̬ջ
		 Stack<Integer> stateStack = new Stack<Integer>();
		 stateStack.push(1);
		 //����ջ
		 Stack<String> charStack = new Stack<String>();
		 charStack.push("$");
		 //����ջ
		 Stack<String> inputStack = new Stack<String>(); 
		 inputStack.push("$");
		 
		 String[] inputStr2=inputStr.split(" ");
		 for(int i=inputStr2.length-1;i>0;i--)
		 {
		//	 System.out.print(inputStr2[i]+"  ");
			 inputStack.push(inputStr2[i]);
			 //System.out.println("����ջ����״̬��"+inputStack.peek()); 
		 }
		 System.out.println();
		 /********************��ʼ�����***********************/
		 System.out.println("���Կ�ʼ======================");
		 int sign=0;
		 boolean ise1=false;
		 do{
			 System.out.println("======================");
			 System.out.println("״̬ջ����״̬��"+stateStack.peek());
			 System.out.println("����ջ����״̬��"+charStack.peek());
			 System.out.println("����ջ����״̬��"+inputStack.peek()); 
			 System.out.println("��ջ����״̬��"+treeStack.peek()); 
			 String next[]=findNext(slrTable,stateStack.peek(),inputStack.peek());
			 System.out.println("======================");
			 if(next[0]==null)
			 {
				 System.out.println("û�ҵ�  "+inputStack.peek());
				 inputStack.pop();
				 sign=sign+1;
				 continue;
			 }
			 if(next[0].equals("acc"))
			 {
				 System.out.println("�ɹ�����������������������������");
				// syntaxListTbModel.addRow(new Object[] { output.get(j), "��"+j+"��" });
				 break;
			 }
			 //s 3�ƽ�   ����ջ��һ��������ջ������ �� ״̬ջΪ��һ��״̬
			 if(next[0].equals("s"))
			 {
				 System.out.print("�ƽ�");
				 System.out.println("   ��һ״̬��"+next[1]);
				 charStack.push(inputStack.peek());
				 inputStack.pop();
				 if(ise1)
				 {
					 treeStack.push(slrTreeArray.size()-2);
					 ise1=false; 
				 }
				 else{
					 treeStack.push(getFatherId(slrTreeArray,sign));
					 sign=sign+1;
					 ise1=false;
				 }
				 stateStack.push(Integer.parseInt(next[1]));
				 
				 
			 }
			 //r 3 ��Լ   ����ջ��һ��������ջ������ �� ״̬ջΪ��һ��״̬
			 else if(next[0].equals("r"))//����Ҫ����
			 {
				 System.out.println("��Լ");
				 int num = Integer.parseInt(next[1]);
				 String out="";
				 String tree="";
				 if(!grammerTable[num].getValue()[0].equals("no"))
				 {
					 int len=grammerTable[num].getValue().length;
					 for(int k=0;k<len;k++)
					 {
						 out=charStack.peek()+" "+out;
						 tree=tree+treeStack.peek()+" ";
						 charStack.pop();
						 stateStack.pop();
						 treeStack.pop();
					 }
				 }
				 if(tree.equals(""))
				 {
					 tree="-1";
				 }
				 SLRTree slrTree =new SLRTree();
				 slrTree.setName(grammerTable[num].getName());
				 String a=tree;
				 String[] b=a.split(" ");
				 slrTree.setChildId(b);
				 slrTreeArray.add(slrTree);
				 
				 syntaxListTbModel.addRow(new Object[] { grammerTable[num].getName()+"->"+out});
				 charStack.push(grammerTable[num].getName());
				 String next2[]=findNext(slrTable,stateStack.peek(),charStack.peek());
				 stateStack.push(Integer.parseInt(next2[1]));
				 treeStack.push(slrTreeArray.size()-1);
			 }
			 else if(next[0].equals("e")){
				 int num = Integer.parseInt(next[1]);
				 System.out.println("���� "+num);
				 if(num==2)
				 {
					 grammererrorListTbModel.addRow(new Object[]{"��"+getLine(sign)+"�д���",inputStack.peek(),"��ƥ��������"});
					 inputStack.pop();
					 sign=sign+1;
				 }
				 else if(num==1)
				 {
					 grammererrorListTbModel.addRow(new Object[]{"��"+getLine(sign)+"�д���",inputStack.peek(),"ȱ���������",""});
					 inputStack.push("id");
					 SLRTree slrTree =new SLRTree();
					 slrTree.setName("id");
					 String a="-1";
					 String[] b=a.split(" ");
					 slrTree.setChildId(b);
					 slrTreeArray.add(slrTree);
					 ise1=true;
				 }
				 else if(num==3)
				 {
					 grammererrorListTbModel.addRow(new Object[]{"��"+getLine(sign)+"�д���",inputStack.peek(),"ȱ�������"});
					 inputStack.push("+");
					 SLRTree slrTree =new SLRTree();
					 slrTree.setName("+");
					 String a="-1";
					 String[] b=a.split(" ");
					 slrTree.setChildId(b);
					 slrTreeArray.add(slrTree);
					 ise1=true;
				 }
				 
			 }
		 }while(true);
		 for(int i=0;i<slrTreeArray.size();i++)
		 {
			 System.out.print("��� "+i);
			 System.out.print(" Ԫ��"+slrTreeArray.get(i).getName());
			 for(int j=0;j<slrTreeArray.get(i).getChildId().length;j++)
			 {
				 System.out.print("  ���ӱ�ţ�"+slrTreeArray.get(i).getChildId()[j]);
			 }
			 System.out.println();
		 }
		 for(int i=0;i<slrTreeArray.size();i++)
		 {
			 System.out.print("��� "+i);
			 System.out.print(" Ԫ��"+slrTreeArray.get(i).getName());
			 System.out.print("  ���ӣ�");
			 for(int j=slrTreeArray.get(i).getChildId().length-1;j>=0;j--)
			 {
				 if(slrTreeArray.get(i).getChildId()[j].equals("-1"))
				 {
					 System.out.print("��");
					 break;
				 }
				 System.out.print(" "+slrTreeArray.get(Integer.parseInt(slrTreeArray.get(i).getChildId()[j])).getName());
			 }
			 System.out.println();
		 }
		// getAnalysisTree(slrTreeArray);
		return slrTreeArray;
		 }
	
	
	public int getFatherId(ArrayList<SLRTree> slrTreeArray,int childId){
		for(int i=0;i<slrTreeArray.size();i++)
		 {
			for(int j=0;j<slrTreeArray.get(i).getChildId().length;j++)
			 {
				//System.out.println("slrTreeArray.get(i).getChildId()[j]="+slrTreeArray.get(i).getChildId()[j]);
				if(slrTreeArray.get(i).getChildId()[j].equals(String.valueOf(childId)))
				{
					return i;
				}
			 }
		 }
		return childId;
	}
	
	/**
	 * �����ģ��
	 * @param slrTreeArray
	 * @param num
	 * @return
	 */
	public DefaultTreeModel getTreeModel(ArrayList<SLRTree> slrTreeArray,int num){
		//System.out.println(num);
		String fatherStr=slrTreeArray.get(num).getName();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(fatherStr);
		//System.out.println("root="+fatherStr);
		DefaultTreeModel treeModel1 = new DefaultTreeModel(root);
		for(int j=0;j<slrTreeArray.get(num).getChildId().length;j++)
		{
			int childID=Integer.parseInt(slrTreeArray.get(num).getChildId()[j]);
			
		//	System.out.println("root childID:"+childID);
			if(childID!=-1)
			{
				String childName=slrTreeArray.get(childID).getName();
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childName);
				//DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childID);
			//	treeModel1.insertNodeInto(childNode,root,root.getChildCount());
				//getMutableTreeNode(childNode,childID,slrTreeArray);
				treeModel1.insertNodeInto(
						getMutableTreeNode(childNode,childID,slrTreeArray),
						root,
						j);
				//System.out.println("treeModel1�ɹ�");
			}
		}
		return treeModel1;
	}
	/**
	 * ��������
	 * @param fathNode
	 * @param num
	 * @param slrTreeArray
	 * @return
	 */
	public MutableTreeNode getMutableTreeNode(MutableTreeNode fathNode,int num,ArrayList<SLRTree> slrTreeArray){
		//System.out.println("**************��ʼ************");
	//	System.out.println("fathNode:"+fathNode);
		for(int j=0;j<slrTreeArray.get(num).getChildId().length;j++)
		{
		//	System.out.println("j="+j);
			int childID=Integer.parseInt(slrTreeArray.get(num).getChildId()[j]);
			
		//	System.out.println("childID="+childID);
			if(childID!=-1)
			{
				String childName=slrTreeArray.get(childID).getName();
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childName);
				MutableTreeNode childchildNode=getMutableTreeNode(childNode,childID,slrTreeArray);
				fathNode.insert(childchildNode,fathNode.getChildCount());
			//	System.out.println("fathNode"+fathNode);
			}
		}
		return fathNode;
	}
	
	/**
	 * ��õ�ǰ����
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public int getLine(int count) throws Exception{
		String str=textArea.getText();
		String[] a=str.split("\n");
		int len=0;
		for(int i=0;i<a.length;i++)
		{
			len+=lexicalAnalysis(a[i]).size();
			if(len>count)
			{
				return i+1;
			}
		}
		return 0;
	}
	
	/**
	 * ���������ʼ
	 * @throws Exception ****************************
	 */
	
public void initRecord(ArrayList<RreeSemanticRecord> treeSemanticRecord,ArrayList<SLRTree> slrTreeArray){
		
		for(int i=0;i<slrTreeArray.size();i++)
		{
			int firstchildID=Integer.parseInt(slrTreeArray.get(i).getChildId()[0]);
			if(firstchildID==-1)
			{
				int fatherId=getFatherId(slrTreeArray,i);
				String fatherName=slrTreeArray.get(fatherId).getName();
				if(fatherName.equals("id")||fatherName.equals("digit"))
				{
					RreeSemanticRecord r=new RreeSemanticRecord();
					r.setTreeNodeNum(fatherId);
					r.setTreeNodeName(fatherName);
					r.setProperty("lexeme");
					r.setValue(slrTreeArray.get(i).getName());
					treeSemanticRecord.add(r);
				}
			}
		}
		RreeSemanticRecord r=new RreeSemanticRecord();
		r.setTreeNodeNum(slrTreeArray.size()+1);
		r.setTreeNodeName("t");
		r.setProperty("t");
		r.setValue("ceshi");
		treeSemanticRecord.add(r);
	}
	/**
	 * 
	 * @param grammerTable����ʽ��
	 * @param slrTreeArray��������õ�����
	 * @param treeSemanticRecord��¼�����������Ϣ�ļ���
	 * @param grammersemanticLoca���ĵ��ı��¼ÿ������ʽ������������Ƭ��
	 * @param num��ǰ�����
	 * @throws Exception
	 */
	public void semanticTest(grammerTable[] grammerTable,ArrayList<SLRTree> slrTreeArray,ArrayList<RreeSemanticRecord> treeSemanticRecord,
			grammerSemanticLoca[] grammersemanticLoca,ArrayList<String> addrList,
			ArrayList<String> addrResult,ArrayList<AddrNum> addrNum,
			ArrayList<CharTable> charTable,ArrayList<FourAddr> fourAddr,ArrayList<String> param,int num) throws Exception{
		
		//�ҵ���Ӧ�Ĳ���ʽ
		System.out.println(" "+slrTreeArray.get(num).getName());
		System.out.println("num:"+num);
		
		int length=slrTreeArray.get(num).getChildId().length;//���ӽ��ĸ���
		System.out.println("���Ӹ���length:"+length);
		String beforString = slrTreeArray.get(num).getName();//��������
		String nextString="";
		for(int j=length-1;j>=0;j--)
		 {
			 if(slrTreeArray.get(num).getChildId()[j].equals("-1"))
			 {
				 nextString="no";
				 break;
			 }
			 nextString+=slrTreeArray.get(Integer.parseInt(slrTreeArray.get(num).getChildId()[j])).getName()+" ";
		 }
		System.out.println("�����ַ�����"+nextString);
		SLRFormula formula=new SLRFormula();
		formula.setBeforeString(beforString);
		formula.setFlag(0);
		formula.setNextString(nextString.split(" "));
		int grammerNum=findActionNum(formula,grammerTable);//�ķ���Ĳ���ʽ���
		System.out.println("����ʽ�ı�ţ�"+grammerNum);
		//System.out.println("����ʽ��\t"+grammerTable[grammerNum].getName()+" -> "+grammerTable[grammerNum].getValue()[0]+"("+grammerTable[grammerNum].getValue().length +")" );
		//�﷨������ʼ
		
		//�Բ���ʽ��ÿһ����
		for(int i=0;i<length;i++){
			//��ǰλ���г���Ƭ��
			//num�ڵ��ţ�grammarNum����ʽ��ţ�i����ʽ��λ��
			//System.out.println("����ʽ "+grammerNum+" ��ǰ");
			record(grammersemanticLoca,slrTreeArray, treeSemanticRecord,addrList,addrResult, addrNum, charTable, fourAddr, param, num,grammerNum,i);
        	
        	int firstchildID=Integer.parseInt(slrTreeArray.get(num).getChildId()[0]);
        	//System.out.println("firstchildID/////\t"+firstchildID);
        	if(firstchildID!=-1 )//�Լ�����Ҷ�ڵ�
        	{
        		System.out.println("����Ҷ�ڵ�");
        		//�Ե�ǰ�ӽڵ㣬����к��ӽ�㣬�ݹ飬�޺��ӽ�㣬����
            	int childID=Integer.parseInt(slrTreeArray.get(num).getChildId()[length-1-i]);
            	System.out.println("====���ӽ��:"+childID);
            	semanticTest(grammerTable,slrTreeArray,treeSemanticRecord,grammersemanticLoca,addrList, addrResult, addrNum, charTable, fourAddr, param, childID);
        	}
		}
		//����Ƿ�Ҳ������Ƭ��
		System.out.println("����ʽ "+grammerNum+" ���");
		record(grammersemanticLoca,slrTreeArray, treeSemanticRecord,addrList,addrResult, addrNum, charTable, fourAddr, param, num,grammerNum,length);
       
	}
	
	/**
	 * ��õ�ǰ����ʽ��ǰλ���Ƿ����������Ƭ�Σ����򷵻�Ƭ�κţ����򷵻�-1
	 * @param grammerNum
	 * @param loc
	 * @param grammersemanticLoca
	 * @return
	 */
	public int isExitSemanticRule(int grammerNum,int loc,grammerSemanticLoca[] grammersemanticLoca){
		System.out.println("����ʽ�е�λ�ã�\t"+loc);
		for(int i=0;i<grammersemanticLoca.length;i++)
		{
			if(grammersemanticLoca[i].getGrammerNum()==grammerNum&&grammersemanticLoca[i].getRulelLoc()==loc)
			{
				return grammersemanticLoca[i].getRuleNum();
			}
		}
		return -1;
	}
	
	/**
	 * ִ����Ӧ����Ƭ��
	 * @param grammersemanticLoca
	 * @param slrTreeArray
	 * @param treeSemanticRecord
	 * @param addrList
	 * @param addrResult
	 * @param addrNum
	 * @param charTable
	 * @param fourAddr
	 * @param param
	 * @param treeNodeNum
	 * @param grammerNum
	 * @param loc
	 */
	public void record(grammerSemanticLoca[] grammersemanticLoca,ArrayList<SLRTree> slrTreeArray,
			ArrayList<RreeSemanticRecord> treeSemanticRecord,ArrayList<String> addrList,
			ArrayList<String> addrResult,ArrayList<AddrNum> addrNum,ArrayList<CharTable> charTable,
			ArrayList<FourAddr> fourAddr,ArrayList<String> param,int treeNodeNum,int grammerNum,int loc){
		//System.out.println("-- ���ű��С�� "+charTable.size());
		//System.out.println("////\t"+treeNodeNum+"\t"+loc);
		int ruleNum=isExitSemanticRule(grammerNum,loc,grammersemanticLoca);
		if(ruleNum!=-1)
		{
			System.out.println("����ʽ"+grammerNum+" λ��"+loc+" ������Ƭ����ţ�"+ruleNum);
		}
		switch(ruleNum){
		case 1:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_1(treeSemanticRecord,slrTreeArray,treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 2:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_2(treeSemanticRecord,slrTreeArray,treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 3:
			System.out.println("��"+ruleNum+"��");
			String s3=new semanticRule().semanticRule_3(treeSemanticRecord,slrTreeArray,charTable, fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			String[] e3=s3.split(" ");
			if(e3[0].equals("error"))
			{
				semerrorListTbModel.addRow((new Object[]{3,"������ƥ��",e3[2]+" "+e3[3]}));
				break;
			}
			addrResult.add(s3);
			break;
		case 4:
			System.out.println("��"+ruleNum+"��");
			String s4=new semanticRule().semanticRule_4(treeSemanticRecord,slrTreeArray,charTable, fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			String[] e4=s4.split(" ");
			if(e4[0].equals("error"))
			{
				semerrorListTbModel.addRow((new Object[]{3,"������ƥ��",e4[2]+" "+e4[3]}));
				break;
			}
			addrResult.add(s4);
			break;
		case 5:
			System.out.println("��"+ruleNum+"��");
			String s5=new semanticRule().semanticRule_5(treeSemanticRecord,slrTreeArray,fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			
			addrResult.add(s5);
			break;
		case 6:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_6(treeSemanticRecord,slrTreeArray,treeNodeNum);
			 //testRecord(treeSemanticRecord);
			break;
		case 7:
			System.out.println("��"+ruleNum+"��");
			String e7=new semanticRule().semanticRule_7(treeSemanticRecord,slrTreeArray,charTable, treeNodeNum);
			// testRecord(treeSemanticRecord);
			String[] e77=e7.split(" ");
			if(e77[0].equals("error"))
			{
				semerrorListTbModel.addRow((new Object[]{1,"����δ����",e77[2]}));
			}
			break;
		case 8:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_8(treeSemanticRecord,slrTreeArray,treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 9:
			System.out.println("��"+ruleNum+"��");
			String s9=new semanticRule().semanticRule_9(treeSemanticRecord,slrTreeArray,fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			
			addrResult.add(s9);
		case 10:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_10(treeSemanticRecord,slrTreeArray,treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 11:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_11(treeSemanticRecord,slrTreeArray,treeNodeNum,addrList);
			//testRecord(treeSemanticRecord);
			break;
		case 12:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_12(treeSemanticRecord,slrTreeArray,treeNodeNum, addrResult, addrNum);
			//testRecord(treeSemanticRecord);
			testAddr(addrNum);
			break;
		case 13:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_13(treeSemanticRecord,slrTreeArray, addrList, treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 14:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_14(treeSemanticRecord,slrTreeArray,addrResult, addrNum, treeNodeNum);
			//testRecord(treeSemanticRecord);
			testAddr(addrNum);
			break;
		case 15:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_15(treeSemanticRecord,slrTreeArray, addrList, treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 16:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_16(treeSemanticRecord,slrTreeArray,addrResult, addrNum, treeNodeNum);
			//testRecord(treeSemanticRecord);
			testAddr(addrNum);
			break;
		case 17:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_17(treeSemanticRecord,slrTreeArray,treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 18:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_18(treeSemanticRecord,slrTreeArray,treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 19:
			System.out.println("��"+ruleNum+"��");
			String[] s19=new semanticRule().semanticRule_19(treeSemanticRecord,slrTreeArray,fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			String s191=s19[0];
			String s192=s19[1];
			
			addrResult.add(s191);
			addrResult.add(s192);
			break;
		case 20:
			System.out.println("��"+ruleNum+"��");
			String s20=new semanticRule().semanticRule_20(treeSemanticRecord,slrTreeArray,fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			
			addrResult.add(s20);
			break;
		case 21:
			System.out.println("��"+ruleNum+"��");
			String s21=new semanticRule().semanticRule_21(treeSemanticRecord,slrTreeArray,fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			
			addrResult.add(s21);
			break;
		case 22:
			System.out.println("��"+ruleNum+"��");
			String s22=new semanticRule().semanticRule_22(treeSemanticRecord,slrTreeArray,charTable, fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			System.out.println(s22+"=s22");
			String[] e22=s22.split(" ");
			if(e22[0].equals("error"))
			{
				if(e22[1].equals("1"))
				{
					semerrorListTbModel.addRow((new Object[]{1,"����δ����",e22[2]}));
				}
				if(e22[1].equals("3"))
				{
					semerrorListTbModel.addRow((new Object[]{3,"������ƥ��",e22[2]+" "+e22[3]}));
				}
				break;
			}
			
			addrResult.add(s22);
			break;
		case 23:
			System.out.println("��"+ruleNum+"��");
			String s23=new semanticRule().semanticRule_23(treeSemanticRecord,slrTreeArray,fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			
			addrResult.add(s23);
			break;
		case 24:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_24(treeSemanticRecord,slrTreeArray,treeNodeNum,addrList);
			//testRecord(treeSemanticRecord);
			break;
		case 25:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_25(treeSemanticRecord,slrTreeArray,treeNodeNum, addrResult, addrNum);
			//testRecord(treeSemanticRecord);
			testAddr(addrNum);
			break;
		case 26:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_26(treeSemanticRecord,slrTreeArray,treeNodeNum,addrList);
			//testRecord(treeSemanticRecord);
			break;
		case 27:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_27(treeSemanticRecord,slrTreeArray,treeNodeNum, addrResult, addrNum);
			//testRecord(treeSemanticRecord);
			testAddr(addrNum);
			break;
		case 28:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_28(treeSemanticRecord,slrTreeArray,treeNodeNum, addrResult, addrNum);
			//testRecord(treeSemanticRecord);
			testAddr(addrNum);
			break;
		case 29:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_29(treeSemanticRecord,slrTreeArray,treeNodeNum,addrList, addrResult, addrNum);
			//testRecord(treeSemanticRecord);
			testAddr(addrNum);
			break;
		case 30:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_30(treeSemanticRecord,slrTreeArray,treeNodeNum, addrResult, addrNum);
			//testRecord(treeSemanticRecord);
			testAddr(addrNum);
			break;
		case 31:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_31(treeSemanticRecord,slrTreeArray,treeNodeNum,addrList);
			//testRecord(treeSemanticRecord);
			break;
		case 32:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_32(treeSemanticRecord,slrTreeArray,treeNodeNum,addrList, addrResult, addrNum);
			//testRecord(treeSemanticRecord);
			testAddr(addrNum);
			break;
		case 33:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_33(treeSemanticRecord,slrTreeArray,treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 34:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_34(treeSemanticRecord,slrTreeArray,treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 35:
			System.out.println("��"+ruleNum+"��");
			String s35=new semanticRule().semanticRule_35(treeSemanticRecord,slrTreeArray,charTable, fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			String[] e35=s35.split(" ");
			if(e35[0].equals("error"))
			{
				semerrorListTbModel.addRow((new Object[]{1,"����δ����",e35[2]}));
				break;
			}
			addrResult.add(s35);
			break;
		case 36:
			System.out.println("��"+ruleNum+"��");
			String[] s36=new semanticRule().semanticRule_36(treeSemanticRecord,slrTreeArray,fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			String s361=s36[0];
			String s362=s36[1];
			
			addrResult.add(s361);
			addrResult.add(s362);
			break;
		case 37:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_37(treeSemanticRecord,slrTreeArray,treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 38:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_38(treeSemanticRecord,slrTreeArray,treeNodeNum);
			//testRecord(treeSemanticRecord);
			break;
		case 39:
			System.out.println("��"+ruleNum+"��");
			String s39=new semanticRule().semanticRule_39(treeSemanticRecord,slrTreeArray,fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
			
			addrResult.add(s39);
			break;
		case 40:
			System.out.println("��"+ruleNum+"��");
			String s40=new semanticRule().semanticRule_40(treeSemanticRecord,slrTreeArray,fourAddr, treeNodeNum);
			//testRecord(treeSemanticRecord);
		
			addrResult.add(s40);
			break;
		case 41:
			System.out.println("��"+ruleNum+"��");
			String e41=new semanticRule().semanticRule_41(treeSemanticRecord,slrTreeArray,charTable, treeNodeNum);
			//testRecord(treeSemanticRecord);
			String[] e411=e41.split(" ");
			if(e411[0].equals("error"))
			{
				semerrorListTbModel.addRow((new Object[]{2,"�����ظ�����",e411[2]}));
			}
			break;
		case 42:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_42(treeSemanticRecord,slrTreeArray,param,treeNodeNum);
			break;
		case 43:
			System.out.println("��"+ruleNum+"��");
			new semanticRule().semanticRule_43(treeSemanticRecord,slrTreeArray,param,treeNodeNum);
			break;
		case 44:
			System.out.println("��"+ruleNum+"��");
			//System.out.println("+++++���ű��С�� "+charTable.size());
			ArrayList<String> s44=new semanticRule().semanticRule_44(treeSemanticRecord,slrTreeArray,param,fourAddr, charTable, treeNodeNum);
			String e44=s44.get(0);
			String[] e441=e44.split(" ");
			if(e441[0].equals("error"))
			{
				semerrorListTbModel.addRow((new Object[]{5,"������δ����",e441[2]}));
			}
			for(int i=0;i<s44.size();i++)
			{
				addrResult.add(s44.get(i));
			}
			break;
		case 45:
			System.out.println("��"+ruleNum+"��==============");
			String e45=new semanticRule().semanticRule_45(treeSemanticRecord,slrTreeArray,charTable, treeNodeNum);
			//testRecord(treeSemanticRecord);
			String[] e451=e45.split(" ");
			if(e451[0].equals("error"))
			{
				semerrorListTbModel.addRow((new Object[]{4,"�����������ͻ",e451[2]}));
			}
			break;
		default :
			System.out.println("û��"+ruleNum);
			break;
			
		}
		
	}
	public void testRecord(ArrayList<RreeSemanticRecord> treeSemanticRecord){
		for(int i=0;i<treeSemanticRecord.size();i++)
		{
			System.out.print("���� "+treeSemanticRecord.get(i).getTreeNodeNum());
			System.out.print(" ������ "+treeSemanticRecord.get(i).getTreeNodeName());
			System.out.print(" ����"+treeSemanticRecord.get(i).getProperty());
			System.out.println(" ֵ "+treeSemanticRecord.get(i).getValue());
		}
	}
	
	public void testAddr(ArrayList<AddrNum> addrNum){
		for(int i=0;i<addrNum.size();i++)
		{
			System.out.println(addrNum.get(i).getAddr()+"  "+addrNum.get(i).getNum());
		}
	}
	
	public String findValue(ArrayList<RreeSemanticRecord> treeSemanticRecord,int treeNodeNum,String property){
		for(int i=0;i<treeSemanticRecord.size();i++)
		{
			if(treeSemanticRecord.get(i).getTreeNodeNum()==treeNodeNum&&treeSemanticRecord.get(i).getProperty().equals(property))
			{
				return treeSemanticRecord.get(i).getValue();
			}
		}
		return "null";
	}
	
	private ArrayList<String> change(ArrayList<String> addrResult,ArrayList<AddrNum> addrNum){
		ArrayList<String> a= new ArrayList<String>();
		for(int i=0;i<addrResult.size();i++)
		{
			String[] b=addrResult.get(i).split(" ");
			StringBuffer sb2 = new StringBuffer();
			for(int j=0;j<b.length;j++)
			{
				boolean is=false;
				for(int z=0;z<addrNum.size();z++)
				{
					if(b[j].equals(addrNum.get(z).getAddr()))
					{
						sb2.append(addrNum.get(z).getNum()+" ");
						is=true;
						continue;
					}
				}
				if(!is)
				{
					sb2.append(b[j]+" ");
				}
				
			}
			a.add(sb2.toString());
		}
		return a;
	}
}


