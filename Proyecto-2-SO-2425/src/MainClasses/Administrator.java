/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClasses;

import EDD.List;
import EDD.Queue;
import GUIClasses.ControlMainUI;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Moises Liota
 */
public class Administrator extends Thread{
    private IA ia;
    private final Semaphore mutex;
    private final TelevisionShow starWars;
    private final TelevisionShow starTrek;
    private int numRound = 0;

    public Administrator(IA ia, Semaphore mutex, List yellowCards1, List greenCards1, List redCards1,
            List yellowCards2, List greenCards2, List redCards2) {

        this.ia = ia;
        this.mutex = mutex;
        this.starWars = new TelevisionShow("StarWars", "/GUI/Assets/StarWars",
                redCards1, yellowCards1, greenCards1);
        this.starTrek = new TelevisionShow("StarTrek", "/GUI/Assets/StarTrek",
                redCards2, yellowCards2, greenCards2);
    }

    public void startSimulation() {
        ControlMainUI.getHome().setVisible(true);

        for (int i = 0; i < 20; i++) {
            getStarWars().createCharacter();
            getStarTrek().createCharacter();
        }

        ControlMainUI.getHome().getTvPanelUI1().updateUIQueue(getStarWars().getQueue1(),
                getStarWars().getQueue2(),
                getStarWars().getQueue3(),
                getStarWars().getQueue4()
        );

        ControlMainUI.getHome().getTvPanelUI2().updateUIQueue(getStarTrek().getQueue1(),
                getStarTrek().getQueue2(),
                getStarTrek().getQueue3(),
                getStarTrek().getQueue4()
        );

        ControlMainUI.getHome().setVisible(true);

        try {
            mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.start();
        this.getIa().start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                int battleDuration = ControlMainUI.getHome().getBattleDuration().getValue();
                ia.setTime(battleDuration);

                updateReinforcementQueue(this.starWars);
                updateReinforcementQueue(this.starTrek);

                if (numRound == 2) {
                    tryCreateCharacters();
                    numRound = 0;
                }

                CharacterTv starWarsFighter = chooseFighters(this.getStarWars());
                CharacterTv starTrekFighter = chooseFighters(this.getStarTrek());

                //------------------
                //TODO: Pasarle los fighters a la IA
                // Aca 0j0
                //------------------
                this.getIa().setRegularShowFighter(starWarsFighter);
                this.getIa().setStarTrekFighter(starTrekFighter);

                updateUIqueue();
                mutex.release();
                Thread.sleep(100);
                mutex.acquire();

                this.numRound += 1;
                
                risePriorities(this.getStarWars());
                risePriorities(this.getStarTrek());

                updateUIqueue();

            } catch (InterruptedException ex) {
                Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void risePriorities(TelevisionShow tvShow) {
        riseQueue(tvShow.getQueue2(), tvShow.getQueue1());
        riseQueue(tvShow.getQueue3(), tvShow.getQueue2());
    }

    private void riseQueue(Queue currentLevel, Queue nextLevel) {
        int len = currentLevel.getLength();

        for (int i = 0; i < len; i++) {
            CharacterTv character = (CharacterTv) currentLevel.dequeue();
            character.setCounter(character.getCounter() + 1);

            if (character.getCounter() >= 8) {
                character.setCounter(0);
                nextLevel.enqueue(character);
            } else {
                currentLevel.enqueue(character);
            }
        }
    }

    private CharacterTv chooseFighters(TelevisionShow tvShow) {
        if (tvShow.getQueue1().isEmpty()) {
            tvShow.updateQueue1();
            this.updateUIqueue();
        }
        CharacterTv fighter = (CharacterTv) tvShow.getQueue1().dequeue();
        fighter.setCounter(0);
        return fighter;
    }

    public void updateUIqueue() {
        ControlMainUI.updateUIQueue("StarWras",
                this.getStarWars().getQueue1(),
                this.getStarWars().getQueue2(),
                this.getStarWars().getQueue3(),
                this.getStarWars().getQueue4());
        ControlMainUI.updateUIQueue("StarTrek",
                this.getStarTrek().getQueue1(),
                this.getStarTrek().getQueue2(),
                this.getStarTrek().getQueue3(),
                this.getStarTrek().getQueue4());
    }

    private void updateReinforcementQueue(TelevisionShow tvShow) {
        if (!(tvShow.getQueue4().isEmpty())) {
            double randomNum = Math.random();

            if (randomNum <= 0.4) {
                CharacterTv character =(CharacterTv) tvShow.getQueue4().dequeue();
                character.setCounter(0);
                tvShow.getQueue1().enqueue(character);
            }else {
            CharacterTv character = (CharacterTv) tvShow.getQueue4().dequeue();
            tvShow.getQueue4().enqueue(character);
            }
        }
    }

    private void tryCreateCharacters() {
        double randomNum = Math.random();

        if (randomNum <= 0.8) {
            getStarWars().createCharacter();
            getStarTrek().createCharacter();
        }
    }

    /**
     * @return the starWars
     */
    public TelevisionShow getStarWars() {
        return starWars;
    }

    /**
     * @return the starTrek
     */
    public TelevisionShow getStarTrek() {
        return starTrek;
    }

    /**
     * @return the ia
     */
    public IA getIa() {
        return ia;
    }

    /**
     * @param ia the ia to set
     */
    public void setIa(IA ia) {
        this.ia = ia;
    }
    
}
