/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileFunctions;

import EDD.List;
import MainClasses.CharacterTv;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Moises Liota
 */
public class FileFunction {
     // Definimos arrays para cada categoría
    private List yellowStarWars = new List();
    private List greenStarWars = new List();
    private List redStarWars = new List();

    private List yellowStarTrek = new List();
    private List greenStarTrek = new List();
    private List redStarTrek = new List();

    public void read(File file) {
        String line;

        // Rellenar los arrays con las instancias de Character
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String currentShow = null;

            while ((line = br.readLine()) != null) {
                if (!(line.isEmpty())) {
                    if (line.startsWith("[")) {
                        currentShow = line.substring(1, line.length() - 1);

                    } else {
                        String[] parts = line.split(",");

                        CharacterTv character = new CharacterTv(
                                "",
                                parts[0],
                                Integer.parseInt(parts[1]),
                                Integer.parseInt(parts[2]),
                                Integer.parseInt(parts[3]),
                                parts[4],
                                parts[5].split(";")[0]
                        );
 
                        // Clasificar el personaje en la linkedList correspondiente
                        if (line.contains("red.png") && "StarWars".equals(currentShow)) {
                            character.setPriorityLevel(1);
                            this.getRedStarWars().addEnd(character);
                        } else if (line.contains("yellow.png") && "StarWars".equals(currentShow)) {
                            character.setPriorityLevel(2);
                            this.getYellowStarWars().addEnd(character);
                        } else if (line.contains("green.png") && "StarWars".equals(currentShow)) {
                            character.setPriorityLevel(3);
                            this.getGreenStarWars().addEnd(character);
                        } else if (line.contains("red.png") && "StarTrek".equals(currentShow)) {
                            character.setPriorityLevel(1);
                            this.getRedStarTrek().addEnd(character);
                        } else if (line.contains("yellow.png") && "StarTrek".equals(currentShow)) {
                            character.setPriorityLevel(2);
                            this.getYellowStarTrek().addEnd(character);
                        } else if (line.contains("green.png") && "StarTrek".equals(currentShow)) {
                            character.setPriorityLevel(3);
                            this.getGreenStarTrek().addEnd(character);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the yellowStarWars
     */
    public List getYellowStarWars() {
        return yellowStarWars;
    }

    /**
     * @param yellowStarWars the yellowStarWars to set
     */
    public void setYellowRegularShow(List yellowStarWars) {
        this.yellowStarWars = yellowStarWars;
    }

    /**
     * @return the greenStarWars
     */
    public List getGreenStarWars() {
        return greenStarWars;
    }

    /**
     * @param greenStarWars the greenStarWars to set
     */
    public void setGreenStarWars(List greenStarWars) {
        this.greenStarWars = greenStarWars;
    }

    /**
     * @return the redStarWars
     */
    public List getRedStarWars() {
        return redStarWars;
    }

    /**
     * @param redStarWars the redStarWars to set
     */
    public void setRedStarWars(List redStarWars) {
        this.redStarWars = redStarWars;
    }

    /**
     * @return the yellowStarTrek
     */
    public List getYellowStarTrek() {
        return yellowStarTrek;
    }

    /**
     * @param yellowStarTrek the yellowStarTrek to set
     */
    public void setYellowAvatar(List yellowStarTrek) {
        this.yellowStarTrek = yellowStarTrek;
    }

    /**
     * @return the greenStarTrek
     */
    public List getGreenStarTrek() {
        return greenStarTrek;
    }

    /**
     * @param greenStarTrek the greenStarTrek to set
     */
    public void setGreenStarTrek(List greenStarTrek) {
        this.greenStarTrek = greenStarTrek;
    }

    /**
     * @return the redStarTrek
     */
    public List getRedStarTrek() {
        return redStarTrek;
    }

    /**
     * @param redStarTrek the redStarTrek to set
     */
    public void setRedStarTrek(List redStarTrek) {
        this.redStarTrek = redStarTrek;
    }
}
