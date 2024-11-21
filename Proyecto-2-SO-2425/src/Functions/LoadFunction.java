/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Functions;

import FileFunctions.FileFunction;
import MainClasses.Administrator;
import MainClasses.IA;
import proyecto.pkg2.so.pkg2425.App;

/**
 *
 * @author Moises Liota
 */
public class LoadFunction {
    public final static String[] priority = {"Red", "Yellow", "Gree"};

    public static void loadParams() {
        App app = App.getInstance();
        FileFunction fileFunctions = new FileFunction();
        fileFunctions.read(App.getSelectedFile());

        app.setIa(new IA());

        Administrator admin = new Administrator(
                app.getIa(),
                app.getMutex(),
                fileFunctions.getRedStarWars(), fileFunctions.getYellowStarWars(), fileFunctions.getGreenStarWars(),
                fileFunctions.getRedStarTrek(), fileFunctions.getYellowStarTrek(), fileFunctions.getGreenStarTrek());

        app.setAdmin(admin);
        app.getAdmin().getIa().setAdministrator(admin);
        app.getAdmin().startSimulation();

    }
}
