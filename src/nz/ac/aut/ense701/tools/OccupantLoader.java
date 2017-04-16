/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.tools;

import java.util.ArrayList;
import nz.ac.aut.ense701.gameModel.Fauna;
import nz.ac.aut.ense701.gameModel.Food;
import nz.ac.aut.ense701.gameModel.Hazard;
import nz.ac.aut.ense701.gameModel.Kiwi;
import nz.ac.aut.ense701.gameModel.Occupant;
import nz.ac.aut.ense701.gameModel.Position;
import nz.ac.aut.ense701.gameModel.Predator;
import nz.ac.aut.ense701.gameModel.Tool;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Rudster
 */
public class OccupantLoader extends DefaultHandler {
    
    
    boolean bType = false;
    boolean bItemName = false;
    boolean bItemDescription = false;
    boolean bItemEnergy = false;
    boolean bItemWeight = false;
    boolean bItemSize = false;
    boolean bItemImpact = false;
    boolean bHazardName = false;
    boolean bHazardDescription = false;
    
    private ArrayList<LoaderItem> ItemList = new ArrayList<LoaderItem>();
    LoaderItem tempItem;
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
        
        if (qName.equalsIgnoreCase("item")){
            
            bType = true;
            
            tempItem = new LoaderItem(Integer.parseInt(attributes.getValue("itemID")));
            tempItem.setType(attributes.getValue("Type"));
            System.err.println(tempItem.getType());
            
        }else if(qName.equalsIgnoreCase("itemName") || qName.equalsIgnoreCase("animalName")){
            bItemName = true;
            //System.out.println(qName);
        }else if(qName.equalsIgnoreCase("itemDescription") || qName.equalsIgnoreCase("animalDescription")){
            bItemDescription = true;
        }else if(qName.equalsIgnoreCase("energy")){
            bItemEnergy = true;
        }else if(qName.equalsIgnoreCase("weight")){
            bItemWeight = true;
        }else if(qName.equalsIgnoreCase("size")){
            bItemSize = true;
        }else if(qName.equalsIgnoreCase("animal")){
            tempItem = new LoaderItem(Integer.parseInt(attributes.getValue("itemID")));
            tempItem.setType(attributes.getValue("Type"));
        }else if(qName.equalsIgnoreCase("Hazard")){
            //System.out.println("Hazard");
            tempItem = new LoaderItem(Integer.parseInt(attributes.getValue("itemID")));
            tempItem.setType("Hazard");
        }else if(qName.equalsIgnoreCase("Impact")){
            bItemImpact = true;
        }else if(qName.equalsIgnoreCase("hazardName")){
            bHazardName = true;
        }else if(qName.equalsIgnoreCase("hazardDescription")){
            bHazardDescription = true;
        }
    }

    public ArrayList<LoaderItem> getItemList() {
        return ItemList;
    }
    
    public LoaderItem getOccupantObject(int occupantID){
        
        for(LoaderItem object: this.ItemList){
            if(object.getItemID() == occupantID){
                return object;
            }
        }
        return null;
    }
    
    public Occupant loadItem(Position pos, int itemID){
        for(LoaderItem item: this.ItemList){
            System.err.println(item.itemName);
            if(item.getItemID() == itemID){
                if(item.getType().equalsIgnoreCase("Food")){
                    return new Food(pos, item.getItemName(), item.getItemDescription(), item.getItemWeight(),
                    item.getItemSize(), item.getItemEnergy());
                }else if(item.getType().equalsIgnoreCase("Trap")){
                    
                    return new Tool(pos, item.getItemName(), item.getItemDescription(), item.getItemWeight(), item.getItemSize());
                }else if(item.getType().equalsIgnoreCase("Hazard")){
                    return new Hazard(pos, item.getItemName(), item.getItemDescription(), item.getItemEnergy());
                }else if(item.getType().equalsIgnoreCase("Kiwi")){
                    return new Kiwi(pos, item.getItemName(), item.getItemDescription());
                }else if(item.getType().equalsIgnoreCase("Fauna")){
                    return new Fauna(pos, item.getItemName(), item.getItemDescription());
                }else if(item.getType().equalsIgnoreCase("Predator")){
                    return new Predator(pos, item.getItemName(), item.getItemDescription());
                }
            }
        }
        
        return null;
    }
    
    @Override
    public void characters(char ch[], int start, int length)throws SAXException{
//        if(bType){
//            tempItem.setType(new String(ch, start, length));
//        }else 
            
        if(bItemName){
            tempItem.setItemName(new String(ch, start, length));
            bItemName = false;
        }else if(bItemDescription){
            tempItem.setItemDescription(new String(ch, start, length));
            bItemDescription = false;
        }else if(bItemEnergy){
            tempItem.setItemEnergy(Integer.parseInt(new String(ch, start, length)));
            bItemEnergy = false;
        }else if(bItemWeight){
            tempItem.setItemWeight(Double.parseDouble(new String(ch, start, length)));
            bItemWeight = false;
        }else if(bItemSize){
            tempItem.setItemSize(Double.parseDouble(new String(ch, start, length)));
            bItemSize = false;
        }else if(bItemImpact){
            tempItem.setImpact(Double.parseDouble(new String(ch, start, length)));
            bItemImpact = false;
        }else if(bHazardName){
            tempItem.setItemName(new String(ch, start, length));
            bHazardName = false;
        }else if(bHazardDescription){
            tempItem.setItemDescription(new String(ch, start, length));
            bHazardDescription = false;
        }
    }
    
   @Override
   public void endElement(String uri, 
   String localName, String qName) throws SAXException {
       
       if(qName.equalsIgnoreCase("item") || qName.equalsIgnoreCase("animal") || 
               qName.equalsIgnoreCase("hazard")){
            this.ItemList.add(this.tempItem);
       }
   }
    
    
    
    
    public class LoaderItem{
        int itemID;

        String type = "";
        String itemName = "";
        String itemDescription = "";
        int itemEnergy = 0;
        double itemWeight = 0.0;
        double itemSize = 0.0;
        double impact = 0.0;
        
        public LoaderItem(int ID){
            itemID = ID;
        }
        
        public int getItemID() {
            return itemID;
        }

        public double getImpact() {
            return impact;
        }

        public void setImpact(double impact) {
            this.impact = impact;
        }

        public void setItemID(int itemID) {
            this.itemID = itemID;
        }

        public String getType() {
            return type;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemDescription() {
            return itemDescription;
        }

        public int getItemEnergy() {
            return itemEnergy;
        }

        public double getItemWeight() {
            return itemWeight;
        }

        public double getItemSize() {
            return itemSize;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public void setItemDescription(String itemDescription) {
            this.itemDescription = itemDescription;
        }

        public void setItemEnergy(int itemEnergy) {
            this.itemEnergy = itemEnergy;
        }

        public void setItemWeight(double itemWeight) {
            this.itemWeight = itemWeight;
        }

        public void setItemSize(double itemSize) {
            this.itemSize = itemSize;
        }
        
        
    }
}

