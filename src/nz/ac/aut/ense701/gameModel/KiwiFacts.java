package nz.ac.aut.ense701.gameModel;

import java.util.Random;

public class KiwiFacts {

    private static final String[] KIWI_FACTS = {
        "There are about 68,000 kiwi left in all of New Zealand. We are losing 2% of our unmanaged kiwi every year –that's around 20 per week.",
        "Kiwi are mostly nocturnal. They are most commonly forest dwellers, making daytime dens and nests in burrows, hollow logs or under dense vegetation.",
        "Kiwi are the only bird to have nostrils at the end of their very long bill. Their nostrils are used to probe in the ground, sniffing out invertebrates to eat, along with some fallen fruit.",
        "They also have one of the largest egg-to-body weight ratios of any bird. The egg averages 15% of the female's body weight (compared to 2% for the ostrich).",
        "Females are larger than males (up to 3.3 kg and 45 cm). Kiwi are long-lived, and depending on the species live for between 25 and 50 years."
    };
    
    private final Random random = new Random(System.currentTimeMillis());
    
    public String getFact() {
        return KIWI_FACTS[random.nextInt(KIWI_FACTS.length)];
    }

}
