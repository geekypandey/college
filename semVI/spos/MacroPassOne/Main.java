import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

class Main {
	public static void main(String[] args){
		/*
		Read file and give output as file or as object.
		Initially object,later in file
		*/	
		System.out.println("WELCOME TO MACRO PASS ONE ASSEMBLER!");
		System.out.print("Enter filename: ");
		Scanner input = new Scanner(System.in);
		String filename = input.next();
		try{
			MacFile inputfile = new MacFile(filename);
			inputfile.parse();	
			inputfile.getMnt();
			inputfile.getMdt();
			inputfile.getKpTab();
		}catch(Exception e){
			System.err.println("Error in parsing!");
		}

	}
}

class MacFile {
	ArrayList<MntEntry> mnt;
	ArrayList<String> mdt;
	HashMap<Integer,KpTabEntry> kptab;
	BufferedReader br;

	public MacFile(){
		mnt = new ArrayList<MntEntry>();
		mdt = new ArrayList<String>();
		kptab = new HashMap<Integer,KpTabEntry>();
	}

	public MacFile(String filename) throws Exception{
		mnt = new ArrayList<MntEntry>();
		mdt = new ArrayList<String>();
		kptab = new HashMap<Integer,KpTabEntry>();
		File file = new File(filename);
		br = new BufferedReader(new FileReader(file));
	}	

	public void parse() throws Exception{
		int kptabpointer=1;
		int mdtpointer=1;
		int pntabpointer=1;
		String st;
		String output = "";
		Boolean isname = false;
		//reading each line from the file
		HashMap<Integer,String> pntab = new HashMap<Integer,String>();
		while((st = br.readLine()) != null){
			String[] arr = st.split("\\s+|,\\s*");
			//encountering empty lines
			if(arr[0].equals("")){
				continue;
			}
			output = "";
			//if name Macro then next line would contain macro name and parameters
			if(arr[0].equals("MACRO")){
				isname = true;
				pntab.clear();
			}else{
				if(isname == true){
					String name = arr[0]; //macro name
					int pp=0;
					int kp=0;
					int mdtp = mdtpointer;
					int kpdtp = kptabpointer;
					int i;
					//processing all parameters
					for(i=1;i<arr.length;i++){
						int index = arr[i].indexOf("=");
						if(index < 0){
							pp = pp +1;
							pntab.put(i,arr[i]);
						}else{
							KpTabEntry kentry;
							if(index == arr[i].length() - 1){
								kentry = new KpTabEntry(arr[i].substring(0,index),"-");
							}else{
								kentry = new KpTabEntry(arr[i].substring(0,index),arr[i].substring(index+1,arr[i].length()));
							}
							pntab.put(i,arr[i].substring(0,index));
							kptab.put(kptabpointer,kentry);
							kptabpointer = kptabpointer + 1 ;
							kp = kp +1;
						}
					}
					MntEntry entry = new MntEntry(name,pp,kp,mdtp,kpdtp);
					mnt.add(entry);
					isname = false;	
				}else{
					//process the model statements	
					output = "";
					for(String a:arr){
						if(pntab.containsValue(a)){
							for(Map.Entry entry : pntab.entrySet()){
								if(a.equals(entry.getValue())){
									output = output + " " + "(P,"+entry.getKey()+")";
								}
							}		
						}else{
							output = output + " " + a;
						}
					}
					mdt.add(output);
					mdtpointer = mdtpointer + 1;
				}
			}
		}
	}

	public void getMnt(){
		System.out.println("-----------------------------------");
		for(MntEntry entry: mnt){
			System.out.println(entry.get());
		}
		System.out.println("-----------------------------------\n");
	}

	public void getMdt(){
		System.out.println("-----------------------------------");
		int i;
		for(i=0;i<mdt.size();i++){
			System.out.println((i+1) + mdt.get(i));
		}
		System.out.println("-----------------------------------\n");
	}

	public void getKpTab(){
		System.out.println("-----------------------------------");
		//kptab is hashmap of <Integer,KpTabEntry>
		for(Map.Entry<Integer,KpTabEntry> entry : kptab.entrySet()){
			System.out.print(entry.getKey() + " ");
			System.out.println((entry.getValue()).get());
		}
		System.out.println("-----------------------------------\n");
	}
}

class MntEntry {
	/*
	 Class for entry in Macro Name Table(MNT).Parameters containing MACRO NAME(name),POSTIONAL PARAMETERS(pp),
	 KEYWORD PARAMETERS(kp),MACRO DEFINITION TABLE POINTER(mdtp) & KEYWORD PARAMETER DESCRIPTION TABLE POINTER(kpdtp).
	 */
	
	String name; 
	int pp;  
	int kp;
	int mdtp;
	int kpdtp;

	public MntEntry(String name, int pp ,int kp , int mdtp , int kpdtp){
		this.name = name;
		this.pp = pp;
		this.kp = kp;
		this.mdtp = mdtp;
		this.kpdtp = kpdtp;
	}

	public String get(){
		String output = name + " " + pp + " " + kp + " "+ mdtp + " " + kpdtp;
		return output;
	}

}

class KpTabEntry {
	String key;
	String value;

	public KpTabEntry(String key,String value) {
		this.key = key;
		this.value = value;
	}

	public String get(){
		String output = key + " " + value;
		return output;
	}

}
