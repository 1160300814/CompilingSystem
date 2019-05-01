package lab1;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import java.awt.Font;

public class Main {
	/* ���ӻ� */
	private JFrame frame2;
	private JFrame frame1;
	private JFrame frame3;
	private JFrame frame4;
	private JTextArea textArea;
	private DefaultTableModel tokenListTbModel;
	private DefaultTableModel dfaListTbModel;// DFA
	private DefaultTableModel dfaListTbMode2;// ״̬��Ϣ
	private DefaultTableModel dfaListTbMode3;// �ֱ��룬����ֵ
	private DefaultTableModel errorListTbModel;
	private String filepath2;//�I��������ļ�
	private static String _KEY_WORD_END = "end string of string";

	// �ؼ��� keywords
	String[] keys = { "char", "long", "short", "float", "double", "const", "boolean", "void", "null", "false", "true",
			"enum", "int", "do", "while", "if", "else", "for", "then", "break", "continue", "class", "static", "final",
			"extends", "new", "return", "signed", "struct", "union", "unsigned", "goto", "switch", "case", "default",
			"extern", "register", "sizeof", "typedef", "print", "out", _KEY_WORD_END };
	// �ؼ��֡�����������
	String[] token = { "char", "long", "short", "float", "double", "const", "boolean", "void", "null", "false", "true",
			"enum", "int", "do", "while", "if", "else", "for", "then", "break", "continue", "class", "static", "final",
			"extends", "new", "return", "signed", "struct", "union", "unsigned", "goto", "switch", "case", "default",
			"extern", "register", "sizeof", "typedef", "print", "out", ">", ">=", "<", "<=", "==", "!=", "|", "&",
			"||", "&&", "!", "^", "+", "-", "*", "/", "%", "++", "--", "+=", "-=", "*=", "/=", ".", ",", "=", ";", "[",
			"]", "(", ")", "{", "}", "\"", "'"

	};

	public Main() throws Exception {
		initialize();
		initialize2();
		initialize3();
		initialize4();
	}

	/**
	 * ���ӻ�.
	 * 
	 * @throws Exception
	 */
	/**
	 * �̶����ֱ��������ֵ
	 * 
	 * @throws Exception
	 */
	private void initialize4() throws Exception {
		frame4 = new JFrame();
		frame4.setBounds(100, 100, 598, 526);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame4.getSize();
		frame4.setLocation((screensize.width - frameSize.width) / 2, (screensize.height - frameSize.height) / 2);

		frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame4.getContentPane().setLayout(null);

		JPanel panel_1 = new JPanel();
		frame4.getContentPane().add(panel_1);
		panel_1.setBackground(new Color(220, 220, 220));// �װ�ɫ��ߣ�����
		panel_1.setBounds(0, 0, 598, 526);
		panel_1.setLayout(null);

		new JScrollPane();

		// �ֱ��룬����ֵ
		dfaListTbMode3 = new DefaultTableModel(new Object[][] {}, new String[] { "��", "��������", "�ֱ���", "����ֵ" });

		for (int j = 0; j <= 40; j++) {
			String test = this.token[j];
			dfaListTbMode3.addRow(new Object[] { test, "�ؼ���", test.toUpperCase(), "-" });
		}
		for (int j = 41; j <= 63; j++) {
			String test = this.token[j];
			dfaListTbMode3.addRow(new Object[] { test, "�����", "OP", j - 40 });
		}
		for (int j = 64; j <= 75; j++) {
			String test = this.token[j];
			dfaListTbMode3.addRow(new Object[] { test, "���", "BOUND", j - 63 });
		}
		dfaListTbMode3.addRow(new Object[] { "(w | _)(w | _ | d )*", "��ʶ��", "IDN", "(w | _)(w | _ | d )*" });
		dfaListTbMode3.addRow(new Object[] { "/**/", "ע��", "NOTE", "-" });
		dfaListTbMode3.addRow(new Object[] { "��", "����", "CONST", "��" });
		dfaListTbMode3.addRow(new Object[] { "0(1|2|3|4|5|6|7)(0|1|2|3|4|5|6|7)*", "�˽�����", "OCTAL",
				"0(1|2|3|4|5|6|7)(0|1|2|3|4|5|6|7)*" });
		dfaListTbMode3.addRow(new Object[] { "0x(1|...|9|a|...|f|A|��|F)(0|...|9|a|...|f |A|��|F)*", "ʮ��������", "HEXAD",
				"0x(1|...|9|a|...|f|A|��|F)(0|...|9|a|...|f|A|��|F)*" });

		RowSorter<DefaultTableModel> sorter1 = new TableRowSorter<DefaultTableModel>(dfaListTbMode3);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(245, 245, 245));// ������ǳ��
		panel_2.setBounds(10, 13, 543, 404);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		JTable dfaListTb = new JTable();
		dfaListTb.setBackground(new Color(248, 248, 255));// ƫ��
		dfaListTb.setFillsViewportHeight(true);
		dfaListTb.setModel(dfaListTbMode3);
		dfaListTb.setRowSorter(sorter1);
		JScrollPane dfaSP = new JScrollPane();
		dfaSP.setViewportView(dfaListTb);
		dfaSP.setBounds(23, 70, 455, 321);// ���ߴ�
		panel_2.add(dfaSP);

		JLabel lblDfa = new JLabel("�̶���Ϣ");
		lblDfa.setBounds(173, 10, 285, 47);// ��ͷ�ߴ�
		panel_2.add(lblDfa);
		lblDfa.setFont(new Font("����", Font.BOLD, 42));
	}

	/**
	 * ״̬��Ϣ���
	 * 
	 * @throws Exception
	 */
	private void initialize3() throws Exception {
		frame3 = new JFrame();
		frame3.setBounds(100, 100, 598, 526);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame3.getSize();
		frame3.setLocation((screensize.width - frameSize.width) / 2, (screensize.height - frameSize.height) / 2);

		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.getContentPane().setLayout(null);

		JPanel panel_1 = new JPanel();
		frame3.getContentPane().add(panel_1);
		panel_1.setBackground(new Color(240, 255, 240));// ��ײ�
		panel_1.setBounds(0, 0, 598, 526);
		panel_1.setLayout(null);

		new JScrollPane();

		// ״̬��Ϣ
		dfaListTbMode2 = new DefaultTableModel(new Object[][] {}, new String[] { "״̬", "����", "�Ƿ���ֹ״̬" });
		Read result = new Read();
		Map<Integer, String> rr = result.getType();
		Map<Integer, String> map = rr;
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			dfaListTbMode2.addRow(
					new Object[] { entry.getKey().toString(), entry.getValue(), result.isFinish(entry.getKey()) });
		}

		RowSorter<DefaultTableModel> sorter1 = new TableRowSorter<DefaultTableModel>(dfaListTbMode2);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(240, 255, 255));// �в�
		panel_2.setBounds(10, 13, 543, 404);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		JTable dfaListTb = new JTable();
		dfaListTb.setBackground(new Color(245, 255, 250));
		dfaListTb.setFillsViewportHeight(true);
		dfaListTb.setModel(dfaListTbMode2);
		dfaListTb.setRowSorter(sorter1);
		JScrollPane dfaSP = new JScrollPane();
		dfaSP.setViewportView(dfaListTb);
		dfaSP.setBounds(23, 70, 455, 321);// ���ߴ�
		panel_2.add(dfaSP);

		JLabel lblDfa = new JLabel("DFA״̬");
		lblDfa.setBounds(173, 10, 285, 47);// ��ͷ�ߴ�
		panel_2.add(lblDfa);
		lblDfa.setFont(new Font("����", Font.BOLD, 42));
	}

	/**
	 * DFA���
	 * 
	 * @throws Exception
	 */
	private void initialize2() throws Exception {
		frame1 = new JFrame();
		frame1.setBounds(100, 100, 1098, 526);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame1.getSize();
		frame1.setLocation((screensize.width - frameSize.width) / 2, (screensize.height - frameSize.height) / 2);

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(null);

		JPanel panel_1 = new JPanel();
		frame1.getContentPane().add(panel_1);
		panel_1.setBackground(new Color(255, 228, 196));// ��ײ�
		panel_1.setBounds(0, 0, 1598, 526);
		panel_1.setLayout(null);

		new JScrollPane();

		// DFA���
		dfaListTbModel = new DefaultTableModel(new Object[][] {},
				new String[] { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" });
		String[][] result = new Read().showDFA();
		for (int i = 1; i < result.length; i++) {
			for (int j = 1; j < result[i].length - 2; j++) {
				result[i][j] = result[i][j].replaceAll("-1", " ");
			}
		}
		for (int i = 0; i < result.length; i++) {
			dfaListTbModel.addRow(new Object[] { result[i][0], result[i][1], result[i][2], result[i][3], result[i][4],
					result[i][5], result[i][6], result[i][7], result[i][8], result[i][9], result[i][10], result[i][11],
					result[i][12], result[i][13], result[i][14], result[i][15], result[i][16], result[i][17] });

		}
		RowSorter<DefaultTableModel> sorter1 = new TableRowSorter<DefaultTableModel>(dfaListTbModel);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 222, 173));// ��ɫ
		panel_2.setBounds(10, 13, 1043, 404);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		JTable dfaListTb = new JTable();
		dfaListTb.setBackground(new Color(255, 250, 205));
		dfaListTb.setFillsViewportHeight(true);
		dfaListTb.setModel(dfaListTbModel);
		dfaListTb.setRowSorter(sorter1);
		JScrollPane dfaSP = new JScrollPane();
		dfaSP.setViewportView(dfaListTb);
		dfaSP.setBounds(23, 70, 955, 321);// ���ߴ�
		panel_2.add(dfaSP);

		JLabel lblDfa = new JLabel("DFA\u8F6C\u6362\u8868");
		lblDfa.setBounds(403, 10, 285, 47);// ��ͷ�ߴ�
		panel_2.add(lblDfa);
		lblDfa.setFont(new Font("����", Font.BOLD, 42));
	}

	/**
	 * ���Ա��
	 * 
	 * @throws Exception
	 */
	private void initialize() throws Exception {
		// �ڶ���
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 1098, 626);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame2.getSize();
		frame2.setLocation((screensize.width - frameSize.width) / 2, (screensize.height - frameSize.height) / 2);

		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 180, 180));
		panel.setBounds(0, 0, 1080, 879);
		frame2.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 193, 193));
		panel_1.setBounds(23, 13, 1043, 853);
		panel.add(panel_1);
		panel_1.setLayout(null);

		new JScrollPane();

		textArea = new JTextArea();
		textArea.setBackground(new Color(255, 245, 238));

		textArea.setBounds(14, 77, 285, 372);
		panel_1.add(textArea);
		textArea.setColumns(10);

		final JButton btnNewButton_2 = new JButton("ѡ���ļ�");
		btnNewButton_2.setFont(new Font("����", Font.BOLD, 15));
		btnNewButton_2.setBackground(new Color(255, 228, 225));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(new File("D:\\eclipse\\BianYi")); // ����ѡ����,���ô�·��
				chooser.setMultiSelectionEnabled(true); // ��Ϊ��ѡ
				int returnVal = chooser.showOpenDialog(btnNewButton_2);
				if (returnVal == JFileChooser.APPROVE_OPTION) { // ��������ļ�����
					String filepath = chooser.getSelectedFile().getAbsolutePath(); // ��ȡ·��
					System.out.println(filepath);
					filepath2 = filepath.replaceAll("\\\\", "/");
					File file = new File(filepath2);
					textArea.setText(getContent(file));
				}
			}
		});
		btnNewButton_2.setBounds(42, 30, 107, 34);
		panel_1.add(btnNewButton_2);

		JButton btnNewButton_1 = new JButton("\u6D4B\u8BD5");
		btnNewButton_1.setFont(new Font("����", Font.BOLD, 15));
		btnNewButton_1.setBackground(new Color(255, 228, 225));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textArea.getText();
				String str2 = str.replaceAll("\r|\n", " "); // ȥ�����з��ո�
				try {
					System.out.println("=====================Start=====================");
					Analysis(str2);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_1.setBounds(186, 30, 92, 34);
		panel_1.add(btnNewButton_1);

		// �ֱ�����
		tokenListTbModel = new DefaultTableModel(new Object[][] {}, new String[] { "�ַ���", "���", "�ֱ���", "value" });

		JTable tokenListTb = new JTable();
		tokenListTb.setBackground(new Color(255, 240, 245));
		tokenListTb.setFillsViewportHeight(true);
		tokenListTb.setModel(tokenListTbModel);

		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tokenListTbModel);
		tokenListTb.setRowSorter(sorter);
		JScrollPane tokenSP = new JScrollPane();
		tokenSP.setViewportView(tokenListTb);
		tokenSP.setBounds(313, 77, 461, 372);
		panel_1.add(tokenSP);

		// ������
		errorListTbModel = new DefaultTableModel(new Object[][] {}, new String[] { "�������", "����ط�", "����ԭ��" });

		JTable errorListTb = new JTable();
		errorListTb.setBackground(new Color(255, 240, 245));
		errorListTb.setFillsViewportHeight(true);
		errorListTb.setModel(errorListTbModel);

		RowSorter<DefaultTableModel> sorter2 = new TableRowSorter<DefaultTableModel>(errorListTbModel);
		errorListTb.setRowSorter(sorter2);
		JScrollPane errorSP = new JScrollPane();
		errorSP.setViewportView(errorListTb);
		errorSP.setBounds(788, 77, 241, 372);
		panel_1.add(errorSP);

		JLabel lblToken = new JLabel("TOKEN TABLE");
		lblToken.setFont(new Font("����", Font.BOLD, 30));
		lblToken.setBounds(462, 30, 254, 34);
		panel_1.add(lblToken);

		JLabel lblErrorTable = new JLabel("ERROR TABLE");
		lblErrorTable.setFont(new Font("����", Font.BOLD, 30));
		lblErrorTable.setBounds(811, 33, 203, 41);
		panel_1.add(lblErrorTable);

	}

	/**
	 * �ʷ�����
	 * 
	 * @param str2
	 * @throws Exception
	 */
	private void Analysis(String s) throws Exception {
		// ��ʼ�������
		while (tokenListTbModel.getRowCount() > 0) {
			tokenListTbModel.removeRow(0);
		}
		while (errorListTbModel.getRowCount() > 0) {
			errorListTbModel.removeRow(0);
		}
		// ���ַ���ת��Ϊ�ַ�����
		char[] strline = s.toCharArray();
		String cString = "";// ��ǰ�ַ���
		int cState = 0; // ��ǰ״̬
		int lState = 0; // ��һ״̬

		Read read = new Read();
		DFA dfa[] = read.addDFA();

		for (int i = 0; i < strline.length; i++) {
			System.out.println("�� " + i + " �� :");
			System.out.println(strline[i]);
			cString = cString.concat(String.valueOf(strline[i]));
			System.out.println("currentString:" + cString);

			lState = cState;

			if (cString == " " || strline[i] == ' ') {// �������ո�
				//�����ע����Ŀո񣬿��Խ���
				if(cState == 9 ||cState == 10 ) {
					cState = 9;
					System.out.println("newcurrentString: "+cState);
					// �ж��Ƿ������һ������
					if (i == strline.length - 1) {
						if (!read.isFinish(cState)) {
							// System.out.println("***\t"+read.isFinish(lState));
							errorListTbModel.addRow(new Object[] { cString, "��" + i + "�ַ�", "ע��ע��δ���" });
							tokenListTbModel.addRow(new Object[] { cString, "ע��ע��δ���", "��", "   " });
						} else {
							if (IsKeyWord(cString)) {
								tokenListTbModel.addRow(new Object[] { cString, "�ؼ���", TakenID(cString, "�ؼ���"), "-" });
							} else {
								tokenListTbModel.addRow(new Object[] { cString, read.getType(cState),
										TakenID(cString, read.getType(cState)), AttrID(read.getType(cState), cString) });
								//System.out.println("&&&&&&&&&&&&&&");
							}
						}
					}
					continue;
				}
				cString = cString.replaceAll(" ", "");// ȥ���ո�
				if (IsKeyWord(cString)) {
					tokenListTbModel.addRow(new Object[] { cString, "�ؼ���", TakenID(cString, "�ؼ���"), "-" });
				} else {
					tokenListTbModel.addRow(new Object[] { cString, read.getType(lState),
							TakenID(cString, read.getType(lState)), AttrID(read.getType(lState), cString) });
				}
				cString = "";
				cState = 0;
				continue;
			}
			cState = ChangeState(strline[i], cState, dfa);
			System.out.println(" ��ǰ״̬  =  " + cState);
			System.out.println(" ��һ״̬  =  " + lState);
			/*if (cState == -1 || cState == -2)// �����ܼ�����ת
			{
				cString = cString.substring(0, cString.length() - 1);

				// �жϵ�ǰ״̬�Ƿ�Ϊ��ֹ״̬, �粻�� ,����
				if (!read.isFinish(lState)) {
					errorListTbModel.addRow(new Object[] { cString, "��" + i + "�ַ�", "�Ƿ��ַ�" });
					tokenListTbModel.addRow(new Object[] { cString, "�Ƿ��ַ�", "��", "   " });
				} else {
					if (IsKeyWord(cString)) {
						tokenListTbModel.addRow(new Object[] { cString, "�ؼ���", TakenID(cString, "�ؼ���"), "-" });
					} else {
						tokenListTbModel.addRow(new Object[] { cString, read.getType(lState),
								TakenID(cString, read.getType(lState)), AttrID(read.getType(lState), cString) });
					}
				}
				if (cState == -2) {// ʶ��Ƿ��ַ�
					// System.out.println("����-1");
					tokenListTbModel.addRow(new Object[] { strline[i], "�Ƿ��ַ�", "��", "   " });
					errorListTbModel.addRow(new Object[] { strline[i], "��" + i + "�ַ�", "�Ƿ��ַ�" });
					i++;
				}
				cString = "";
				cState = 0;
				i--;
			}*/
			if(cState == -1) {//��������ת����˵��״̬�ѵ���ͷ
				cString = cString.substring(0, cString.length() - 1);
				// �жϵ�ǰ״̬�Ƿ�Ϊ��ֹ״̬, �粻�� ,����
				if (!read.isFinish(lState)) {
					errorListTbModel.addRow(new Object[] { cString, "��" + i + "�ַ�", "�Ƿ��ַ�" });
					tokenListTbModel.addRow(new Object[] { cString, "�Ƿ��ַ�", "��", "   " });
				} else {
					if (IsKeyWord(cString)) {
						tokenListTbModel.addRow(new Object[] { cString, "�ؼ���", TakenID(cString, "�ؼ���"), "-" });
					} else {
						tokenListTbModel.addRow(new Object[] { cString, read.getType(lState),
								TakenID(cString, read.getType(lState)), AttrID(read.getType(lState), cString) });
					}
				}
				cString = "";
				cState = 0;
				i--;
			}
			if(cState == -2) {//����ǷǷ��ַ�
				
				if(lState == 2) {//˵��֮ǰʶ���Ϊ����
					cString = cString.substring(0, cString.length() - 1);
					// System.out.println("����-1");
					tokenListTbModel.addRow(new Object[] { cString+strline[i], "��ʽ����ȷ", "��", "   " });
					errorListTbModel.addRow(new Object[] { cString+strline[i], "��" + i + "�ַ�", "��ʽ����ȷ" });
					i++;
				}else if(lState == 9 ||lState == 10 ) {
					cState = 9;
					System.out.println("newcurrentString: "+cState);
					// �ж��Ƿ������һ������
					if (i == strline.length - 1) {
						if (!read.isFinish(cState)) {
							// System.out.println("***\t"+read.isFinish(lState));
							errorListTbModel.addRow(new Object[] { cString, "��" + i + "�ַ�", "ע��ע��δ���" });
							tokenListTbModel.addRow(new Object[] { cString, "ע��ע��δ���", "��", "   " });
						} else {
							if (IsKeyWord(cString)) {
								tokenListTbModel.addRow(new Object[] { cString, "�ؼ���", TakenID(cString, "�ؼ���"), "-" });
							} else {
								tokenListTbModel.addRow(new Object[] { cString, read.getType(cState),
										TakenID(cString, read.getType(cState)), AttrID(read.getType(cState), cString) });
								//System.out.println("&&&&&&&&&&&&&&");
							}
						}
					}
					continue;
				}else {
					cString = cString.substring(0, cString.length() - 1);
					if (!read.isFinish(lState)) {
						errorListTbModel.addRow(new Object[] { cString, "��" + i + "�ַ�", "�Ƿ��ַ�" });
						tokenListTbModel.addRow(new Object[] { cString, "�Ƿ��ַ�", "��", "   " });
					} else {
						if (IsKeyWord(cString)) {
							tokenListTbModel.addRow(new Object[] { cString, "�ؼ���", TakenID(cString, "�ؼ���"), "-" });
						} else {
							tokenListTbModel.addRow(new Object[] { cString, read.getType(lState),
									TakenID(cString, read.getType(lState)), AttrID(read.getType(lState), cString) });
						}
					}
					tokenListTbModel.addRow(new Object[] { strline[i], "�Ƿ��ַ�", "��", "   " });
					errorListTbModel.addRow(new Object[] { strline[i], "��" + i + "�ַ�", "�Ƿ��ַ�" });
					i++;
				}
				cString = "";
				cState = 0;
				i--;
			}
			// ���һ������
			if (i == strline.length - 1) {
				if (!read.isFinish(cState)) {
					// System.out.println("***\t"+read.isFinish(lState));
					errorListTbModel.addRow(new Object[] { cString, "��" + i + "�ַ�", "�Ƿ��ַ�" });
					tokenListTbModel.addRow(new Object[] { cString, "�Ƿ��ַ�", "��", "   " });
				} else {
					if (IsKeyWord(cString)) {
						tokenListTbModel.addRow(new Object[] { cString, "�ؼ���", TakenID(cString, "�ؼ���"), "-" });
					} else {
						tokenListTbModel.addRow(new Object[] { cString, read.getType(cState),
								TakenID(cString, read.getType(cState)), AttrID(read.getType(cState), cString) });
						System.out.println("&&&&&&&&&&&&&&");
					}
				}
				if (cState == -2) {
					tokenListTbModel.addRow(new Object[] { strline[i], "�Ƿ��ַ�", "��", "   " });
					errorListTbModel.addRow(new Object[] { strline[i], "��" + i + "�ַ�", "�Ƿ��ַ�" });
					i++;
				}
			}

		}
	}

	/**
	 * ״̬�ı�
	 * 
	 * @param currentChar
	 * @param currentState
	 * @param dfa
	 * @return
	 */
	public int ChangeState(char currentChar, int currentState, DFA[] dfa) {
		System.out.println("����Ѱ�Ƿ���״̬������ת");
		boolean isInput = false;
		for (int i = 0; i < dfa.length; i++) {
			if (IsIn(dfa[i].getInput(), currentChar)) {// ���ڸ�����
				isInput = true;
				if (dfa[i].getState() == currentState && dfa[i].getNextState() != -1)// ��ǰ״̬һ��������һ״̬��Ϊ�գ�ת����һ״̬
				{
					return dfa[i].getNextState();
				}

			}
		}
		if (isInput == false)// ʶ��Ƿ��ַ�
		{
			return -2;
		}
		return -1;// ����Ϸ�������û��״̬��ת
	}

	/**
	 * �ж������Ƿ�����ַ�
	 * 
	 * @param arr
	 * @param cC
	 * @return
	 */
	public static boolean IsIn(String[] arr, char cC) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(String.valueOf(cC))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �ж��Ƿ�ؼ���
	 * 
	 * @param a
	 * @return
	 */
	public boolean IsKeyWord(String a) {
		int i = 0;
		while (keys[i].compareTo(_KEY_WORD_END) != 0) {
			if (keys[i].compareTo(a) == 0) {
				return true;
			}
			i++;
		}
		return false;

	}

	/**
	 * ��������ֵ
	 * 
	 * 
	 * @param b ����
	 * @param c ��ǰ�ַ���
	 * @return
	 */
	public String AttrID(String b, String c) {
		if (b.equals("ע��") || b.equals("�ؼ���")) {
			return "-";
		} else if (b.equals("�����")) {
			for (int i = 0; i < token.length; i++) {
				if (token[i].equals(c.toLowerCase())) {
					return String.valueOf(i - 40);
				}
			}
		} else if (b.equals("���")) {
			for (int i = 0; i < token.length; i++) {
				if (token[i].equals(c.toLowerCase())) {
					return String.valueOf(i - 63);
				}
			}
		} else {
			return c;
		}
		return c;

	}

	/**
	 * �����ֱ���
	 * 
	 * @param a ���봮
	 * @param b ����
	 * @return
	 */
	public String TakenID(String a, String b) {
		boolean flag = false;
		for (int i = 0; i < token.length; i++) {
			if (token[i].equals(a.toLowerCase())) {
				flag = true;
				if (i <= 40) {
					return token[i].toUpperCase();
				} else if (i <= 63) {
					return "OP";
				} else if (i <= 75) {
					return "BOUND";
				} else {
					if (b.equals("��ʶ��")) {
						return "IDN";
					} else if (b.equals("����") || b.equals("����") || b.equals("С��") || b.equals("��ѧ����������")) {
						return "CONST";
					} else if (b.equals("�˽�����")) {
						return "OCTAL";
					} else if (b.equals("ʮ��������")) {
						return "HEXAD";
					} else if (b.equals("ע��")) {
						return "NOTE";
					} else {
						return "";
					}

				}
			}
		}
		if (flag == false) {
			token = Arrays.copyOf(token, token.length + 1);
			token[token.length - 1] = a;
			if (b.equals("��ʶ��")) {
				return "IDN";
			} else if (b.equals("����") || b.equals("����") || b.equals("С��") || b.equals("��ѧ����������")) {
				return "CONST";
			} else if (b.equals("�˽�����")) {
				return "OCTAL";
			} else if (b.equals("ʮ��������")) {
				return "HEXAD";
			} else if (b.equals("ע��")) {
				return "NOTE";
			} else {
				return "";
			}
		}
		return "";
	}

	/**
	 * ��ָ�����ļ��ж�ȡԴ�����ļ�����
	 * 
	 * @return
	 */
	public String getContent(File file) {
		StringBuilder stringBuilder = new StringBuilder();
		try (Scanner reader = new Scanner(file)) {
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				stringBuilder.append(line + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		DFA dfa[] = new Read().addDFA();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame2.setVisible(true);
					window.frame1.setVisible(true);
					window.frame3.setVisible(true);
					window.frame4.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
