package 구현.연결스택;

class Node{
	int data;
	Node link;
	
	public Node(int data) {
		this.data = data;
	}
}
public class LinkedStack {
	Node top;

	//공백 확인	
	public boolean isEmpty() {
		return top==null;
	}
	//삽입
	public void push(int data) {
		//새로운 노드만들기
		Node node = new Node(data);
		//새로운 노드의 링크를 탑으로 (탑을 내리기)
		node.link = top;
		//노드를 맨위로 올리기
		top = node;
		
	}
	//조회
	public Integer peek() {
		//공백확인
		if(isEmpty()) return null;
		//top의 데이터 반환
		return top.data;
	}
	//삭제
	public Integer pop() {
		//공백확인
		if(isEmpty()) return null;
		//top의 데이터를 뽑아냄
		int data = top.data;
		//top을 탑의 링크로(삭제)
		top = top.link;
		//데이터 반환
		return data;
	}
	
}
