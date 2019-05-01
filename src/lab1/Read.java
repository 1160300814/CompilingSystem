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
	 * 存储DFA转换表
	 * @return
	 * @throws Exception
	 */
	public DFA[] addDFA() throws Exception {
		int rowLength = result1.length;
		DFA dfa[] = new DFA[663];// 得到
		int x = 0;
		for (int i = 1; i < rowLength; i++) {// 第i行
			for (int j = 1; j < result1[i].length - 2; j++) {// 第j列
				dfa[x] = new DFA();
				dfa[x].setState(Integer.parseInt(result1[i][0]));// 读取状态
				String[] strArray = null;
				strArray = result1[0][j].split(" ");
				dfa[x].setInput(strArray);// 设置输入串
				dfa[x].setNextState(Integer.parseInt(result1[i][j]));// 设置下一个状态
				// System.out.print(result[i][j]+" ");
				x = x + 1;
			}

			// System.out.println();

		}
		for (int i = 0; i < dfa.length; i++)// 表示不同状态识别出来的结果
		{
			if (dfa[i].getState() == 1) {
				dfa[i].setType("标识符");
			}
			if (dfa[i].getState() >= 2 && dfa[i].getState() <= 7) {
				dfa[i].setType("标识符");
			}
			if (dfa[i].getState() >= 8 && dfa[i].getState() <= 11) {
				dfa[i].setType("注释");
			}
			if (dfa[i].getState() >= 12 && dfa[i].getState() <= 18
					|| dfa[i].getState() >= 20 && dfa[i].getState() <= 28) {
				dfa[i].setType("运算符");
			}
			if (dfa[i].getState() == 29 || dfa[i].getState() == 19) {
				dfa[i].setType("界符");
			}
		}

		return dfa;
	}

	/**
	 * 展示得到的DFA
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[][] showDFA() throws Exception {
		return result1;
	}

	/**
	 * 设置是否终止状态和具体类型
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
	 * 判断是否终止状态
	 * 
	 * @param t
	 * @return
	 */
	public boolean isFinish(int t) {
		// System.out.println("............"+IsFinish.get(t));
		return IsFinish.get(t);
	}

	/**
	 * 判断具体什么类型
	 * 
	 * @param t
	 * @return
	 */
	public String getType(int t) {
		return IsType.get(t);
	}

	/**
	 * 得到类型表
	 * 
	 * @return
	 */
	public Map<Integer, String> getType() {
		return this.IsType;
	}

	/**
	 * 
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file       读取数据的源Excel
	 * 
	 * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * 
	 * @return 读出的Excel中数据的内容
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

		// 打开HSSFWorkbook

		POIFSFileSystem fs = new POIFSFileSystem(in);

		HSSFWorkbook wb = new HSSFWorkbook(fs);

		HSSFCell cell = null;

		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

			HSSFSheet st = wb.getSheetAt(sheetIndex);

			// 第一行为标题，不取

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

						// 注意：一定要设成这个，否则可能会出现乱码

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

							// 导入时如果为公式生成的数据则无值

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
	 * 去掉字符串右边的空格
	 * 
	 * @param str 要处理的字符串
	 * 
	 * @return 处理后的字符串
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
