package lab1;

public class DFA {
	private int state;// ��ǰ״̬
	private String[] input;// ���봮����
	private int nextState;// ��һ��״̬
	private String type;// ���ͣ����ࣩ

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
