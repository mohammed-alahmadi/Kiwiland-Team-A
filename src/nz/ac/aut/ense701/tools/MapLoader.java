/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.tools;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Rudster
 */
public class MapLoader extends  DefaultHandler {
    
    OccupantLoader occupantsLoader;
    
    ArrayList<OccupantObject> occupants = new ArrayList<OccupantObject>();
    OccupantObject tempOccupant;
    String terrain;
    
    boolean bTerrain = false;
    boolean bItemID = false;
    boolean bXPos = false;
    boolean bYPos = false;
    boolean bSize = false;
    
    private int mapSize;
    
    public MapLoader(OccupantLoader occupants){
        
        this.occupantsLoader = occupants;
    }

    public ArrayList<OccupantObject> getOccupants() {
        return occupants;
    }
    
    public int getMapSize(){
        return mapSize;
    }
    
    public int getOccupantSize(){
        return occupants.size();
    }
    
    public String getTerrain(){
        return terrain;
    }
    
    public int getXPos(int itemID){
        for(OccupantObject object: occupants){
            if(object.getItemID() == itemID){
                return object.getxPos();
            }
        }
        return -1;
    }
    
    public int getYPos(int itemID){
        for(OccupantObject object: occupants){
            if(object.getItemID() == itemID){
                return object.getyPos();
            }
        }
        return -1;
    }
    
    @Override
    public void startElement(String uri, String localName,
        String qName, Attributes attributes){
        
        //System.out.println(qName);
        
        if(qName.equalsIgnoreCase("Terrain")){
            bTerrain = true;
        }else if(qName.equalsIgnoreCase("Occupant")){
            tempOccupant = new OccupantObject();
        }else if(qName.equalsIgnoreCase("itemID")){
            bItemID = true;
        }else if(qName.equalsIgnoreCase("xPos")){
            bXPos = true;
        }else if(qName.equalsIgnoreCase("yPos")){
            bYPos = true;
        }else if(qName.equalsIgnoreCase("mapSize")){
            System.err.println(qName);
            bSize = true;
        }
        
        
    }
    
    @Override
    public void endElement(String uri, 
        String localName, String qName) throws SAXException {
        System.out.println(qName + " Ended");
        if(qName.equalsIgnoreCase("Occupant")){
            this.occupants.add(tempOccupant);
        }
        
    }
        
    @Override
    public void characters(char ch[], int start, int length)throws SAXException{
            if(bTerrain){
                this.terrain = new String(ch, start, length);
                bTerrain = false;
            }else if(bXPos){
                this.tempOccupant.setxPos(Integer.parseInt(new String(ch, start, length)));
                bXPos = false;
            }else if(bYPos){
                this.tempOccupant.setyPos(Integer.parseInt(new String(ch, start, length)));
                bYPos = false;
            }else if(bSize){
                //System.err.println(1 + new String(ch, start, length) + 1);
                mapSize = Integer.parseInt(new String(ch, start, length));
                bSize = false;
            }else if(bItemID){
                this.tempOccupant.setItemID(Integer.parseInt(new String(ch, start, length)));
                bItemID = false;
                System.err.println(this.tempOccupant.itemID + " ArraySize " + this.occupants.size());
            }
        
    }
    
    public class OccupantObject{
        
        private int itemID;
        private int xPos;
        private int yPos;
        

        public int getItemID() {
            return itemID;
        }

        public void setItemID(int itemID) {
            this.itemID = itemID;
        }

        public int getxPos() {
            return xPos;
        }

        public void setxPos(int xPos) {
            this.xPos = xPos;
        }

        public int getyPos() {
            return yPos;
        }

        public void setyPos(int yPos) {
            this.yPos = yPos;
        }

        
        

        
        
        
    }
    
}
