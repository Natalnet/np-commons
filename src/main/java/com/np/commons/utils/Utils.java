package com.np.commons.utils;

public class Utils 
{
	static private Utils utils = null;
	
	private Utils() 
	{
		
	}
	
	final static synchronized public Utils getInstance()
	{
		if (utils == null)
		{
			utils = new Utils();
		}
		
		return utils;
	}
	
	public String hashCode(String sIn) 
	{
		if (sIn == null) 
		{
			return null;
		}

		return sIn.hashCode() >= 0 ? 
				"p".concat(String.valueOf(sIn.hashCode())) : "n".concat(String.valueOf(sIn.hashCode()).substring(1));
	}
	
	public static void main(String [] args) throws Exception
	{
//		FileWriter writer = new FileWriter("/home/julio/Downloads/cases-rn.csv", true);
//		BufferedWriter bufferedWriter = new BufferedWriter(writer);
//		
//		String filePath = "/home/julio/Downloads/cases-brazil-states.csv";
//		File file = new File(filePath);
//		FileReader fReader = new FileReader(file);
//		BufferedReader bReader = new BufferedReader(fReader);
//		String line = null;
//		while ((line = bReader.readLine()) != null) 
//		{
//			if (line.contains(",RN,") || line.contains("epi_week,date,country,"))
//			{
//				bufferedWriter.write(line);
//				bufferedWriter.newLine();
//			}
//		}
//		
//		if (bReader != null) 
//		{
//			bReader.close();
//			fReader.close();
//			bufferedWriter.close();
//		}
		getInstance().list();
	}
	
	private void list()
	{
//		Collection<?> files = FileUtils.listFiles(
//				  new File("/var/np/repos/n601987301-dir"), 
//				  new RegexFileFilter("^(.*?)"), 
//				  DirectoryFileFilter.DIRECTORY
//				);
//		
//		System.out.println(files.toString().replace("/var/np/repos/n601987301-dir", ""));
	}
}
