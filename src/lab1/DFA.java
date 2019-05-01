package lab1;

public class DFA {
	private int state;// 当前状态
	private String[] input;// 输入串数组
	private int nextState;// 下一个状态
	private String type;// 类型（大类）

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getNextState() {
		return nextState;
	}

	public void setNextState(int nextState) {
		this.nextState = nextState;
	}

	public String[] getInput() {
		return input;
	}

	public void setInput(String[] input) {
		this.input = input;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
