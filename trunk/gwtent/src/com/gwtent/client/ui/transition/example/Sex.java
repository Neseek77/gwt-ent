package com.gwtent.client.ui.transition.example;

public class Sex {

		private String desc;
		private int value;
		
		private Sex(String desc, int value){
			this.desc = desc;
			this.value = value;
		}
		
		public String toString(){
			return desc;
		}			
		
		public int getValue(){
			return value;
		}
		
		
		public static Sex Male = new Sex("Male", 1);
		public static Sex Female = new Sex("Female", 2);
	
}
