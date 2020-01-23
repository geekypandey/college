import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

class Main {
	public static void main(String[] args){
	 	MacFile input = new MacFile(); 				
		try {
			input.readData();
			input.getMnt();
			input.getMdt();
			input.getKpTab();
		}catch(Exception e){
			System.err.println("Error in parsing");
		}			
	}
	
}


class MacFile {
	ArrayList<String> mdt;
	ArrayList<MntEntry> mnt;
	HashMap<Integer,KpTabEntry> kptab;
	BufferedReader br;

	public MacFile() {
		mdt = new ArrayList<String>();
		mnt = new ArrayList<MntEntry>();
		kptab = new HashMap<Integer,KpTabEntry>();
		 
	}	

	public void readData() throws Exception{
		File mdt_file = new File("mdt.txt");
		File mnt_file = new File("mnt.txt");
		File kptab_file = new File("kptab.txt");
		br = new BufferedReader(new FileReader(mdt_file));
		String st;	
		//Readingn mdt content
		while((st = br.readLine()) != null){
			mdt.add(st);	
		}
		br.close();

		br = new BufferedReader(new FileReader(mnt_file));
		while((st = br.readLine())!= null){
			String[] split_string = st.split(" ");
			MntEntry entry =  new MntEntry(split_string);
			mnt.add(entry);
		}
		br.close();

		br = new BufferedReader(new FileReader(kptab_file));
		while((st = br.readLine()) != null){
			String[] split_string = st.split(" ");	
			KpTabEntry kentry = new KpTabEntry(split_string[1],split_string[2]);
			kptab.put(Integer.parseInt(split_string[0]),kentry);
		}
		br.close();

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

	public void readCall(String filename)throws Exception{
		File file = new File(filename);
		br = new BufferedReader(new FileReader(file));
		String st;
		//process the call and form an aptab
		//And then there is just substitution work	
				
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
	
	public MntEntry(String[] arr){
		this.name = arr[0];
		this.pp = Integer.parseInt(arr[1]);
		this.kp = Integer.parseInt(arr[2]);
		this.mdtp = Integer.parseInt(arr[3]);
		this.kpdtp = Integer.parseInt(arr[4]);
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
