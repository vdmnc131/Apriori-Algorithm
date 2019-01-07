import java.io.*;
import java.lang.*;
import java.lang.Integer;
import java.util.*;
class Simulate {
	static int minsup=0;
	static int line=0;
	public static void main (String[] args)
	{	
		// long start=System.currentTimeMillis();
		try 
		{
			
			HashMap<Integer, Integer> record;
			HashSet<Vector<Integer>> F12;
			F12 = new HashSet<Vector<Integer>>();
			record= new HashMap<Integer, Integer>();
	
			int count = 2;
			Vector<Vector<Integer>> data = new Vector<Vector<Integer>>();
			Vector<Vector<Integer>> C1 = new Vector<Vector<Integer>>();
			Vector<Vector<Integer>> F1 = new Vector<Vector<Integer>>();
			FileInputStream fstream =  new FileInputStream(args[0]); 
			minsup=Integer.parseInt(args[1]);

	 		Scanner f = new Scanner(fstream);

	 		 while(f.hasNextLine())
	 		{	line++;
	 			String query = f.nextLine(); 
	 			String[] word = query.split(" ");
	 			int size = word.length;	
	 			 Vector<Integer>temp= new Vector<Integer>();


	 			for(int i=0;i<size;i++) {
	 				temp.add(Integer.parseInt(word[i]));
	 					if(record.containsKey(Integer.parseInt(word[i]))) {
	 						record.put(Integer.parseInt(word[i]),record.get(Integer.parseInt(word[i]))+1);
	 					}
	 					else {
	 						record.put(Integer.parseInt(word[i]),1);
	 						
	 					}

	 			}

	 			data.add(temp);// add the line size of the each line
	 		}
	 		minsup=(minsup*line)/100;
	 		Iterator <Integer> it= record. keySet (). iterator ();
			// C1 formation
			while(it.hasNext())
			{
				 int temp4;
				 temp4=it.next();
				 Vector<Integer>temp2= new Vector<Integer>();
				 temp2.add(temp4);
				 temp2.add(record.get(temp4));
				 C1.add(temp2);

			}
			//F1 formation
			for(int i=0;i<C1.size();i++) {
				if(C1.get(i).get(1)>=minsup) {                                      
					C1.get(i).remove(1);
					F1.add(C1.get(i));
				}
			}


		 
			Vector<Integer> A = new Vector<Integer>();
			for(int i=0;i<F1.size();i++) {
				A.add(F1.get(i).get(0));
			}
			Collections.sort(A);
			F1.clear();
			for(int i=0;i<A.size();i++) {
				Vector<Integer> B =new Vector<Integer>();
				B.add(A.get(i));
				F1.add(B);
			}
 				
				






			for(int i=0;i<F1.size();i++) {
					for(int p=0;p<F1.get(i).size();p++) {
						System.out.print(F1.get(i).get(p));
						if(p!=F1.get(i).size()-1)
						System.out.print(" ");
					}
					System.out.println();
			}





				while(F1.size()>1) {                 									
				C1.clear();

				Candid_Generation(F1, 0, 0, F1.size(), C1, count);
	
				Vector<Integer> temp10=new Vector<Integer>();
				if(F1.get(0).size() >1) {
					for(int i=0;i<C1.size();i++) {
					 	
					 	

					 	for(int j=0;j<C1.get(i).size()-1;j++) {
					 		Vector<Integer> replica= new Vector<Integer>();
					 		for(int k=0;k<C1.get(i).size();k++) {
					 			if(k!=j) {
					 				replica.add(C1.get(i).get(k));
					 			}
					 		}
					 		// for(int h=0;h<replica.size();h++) {
					 		// 	System.out.print(replica.get(h));
					 		// 	System.out.print(",");
					 		// }
					 		// System.out.println();
					 		boolean b=false;
					 		b=Binary2(F12, replica);
					 		  // System.out.println(b);
					 		if(!b){
					 			temp10.add(i);
					 			break;
					 		}
						} 
					}
				}
				int counter2=0;
				for(int s=0;s<temp10.size();s++) {
					C1.remove(C1.get(temp10.get(s)-counter2));
					counter2++;
					
				}
				

				F1= Frequent(C1, data);
				 mapping(F1, F12);
			
				for(int i=0;i<F1.size();i++) {
					for(int p=0;p<F1.get(i).size();p++) {
						System.out.print(F1.get(i).get(p));
						if(p!=F1.get(i).size()-1)
						System.out.print(" ");
					}
					System.out.println();
				}


				count++;
				
			}


		}
	 	catch (FileNotFoundException e)
		{
	 		System.out.println("File not found");
		}
				// 		long stop=System.currentTimeMillis();
				// long elapsedTime=stop-start;
				// System.out.println(elapsedTime);
				// System.out.println();
				// System.out.println(line);
	}

	public static void Candid_Generation( Vector<Vector<Integer>> A, int k,  int start, int end, Vector<Vector<Integer>> B, int count) {
		

		if(k==count-2) {
			// System.out.println("gfh");
			for(int l=start;l<end;l++) {

				for(int m=l+1;m<end;m++) {
					Vector<Integer> temp9= new Vector<Integer>();
					temp9=(Vector)A.get(l).clone();
					temp9.add(A.get(m).get(count-2));


					B.add(temp9);

					
				}
			}
		}
		else {
			for(int i = start; i<end; i++) {
				for(int j=i+1; j<end; j++) {
					if(A.get(i).get(k) == A.get(end-1).get(k)) {
						Candid_Generation(A, k+1, i, end, B, count);
						i=end;
						break;

					}
					if(A.get(i).get(k) != A.get(j).get(k)) {

						if(i != j-1){
							 // System.out.println(j);
							Candid_Generation(A, k+1, i, j, B, count);
						}
						i=j-1;
						
						break;
					}

				}
			}
		}
		
	
	}

	public static Vector<Vector<Integer> > Frequent( Vector<Vector<Integer>> C, Vector<Vector<Integer>> data) {
		Vector<Vector<Integer>> temp5 = new Vector<Vector<Integer>>();
		for(int i=0;i<C.size();i++) {
				int freq=0;
				for(int k=0;k<data.size();k++) {
					
					int l=0;
					for(int j=0;j<C.get(i).size();j++) {
						if(!data.get(k).contains(C.get(i).get(j))) 
							break;
						else {
							l++;
						}
						if(l==C.get(i).size()){
							freq++;
						}
					}

	
						if(freq>=minsup) {
							temp5.add(C.get(i));
							break;
						}


					
				}

			
		}
		return(temp5);
	}

	public static void  mapping( Vector<Vector<Integer>> F1 ,HashSet<Vector<Integer>> F12) {
		
		F12.clear();
		for(int i=0;i<F1.size();i++) {
			F12.add(F1.get(i));
		}

	}

	public static boolean Binary2(HashSet<Vector<Integer>> F12 ,Vector<Integer> replica) {
		if(F12.contains(replica))
			return true;
		else
			return false;
	}

} 

