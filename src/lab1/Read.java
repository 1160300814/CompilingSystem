package lab1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Read {
	private String[][] result1;
	private String[][] result2;
	private Map<Integer, Boolean> IsFinish = new HashMap<Integer, Boolean>();
	private Map<Integer, String> IsType = new HashMap<Integer, String>();

	public Read() throws FileNotFoundException, IOException {
		File file1 = new File("DFA.xls");
		result1 = getData(file1, 0);
		File file2 = new File("finish.xls");
		result2 = getData(file2, 0);
		SetFinishAndType();
	}

	/**
	 * �洢DFAת����
	 * @return
	 * @throws Exception
	 */
	public DFA[] addDFA() throws Exception {
		int rowLength = result1.length;
		DFA dfa[] = new DFA[663];// �õ�
		int x = 0;
		for (int i = 1; i < rowLength; i++) {// ��i��
			for (int j = 1; j < result1[i].length - 2; j++) {// ��j��
				dfa[x] = new DFA();
				dfa[x].setState(Integer.parseInt(result1[i][0]));// ��ȡ״̬
				String[] strArray = null;
				strArray = result1[0][j].split(" ");
				dfa[x].setInput(strArray);// �������봮
				dfa[x].setNextState(Integer.parseInt(result1[i][j]));// ������һ��״̬
				// System.out.print(result[i][j]+" ");
				x = x + 1;
			}

			// System.out.println();

		}
		for (int i = 0; i < dfa.length; i++)// ��ʾ��ͬ״̬ʶ������Ľ��
		{
			if (dfa[i].getState() == 1) {
				dfa[i].setType("��ʶ��");
			}
			if (dfa[i].getState() >= 2 && dfa[i].getState() <= 7) {
				dfa[i].setType("��ʶ��");
			}
			if (dfa[i].getState() >= 8 && dfa[i].getState() <= 11) {
				dfa[i].setType("ע��");
			}
			if (dfa[i].getState() >= 12 && dfa[i].getState() <= 18
					|| dfa[i].getState() >= 20 && dfa[i].getState() <= 28) {
				dfa[i].setType("�����");
			}
			if (dfa[i].getState() == 29 || dfa[i].getState() == 19) {
				dfa[i].setType("���");
			}
		}

		return dfa;
	}

	/**
	 * չʾ�õ���DFA
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[][] showDFA() throws Exception {
		return result1;
	}

	/**
	 * �����Ƿ���ֹ״̬�;�������
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void SetFinishAndType() throws FileNotFoundException, IOException {
		int rowLength = result2.length;
		for (int i = 0; i < rowLength; i++) {
			IsFinish.put(Integer.parseInt(result2[i][0]), result2[i][1].equals("1") ? true : false);
			// System.out.println(Integer.parseInt(result2[i][0])+"+++++++++++++"+IsFinish.get(Integer.parseInt(result2[i][0])));
			IsType.put(Integer.parseInt(result2[i][0]), result2[i][2]);
		}
		return;
	}

	/**
	 * �ж��Ƿ���ֹ״̬
	 * 
	 * @param t
	 * @return
	 */
	public boolean isFinish(int t) {
		// System.out.println("............"+IsFinish.get(t));
		return IsFinish.get(t);
	}

	/**
	 * �жϾ���ʲô����
	 * 
	 * @param t
	 * @return
	 */
	public String getType(int t) {
		return IsType.get(t);
	}

	/**
	 * �õ����ͱ�
	 * 
	 * @return
	 */
	public Map<Integer, String> getType() {
		return this.IsType;
	}

	/**
	 * 
	 * ��ȡExcel�����ݣ���һά����洢����һ���и��е�ֵ����ά����洢���Ƕ��ٸ���
	 * 
	 * @param file       ��ȡ���ݵ�ԴExcel
	 * 
	 * @param ignoreRows ��ȡ���ݺ��Ե�������������ͷ����Ҫ���� ���Ե�����Ϊ1
	 * 
	 * @return ������Excel�����ݵ�����
	 * 
	 * @throws FileNotFoundException
	 * 
	 * @throws IOException
	 * 
	 */

	public static String[][] getData(File file, int ignoreRows)

			throws FileNotFoundException, IOException {

		List<String[]> result = new ArrayList<String[]>();

		int rowSize = 0;

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(

				file));

		// ��HSSFWorkbook

		POIFSFileSystem fs = new POIFSFileSystem(in);

		HSSFWorkbook wb = new HSSFWorkbook(fs);

		HSSFCell cell = null;

		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

			HSSFSheet st = wb.getSheetAt(sheetIndex);

			// ��һ��Ϊ���⣬��ȡ

			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {

				HSSFRow row = st.getRow(rowIndex);

				if (row == null) {

					continue;

				}

				int tempRowSize = row.getLastCellNum() + 1;

				if (tempRowSize > rowSize) {

					rowSize = tempRowSize;

				}

				String[] values = new String[rowSize];

				Arrays.fill(values, "");

				boolean hasValue = false;

				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {

					String value = "";

					cell = row.getCell(columnIndex);

					if (cell != null) {

						// ע�⣺һ��Ҫ��������������ܻ��������

						cell.setEncoding(HSSFCell.ENCODING_UTF_16);

						switch (cell.getCellType()) {

						case HSSFCell.CELL_TYPE_STRING:

							value = cell.getStringCellValue();

							break;

						case HSSFCell.CELL_TYPE_NUMERIC:

							if (HSSFDateUtil.isCellDateFormatted(cell)) {

								Date date = cell.getDateCellValue();

								if (date != null) {

									value = new SimpleDateFormat("yyyy-MM-dd")

											.format(date);

								} else {

									value = "";

								}

							} else {

								value = new DecimalFormat("0").format(cell

										.getNumericCellValue());

							}

							break;

						case HSSFCell.CELL_TYPE_FORMULA:

							// ����ʱ���Ϊ��ʽ���ɵ���������ֵ

							if (!cell.getStringCellValue().equals("")) {

								value = cell.getStringCellValue();

							} else {

								value = cell.getNumericCellValue() + "";

							}

							break;

						case HSSFCell.CELL_TYPE_BLANK:

							break;

						case HSSFCell.CELL_TYPE_ERROR:

							value = "";

							break;

						case HSSFCell.CELL_TYPE_BOOLEAN:

							value = (cell.getBooleanCellValue() == true ? "Y"

									: "N");

							break;

						default:

							value = "";

						}

					}

					if (columnIndex == 0 && value.trim().equals("")) {

						break;

					}

					values[columnIndex] = rightTrim(value);

					hasValue = true;

				}

				if (hasValue) {

					result.add(values);

				}

			}

		}

		in.close();

		String[][] returnArray = new String[result.size()][rowSize];

		for (int i = 0; i < returnArray.length; i++) {

			returnArray[i] = (String[]) result.get(i);

		}

		return returnArray;

	}

	/**
	 * 
	 * ȥ���ַ����ұߵĿո�
	 * 
	 * @param str Ҫ������ַ���
	 * 
	 * @return �������ַ���
	 * 
	 */

	public static String rightTrim(String str) {

		if (str == null) {

			return "";

		}

		int length = str.length();

		for (int i = length - 1; i >= 0; i--) {

			if (str.charAt(i) != 0x20) {

				break;

			}

			length--;

		}

		return str.substring(0, length);

	}
}
