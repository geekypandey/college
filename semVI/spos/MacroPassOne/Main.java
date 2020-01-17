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

	public String parse() throws Exception{
		int kptabpointer=1;
		int mdtpointer=1;
		int pntabpointer=1;
		String st;
		String output = "";
		Boolean isname = false;
		while((st = br.readLine()) != null){
			String[] arr = st.split("\\s+|,");
			output = ""; 
			for(String a : arr){
				HashMap<Integer,String> pntab = new HashMap<Integer,String>();
				if(a.equals("MACRO")){
					isname = true;
					pntab.clear();
					break;
				}else if(isname == true){
					//create an entry in mnt with the name and then pass through the reamining for subsequent
					//pp,kp and insert in pntab , kptab .
					String name = a;
					int pp = 0;
					int kp = 0;
					int kpdtp = kptabpointer;
					int i;
					for(i = 1 ; i < arr.length ; i++){
						String parameter = arr[i];
							
						if(parameter.indexOf('=') > 0){
							kp = kp+1;
							int index = parameter.indexOf('=');
							KpTabEntry kentry;
							if(index == (parameter.length() - 1)){
								//keyword parameter
								kentry = new KpTabEntry(parameter.substring(0,index),"-");
							}else{
								//default parameter
								kentry = new KpTabEntry(parameter.substring(0,index),parameter.substring(index+1));
							}
							kptab.put(kptabpointer,kentry);
							kptabpointer = kptabpointer + 1;
							pntab.put(i,parameter.substring(0,index));
						}else{
							pp = pp+1;
							pntab.put(i,parameter);
						}
					}
					System.out.println(pntab);
					System.out.println(name + " " + pp + " "+ kp + " " + mdtpointer + " "+ kptabpointer);	
					MntEntry entry = new MntEntry(name,pp,kp,mdtpointer,kpdtp);
					isname = false;
					break;
				}
				//now work for each model statement if present in pntab insert it as (p,#n) else same
				System.out.println(a);
				if(pntab.containsValue("&FIRST")){
					System.out.println("Hello");
				}
			}
		}	
		return output;
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

}

class KpTabEntry {
	String key;
	String value;

	public KpTabEntry(String key,String value) {
		this.key = key;
		this.value = value;
	}
}
