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
public class MapLoaderTest {
    @Test
    public void MapLoaderFunctionalityTest(){
        Island island;
        try{
            File occupantFile = new File("Occupants.xml");
            SAXParserFactory occupantFactory = SAXParserFactory.newInstance();
            SAXParser occupantSAXParser = occupantFactory.newSAXParser();
            OccupantLoader occupantLoader = new OccupantLoader();
            occupantSAXParser.parse(occupantFile, occupantLoader);
            
            System.out.println("test");
            File mapFile = new File("Map.xml");
            SAXParserFactory mapFactory = SAXParserFactory.newInstance();
            SAXParser mapSAXParser = mapFactory.newSAXParser();
            MapLoader mapLoader = new MapLoader(occupantLoader);
            mapSAXParser.parse(mapFile, mapLoader);
            
            int mapSize = mapLoader.getMapSize();
            island = new Island(mapSize, mapSize);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
