package SpeechAct.src;
import java.io.*;
import java.util.*;


public class Feature_Vector {
	
	public static HashMap<String,String> dict=new HashMap<String,String>();
	public static HashMap<String,Integer> list=new HashMap<String,Integer>();
	public static HashMap<String, String> speechActsMap = new HashMap<String, String>();


	public static void features()
	{
		int n=1;
		Global.file_append("/home/pooja/btpdata_new/round2_train_13april/FeatureVectorround2.arff", "@relation Features");
		try
        {
            FileInputStream fis = new FileInputStream("/home/pooja/btpdata_new/round2_train_13april/featuesround2.txt"); 
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
            String line;
            while((line = br.readLine())!=null)
            {
            	line=line.substring(0,line.indexOf(":")).trim();
            	//System.out.println(line);
				if (! dict.containsKey(line))
				{
					dict.put(line, "");
					System.out.println(line);
					Global.file_append("/home/pooja/btpdata_new/round2_train_13april/FeatureVectorround2.arff", "@ATTRIBUTE word"+n+"  NUMERIC");
				    n++;
				}
            	
            }
            
            fis.close();
        }catch(IOException f){}    
	}
	
	
	public static void print_map(HashMap<String, String> map)
	{
		for(Map.Entry<String, String> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + " : "+ entry.getValue());
		}
	}
	
	
	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		features();
		
		//load speech acts codes into the map for ease of encoding them into the Featre_Vector.arff
		speechActsMap = map.initialize_map();
		//print_map(speechActsMap);
		//Global.file_append("/home/pooja/btpdata_new/FinalFeature_Vector2.arff", "@ATTRIBUTE class {A,C,P,N,Q,O,G,I,AQ,Y,L,AP,J,F,AI,AE,AF,V,AG,AB}\n");
		Global.file_append("/home/pooja/btpdata_new/round2_train_13april/FeatureVectorround2.arff", "@ATTRIBUTE class {A,C,G,N,Q,I,AP,AQ,L,F,V}\n");
		Global.file_append("/home/pooja/btpdata_new/round2_train_13april/FeatureVectorround2.arff", "@Data");
		    	//System.out.println(file);
		        //System.out.println(file.getName());
		    	String row="";
		        try
		        {
		            FileInputStream fis = new FileInputStream("/home/pooja/btpdata_new/round2_train_13april/round2finalcorpusannotated.txt");
		            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
		            String line;
		            int count =0;
		            while((line = br.readLine())!=null)
		            {
		            	String words[]=line.toLowerCase().trim().split(" ");
		            	System.out.println(line);
		            	list.clear();
		            	row="";
		            	count++;
		            	System.out.println(count);
		            	
		            	for(int i=0;i<words.length;i++)
		            	{
		            			list.put(words[i], 1);
		            	}
		            	System.out.println(words[words.length -1]);
		            	String tag = words[words.length - 1].split("\\_\\[")[1].replace("]", "").trim();
		            	System.out.println("tag : " + tag);
		            	
		            	Map<String, String> treeMap = new TreeMap<String, String>(dict);
						
						for (Map.Entry<String, String> entry : treeMap.entrySet())
						{
						    String x=entry.getKey();
						    if(list.containsKey(x))
						    {
						    	row+="1,";
						    }
						    else
						    {
						    	row+="0,";
						    }
						}
						System.out.println(tag + " : "+speechActsMap.get(tag));
						row += speechActsMap.get(tag);
						Global.file_append("/home/pooja/btpdata_new/round2_train_13april/FeatureVectorround2.arff", row);
		            }
		            fis.close();
		            System.out.println(dict.size());
		        }catch(IOException f){} 
		}
}