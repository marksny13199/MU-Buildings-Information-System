package mu.buildings.information.system;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;  
import javax.swing.JOptionPane;

public class Core {
    SharedVariables share = new SharedVariables();
    
    public Core(){
        
    }
    
    public boolean create(String filename, String content){
        try{
            FileWriter writeFile = new FileWriter(filename,true); 
            PrintWriter myOutput = new PrintWriter(writeFile);
            myOutput.printf("%s" + "%n", content);
            myOutput.close(); 
	}
        catch(IOException ex){
            return false;
	}
        return true;
    }
    
    public String[] getData (String infofile, String buildingname){
	share.foundRecord = new String[2];
	
	try{
            FileInputStream Filename = new FileInputStream(infofile); 
            DataInputStream FileContent = new DataInputStream(Filename); 
		
            while((share.ThisLine = FileContent.readLine()) != null){ 
                share.data = share.ThisLine.split("\\" + share.delimeter); 
		
		if(share.data[0].compareTo(buildingname) == 0){
                    share.foundRecord[0] = share.data[0];
                    share.foundRecord[1] = share.data[1];
                    break;
		}
            }
            FileContent.close();
        }
        catch(IOException ex){
            JOptionPane.showMessageDialog(null,"There's an error searching the data");
        }
        catch(java.lang.ArrayIndexOutOfBoundsException ex){
            JOptionPane.showMessageDialog(null,"File not found");
        }
        return share.foundRecord;
    }
    public boolean Edit(String infofile, String modName, String modBInfo){
	try{
            FileInputStream Filename = new FileInputStream(infofile);
            DataInputStream FileContent = new DataInputStream(Filename); 
            
            while ((share.ThisLine = FileContent.readLine()) != null){
                share.data = share.ThisLine.split("\\" + share.delimeter);
                share.selectedRecord = share.ThisLine;
                
                if (share.data[0].compareTo(modName) != 0){
                    create("tempdata.txt", share.selectedRecord);
                }
                else{
                    create("tempdata.txt", modBInfo);
                }
            }
            
            FileContent.close();
            File OrigFile = new File(infofile);
            OrigFile.delete();
            File TempFile = new File ("tempdata.txt");
            TempFile.renameTo(new File(infofile));
            
	}
        catch(IOException ex){
            return false;
	}
        return true;
    }
    public boolean delete(String infofile, String buildingName){

        
	try{
            FileInputStream Filename = new FileInputStream(infofile);
            DataInputStream FileContent = new DataInputStream(Filename); 
            
            while ((share.ThisLine = FileContent.readLine()) != null){
                share.buildingInfo = share.ThisLine.split("\\" + share.delimeter);
                share.selectedRecord = share.ThisLine;
                

                if (share.buildingInfo[0].compareTo(buildingName) != 0){  
                    create("tempdata.txt", share.selectedRecord);
                }
            }
            
            FileContent.close();
            File OrigFile = new File(infofile);
            OrigFile.delete();
            File TempFile = new File ("tempdata.txt");
            TempFile.renameTo(new File(infofile));
           
        }catch(IOException ex){
           return false;
        }
        return true;
    }
}