package 구현.연결큐;
class Node{
	Integer data;
	Node link;
	
	public Node(int data) {
		this.data = data;
	}
}

public class LinkedQueue {
	Node front; //맨 앞
	Node rear;  //맨 뒤
	
	//공백 확인
	public boolean isEmpty() {
		return (front == null) && (rear == null);
	}
	//조회
	public Integer peek() {
		//공백확인
		if(isEmpty()) return null;
		return front.data;//제일 앞에 거 반환
	}
	//삽입
	public void enQueue(int data) {
		Node node = new Node(data);
		//공백상태에서
		if(isEmpty()) {
			front = rear = node;
		}
		//공백이 아닌경우
		else {
			//기존 맨뒤의 노드와 연결
			rear.link = node;
			//맨뒤는 새로운 노드
			rear = node;
		}
	}
	//삭제
	public Integer deQueue() {
		//공백일때
		if(isEmpty()) return null;
		//아닐때
		//front 삭제 전 data 빼놓기
		Integer data = front.data;
		//front는 front link(front 삭제)
		front = front.link;
		//만약 기존의 사이즈가 1개라 front rear가 같은 곳을 바라보고 있을 때,
		//front는 null이 되지만 rear는 아직 기존의 노드를 바라보고 있으므로 rear도 비워줘야함
		if(front == null)
			rear = null;
		return data;
	}
	
	
}
