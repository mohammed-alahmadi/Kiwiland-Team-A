/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.tools;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import nz.ac.aut.ense701.gameModel.Island;
import org.junit.Test;

/**
 *
 * @author Rudster
 */
public class OccupantLoaderTest {
    
    @Test
    public void OccupantLoaderFunctionalityTest(){
        Island island;
        try{
            File occupantFile = new File("Occupants.xml");
            SAXParserFactory occupantFactory = SAXParserFactory.newInstance();
            SAXParser occupantSAXParser = occupantFactory.newSAXParser();
            OccupantLoader occupantLoader = new OccupantLoader();
            occupantSAXParser.parse(occupantFile, occupantLoader);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
