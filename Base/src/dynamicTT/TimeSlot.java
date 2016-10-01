package dynamicTT;

public class TimeSlot {
	//private int slotTime;
	private Dictado lecture;
	
//	public TimeSlot(int t){
//		this.setSlotTime(t);
//	}

//	public int getSlotTime() {
//		return slotTime;
//	}
//
//	public void setSlotTime(int slotTime) {
//		this.slotTime = slotTime;
//	}

	public Dictado getDictado() {
		return lecture;
	}

	public void setDictado(Dictado lecture) {
		this.lecture = lecture;
	}
}
