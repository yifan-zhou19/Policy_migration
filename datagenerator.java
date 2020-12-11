package datagen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;
import java.util.Vector;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException; 

@SuppressWarnings("unchecked")
public class datagenerator {

        private static int m_numUsers;
        private static int m_numObjs;
        private static int m_numUserAttribs;
        private static int m_numObjAttribs;
        private static int m_policySize;
        private static int DIV_FACTOR;
        private static int group;

        private static String path = // file path "C:/Users/****/Desktop/CodeFiles_t/";

        /**
         * @param args the command line arguments
         * @throws FileNotFoundException
         */
        @SuppressWarnings("rawtypes")
        public static void main(String[] args) throws FileNotFoundException {
                // TODO code application logic here
                if (args.length < 6) {
                        System.out.println("invoke as ABACMining <numUsers> <numObjs> <numUserAttributes> <numObjAttributes> <policySize> <DIV_FACTOR>");
                        System.exit(0);
                }

                m_numUsers = Integer.parseInt(args[0]);
                m_numObjs = Integer.parseInt(args[1]);
                m_numUserAttribs = Integer.parseInt(args[2]);
                m_numObjAttribs = Integer.parseInt(args[3]);
                m_policySize = Integer.parseInt(args[4]);
                DIV_FACTOR = Integer.parseInt(args[5]);
                group = Integer.parseInt(args[6]);

                HashMap<Integer, String> hmapU = new HashMap<Integer, String>();
                hmapU.put(1,"a");
                hmapU.put(2,"b");
                hmapU.put(3,"c");
                hmapU.put(4,"d");

                HashMap<Integer, String> hmapO = new HashMap<Integer, String>();
                hmapO.put(1,"w");
                hmapO.put(2,"x");
                hmapO.put(3,"y");
                hmapO.put(4,"z");


                HashMap<String, Integer> hmapUU = new HashMap<String, Integer>();
                hmapUU.put("a",1);
                hmapUU.put("b",2);
                hmapUU.put("c",3);
                hmapUU.put("d",4);

                HashMap<String, Integer> hmapOO = new HashMap<String, Integer>();
                hmapOO.put("w",1);
                hmapOO.put("x",2);
                hmapOO.put("y",3);
                hmapOO.put("z",4);


                Random r = new Random(1234);
                Vector userattribs = new Vector(m_numUserAttribs);
                Vector objattribs = new Vector(m_numObjAttribs);

                for (int i=0; i<m_numUserAttribs; i++) {
                        userattribs.add(i);
                }

                for (int i=0; i<m_numObjAttribs; i++) {
                        objattribs.add(i);
                }

                // create policy
                Vector rulesN[][] = new Vector[m_policySize][2];
                Vector rulesExtra[][] = new Vector[m_policySize][2];
				Vector ruleRest[][]=new Vector[m_policySize][2];
				Vector ruleTempExtra[][]=new Vector[m_policySize][2];
				//Create random policies without hierarchy, the other half of the policies will be created with hierarchy
                for (int i=0; i<m_policySize/2; i++) {
					System.out.println(" i:"+i+"\n");
					// For each policy, make sure the num of attributes is more than one.
                        int num_rule_u_attrs = 1 + r.nextInt(m_numUserAttribs/DIV_FACTOR - 1);
                        int num_rule_o_attrs = 1 + r.nextInt(m_numObjAttribs/DIV_FACTOR - 1);
 
                        //-----------------------extra condition so that single rules are not created--------------------------//
                        if(num_rule_u_attrs==1){
                                num_rule_u_attrs+=1;
                        }

                        if(num_rule_o_attrs==1){
                                num_rule_o_attrs+=1;
                        }
                        //-----------------------extra condition--------------------------//

                        Vector tempuattribs = new Vector(userattribs);
                        Vector tempoattribs = new Vector(objattribs);

                        //----------------New addition-----------------
                        Vector rule_u_attribsN = new Vector();
                        Vector rule_o_attribsN = new Vector();

                        Vector rule_u_attribsExtra = new Vector();
                        Vector rule_o_attribsExtra = new Vector();


                        //---------------------------------------------
                        // pick some number of user attribs
						
			// Making sure no duplicated attributes.
                        for (int j=0; j<num_rule_u_attrs; j++) {
                                int pos = r.nextInt(tempuattribs.size());
                                Object temp = tempuattribs.remove(pos);
                                int tempi = (Integer) temp;
                                int t = 1+ r.nextInt(group);
                                String st = hmapU.get(t);
                                temp=temp+st;
                                rule_u_attribsN.add(temp);
                                rule_u_attribsExtra.add(tempi);
                        }
                        // pick some number of object attribs
						
			// Making sure no duplicated attributes.
                        for (int j=0; j<num_rule_o_attrs; j++) {
                                int pos = r.nextInt(tempoattribs.size());
                                Object temp = tempoattribs.remove(pos);
                                int tempi = (Integer) temp;
                                int t = 1+ r.nextInt(group);
                                String st = hmapO.get(t);
                                temp=temp+st;
                                rule_o_attribsN.add(temp);
                                rule_o_attribsExtra.add(tempi);
                        }
                        // Save the vectors into the rule

                        rulesN[i][0] = rule_u_attribsN;
                        rulesN[i][1] = rule_o_attribsN;
						ruleTempExtra[i][0]=rule_u_attribsExtra;
						ruleTempExtra[i][1]=rule_o_attribsExtra;
						ruleRest[i][0]=tempuattribs;
						ruleRest[i][1]=tempoattribs;
						
						
						
						
            //adding random rules
                        Vector tempU = new Vector();
                        for (int k=0;k<m_numUserAttribs;k++){
                                int ran = r.nextInt(m_numUserAttribs);
                                if(!rule_u_attribsExtra.contains(ran)){
                                        int gp = 1+ r.nextInt(group);
                                        String st = String.valueOf(ran)+hmapU.get(gp);
                                        tempU.add(st);
                                }
                                if(tempU.size()==3)
                                        break;
                        }
                        rulesExtra[i][0] = tempU;

                        Vector tempO = new Vector();
                        for (int k=0;k<m_numObjAttribs;k++){
                                int ran = r.nextInt(m_numObjAttribs);
                                if(!rule_o_attribsExtra.contains(ran)){
                                        int gp = 1+ r.nextInt(group);
                                        String st = String.valueOf(ran)+hmapO.get(gp);
                                        tempO.add(st);
                                }
                                if(tempO.size()==3)
                                        break;
                        }
                        rulesExtra[i][1] = tempO;

                        //System.out.println("U "+rule_u_attribs+" "+rule_u_attribsN);
                        //System.out.println("O"+rule_o_attribs+" "+rule_o_attribsN);
						
						
	
	
	//----------------------------------------------------
                }
				
				
									
	//-------------------------------------------------
	// Adding role hierarchy
	// they use extra rule attributes in order to create users and objects.
	        int policy_size=m_policySize/2;
	        for (int i=m_policySize/2; i<m_policySize;) {
				//System.out.println(" i:"+i+"\n");
					int base = r.nextInt(i);
					
					
					//rulesN[base][0] = rule_u_attribsN;
                    //rulesN[base][1] = rule_o_attribsN;
					//ruleRest[base][0]=tempuattribs;
					//ruleRest[base][1]=tempoattribs;
					int num_rule_u_attrs=rulesN[base][0].size();
					int num_rule_o_attrs=rulesN[base][1].size();
					
					int m_num_rule_u_attrs_h=0;
					int m_num_rule_o_attrs_h=0;
					if(num_rule_u_attrs<m_numUserAttribs){
						
						m_num_rule_u_attrs_h=m_numUserAttribs-num_rule_u_attrs;
					}
					if(num_rule_o_attrs<m_numObjAttribs){
						m_num_rule_o_attrs_h=m_numObjAttribs-num_rule_o_attrs;
					}
					int num_rule_u_attrs_h=1+r.nextInt(m_num_rule_u_attrs_h/2+1);//attributes of parents
					int num_rule_o_attrs_h=1+r.nextInt(m_num_rule_o_attrs_h/2+1);
					// for each node, create 1-2 parent;
					int num_parent=1+r.nextInt(2);//
					int p=0;
					//System.out.println(num_parent+" "+i+" "+num_rule_u_attrs+" "+m_numUserAttribs+" "+m_num_rule_u_attrs_h+" "+num_rule_u_attrs_h+"\n");
					for( p=1;p<=num_parent&&i<m_policySize;p++){
						//System.out.println(" "+p+" "+num_parent+" "+i+"\n");
						Vector tempuattribs_h = new Vector(ruleRest[base][0]);
						Vector tempoattribs_h = new Vector(ruleRest[base][1]);
						Vector rule_u_attribsN_h = new Vector(rulesN[base][0]);
                        Vector rule_o_attribsN_h = new Vector(rulesN[base][1]);
						if(tempuattribs_h.size()==0||tempoattribs_h.size()==0){
							break;
						}
                        Vector rule_u_attribsExtra_h = new Vector(ruleTempExtra[base][0]);
                        Vector rule_o_attribsExtra_h = new Vector(ruleTempExtra[base][1]);
						
						for(int j = 0;j<num_rule_u_attrs_h;j++){
							int pos = r.nextInt(tempuattribs_h.size());
							Object temp = tempuattribs_h.remove(pos);
							int tempi = (Integer) temp;
							int t = 1+ r.nextInt(group);
							String st = hmapU.get(t);
							temp=temp+st;
							rule_u_attribsN_h.add(temp);
							rule_u_attribsExtra_h.add(tempi);
								
						}
						for (int j=0; j<num_rule_o_attrs_h; j++) {
                            int pos = r.nextInt(tempoattribs_h.size());
                            Object temp = tempoattribs_h.remove(pos);
                            int tempi = (Integer) temp;
                            int t = 1+ r.nextInt(group);
                            String st = hmapO.get(t);
                            temp=temp+st;
                            rule_o_attribsN_h.add(temp);
                            rule_o_attribsExtra_h.add(tempi);
                        }
                        // Save the vectors into the rule

                        rulesN[i][0] = rule_u_attribsN_h;
                        rulesN[i][1] = rule_o_attribsN_h;
						ruleTempExtra[i][0]=rule_u_attribsExtra_h;
						ruleTempExtra[i][1]=rule_o_attribsExtra_h;
						ruleRest[i][0]=tempuattribs_h;
						ruleRest[i][1]=tempoattribs_h;
						
						
						//adding random rules
                        Vector tempU = new Vector();
                        for (int k=0;k<m_numUserAttribs;k++){
                                int ran = r.nextInt(m_numUserAttribs);
                                if(!rule_u_attribsExtra_h.contains(ran)){
                                        int gp = 1+ r.nextInt(group);
                                        String st = String.valueOf(ran)+hmapU.get(gp);
                                        tempU.add(st);
                                }
                                if(tempU.size()==3)
                                        break;
                        }
                        rulesExtra[i][0] = tempU;

                        Vector tempO = new Vector();
                        for (int k=0;k<m_numObjAttribs;k++){
                                int ran = r.nextInt(m_numObjAttribs);
                                if(!rule_o_attribsExtra_h.contains(ran)){
                                        int gp = 1+ r.nextInt(group);
                                        String st = String.valueOf(ran)+hmapO.get(gp);
                                        tempO.add(st);
                                }
                                if(tempO.size()==3)
                                        break;
                        }
                        rulesExtra[i][1] = tempO;
						
						i++;
					}
						// End adding  role hierarchy
				
			}
				
				
//User attribute relationship: usersmatN
//Object attribute relationship:objsmatN


                String[][] usersmatN = new String[m_numUsers][m_numUserAttribs];
                String[][] objsmatN = new String[m_numObjs][m_numObjAttribs];
                String[][] upamat_newN = new String[m_numUsers*m_numObjs][m_numUserAttribs+m_numObjAttribs+1];
                Vector usersN[] = new Vector[m_numUsers];
                Vector objectsN[] = new Vector[m_numObjs];
                int[][] upamatN = new int[m_numUsers][m_numObjs];
                int[][] upamatMy = new int[m_numUsers*m_numObjs][((m_numUserAttribs+m_numObjAttribs)*group)+1];

                for(int k=0; k<m_numUsers;k++){
                        for(int j=0; j<m_numUserAttribs; j++){
                                usersmatN[k][j]=String.valueOf(0);
                        }
                }


                for(int k=0; k<m_numObjs;k++){
                        for(int j=0; j<m_numObjAttribs; j++){
                                objsmatN[k][j]=String.valueOf(0);
                        }
                }


                for(int k=0; k<m_numUsers*m_numObjs;k++){
                        for(int j=0; j<m_numUserAttribs+m_numObjAttribs+1; j++){
                                upamat_newN[k][j]=String.valueOf(0);
                        }
                }
                //Making sure 80% of the rules are used
                int top80 = (int)(m_policySize*.8);

                for(int i=0; i<top80 && i < m_numUsers; i++){
                        Vector usr = rulesN[i][0];
                        Vector usrE = rulesExtra[i][0];
                        Vector mergeU = new Vector();
                        mergeU.addAll(usr);
                        mergeU.addAll(usrE);
                        Iterator itru = mergeU.iterator();


                        while(itru.hasNext()){
                                String st = (String) itru.next();
                                int val = Integer.parseInt(st.substring(0, st.length()-1));
                                usersmatN[i][val] = st;
                        }

                        System.out.println(mergeU);
                        usersN[i] = mergeU;
                }
                

                for(int i=0; i<top80 && i < m_numObjs; i++){

                        Vector obj = rulesN[i][1];
                        Vector objE = rulesExtra[i][1];
                        Vector mergeO = new Vector();
                        mergeO.addAll(obj);
                        mergeO.addAll(objE);
                        Iterator itro = mergeO.iterator();

                        while(itro.hasNext()){
                                String st = (String) itro.next();
                                int val = Integer.parseInt(st.substring(0, st.length()-1));
                                objsmatN[i][val] = st;
                        }


                        //System.out.println(mergeO);
                        objectsN[i]=mergeO;
                }



                // create user to user attrib matrix
                for (int i=0; i<usersN.length; i++) {
                        if(i<top80 && i < m_numUsers)
                                continue;
                        int num_u_attrs = r.nextInt(m_numUserAttribs);
                        Vector u_attribs = new Vector();
                        Vector u_attribsN = new Vector();
                        Vector tempuattribs = new Vector(userattribs);
                        for (int j=0; j<num_u_attrs; j++) {
                                int pos = r.nextInt(tempuattribs.size());
                                int val = ((Integer)(tempuattribs.remove(pos))).intValue();
                                u_attribs.add(val);
                                int temp = 1+ r.nextInt(group);
                                String st = hmapU.get(temp);
                                //String stt= String.valueOf(i);
                                usersmatN[i][val] = String.valueOf(val)+st;
                                u_attribsN.add(String.valueOf(val)+st);
                        }
                        usersN[i] = u_attribsN;
                }


                // create object to object attrib matrix
                for (int i=0; i<objectsN.length; i++) {
                        if(i<top80 && i < m_numObjs)
                                continue;
                        int num_o_attrs = r.nextInt(m_numUserAttribs);
                        Vector o_attribs = new Vector();
                        Vector o_attribsN = new Vector();
                        Vector tempoattribs = new Vector(objattribs);
                        for (int j=0; j<num_o_attrs; j++) {
                                int pos = r.nextInt(tempoattribs.size());
                                int val = ((Integer)tempoattribs.remove(pos)).intValue();
                                //val=20+val;
                                o_attribs.add(val);
                                int temp = 1+ r.nextInt(group);
                                String st = hmapO.get(temp);
                                objsmatN[i][val] = String.valueOf(val)+st;
                                o_attribsN.add(String.valueOf(val)+st);
                        }
                        objectsN[i] = o_attribsN;
                }


                // check users and objects for policy match new one
                Vector[][] policymatchN = new Vector[m_policySize][2];
                for (int i=0; i<m_policySize; i++) {
                        policymatchN[i][0] = new Vector();
                        TreeSet userreqN = new TreeSet(rulesN[i][0]);
                        for (int j=0; j<m_numUsers; j++) {
                                TreeSet userattsN = new TreeSet(usersN[j]);
                                if (userattsN.containsAll(userreqN)) {
                                        policymatchN[i][0].add(j);
                                }
                        }
                        policymatchN[i][1] = new Vector();
                        TreeSet objreqN = new TreeSet(rulesN[i][1]);
                        for (int j=0; j<m_numObjs; j++) {
                                TreeSet objattsN = new TreeSet(objectsN[j]);

                                if (objattsN.containsAll(objreqN)) {
                                        policymatchN[i][1].add(j);
                                }
                        }

                        for (int j=0; j<policymatchN[i][0].size(); j++) {
                                int x = ((Integer)policymatchN[i][0].elementAt(j)).intValue();
                                for (int k=0; k<policymatchN[i][1].size(); k++) {
                                        int y = ((Integer)policymatchN[i][1].elementAt(k)).intValue();
                                        upamatN[x][y] = 1;
                                }
                        }
                }



                ///////////////////////////////////////////////////////////////////////////////////////
                // tanay
                int ii=0;
                int k=0;
                int p=0;
                int n=0;

                for(; ii<m_numObjs;ii++){
                        k=0;
                        n=ii;
                        while(k<m_numUsers){
                                for(int j=0; j<m_numUserAttribs;j++){
                                        upamat_newN[k+p][j]=usersmatN[k][j];
                                }
                                k++;
                                //ii++;
                                //System.out.println(k-1);
                        }
                        p=k+p;
                        ii=n;
                }

                int m=0;
                ii=0;
                int jj=0;

                for(; ii<m_numObjs;ii++){
                        k=0;
                        while(k<m_numUsers){
                                for(; jj<m_numObjAttribs;jj++){
                                        upamat_newN[m][m_numUserAttribs+jj]=objsmatN[ii][jj];
                                }
                                k++;
                                jj=0;
                                m++;
                        }
                }

                m=0;

                for(int i=0; i<m_numObjs; i++){
                        for(int j=0; j<m_numUsers; j++){
                                if(upamatN[j][i]==1){
                                        upamat_newN[m][m_numObjAttribs+m_numUserAttribs]=String.valueOf(1);
                                }
                                m++;
                        }
                }

                // converting from scott data to my data

                for(int i=0; i<m_numUsers*m_numObjs;i++){
                        for(int j=0; j<m_numUserAttribs+m_numObjAttribs+1;j++){
                                if(!upamat_newN[i][j].equals("0")){
                                        String st=upamat_newN[i][j];
                                        int index=0;
                                        if(!st.substring(0, st.length()-1).equals(""))
                                                index = Integer.parseInt(st.substring(0, st.length()-1));


                                        if(j<m_numUserAttribs){
                                                String gp = st.substring(st.length()-1);
                                                int num = hmapUU.get(gp);
                                                upamatMy[i][(index*group)+num-1]=1;
                                        }
                                        else if(j<m_numUserAttribs+m_numObjAttribs){
                                                String gp = st.substring(st.length()-1);
                                                int num = hmapOO.get(gp);
                                                upamatMy[i][((index+m_numUserAttribs)*group)+num-1]=1;
                                        }
                                        else{
                                                upamatMy[i][(m_numUserAttribs+m_numObjAttribs)*group]=1;
                                        }
                                }
                        }
                }

                //////////////////////////////////////////////////////////////////////////////////////////////////
            /*    System.out.println("User attribute relationship is:");
                printStrMat(usersmatN);
                
                

                System.out.println("Object attribute relationship is:");
                printStrMat(objsmatN); */
                System.out.print("OAR\n");   //here
                String finalpath1 = path+"OAR.txt";
                PrintStream output = new PrintStream(new File(finalpath1));
                for (int i=0; i<objsmatN.length; i++) {
                	//output.print("\n");
                	//System.out.print("resourceAttrib(object" + i+":");
                	//System.out.print("(");
                	
                	for (int j=0; j<objsmatN[i].length-1; j++) {
                		if (!objsmatN[i][j].equals("0")){
                			 String st = "rattrib" + j + "={"+ objsmatN[i][j] +"},";
                			 output.print(st);
                			 //output.print(" ");
                   			 System.out.print(st);	
                   			//System.out.print(usersmatN[i][j]);
                		} 
                		//output.print("\n");
                	}
                	//for last word of every line
                	if ((objsmatN[i][objsmatN[1].length-1]).equals("0")){
                		//System.out.print(" ")
                		System.out.print("\n");
                		}
            		else {
            			//String st = "rattrib" + (objsmatN[i].length-1)+"={"+ (objsmatN[i][objsmatN[1].length-1])+"}";
                	    //output.print(st);
            			//System.out.print(st);
                	    System.out.print("rattrib" + (objsmatN[i].length-1)+"={"+ (objsmatN[i][objsmatN[1].length-1])+"}");
                		output.print("rattrib" + (objsmatN[i].length-1)+"={"+ (objsmatN[i][objsmatN[1].length-1])+"}");
                		//System.out.println((usersmatN[i][usersmatN[1].length-1])+")");
                		//System.out.print(")");
                		System.out.print("\n");
                		//output.print("\n");
            		}
                	output.print("\n");
             
                	}
                output.close();
                System.out.println();

                System.out.print("UAR\n"); //here
                String finalpath2 = path+"UAR.txt";
                PrintStream output2 = new PrintStream(new File(finalpath2));
                for (int i=0; i<usersmatN.length; i++) {
                	//System.out.print("userAttrib(user" + i);
                	//System.out.print("(");
                	for (int j=0; j<usersmatN[i].length-1; j++) {
                		if (!usersmatN[i][j].equals("0")){
                				System.out.print("uattrib" + j + "={"+ usersmatN[i][j]+"},");	
                				output2.print("uattrib" + j + "={"+ usersmatN[i][j]+"},");
                   			//System.out.print(usersmatN[i][j]);
                		}        			      			
                	}
                	if ((usersmatN[i][usersmatN[1].length-1]).equals("0")){
            			//System.out.print(" ");
                		System.out.print("\n");
                	}
            		else {
            			System.out.print("uattrib" + (usersmatN[i].length-1)+"={"+ (usersmatN[i][usersmatN[1].length-1])+"}");
                		//System.out.println((usersmatN[i][usersmatN[1].length-1])+")");
                		//System.out.print(")");
                		System.out.print("\n");
                		output2.print("uattrib" + (usersmatN[i].length-1)+"={"+ (usersmatN[i][usersmatN[1].length-1])+"}");
            		}
                	output2.print("\n");
                	}

                output2.close();
                System.out.println();
                
                System.out.print("UOP\n");  //here
                System.out.println("User Object permission matrix is:");
                printIntMat(upamatN);
                printIntMatToFile_uop(upamatN);
                
                

              /*  System.out.println("User Object permission matrix in Proper Format is:");
                printStrMat(upamat_newN);

                System.out.println("User Object permission matrix Converted to 0's and 1's is:");
                printIntMat(upamatMy); */

             /*   System.out.println("Policy Rules are:");
                for(int i=0; i<rulesN.length; i++) {
                        System.out.println("ABAC " + i + ":" + rulesN[i][0] + ":" +rulesN[i][1]);
                }*/
              
                System.out.print("Policy\n");   //here
                String finalpath3 = path+"policy.txt";
                PrintStream output3 = new PrintStream(new File(finalpath3));
                for(int h=0; h<rulesN.length; h++){
            		//System.out.println( " " + rules[h][0]);
                	//rule( uattrib1 in {P1}; rattrib10 in {G10};{modify}; )
                	//System.out.print("(");
            		for(int f=0; f<rulesN[h][0].size()-1; f++){
            			String st = (String) rulesN[h][0].elementAt(f);
            			st = st.substring(0, st.length()-1);
            		System.out.print("uattrib" + st+ "={" + rulesN[h][0].elementAt(f) +"}," );
        			output3.print("uattrib" + st+ "={" + rulesN[h][0].elementAt(f) +"}," );

            		}
            		String ut = (String) rulesN[h][0].elementAt(rulesN[h][0].size()-1);
        			ut = ut.substring(0, ut.length()-1);
            		System.out.print("uattrib" + ut + "={" + rulesN[h][0].elementAt(rulesN[h][0].size()-1) +"},");
            		output3.print("uattrib" + ut + "={" + rulesN[h][0].elementAt(rulesN[h][0].size()-1) +"},");
            		
            		for(int g=0; g<rulesN[h][1].size()-1; g++){
            			String rt = (String) rulesN[h][1].elementAt(g);
            			rt = rt.substring(0, rt.length()-1);
                		System.out.print("rattrib" + rt + "={" + rulesN[h][1].elementAt(g) +"},");
                		output3.print("rattrib" + rt + "={" + rulesN[h][1].elementAt(g) +"},");
                		
                		}
            		String vt = (String) rulesN[h][1].elementAt(rulesN[h][1].size()-1);
        			vt = vt.substring(0, vt.length()-1);
            		System.out.print("rattrib" + vt + "={"  + rulesN[h][1].elementAt(rulesN[h][1].size()-1) +"}\n");
            		output3.print("rattrib" + vt + "={"  + rulesN[h][1].elementAt(rulesN[h][1].size()-1) +"}\n");
            		//System.out.println("{modify}; )");
            		
            	}
        
        /*        
                for(int h=0; h<rulesN.length; h++){
            		//System.out.println( " " + rules[h][0]);
                	//rule( uattrib1 in {P1}; rattrib10 in {G10};{modify}; )
                	System.out.print("rule(");
            		for(int f=0; f<rulesN[h][0].size()-1; f++){
            			String st = (String) rulesN[h][0].elementAt(f);
            			st = st.substring(0, st.length()-1);
            		System.out.print(" uattrib" + st+ " = {" + rulesN[h][0].elementAt(f) +"}," );
            		}
            		String ut = (String) rulesN[h][0].elementAt(rulesN[h][0].size()-1);
        			ut = ut.substring(0, ut.length()-1);
            		System.out.print(" uattrib" + ut + " = {" + rulesN[h][0].elementAt(rulesN[h][0].size()-1) +"};");
            		
            		for(int g=0; g<rulesN[h][1].size()-1; g++){
            			String rt = (String) rulesN[h][1].elementAt(g);
            			rt = rt.substring(0, rt.length()-1);
                		System.out.print(" rattrib" + rt + " = {" + rulesN[h][1].elementAt(g) +"},");
                		}
            		String vt = (String) rulesN[h][1].elementAt(rulesN[h][1].size()-1);
        			vt = vt.substring(0, vt.length()-1);
            		System.out.print(" rattrib" + vt + " = {" + rulesN[h][1].elementAt(rulesN[h][1].size()-1) +"};");
            		System.out.println("{modify}; )");
            		
            	}
          */      
                
                
                
                
                
                // writing to file
                //printIntMatToFile(upamatMy);
                
                
        }


        private static void printIntMat(int[][] mat) {
                for (int i=0; i<mat.length; i++) {
                        for (int j=0; j<mat[i].length; j++) {
                                System.out.print(mat[i][j] + " ");
                        }
                        System.out.println();
                }
                System.out.println();
        }


        private static void printStrMat(String[][] mat) {
                for (int i=0; i<mat.length; i++) {
                        for (int j=0; j<mat[i].length; j++) {
                                System.out.print(mat[i][j] + " ");
                        }
                        System.out.println();
                }
                System.out.println();
        }

        private static void printIntMatToFile(int[][] mat) throws FileNotFoundException {

                String finalpath = path+"outputABAC_MAC.txt";

                PrintStream output = new PrintStream(new File(finalpath));
                output.print(group);
                output.print("\n");
                output.print(m_numUserAttribs);
                output.print("\n");
                output.print(m_numObjAttribs);
                output.print("\n");
                for (int i=0; i<mat.length; i++) {
                        for (int j=0; j<mat[i].length; j++) {
                                if(j<mat[i].length-1)
                                        output.print(mat[i][j] + " ");
                                else
                                        output.print(mat[i][j]);
                        }
                        //System.out.println();
                        output.print("\n");
                }
                System.out.println();
                output.close();
                       }
        
        private static void printIntMatToFile_uop(int[][] mat) throws FileNotFoundException {

            String finalpath = path+"upa.txt";

            PrintStream output = new PrintStream(new File(finalpath));
            //output.print(group);
            //output.print("\n");
            //output.print(m_numUserAttribs);
            //output.print("\n");
            //output.print(m_numObjAttribs);
            //output.print("\n");
            for (int i=0; i<mat.length; i++) {
                    for (int j=0; j<mat[i].length; j++) {
                            if(j<mat[i].length-1)
                                    output.print(mat[i][j] + " ");
                            else
                                    output.print(mat[i][j]);
                    }
                    //System.out.println();
                    output.print("\n");
            }
            System.out.println();
            output.close();
                   }
}
