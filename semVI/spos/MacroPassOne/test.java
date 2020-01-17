import java.util.HashMap;

class test {
	public static void main(String[] args){
		HashMap<Integer,String> pntab = new HashMap<Integer,String>();
		String test2 = "&MEM_VAL=";
		int index = test2.indexOf('=');
		System.out.println(index);
		System.out.println(test2.substring(0,index));
		pntab.put(1,"&FIRST");
		System.out.println(pntab);
		if(pntab.containsValue("&FIRST")){
			System.out.println("Yes!");
		}
	}

}
