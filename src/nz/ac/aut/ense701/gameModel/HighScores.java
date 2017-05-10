package nz.ac.aut.ense701.gameModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class HighScores {

    private static final int MAXIMUM_SCORES = 10;

    private List<Score> highScores;

    public HighScores() {
        Properties properties = new Properties();
        InputStream inputStream = null;
        highScores = new ArrayList<Score>();
        try {
            inputStream = new FileInputStream("highscores.properties");
            // load a properties file
            properties.load(inputStream);
            for (String name : properties.stringPropertyNames()) {
                highScores.add(new Score(name,
                        Integer.parseInt((String) properties.get(name))));
            }
            Collections.sort(highScores);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public List<Score> getHighScores() {
        return highScores;
    }

    public void saveHighScores() {
        Properties properties = new Properties();
	OutputStream outputStream = null;

	try {

		outputStream = new FileOutputStream("highscores.properties");

		for(Score score : highScores) {
                    properties.setProperty(score.getName(), score.getScore()+"");
                }

		// save properties to project root folder
		properties.store(outputStream, null);

	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

    }

    public void addScore(String name, int score) {
        highScores.add(new Score(name, score));
        Collections.sort(highScores);
        while (highScores.size() > MAXIMUM_SCORES) {
            highScores.remove(highScores.size() - 1);
        }
    }

    public class Score implements Comparable<Score> {

        private String name;
        private int score;

        private Score(String name, int score) {
            this.name = name;
            this.score = score;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the score
         */
        public int getScore() {
            return score;
        }

        /**
         * @param score the score to set
         */
        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public int compareTo(Score o) {
            return o.score - score;
        }

    }

}
