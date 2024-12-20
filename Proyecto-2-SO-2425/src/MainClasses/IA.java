/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClasses;

import Functions.AudioManager;
import Functions.ImageUtils;
import GUIClasses.ControlMainUI;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

import proyecto.pkg2.so.pkg2425.App;

/**
 *
 * @author Moises Liota
 */
public class IA extends Thread{
    private Administrator administrator;
    private CharacterTv starWarsFighter;
    private CharacterTv starTrekFighter;
    private int victoriesStarWars = 0;
    private int victoriesStarTrek = 0;

    private final Semaphore mutex;

    private long time;
    private int round;

    public IA() {
        this.administrator = App.getApp().getAdmin();
        this.mutex = App.getApp().getMutex();
        this.time = App.getApp().getBattleDuration();
        this.round = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.mutex.acquire();

                this.round += 1;

                ControlMainUI.getHome().getWinnerLabelID().setText("");
                ControlMainUI.getHome().getIaStatusLabel().setText("DETERMINANDO RESULTADO...");
                updateCardsUI(getStarWarsFighter(), getStarTrekFighter());

                ControlMainUI.getHome().getRoundLabel().setText("# RONDA: " + String.valueOf(round));

                Thread.sleep((long) (this.getTime() * 1000 * 0.7));

                double aux = Math.random();
                AudioManager audioManager = new AudioManager(); 

                if (aux <= 0.4) {
                    ControlMainUI.getHome().getIaStatusLabel().setText("¡HABRÁ UN GANADOR!");
                    CharacterTv winner = getWinnerCharacter(this.starWarsFighter, this.starTrekFighter);
                    ControlMainUI.getHome().getWinnerLabelID().setText(winner.getCharacterId());
                    audioManager.playSoundEffect("/GUI/Assets/victory.wav", 2.0f);
                    Thread.sleep((long) ((getTime() * 1000 * 0.3) * 0.6));

                } else if (aux > 0.40 && aux <= 0.67) {
                    ControlMainUI.getHome().getIaStatusLabel().setText("¡HABRÁ EMPATE!");
                    audioManager.playSoundEffect("/GUI/Assets/tie.wav", 2.0f);
                    Thread.sleep((long) ((getTime() * 1000 * 0.3) * 0.6));
                    

                    this.getAdministrator().getStarWars().getQueue1().enqueue(this.starWarsFighter);
                    this.getAdministrator().getStarTrek().getQueue1().enqueue(this.starTrekFighter);
                } else {
                    ControlMainUI.getHome().getIaStatusLabel().setText("COMBATE CANCELADO");
                    audioManager.playSoundEffect("/GUI/Assets/fail.wav", 2.0f);
                    Thread.sleep((long) ((getTime() * 1000 * 0.3) * 0.6));

                    this.getAdministrator().getStarWars().getQueue4().enqueue(this.starWarsFighter);
                    this.getAdministrator().getStarTrek().getQueue4().enqueue(this.starTrekFighter);
                }

                clearFightersUI();

                Thread.sleep((long) ((getTime() * 1000 * 0.3) * 0.4));
                this.mutex.release();
                Thread.sleep(100);

            } catch (InterruptedException ex) {
                Logger.getLogger(IA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void clearFightersUI() {
        ControlMainUI.getHome().getIaStatusLabel().setText("ESPERANDO...");
        ControlMainUI.getHome().getWinnerLabelID().setText("");
        ControlMainUI.getHome().getStarWarsFighter().clearFightersLabels();
        ControlMainUI.getHome().getStarTrekFighter().clearFightersLabels();
    }

    private CharacterTv getWinnerCharacter(CharacterTv starWarsFighter, CharacterTv starTrekFighter) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + getTime() * 1000; // Convierte tiempo de combate a milisegundos
        boolean combatEnd = false;

        // Determina quién ataca primero basado en la velocidad inicialmente
        boolean isStarWarsTurn = starWarsFighter.getSpeedVelocity() >= starTrekFighter.getSpeedVelocity();

        while (System.currentTimeMillis() < endTime && !combatEnd) {
            int damage;
            if (isStarWarsTurn) {
                // Star Wars ataca
                ControlMainUI.getHome().getStarWarsFighter().getStatusLabel().setText("ATACANDO");
                ControlMainUI.getHome().getStarTrekFighter().getStatusLabel().setText("DEFENDIENDO");
                damage = calculateDamage(starWarsFighter, starTrekFighter);
                starTrekFighter.takeDamage(damage);
                ControlMainUI.getHome().getStarTrekFighter().getHPLabel().setText(String.valueOf(starTrekFighter.getHitPoints()));
                if (starTrekFighter.getHitPoints() <= 0) combatEnd = true;
            } else {
                // Star Trek ataca
                ControlMainUI.getHome().getStarTrekFighter().getStatusLabel().setText("ATACANDO");
                ControlMainUI.getHome().getStarWarsFighter().getStatusLabel().setText("DEFENDIENDO");
                damage = calculateDamage(starTrekFighter, starWarsFighter);
                starWarsFighter.takeDamage(damage);
                ControlMainUI.getHome().getStarWarsFighter().getHPLabel().setText(String.valueOf(starWarsFighter.getHitPoints()));
                if (starWarsFighter.getHitPoints() <= 0) combatEnd = true;
            }

            // Alterna el turno para el próximo ciclo
            isStarWarsTurn = !isStarWarsTurn;
            ControlMainUI.getHome().getStarTrekFighter().getHPLabel().setText(String.valueOf(starTrekFighter.getHitPoints()));
            ControlMainUI.getHome().getStarWarsFighter().getHPLabel().setText(String.valueOf(starWarsFighter.getHitPoints()));

            // Simula una pausa por ronda
            try {
                Thread.sleep(1000); // Ajusta según necesidad para permitir actualización de UI
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (combatEnd) break; // Salir del bucle si el combate terminó.
        }
        
        if (!combatEnd) {
        // Aquí se decide el ganador basado en quién tiene más HP.
            if (starWarsFighter.getHitPoints() > starTrekFighter.getHitPoints()) {
                this.victoriesStarWars++;
                ControlMainUI.getHome().getTvPanelUI1().getVictoriesLabel().setText(String.valueOf(this.victoriesStarWars));
                return starWarsFighter;
            } else if (starWarsFighter.getHitPoints() < starTrekFighter.getHitPoints()) {
                this.victoriesStarTrek++;
                ControlMainUI.getHome().getTvPanelUI2().getVictoriesLabel().setText(String.valueOf(this.victoriesStarTrek));
                return starTrekFighter;
            } else {
                // En caso de empate por HP
                return starTrekFighter;
            }
        }

        // Determinar ganador basado en HP restante.
        if (starWarsFighter.getHitPoints() > 0) {
            this.victoriesStarWars++;
            ControlMainUI.getHome().getTvPanelUI1().getVictoriesLabel().setText(String.valueOf(this.victoriesStarWars));
            return starWarsFighter;
        } else if (starTrekFighter.getHitPoints() > 0) {
            this.victoriesStarTrek++;
            ControlMainUI.getHome().getTvPanelUI2().getVictoriesLabel().setText(String.valueOf(this.victoriesStarTrek));
            return starTrekFighter;
        } else {
            return null; // Manejo de empate
        }
    }


    private int calculateDamage(CharacterTv attacker, CharacterTv defender) {
        // Daño base con la lógica que el ataque no puede ser completo porque sino lo matariamos de one.
         int baseDamage = (attacker.getSpeedVelocity() + (attacker.getAgility() / 2)) / 2;

         // Inicializamos el daño
         int damage = baseDamage;

         switch (attacker.getHability()) {
             case "Ataque Crítico":
                 //INCREMENTE EL DAÑO BASE DE X1.5
                 damage *= 1.5;
                 break;
             case "Curación":
                 // RECUPERA EN VIDA LA MITAD DE LO QUE LO ATACARÍA 
                 int healAmount = baseDamage / 2;
                 attacker.heal(healAmount);
                 damage = (attacker.getSpeedVelocity() + (attacker.getAgility() / 2)) / 4;
                 break;
             case "Parálisis":
                 // Se le disminuye la agilidad al enemigo en un 50%
                 defender.setAgility(defender.getAgility() / 2);
                 break;
             case "Congelamiento":
                 // Se le disminuye la velocidad al enemigo en un 50%
                 defender.setSpeedVelocity(defender.getSpeedVelocity() / 2);
                 break;
             default:
                 // No special ability
                 break;
         }

         return damage;
     }

    private void updateCardsUI(CharacterTv starWarsCharacter, CharacterTv starTrekCharacterTv) {
        ImageUtils imageUtils = new ImageUtils();

        ImageIcon starWarsCardIA = imageUtils.loadScaledImage(
                starWarsCharacter.getUrlSource(), 150, 200
        );

        ImageIcon starTrekCardIA = imageUtils.loadScaledImage(
                starTrekCharacterTv.getUrlSource(), 150, 200
        );

        ControlMainUI.getHome().getStarWarsFighter().getFighterCard().setIcon(starWarsCardIA);
        ControlMainUI.getHome().getStarWarsFighter().getCharacterIDLabel().setText(starWarsCharacter.getCharacterId());
        ControlMainUI.getHome().getStarWarsFighter().getHPLabel().setText(String.valueOf(starWarsCharacter.getHitPoints()));

        ControlMainUI.getHome().getStarTrekFighter().getFighterCard().setIcon(starTrekCardIA);
        ControlMainUI.getHome().getStarTrekFighter().getCharacterIDLabel().setText(starTrekCharacterTv.getCharacterId());
        ControlMainUI.getHome().getStarTrekFighter().getHPLabel().setText(String.valueOf(starTrekCharacterTv.getHitPoints()));
    }

    /**
     * @return the starWarsFighter
     */
    public CharacterTv getStarWarsFighter() {
        return starWarsFighter;
    }

    /**
     * @param starWarsFighter the starWarsFighter to set
     */
    public void setRegularShowFighter(CharacterTv starWarsFighter) {
        this.starWarsFighter = starWarsFighter;
    }

    /**
     * @return the starTrekFighter
     */
    public CharacterTv getStarTrekFighter() {
        return starTrekFighter;
    }

    /**
     * @param starTrekFighter the starTrekFighter to set
     */
    public void setStarTrekFighter(CharacterTv starTrekFighter) {
        this.starTrekFighter = starTrekFighter;
    }

    /**
     * @return the administrator
     */
    public Administrator getAdministrator() {
        return administrator;
    }

    /**
     * @param administrator the administrator to set
     */
    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;
    }
    
}
