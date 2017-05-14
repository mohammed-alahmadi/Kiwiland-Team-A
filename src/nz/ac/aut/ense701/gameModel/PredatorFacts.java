package nz.ac.aut.ense701.gameModel;


import java.util.Random;
            
public class PredatorFacts 
{

    private static final String[] PREDATOR_FACTS = {
        "possums have superpowers against snakes",
        "The mouth of an opossum holds an impressive 50 teeth.",
        
    };
    
    private final Random random = new Random(System.currentTimeMillis());
    
    public String getFactPredator() {
        return PREDATOR_FACTS[random.nextInt(PREDATOR_FACTS.length)];
    }

}
